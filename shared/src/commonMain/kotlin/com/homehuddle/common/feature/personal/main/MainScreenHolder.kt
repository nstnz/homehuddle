package com.homehuddle.common.feature.personal.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.mainScope
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.router.OnLifecycleEvent
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

@Composable
internal fun MainScreenHolder() {
    val viewModel: MainScreenViewModel by SharedDI.di.instance()
    val viewState by viewModel.viewState.collectAsStateLifecycleAware()

    OnLifecycleEvent(mainScope) { event ->
        when (event) {
            Lifecycle.State.Initialized -> viewModel.sendIntent(MainScreenIntent.FirstLaunch)
            Lifecycle.State.Active -> viewModel.sendIntent(MainScreenIntent.OnResume)
            else -> Unit
        }
    }

    MainScreen(
        state = viewState,
        onPostsFilterSelected = { viewModel.sendIntent(MainScreenIntent.SelectPostsFilter) },
        onTripsFilterSelected = { viewModel.sendIntent(MainScreenIntent.SelectTripsFilter) },
    )
}
