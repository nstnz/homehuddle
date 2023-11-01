package com.homehuddle.common.feature.personal.tripdetails

import com.homehuddle.common.base.di.tripDetailsScope
import com.homehuddle.common.base.domain.trips.usecase.trip.DeleteTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetTripUseCase
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
        is TripDetailsScreenIntent.AddItemClick -> prevState.copy(bottomSheet = BottomSheetType.AddNewItem)
        is TripDetailsScreenIntent.TryDeleteTrip -> prevState.copy(bottomSheet = BottomSheetType.ConfirmDelete)
        TripDetailsScreenIntent.CloseBottomSheet -> prevState.copy(bottomSheet = null)
        is TripDetailsScreenIntent.ConfirmDeleteTrip -> prevState.copy(bottomSheet = null)
        TripDetailsScreenIntent.AddTripClick -> prevState.copy(bottomSheet = null)
        TripDetailsScreenIntent.AddExpensesClick -> prevState.copy(bottomSheet = null)
        TripDetailsScreenIntent.AddTripPostClick -> prevState.copy(bottomSheet = null)
        TripDetailsScreenIntent.AddLocationsClick -> prevState.copy(bottomSheet = null)
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
            router.back(tripDetailsScope)
            null
        }
        is TripDetailsScreenIntent.ConfirmDeleteTrip -> {
            deleteTripUseCase(id)
            router.back(tripDetailsScope)
            null
        }
        is TripDetailsScreenIntent.EditTrip -> {
            router.navigateToEditTrip(intent.trip)
            null
        }
        TripDetailsScreenIntent.AddTripClick -> {
            router.navigateToAddTrip()
            null
        }
        TripDetailsScreenIntent.AddTripPostClick -> {
            router.navigateToAddTripPost()
            null
        }
        TripDetailsScreenIntent.AddExpensesClick -> {
            router.navigateToAddExpenses()
            null
        }
        TripDetailsScreenIntent.AddLocationsClick -> {
            router.navigateToAddLocations()
            null
        }
        is TripDetailsScreenIntent.OnPostClick -> {
            router.navigateToTripPost(intent.post)
            null
        }
        else -> null
    }
}