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
import com.homehuddle.common.design.theme.SetBottomSheetListener
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


        SetBottomSheetListener(bottomSheetState, onHide = {
            viewModel.sendIntent(CreateTripScreenIntent.CloseBottomSheet)
        })
        scope.launch {
            if (viewState.bottomSheet != null) {
                bottomSheetState.show()
            } else {
                bottomSheetState.hide()
            }
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
            onFromDateSelected = { viewModel.sendIntent(CreateTripScreenIntent.OnFromDateSelected(it)) },
            onToDateSelected = { viewModel.sendIntent(CreateTripScreenIntent.OnToDateSelected(it)) },
            onSaveClick = { viewModel.sendIntent(CreateTripScreenIntent.OnSaveClick) },
            onBackClick = { viewModel.sendIntent(CreateTripScreenIntent.GoBack) },
            onFromDateClick = { viewModel.sendIntent(CreateTripScreenIntent.OnFromDateClick) },
            onToDateClick = { viewModel.sendIntent(CreateTripScreenIntent.OnToDateClick) },
            onCurrencyClick = { viewModel.sendIntent(CreateTripScreenIntent.OnCurrencyClick) },
            onChangeCurrency = { viewModel.sendIntent(CreateTripScreenIntent.OnChangeCurrency(it)) },
            onCountrySelected = { model, selected ->
                viewModel.sendIntent(
                    CreateTripScreenIntent.OnCountrySelected(
                        model,
                        selected
                    )
                )
            }
        )
    }
}
