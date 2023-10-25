package com.homehuddle.common.feature.personal.trippost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import org.kodein.di.instance

class TripPostScreenHolder(
    private val tripPost: TripPostModel
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: TripPostScreenViewModel by SharedDI.di.instance()
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        TripPostScreen(
            state = viewState
        )
    }
}
