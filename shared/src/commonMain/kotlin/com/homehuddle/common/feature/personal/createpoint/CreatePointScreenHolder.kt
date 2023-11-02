package com.homehuddle.common.feature.personal.createpoint

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.createPointScope
import com.homehuddle.common.base.domain.general.model.TripPointModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.design.theme.SetBottomSheetListener
import com.homehuddle.common.router.OnLifecycleEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

internal class CreatePointScreenHolder(
    private val pointModel: TripPointModel?,
    private val onCustomExpenseCreation: ((TripPointModel?) -> Unit)? = null,
) : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel: CreatePointScreenViewModel by SharedDI.di.instance(arg = pointModel?.id.orEmpty())
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )

        LaunchedEffect(Unit) {
            viewModel.singleEvent.onEach { event ->
                when (event) {
                    is CreatePointScreenSingleEvent.ShowError -> {
                        snackbarHostState.showSnackbar("Errorrrr", isError = true)
                    }
                }
            }.collect()
        }

        OnLifecycleEvent(createPointScope) { event ->
            when (event) {
                Lifecycle.State.Active -> viewModel.sendIntent(CreatePointScreenIntent.OnResume)
                else -> Unit
            }
        }

        SetBottomSheetListener(bottomSheetState, onHide = {
            viewModel.sendIntent(CreatePointScreenIntent.CloseBottomSheet)
        })
        scope.launch {
            if (viewState.bottomSheet != null) {
                bottomSheetState.show()
            } else {
                bottomSheetState.hide()
            }
        }

        CreatePointScreen(
            state = viewState,
            bottomSheetState = bottomSheetState,
            snackbarHostState = snackbarHostState,
            onBackClick = { viewModel.sendIntent(CreatePointScreenIntent.GoBack) },
            onSaveClick = {
                viewModel.sendIntent(CreatePointScreenIntent.OnSaveClick(onCustomExpenseCreation))
            },
            onDescriptionChanged = {
                viewModel.sendIntent(CreatePointScreenIntent.OnDescriptionChanged(it))
            },
            onNameChanged = { viewModel.sendIntent(CreatePointScreenIntent.OnNameChanged(it)) },
            onTripClick = { viewModel.sendIntent(CreatePointScreenIntent.OnChangeTripClick) },
            onChangeTrip = { viewModel.sendIntent(CreatePointScreenIntent.OnTripChanged(it)) },
            onChangeLocation = { viewModel.sendIntent(CreatePointScreenIntent.OnChangeLocation(it)) },
            onLocationChanged = { viewModel.sendIntent(CreatePointScreenIntent.OnLocationChanged(it)) },
        )
    }
}
