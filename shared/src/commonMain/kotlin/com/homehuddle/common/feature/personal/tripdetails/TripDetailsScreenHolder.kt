package com.homehuddle.common.feature.personal.tripdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import org.kodein.di.instance

class TripDetailsScreenHolder(
    private val trip: TripModel
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: TripDetailsScreenViewModel by SharedDI.di.instance()
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        TripDetailsScreen(
            state = viewState
        )
    }
}
