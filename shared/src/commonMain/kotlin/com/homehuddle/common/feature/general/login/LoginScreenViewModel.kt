package com.homehuddle.common.feature.general.login

import com.homehuddle.common.base.domain.general.usecase.AnonymousAuthUseCase
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class LoginScreenViewModel(
    private val router: Router,
    private val authUseCase: AnonymousAuthUseCase,
) : CoroutinesViewModel<LoginScreenState, LoginScreenIntent, LoginScreenSingleEvent>() {

    override fun initialState(): LoginScreenState = LoginScreenState

    override fun reduce(
        intent: LoginScreenIntent,
        prevState: LoginScreenState
    ): LoginScreenState =
        prevState

    override suspend fun performSideEffects(
        intent: LoginScreenIntent,
        state: LoginScreenState
    ): LoginScreenIntent? = when (intent) {
        LoginScreenIntent.Load -> {
            null
        }
        LoginScreenIntent.MakeLogin -> {
            if (authUseCase()) {
                router.navigateToMainScreen()
            }
            null
        }
    }
}