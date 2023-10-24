package com.homehuddle.common.feature.general.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.welcomeScope
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.router.OnLifecycleEvent
import org.kodein.di.instance

object WelcomeScreenHolder : Screen {

    @Composable
    override fun Content() {
        val viewModel: WelcomeScreenViewModel by SharedDI.di.instance()
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        OnLifecycleEvent(welcomeScope) { event ->
            when (event) {
                else -> Unit
            }
        }

        WelcomeScreen(
            onProceedClick = { viewModel.sendIntent(WelcomeScreenIntent.Proceed) }
        )
    }
}
