package com.homehuddle.common.feature.personal.main

import com.homehuddle.common.base.domain.general.usecase.GetMeUseCase
import com.homehuddle.common.base.domain.trips.scenario.GetUserTripsScenario
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class MainScreenViewModel(
    private val router: Router,
    private val getMeUseCase: GetMeUseCase,
    private val getUserTripsScenario: GetUserTripsScenario,
) : CoroutinesViewModel<MainScreenState, MainScreenIntent, MainScreenSingleEvent>() {

    override fun initialState(): MainScreenState = MainScreenState()

    override fun reduce(
        intent: MainScreenIntent,
        prevState: MainScreenState
    ): MainScreenState = when (intent) {
        MainScreenIntent.SelectPostsFilter -> prevState.copy(tripsSelected = false)
        MainScreenIntent.SelectTripsFilter -> prevState.copy(tripsSelected = true)
        is MainScreenIntent.UpdateUser -> prevState.copy(user = intent.user)
        is MainScreenIntent.UpdateTrips -> prevState.copy(trips = intent.trips)
        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: MainScreenIntent,
        state: MainScreenState
    ): MainScreenIntent? = when (intent) {
        MainScreenIntent.FirstLaunch -> {
            getMeUseCase()?.let {
                sendIntent(MainScreenIntent.UpdateUser(it))
                sendIntent(MainScreenIntent.UpdateTrips(getUserTripsScenario(it.id)))
            }
            null
        }

        MainScreenIntent.OnResume -> null
        is MainScreenIntent.UpdateUser -> null
        is MainScreenIntent.UpdateTrips -> null
        MainScreenIntent.SelectPostsFilter -> null
        MainScreenIntent.SelectTripsFilter -> null
    }
}