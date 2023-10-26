package com.homehuddle.common.feature.personal.createtrip

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.createTripScope
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.router.OnLifecycleEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

internal class CreateTripScreenHolder(
    private val tripModel: TripModel?
) : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel: CreateTripScreenViewModel by SharedDI.di.instance(arg = tripModel?.id.orEmpty())
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )

        LaunchedEffect(Unit) {
            viewModel.singleEvent.onEach { event ->
                when (event) {
                    is CreateTripScreenSingleEvent.ShowError -> {
                        snackbarHostState.showSnackbar("Errorrrr", isError = true)
                    }
                }
            }.collect()
        }

        OnLifecycleEvent(createTripScope) { event ->
            when (event) {
                Lifecycle.State.Active -> {
                    viewModel.sendIntent(CreateTripScreenIntent.OnResume)
                }
                else -> Unit
            }
        }

        CreateTripScreen(
            bottomSheetState = bottomSheetState,
            state = viewState,
            snackbarHostState = snackbarHostState,
            onNameChanged = { viewModel.sendIntent(CreateTripScreenIntent.OnChangeName(it)) },
            onDescriptionChanged = {
                viewModel.sendIntent(
                    CreateTripScreenIntent.OnChangeDescription(
                        it
                    )
                )
            },
            onFromDateSelected = {
                scope.launch {
                    viewModel.sendIntent(CreateTripScreenIntent.OnFromDateSelected(it))
                    bottomSheetState.hide()
                }
            },
            onToDateSelected = {
                scope.launch {
                    viewModel.sendIntent(CreateTripScreenIntent.OnToDateSelected(it))
                    bottomSheetState.hide()
                }
            },
            onSaveClick = { viewModel.sendIntent(CreateTripScreenIntent.OnSaveClick) },
            onBackClick = { viewModel.sendIntent(CreateTripScreenIntent.GoBack) },
            onFromDateClick = {
                scope.launch {
                    viewModel.sendIntent(CreateTripScreenIntent.OnFromDateClick)
                    bottomSheetState.show()
                }
            },
            onToDateClick = {
                scope.launch {
                    viewModel.sendIntent(CreateTripScreenIntent.OnToDateClick)
                    bottomSheetState.show()
                }
            },
        )
    }
}
