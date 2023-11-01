package com.homehuddle.common.feature.general.welcome

import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class WelcomeScreenViewModel(
    private val router: Router,
) : CoroutinesViewModel<WelcomeScreenState, WelcomeScreenIntent, WelcomeScreenSingleEvent>() {

    override fun initialState(): WelcomeScreenState = WelcomeScreenState

    override fun reduce(
        intent: WelcomeScreenIntent,
        prevState: WelcomeScreenState
    ): WelcomeScreenState =
        prevState

    override suspend fun performSideEffects(
        intent: WelcomeScreenIntent,
        state: WelcomeScreenState
    ): WelcomeScreenIntent? = when (intent) {
        WelcomeScreenIntent.Load -> {
            null
        }
        WelcomeScreenIntent.Proceed -> {
            router.navigateToLogin()
            null
        }
    }
}