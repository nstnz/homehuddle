package com.homehuddle.common.feature.personal.tripdetails

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.tripDetailsScope
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.design.theme.SetBottomSheetListener
import com.homehuddle.common.router.OnLifecycleEvent
import kotlinx.coroutines.launch
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

class TripDetailsScreenHolder(
    private val trip: TripModel
) : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel: TripDetailsScreenViewModel by SharedDI.di.instance(arg = trip.id.orEmpty())
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        val scope = rememberCoroutineScope()
        val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )

        SetBottomSheetListener(bottomSheetState, onHide = {
            viewModel.sendIntent(TripDetailsScreenIntent.CloseBottomSheet)
        })
        scope.launch {
            if (viewState.bottomSheet != null) {
                bottomSheetState.show()
            } else {
                bottomSheetState.hide()
            }
        }

        OnLifecycleEvent(tripDetailsScope) { event ->
            when (event) {
                Lifecycle.State.Active -> viewModel.sendIntent(TripDetailsScreenIntent.OnResume)
                else -> Unit
            }
        }

        TripDetailsScreen(
            state = viewState,
            bottomSheetState = bottomSheetState,
            onBackClick = { viewModel.sendIntent(TripDetailsScreenIntent.GoBack) },
            onAllFilterSelected = { viewModel.sendIntent(TripDetailsScreenIntent.AllFilterSelected) },
            onExpensesFilterSelected = { viewModel.sendIntent(TripDetailsScreenIntent.ExpensesFilterSelected) },
            onMapFilterSelected = { viewModel.sendIntent(TripDetailsScreenIntent.MapFilterSelected) },
            onPhotosFilterSelected = { viewModel.sendIntent(TripDetailsScreenIntent.PhotosFilterSelected) },
            onOverviewFilterSelected = { viewModel.sendIntent(TripDetailsScreenIntent.OverviewFilterSelected) },
            onDeleteClick = { viewModel.sendIntent(TripDetailsScreenIntent.TryDeleteTrip(it)) },
            onEditClick = { viewModel.sendIntent(TripDetailsScreenIntent.EditTrip(it)) },
            onAddClick = { viewModel.sendIntent(TripDetailsScreenIntent.AddItemClick(it)) },
            onConfirmDeleteClick = { viewModel.sendIntent(TripDetailsScreenIntent.ConfirmDeleteTrip(it)) },
            onCloseBottomSheet = { viewModel.sendIntent(TripDetailsScreenIntent.CloseBottomSheet) },
            onAddTripClick = { viewModel.sendIntent(TripDetailsScreenIntent.AddTripClick) },
            onAddTripPostClick = { viewModel.sendIntent(TripDetailsScreenIntent.AddTripPostClick) },
            onAddExpensesClick = { viewModel.sendIntent(TripDetailsScreenIntent.AddExpensesClick) },
            onAddLocationsClick = { viewModel.sendIntent(TripDetailsScreenIntent.AddLocationsClick) },
            onPostClick = { viewModel.sendIntent(TripDetailsScreenIntent.OnPostClick(it)) },
        )
    }
}
