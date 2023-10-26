package com.homehuddle.common.feature.general.splash

import com.homehuddle.common.base.domain.general.usecase.IsSignedInUseCase
import com.homehuddle.common.base.domain.general.usecase.RefreshDataUseCase
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal class SplashScreenViewModel(
    private val router: Router,
    private val refreshDataUseCase: RefreshDataUseCase,
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
                GlobalScope.launch {
                    refreshDataUseCase()
                }
                router.navigateToMainScreen()
            }
            null
        }
    }
}