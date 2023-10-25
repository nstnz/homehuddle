package com.homehuddle.common.feature.personal.tripdetails

import com.homehuddle.common.base.domain.trips.usecase.DeleteTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.GetTripUseCase
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class TripDetailsScreenViewModel(
    private val id: String,
    private val router: Router,
    private val getTripUseCase: GetTripUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
) : CoroutinesViewModel<TripDetailsScreenState, TripDetailsScreenIntent, TripDetailsScreenSingleEvent>() {

    override fun initialState(): TripDetailsScreenState = TripDetailsScreenState()

    override fun reduce(
        intent: TripDetailsScreenIntent,
        prevState: TripDetailsScreenState
    ): TripDetailsScreenState = when (intent) {
        is TripDetailsScreenIntent.UpdateTrip -> prevState.copy(
            trip = intent.trip
        )

        TripDetailsScreenIntent.AllFilterSelected -> prevState.copy(selectedTab = TripDetailsTab.All)
        TripDetailsScreenIntent.MapFilterSelected -> prevState.copy(selectedTab = TripDetailsTab.Map)
        TripDetailsScreenIntent.ExpensesFilterSelected -> prevState.copy(selectedTab = TripDetailsTab.Expenses)
        TripDetailsScreenIntent.PhotosFilterSelected -> prevState.copy(selectedTab = TripDetailsTab.Photos)
        TripDetailsScreenIntent.OverviewFilterSelected -> prevState.copy(selectedTab = TripDetailsTab.Overview)

        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: TripDetailsScreenIntent,
        state: TripDetailsScreenState
    ): TripDetailsScreenIntent? = when (intent) {
        TripDetailsScreenIntent.OnResume -> {
            val trip = getTripUseCase(id)
            TripDetailsScreenIntent.UpdateTrip(trip)
        }

        TripDetailsScreenIntent.GoBack -> {
            router.back()
            null
        }

        is TripDetailsScreenIntent.DeleteTrip -> {
            deleteTripUseCase(id)
            router.back()
            null
        }

        is TripDetailsScreenIntent.EditTrip -> {
            router.navigateToEditTrip(intent.trip)
            null
        }

        else -> null
    }
}