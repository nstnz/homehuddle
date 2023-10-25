package com.homehuddle.common.feature.personal.createtrip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.tripDetailsScope
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.router.OnLifecycleEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

internal class CreateTripScreenHolder(
    private val tripModel: TripModel?
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: CreateTripScreenViewModel by SharedDI.di.instance(arg = tripModel?.id.orEmpty())
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(Unit) {
            viewModel.singleEvent.onEach { event ->
                when (event) {
                    is CreateTripScreenSingleEvent.ShowError -> {
                        snackbarHostState.showSnackbar("Errorrrr", isError = true)
                    }
                }
            }.collect()
        }

        OnLifecycleEvent(tripDetailsScope) { event ->
            when (event) {
                Lifecycle.State.Active -> viewModel.sendIntent(CreateTripScreenIntent.OnResume)
                else -> Unit
            }
        }

        CreateTripScreen(
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
            onBackClick = { viewModel.sendIntent(CreateTripScreenIntent.GoBack) }
        )
    }
}
