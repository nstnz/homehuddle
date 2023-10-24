package com.homehuddle.common.feature.general.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.splashScope
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.router.OnLifecycleEvent
import com.homehuddle.common.router.Router
import org.kodein.di.instance

object SplashScreenHolder : Screen {

    @Composable
    override fun Content() {
        val viewModel: SplashScreenViewModel by SharedDI.di.instance()
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()
        val navigator = LocalNavigator.currentOrThrow
        val router: Router by SharedDI.di.instance()
        router.init(navigator)

        OnLifecycleEvent(splashScope) { event ->
            when (event) {
                else -> Unit
            }
        }

        SplashScreen(
            onReady = { viewModel.sendIntent(SplashScreenIntent.Load) }
        )
    }
}


