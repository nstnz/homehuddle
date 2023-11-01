package com.homehuddle.common.feature.personal.trippost

import com.homehuddle.common.base.di.tripPostScope
import com.homehuddle.common.base.domain.trips.usecase.trip.GetTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.DeleteTripPostUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.GetTripPostUseCase
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class TripPostScreenViewModel(
    private val id: String,
    private val router: Router,
    private val getTripUseCase: GetTripUseCase,
    private val getTripPostUseCase: GetTripPostUseCase,
    private val deleteTripPostUseCase: DeleteTripPostUseCase
) : CoroutinesViewModel<TripPostScreenState, TripPostScreenIntent, TripPostScreenSingleEvent>() {

    override fun initialState(): TripPostScreenState = TripPostScreenState()

    override fun reduce(
        intent: TripPostScreenIntent,
        prevState: TripPostScreenState
    ): TripPostScreenState = when (intent) {
        is TripPostScreenIntent.Update -> prevState.copy(
            trip = intent.trip,
            tripPost = intent.postModel
        )
        is TripPostScreenIntent.OnDeleteClick -> prevState.copy(
            bottomSheet = BottomSheetType.ConfirmDelete
        )
        TripPostScreenIntent.CloseBottomSheet -> prevState.copy(
            bottomSheet = null
        )
        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: TripPostScreenIntent,
        state: TripPostScreenState
    ): TripPostScreenIntent? = when (intent) {
        TripPostScreenIntent.OnResume -> {
            val tripPost = getTripPostUseCase(id)
            val trip = getTripUseCase(tripPost?.tripId.orEmpty())
            TripPostScreenIntent.Update(trip, tripPost)
        }
        TripPostScreenIntent.GoBack -> {
            router.back(tripPostScope)
            null
        }
        TripPostScreenIntent.OnConfirmDelete -> {
            deleteTripPostUseCase(id)
            router.back(tripPostScope)
            null
        }
        TripPostScreenIntent.OnEditClick -> {
            router.navigateToEditTripPost(state.tripPost)
            null
        }
        else -> null
    }
}