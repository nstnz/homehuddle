package com.homehuddle.common.feature.personal.main

import com.homehuddle.common.base.domain.trips.usecase.trip.GetUserTripsFlowUseCase
import com.homehuddle.common.base.domain.trips.usecase.tripexpense.CreateOnlyTripExpenseUseCase
import com.homehuddle.common.base.domain.trips.usecase.trippost.GetUserTripPostsFlowUseCase
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

internal class MainScreenViewModel(
    private val router: Router,
    private val getUserTripsFlowUseCase: GetUserTripsFlowUseCase,
    private val getUserTripPostsFlowUseCase: GetUserTripPostsFlowUseCase,
    private val createOnlyTripExpenseUseCase: CreateOnlyTripExpenseUseCase
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
            showAddExpenseBottomSheet = false
        )
        MainScreenIntent.AddTripClick -> prevState.copy(
            showAddItemBottomSheet = false,
            showAddExpenseBottomSheet = false
        )
        MainScreenIntent.AddTripPostClick -> prevState.copy(
            showAddItemBottomSheet = false,
            showAddExpenseBottomSheet = false
        )
        MainScreenIntent.AddExpensesClick -> prevState.copy(
            showAddItemBottomSheet = false,
            showAddExpenseBottomSheet = true
        )
        MainScreenIntent.AddLocationsClick -> prevState.copy(
            showAddItemBottomSheet = false,
            showAddExpenseBottomSheet = false
        )
        MainScreenIntent.CloseBottomSheet -> prevState.copy(
            showAddItemBottomSheet = false,
            showAddExpenseBottomSheet = false
        )
        is MainScreenIntent.OnCreateTripExpense -> prevState.copy(
            showAddItemBottomSheet = false,
            showAddExpenseBottomSheet = false
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
        is MainScreenIntent.OnCreateTripExpense -> {
            createOnlyTripExpenseUseCase(intent.model, intent.trip.id.orEmpty())
            null
        }
        else -> null
    }
}