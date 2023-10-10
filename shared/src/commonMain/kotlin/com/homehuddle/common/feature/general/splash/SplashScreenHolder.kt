package com.homehuddle.common.feature.general.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.splashScope
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.router.OnLifecycleEvent
import org.kodein.di.instance

@Composable
internal fun SplashScreenHolder() {
    val viewModel: SplashScreenViewModel by SharedDI.di.instance()
    val viewState by viewModel.viewState.collectAsStateLifecycleAware()

    OnLifecycleEvent(splashScope) { event ->
        when (event) {
            else -> Unit
        }
    }

    SplashScreen(
        onReady = { viewModel.sendIntent(SplashScreenIntent.Load) }
    )
}
