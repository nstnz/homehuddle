package com.homehuddle.common.feature.personal.main

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.mainScope
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.design.theme.SetBottomSheetListener
import com.homehuddle.common.router.OnLifecycleEvent
import kotlinx.coroutines.launch
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

object MainScreenHolder : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel: MainScreenViewModel by SharedDI.di.instance()
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        val bottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )
        val scope = rememberCoroutineScope()

        OnLifecycleEvent(mainScope) { event ->
            when (event) {
                Lifecycle.State.Active -> viewModel.sendIntent(MainScreenIntent.OnResume)
                else -> Unit
            }
        }

        SetBottomSheetListener(bottomSheetState, onHide = {
            viewModel.sendIntent(MainScreenIntent.CloseBottomSheet)
        })
        scope.launch {
            if (viewState.isBottomSheetShown) {
                bottomSheetState.show()
            } else {
                bottomSheetState.hide()
            }
        }

        MainScreen(
            state = viewState,
            bottomSheetState = bottomSheetState,
            onTripClick = { viewModel.sendIntent(MainScreenIntent.OnTripClick(it)) },
            onTripPostClick = { viewModel.sendIntent(MainScreenIntent.OnTripPostClick(it)) },
            onPostsFilterSelected = { viewModel.sendIntent(MainScreenIntent.SelectPostsFilter) },
            onTripsFilterSelected = { viewModel.sendIntent(MainScreenIntent.SelectTripsFilter) },
            onAddClick = { viewModel.sendIntent(MainScreenIntent.AddNewItemClick) },
            onAddTripClick = { viewModel.sendIntent(MainScreenIntent.AddTripClick) },
            onAddTripPostClick = { viewModel.sendIntent(MainScreenIntent.AddTripPostClick) },
            onAddExpensesClick = { viewModel.sendIntent(MainScreenIntent.AddExpensesClick) },
            onAddLocationsClick = { viewModel.sendIntent(MainScreenIntent.AddLocationsClick) },
        )
    }
}
