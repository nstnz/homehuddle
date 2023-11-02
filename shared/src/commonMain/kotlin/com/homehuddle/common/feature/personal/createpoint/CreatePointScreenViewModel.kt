package com.homehuddle.common.feature.personal.createpoint

import com.homehuddle.common.base.di.createPointScope
import com.homehuddle.common.base.domain.general.model.TripPointModel
import com.homehuddle.common.base.domain.general.usecase.GetMeUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetLastTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetUserTripsFlowUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippoint.CreateOnlyTripPointUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippoint.GetTripPointUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.GetTripPostUseCase
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router
import kotlinx.coroutines.flow.firstOrNull

internal class CreatePointScreenViewModel(
    private val pointId: String?,
    private val router: Router,
    private val getMeUseCase: GetMeUseCase,
    private val getTripUseCase: GetTripUseCase,
    private val getLastTripUseCase: GetLastTripUseCase,
    private val getTripPointUseCase: GetTripPointUseCase,
    private val getTripPostUseCase: GetTripPostUseCase,
    private val getUserTripsFlowUseCase: GetUserTripsFlowUseCase,
    private val createOnlyTripPointUseCase: CreateOnlyTripPointUseCase
) : CoroutinesViewModel<CreatePointScreenState, CreatePointScreenIntent, CreatePointScreenSingleEvent>() {

    override fun initialState(): CreatePointScreenState = CreatePointScreenState(
        isCreateMode = pointId.isNullOrEmpty()
    )

    override fun reduce(
        intent: CreatePointScreenIntent,
        prevState: CreatePointScreenState
    ): CreatePointScreenState = when (intent) {
        is CreatePointScreenIntent.Update -> prevState.copy(
            model = intent.model,
            trip = intent.trip
        )
        is CreatePointScreenIntent.UpdateUser -> prevState.copy(
            userModel = intent.userModel
        )
        is CreatePointScreenIntent.UpdateTrips -> prevState.copy(
            trips = intent.trips
        )
        CreatePointScreenIntent.CloseBottomSheet -> prevState.copy(
            bottomSheet = null
        )
        is CreatePointScreenIntent.OnDescriptionChanged -> prevState.copy(description = intent.value)
        is CreatePointScreenIntent.OnNameChanged -> prevState.copy(name = intent.value)
        CreatePointScreenIntent.OnChangeTripClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectTrip(
                trips = prevState.trips,
                selected = prevState.trip
            )
        )
        is CreatePointScreenIntent.OnChangeLocation -> prevState.copy(
            bottomSheet = BottomSheetType.SelectLocation(intent.value)
        )
        is CreatePointScreenIntent.OnLocationChanged -> prevState.copy(
            bottomSheet = null,
            model = prevState.model?.copy(
                address = intent.value.address,
                lat = intent.value.lat,
                lon = intent.value.lon,
            )
        )
        is CreatePointScreenIntent.OnTripChanged -> prevState.copy(
            trip = intent.value,
            bottomSheet = null
        )
        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: CreatePointScreenIntent,
        state: CreatePointScreenState
    ): CreatePointScreenIntent? = when (intent) {
        CreatePointScreenIntent.OnResume -> {
            val user = getMeUseCase()
            val trips = getUserTripsFlowUseCase().firstOrNull().orEmpty()
            sendIntent(CreatePointScreenIntent.UpdateUser(user))
            sendIntent(CreatePointScreenIntent.UpdateTrips(trips))
            pointId.takeIf { !it.isNullOrEmpty() }?.let {
                val point = getTripPointUseCase(it)
                val post = point?.let { getTripPostUseCase(it.tripPostId.orEmpty()) }
                val trip = post?.let { getTripUseCase(it.tripId.orEmpty()) }
                sendIntent(CreatePointScreenIntent.Update(point, trip))
            } ?: run {
                val lastTrip = getLastTripUseCase()
                val point = TripPointModel.createEmpty()
                sendIntent(CreatePointScreenIntent.Update(point, lastTrip))
            }
            null
        }
        is CreatePointScreenIntent.OnSaveClick -> {
            val model = state.model?.copy(
                description = state.description.text,
                name = state.name.text,
            )
            val error = when {
                //(model?.sum ?: 0.0) == 0.0 -> true
                else -> false
            }
            if (!error) {
                model?.let {
                    if (intent.onCustomPointCreation != null) {
                        intent.onCustomPointCreation.invoke(model)
                    } else {
                        createOnlyTripPointUseCase(model, state.trip)
                    }
                }
                router.back(createPointScope)
            } else {
                triggerSingleEvent(CreatePointScreenSingleEvent.ShowError)
            }
            null
        }
        CreatePointScreenIntent.GoBack -> {
            router.back(createPointScope)
            null
        }
        else -> null
    }
}