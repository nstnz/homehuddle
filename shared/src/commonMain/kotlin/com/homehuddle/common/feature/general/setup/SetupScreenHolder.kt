package com.homehuddle.common.feature.general.setup

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.setupScope
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.design.theme.SetBottomSheetListener
import com.homehuddle.common.router.OnLifecycleEvent
import kotlinx.coroutines.launch
import org.kodein.di.instance

object SetupScreenHolder : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel: SetupScreenViewModel by SharedDI.di.instance()
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        OnLifecycleEvent(setupScope) { event ->
            when (event) {
                else -> Unit
            }
        }

        val scope = rememberCoroutineScope()
        val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )
        SetBottomSheetListener(bottomSheetState, onHide = {
            viewModel.sendIntent(SetupScreenIntent.CloseBottomSheet)
        })
        scope.launch {
            if (viewState.bottomSheet != null) {
                bottomSheetState.show()
            } else {
                bottomSheetState.hide()
            }
        }

        SetupScreen(
            state = viewState,
            bottomSheetState = bottomSheetState,
            onSaveClick = { viewModel.sendIntent(SetupScreenIntent.Save) },
            onAddCountry = { viewModel.sendIntent(SetupScreenIntent.OnAddCountry) },
            onDeleteCountry = { viewModel.sendIntent(SetupScreenIntent.OnDeleteCountry(it)) },
            onChangeCurrency = { viewModel.sendIntent(SetupScreenIntent.OnChangeCurrency(it)) },
            onCurrencyClick = { viewModel.sendIntent(SetupScreenIntent.OnCurrencyClick) },
            onSelectCountry = { viewModel.sendIntent(SetupScreenIntent.OnSelectCountry(it)) },
        )
    }
}
