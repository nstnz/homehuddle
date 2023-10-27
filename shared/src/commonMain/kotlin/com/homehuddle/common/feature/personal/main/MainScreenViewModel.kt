package com.homehuddle.common.feature.personal.main

import com.homehuddle.common.base.domain.general.usecase.GetMeUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetUserTripsFlowUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.GetUserTripPostsFlowUseCase
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

internal class MainScreenViewModel(
    private val router: Router,
    private val getMeUseCase: GetMeUseCase,
    private val getUserTripsFlowUseCase: GetUserTripsFlowUseCase,
    private val getUserTripPostsFlowUseCase: GetUserTripPostsFlowUseCase,
) : CoroutinesViewModel<MainScreenState, MainScreenIntent, MainScreenSingleEvent>() {

    init {
        viewModelScope.launch {
            getUserTripsFlowUseCase().collect {
                sendIntent(MainScreenIntent.UpdateTrips(it))
            }
        }
        viewModelScope.launch {
            getUserTripPostsFlowUseCase().collect {
                sendIntent(MainScreenIntent.UpdateTripPosts(it))
            }
        }
        viewModelScope.launch {
            getMeUseCase()?.let { sendIntent(MainScreenIntent.UpdateUser(it)) }
        }
    }

    override fun initialState(): MainScreenState = MainScreenState()

    override fun reduce(
        intent: MainScreenIntent,
        prevState: MainScreenState
    ): MainScreenState = when (intent) {
        MainScreenIntent.SelectPostsFilter -> prevState.copy(tripsSelected = false)
        MainScreenIntent.SelectTripsFilter -> prevState.copy(tripsSelected = true)
        is MainScreenIntent.UpdateUser -> prevState.copy(user = intent.user)
        is MainScreenIntent.UpdateTrips -> prevState.copy(trips = intent.trips)
        is MainScreenIntent.UpdateTripPosts -> prevState.copy(tripPosts = intent.tripPosts)
        MainScreenIntent.AddNewItemClick -> prevState.copy(
            showAddItemBottomSheet = true,
        )
        MainScreenIntent.AddTripClick -> prevState.copy(
            showAddItemBottomSheet = false,
        )
        MainScreenIntent.AddTripPostClick -> prevState.copy(
            showAddItemBottomSheet = false,
        )
        MainScreenIntent.AddLocationsClick -> prevState.copy(
            showAddItemBottomSheet = false,
        )
        MainScreenIntent.AddExpensesClick -> prevState.copy(
            showAddItemBottomSheet = false,
        )
        MainScreenIntent.CloseBottomSheet -> prevState.copy(
            showAddItemBottomSheet = false,
        )
        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: MainScreenIntent,
        state: MainScreenState
    ): MainScreenIntent? = when (intent) {
        MainScreenIntent.AddTripClick -> {
            router.navigateToAddTrip()
            null
        }
        MainScreenIntent.AddTripPostClick -> {
            router.navigateToAddTripPost()
            null
        }
        MainScreenIntent.AddExpensesClick -> {
            router.navigateToAddExpenses()
            null
        }
        MainScreenIntent.AddLocationsClick -> {
            router.navigateToAddLocations()
            null
        }
        is MainScreenIntent.OnTripClick -> {
            router.navigateToTrip(intent.model)
            null
        }
        is MainScreenIntent.OnTripPostClick -> {
            router.navigateToTripPost(intent.model)
            null
        }
        else -> null
    }
}