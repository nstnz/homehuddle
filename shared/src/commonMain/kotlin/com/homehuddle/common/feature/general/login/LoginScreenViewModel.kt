package com.homehuddle.common.feature.general.login

import GoogleAuthApi
import com.homehuddle.common.base.domain.general.usecase.AuthUseCase
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class LoginScreenViewModel(
    private val router: Router,
    private val authUseCase: AuthUseCase,
    private val googleAuthApi: GoogleAuthApi,
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
            googleAuthApi.signIn {
                sendIntent(LoginScreenIntent.Login(it))
            }
            null
        }
        is LoginScreenIntent.Login -> {
            val existingUser = authUseCase(intent.token)
            if (existingUser != null) {
                router.navigateToMainScreen()
            } else {
                router.navigateToSetup()
            }
            null
        }
    }
}