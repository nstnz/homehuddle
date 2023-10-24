package com.homehuddle.common.feature.personal.main

import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class MainScreenViewModel(
    private val router: Router,
) : CoroutinesViewModel<MainScreenState, MainScreenIntent, MainScreenSingleEvent>() {

    override fun initialState(): MainScreenState = MainScreenState()

    override fun reduce(
        intent: MainScreenIntent,
        prevState: MainScreenState
    ): MainScreenState = when (intent) {
        MainScreenIntent.SelectPostsFilter -> prevState.copy(tripsSelected = false)
        MainScreenIntent.SelectTripsFilter -> prevState.copy(tripsSelected = true)
        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: MainScreenIntent,
        state: MainScreenState
    ): MainScreenIntent? = when (intent) {
        MainScreenIntent.FirstLaunch -> null
        MainScreenIntent.OnResume -> null
        MainScreenIntent.SelectPostsFilter -> null
        MainScreenIntent.SelectTripsFilter -> null
    }
}