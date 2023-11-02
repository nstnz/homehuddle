package com.homehuddle.common.feature.personal.createexpense

import androidx.compose.ui.text.TextRange
import com.homehuddle.common.base.di.createExpenseScope
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.usecase.GetMeUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetLastTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetUserTripsFlowUseCase
import com.homehuddle.common.base.domain.trips.usecase.tripexpense.CreateOnlyTripExpenseUseCase
import com.homehuddle.common.base.domain.trips.usecase.tripexpense.GetTripExpenseUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.GetTripPostUseCase
import com.homehuddle.common.base.domain.utils.formatDate
import com.homehuddle.common.base.domain.utils.formatSum
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.flow.firstOrNull

internal class CreateExpenseScreenViewModel(
    private val expenseId: String?,
    private val router: Router,
    private val getMeUseCase: GetMeUseCase,
    private val getTripUseCase: GetTripUseCase,
    private val getLastTripUseCase: GetLastTripUseCase,
    private val getTripExpenseUseCase: GetTripExpenseUseCase,
    private val getTripPostUseCase: GetTripPostUseCase,
    private val getUserTripsFlowUseCase: GetUserTripsFlowUseCase,
    private val createOnlyTripExpenseUseCase: CreateOnlyTripExpenseUseCase
) : CoroutinesViewModel<CreateExpenseScreenState, CreateExpenseScreenIntent, CreateExpenseScreenSingleEvent>() {

    override fun initialState(): CreateExpenseScreenState = CreateExpenseScreenState(
        isCreateMode = expenseId.isNullOrEmpty()
    )

    override fun reduce(
        intent: CreateExpenseScreenIntent,
        prevState: CreateExpenseScreenState
    ): CreateExpenseScreenState = when (intent) {
        is CreateExpenseScreenIntent.Update -> prevState.copy(
            model = intent.model,
            trip = intent.trip
        )
        is CreateExpenseScreenIntent.UpdateUser -> prevState.copy(
            userModel = intent.userModel
        )
        is CreateExpenseScreenIntent.UpdateTrips -> prevState.copy(
            trips = intent.trips
        )
        CreateExpenseScreenIntent.CloseBottomSheet -> prevState.copy(
            bottomSheet = null
        )
        is CreateExpenseScreenIntent.OnCategoryChanged -> prevState.copy(
            model = prevState.model?.copy(
                category = intent.category
            )
        )
        is CreateExpenseScreenIntent.OnDescriptionChanged -> prevState.copy(description = intent.value)
        is CreateExpenseScreenIntent.OnSumChanged -> prevState.copy(formattedSum = when {
            intent.value.text.endsWith(".") && intent.value.text.count { it == '.' } > 1 -> {
                val newSum = intent.value.text.formatSum()
                intent.value.copy(
                    text = newSum.formatSum().orEmpty(),
                    selection = TextRange(newSum.formatSum().orEmpty().length)
                )
            }
            intent.value.text == "." -> intent.value.copy(
                text = "0.",
                selection = TextRange(2)
            )
            intent.value.text.endsWith(".") -> intent.value
            intent.value.text.split(".").getOrNull(1)?.length == 3 -> prevState.formattedSum
            else -> {
                val newSum = intent.value.text.formatSum()
                intent.value.copy(
                    text = newSum.formatSum().orEmpty(),
                    selection = TextRange(newSum.formatSum().orEmpty().length)
                )
            }
        })
        CreateExpenseScreenIntent.OnChangeCurrencyClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectCurrency(
                currencies = prevState.userModel?.allCurrencies.orEmpty(),
                selected = prevState.model?.currency
            )
        )
        CreateExpenseScreenIntent.OnChangeTripClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectTrip(
                trips = prevState.trips,
                selected = prevState.trip
            )
        )
        is CreateExpenseScreenIntent.OnCurrencyChanged -> prevState.copy(
            model = prevState.model?.copy(
                currency = intent.value
            ),
            bottomSheet = null
        )
        is CreateExpenseScreenIntent.OnTripChanged -> prevState.copy(
            trip = intent.value,
            bottomSheet = null
        )
        CreateExpenseScreenIntent.OnDateClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectDate(
                timestamp = prevState.model?.timestamp
            )
        )
        is CreateExpenseScreenIntent.OnDateChanged -> prevState.copy(
            model = prevState.model?.copy(
                timestamp = intent.date,
                date = (intent.date ?: getTimeMillis()).formatDate()
            ),
            bottomSheet = null
        )
        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: CreateExpenseScreenIntent,
        state: CreateExpenseScreenState
    ): CreateExpenseScreenIntent? = when (intent) {
        CreateExpenseScreenIntent.OnResume -> {
            val user = getMeUseCase()
            val trips = getUserTripsFlowUseCase().firstOrNull().orEmpty()
            sendIntent(CreateExpenseScreenIntent.UpdateUser(user))
            sendIntent(CreateExpenseScreenIntent.UpdateTrips(trips))
            expenseId.takeIf { !it.isNullOrEmpty() }?.let {
                val expense = getTripExpenseUseCase(it)
                val post = expense?.let { getTripPostUseCase(it.tripPostId.orEmpty()) }
                val trip = post?.let { getTripUseCase(it.tripId.orEmpty()) }
                sendIntent(CreateExpenseScreenIntent.Update(expense, trip))
            } ?: run {
                val lastTrip = getLastTripUseCase()
                val expense = TripExpenseModel.createEmpty(
                    lastTrip?.currency ?: user?.currency
                )
                sendIntent(CreateExpenseScreenIntent.Update(expense, lastTrip))
            }
            null
        }
        is CreateExpenseScreenIntent.OnSaveClick -> {
            val model = state.model?.copy(
                description = state.description.text,
                sum = state.formattedSum.text.formatSum()
            )
            val error = when {
                (model?.sum ?: 0.0) == 0.0 -> true
                else -> false
            }
            if (!error) {
                model?.let {
                    if (intent.onCustomExpenseCreation != null) {
                        intent.onCustomExpenseCreation.invoke(model)
                    } else {
                        createOnlyTripExpenseUseCase(model, state.trip)
                    }
                }
                router.back(createExpenseScope)
            } else {
                triggerSingleEvent(CreateExpenseScreenSingleEvent.ShowError)
            }
            null
        }
        CreateExpenseScreenIntent.GoBack -> {
            router.back(createExpenseScope)
            null
        }
        else -> null
    }
}