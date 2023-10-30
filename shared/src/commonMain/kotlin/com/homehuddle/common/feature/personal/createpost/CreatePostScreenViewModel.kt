package com.homehuddle.common.feature.personal.createpost

import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.di.createPostScope
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.usecase.GetMeUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetLastTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetUserTripsFlowUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.CreateTripPostUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.GetTripPostUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.UpdateTripPostUseCase
import com.homehuddle.common.base.domain.utils.formatDate
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

internal class CreatePostScreenViewModel(
    private val tripPostId: String?,
    private val router: Router,
    private val getMeUseCase: GetMeUseCase,
    private val getTripUseCase: GetTripUseCase,
    private val getTripPostUseCase: GetTripPostUseCase,
    private val createTripPostUseCase: CreateTripPostUseCase,
    private val getUserTripsFlowUseCase: GetUserTripsFlowUseCase,
    private val getLastTripUseCase: GetLastTripUseCase,
    private val updateTripPostUseCase: UpdateTripPostUseCase
) : CoroutinesViewModel<CreatePostScreenState, CreatePostScreenIntent, CreatePostScreenSingleEvent>() {

    init {
        viewModelScope.launch {
            val user = getMeUseCase()
            val trips = getUserTripsFlowUseCase().firstOrNull().orEmpty()
            sendIntent(CreatePostScreenIntent.UpdateUser(user))
            sendIntent(CreatePostScreenIntent.UpdateTrips(trips))
            tripPostId.takeIf { !it.isNullOrEmpty() }?.let {
                val post = getTripPostUseCase(it)
                val trip = post?.let { getTripUseCase(it.tripId.orEmpty()) }
                sendIntent(CreatePostScreenIntent.Update(post, trip))
            } ?: run {
                val lastTrip = getLastTripUseCase()
                val post = TripPostModel.createEmpty()
                sendIntent(CreatePostScreenIntent.Update(post, lastTrip))
            }
        }
    }

    override fun initialState(): CreatePostScreenState = CreatePostScreenState(
        isCreateMode = tripPostId.isNullOrEmpty()
    )

    override fun reduce(
        intent: CreatePostScreenIntent,
        prevState: CreatePostScreenState
    ): CreatePostScreenState = when (intent) {
        is CreatePostScreenIntent.Update -> prevState.copy(
            name = TextFieldValue(intent.model?.name.orEmpty()),
            description = TextFieldValue(intent.model?.description.orEmpty()),
            trip = intent.trip,
            model = intent.model,
            bitmaps = if (prevState.isCreateMode) {
                mutableListOf()
            } else {
                mutableListOf() //todo
            }
        )
        is CreatePostScreenIntent.UpdateUser -> prevState.copy(
            userModel = intent.userModel
        )
        is CreatePostScreenIntent.UpdateTrips -> prevState.copy(
            trips = intent.trips
        )
        is CreatePostScreenIntent.OnChangeDescription -> prevState.copy(
            description = intent.text
        )
        is CreatePostScreenIntent.OnChangeName -> prevState.copy(
            name = intent.text
        )
        CreatePostScreenIntent.CloseBottomSheet -> prevState.copy(
            bottomSheet = null
        )
        CreatePostScreenIntent.OnFromDateClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectFromDate(
                timestamp = prevState.model?.timestampEnd
            )
        )
        CreatePostScreenIntent.OnToDateClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectToDate(
                timestamp = prevState.model?.timestampStart
            )
        )
        CreatePostScreenIntent.OnTripClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectTrip(
                trips = prevState.trips,
                selected = prevState.trip
            )
        )
        CreatePostScreenIntent.OnAddNewCountry -> prevState.copy(
            bottomSheet = BottomSheetType.SelectCountry
        )
        is CreatePostScreenIntent.OnChangeTrip -> prevState.copy(
            trip = intent.item,
            bottomSheet = null
        )
        is CreatePostScreenIntent.OnDeleteCountry -> prevState.copy(
            updateTs = getTimeMillis(),
            model = prevState.model?.copy(
                countries = prevState.model.countries.toMutableList()
                    .apply { this.remove(intent.item) }
            )
        )
        is CreatePostScreenIntent.OnSelectCountry -> prevState.copy(
            updateTs = getTimeMillis(),
            model = prevState.model?.copy(
                countries = prevState.model.countries.toMutableList()
                    .apply {
                        if (!this.contains(intent.item)) this.add(intent.item)
                    }
            ),
            bottomSheet = null
        )
        is CreatePostScreenIntent.OnDeleteExpense -> prevState.copy(
            updateTs = getTimeMillis(),
            model = prevState.model?.copy(
                expenses = prevState.model.expenses.toMutableList()
                    .apply { this.remove(intent.item) }
            )
        )
        is CreatePostScreenIntent.OnFromDateSelected -> prevState.copy(
            updateTs = getTimeMillis(),
            model = prevState.model?.copy(
                timestampStart = intent.date,
                dateStart = intent.date.formatDate(),
            ),
            bottomSheet = null
        )
        is CreatePostScreenIntent.OnToDateSelected -> prevState.copy(
            updateTs = getTimeMillis(),
            model = prevState.model?.copy(
                timestampEnd = intent.date,
                dateEnd = intent.date.formatDate(),
            ),
            bottomSheet = null
        )
        is CreatePostScreenIntent.OnAddExpense -> prevState.copy(
            updateTs = getTimeMillis(),
            model = prevState.model?.copy(
                expenses = prevState.model.expenses.toMutableList()
                    .apply { this.add(intent.item) }
            )
        )
        is CreatePostScreenIntent.OnAddPhoto -> prevState.copy(
            updateTs = getTimeMillis(),
            bitmaps = prevState.bitmaps.apply {
                if (prevState.bitmaps.size < 10) {
                    intent.bitmap?.let {
                        this.add(intent.bitmap)
                    }
                }
            }
        )
        is CreatePostScreenIntent.OnDeletePhotoClick -> prevState.copy(
            updateTs = getTimeMillis(),
            bitmaps = prevState.bitmaps.apply {
                this.remove(intent.item)
            }
        )
        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: CreatePostScreenIntent,
        state: CreatePostScreenState
    ): CreatePostScreenIntent? = when (intent) {
        CreatePostScreenIntent.OnSaveClick -> {
            val model = state.model?.copy(
                description = state.description.text,
                name = state.name.text
            )
            val error = when {
                model?.name.isNullOrEmpty() -> true
                else -> false
            }
            if (!error) {
                model?.let {
                    if (state.isCreateMode) {
                        createTripPostUseCase(state.trip?.id.orEmpty(), model, state.bitmaps)
                    } else {
                        updateTripPostUseCase(state.trip?.id.orEmpty(), model)
                    }
                }
                router.back(createPostScope)
            } else {
                triggerSingleEvent(CreatePostScreenSingleEvent.ShowError)
            }
            null
        }
        CreatePostScreenIntent.OnAddNewExpense -> {
            router.navigateToAddExpenses { expense ->
                expense?.let { sendIntent(CreatePostScreenIntent.OnAddExpense(it)) }
            }
            null
        }
        CreatePostScreenIntent.GoBack -> {
            router.back(createPostScope)
            null
        }
        else -> null
    }
}