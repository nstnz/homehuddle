package com.homehuddle.common.feature.general.splash

import com.homehuddle.common.base.domain.general.usecase.IsSignedInUseCase
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class SplashScreenViewModel(
    private val router: Router,
    private val isSignedInUseCase: IsSignedInUseCase,
) : CoroutinesViewModel<SplashScreenState, SplashScreenIntent, SplashScreenSingleEvent>() {

    override fun initialState(): SplashScreenState = SplashScreenState

    override fun reduce(
        intent: SplashScreenIntent,
        prevState: SplashScreenState
    ): SplashScreenState =
        prevState

    override suspend fun performSideEffects(
        intent: SplashScreenIntent,
        state: SplashScreenState
    ): SplashScreenIntent? = when (intent) {
        SplashScreenIntent.Load -> {
            if (!isSignedInUseCase()) {
                //first launch
                router.navigateToWelcomeScreen()
            } else {
                router.navigateToMainScreen()
            }
            null
        }
    }
}