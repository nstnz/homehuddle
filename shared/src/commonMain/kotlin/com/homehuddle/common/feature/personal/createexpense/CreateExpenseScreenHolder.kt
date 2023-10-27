package com.homehuddle.common.feature.personal.createexpense

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
import com.homehuddle.common.base.di.createExpenseScope
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.design.theme.SetBottomSheetListener
import com.homehuddle.common.router.OnLifecycleEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

internal class CreateExpenseScreenHolder(
    private val tripExpense: TripExpenseModel?,
) : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel: CreateExpenseScreenViewModel by SharedDI.di.instance(arg = tripExpense?.id.orEmpty())
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
                    is CreateExpenseScreenSingleEvent.ShowError -> {
                        snackbarHostState.showSnackbar("Errorrrr", isError = true)
                    }
                }
            }.collect()
        }

        OnLifecycleEvent(createExpenseScope) { event ->
            when (event) {
                Lifecycle.State.Active -> viewModel.sendIntent(CreateExpenseScreenIntent.OnResume)
                else -> Unit
            }
        }

        SetBottomSheetListener(bottomSheetState, onHide = {
            viewModel.sendIntent(CreateExpenseScreenIntent.CloseBottomSheet)
        })
        scope.launch {
            if (viewState.bottomSheet != null) {
                bottomSheetState.show()
            } else {
                bottomSheetState.hide()
            }
        }

        CreateExpenseScreen(
            state = viewState,
            bottomSheetState = bottomSheetState,
            snackbarHostState = snackbarHostState,
            onBackClick = { viewModel.sendIntent(CreateExpenseScreenIntent.GoBack) },
            onSaveClick = { viewModel.sendIntent(CreateExpenseScreenIntent.OnSaveClick) },
            onCategoryClick = { viewModel.sendIntent(CreateExpenseScreenIntent.OnCategoryChanged(it)) },
            onDescriptionChanged = {
                viewModel.sendIntent(
                    CreateExpenseScreenIntent.OnDescriptionChanged(
                        it
                    )
                )
            },
            onSumChanged = { viewModel.sendIntent(CreateExpenseScreenIntent.OnSumChanged(it)) },
            onCurrencyClick = { viewModel.sendIntent(CreateExpenseScreenIntent.OnChangeCurrencyClick) },
            onTripClick = { viewModel.sendIntent(CreateExpenseScreenIntent.OnChangeTripClick) },
            onChangeCurrency = { viewModel.sendIntent(CreateExpenseScreenIntent.OnCurrencyChanged(it)) },
            onChangeTrip = { viewModel.sendIntent(CreateExpenseScreenIntent.OnTripChanged(it)) },
            onDateClick = { viewModel.sendIntent(CreateExpenseScreenIntent.OnDateClick) },
            onChangeDate = { viewModel.sendIntent(CreateExpenseScreenIntent.OnDateChanged(it)) },
        )
    }
}
