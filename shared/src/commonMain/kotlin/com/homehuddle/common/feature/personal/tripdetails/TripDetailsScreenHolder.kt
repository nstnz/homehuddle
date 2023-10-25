package com.homehuddle.common.feature.personal.tripdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.tripDetailsScope
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.router.OnLifecycleEvent
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

class TripDetailsScreenHolder(
    private val trip: TripModel
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: TripDetailsScreenViewModel by SharedDI.di.instance(arg = trip.id.orEmpty())
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        OnLifecycleEvent(tripDetailsScope) { event ->
            when (event) {
                Lifecycle.State.Active -> viewModel.sendIntent(TripDetailsScreenIntent.OnResume)
                else -> Unit
            }
        }

        TripDetailsScreen(
            state = viewState,
            onBackClick = { viewModel.sendIntent(TripDetailsScreenIntent.GoBack) },
            onAllFilterSelected = { viewModel.sendIntent(TripDetailsScreenIntent.AllFilterSelected) },
            onExpensesFilterSelected = { viewModel.sendIntent(TripDetailsScreenIntent.ExpensesFilterSelected) },
            onMapFilterSelected = { viewModel.sendIntent(TripDetailsScreenIntent.MapFilterSelected) },
            onPhotosFilterSelected = { viewModel.sendIntent(TripDetailsScreenIntent.PhotosFilterSelected) },
            onOverviewFilterSelected = { viewModel.sendIntent(TripDetailsScreenIntent.OverviewFilterSelected) },
            onDeleteClick = { viewModel.sendIntent(TripDetailsScreenIntent.DeleteTrip(it)) },
            onEditClick = { viewModel.sendIntent(TripDetailsScreenIntent.EditTrip(it)) },
        )
    }
}
