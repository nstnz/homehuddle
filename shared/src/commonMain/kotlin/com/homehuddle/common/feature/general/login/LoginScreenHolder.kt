package com.homehuddle.common.feature.general.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.welcomeScope
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.router.OnLifecycleEvent
import org.kodein.di.instance

object LoginScreenHolder : Screen {

    @Composable
    override fun Content() {
        val viewModel: LoginScreenViewModel by SharedDI.di.instance()
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        OnLifecycleEvent(welcomeScope) { event ->
            when (event) {
                else -> Unit
            }
        }

        LoginScreen(
            onLoginClick = {
                viewModel.sendIntent(LoginScreenIntent.MakeLogin)
            }
        )
    }
}
