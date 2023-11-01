package com.homehuddle.common.feature.personal.trippost

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.tripPostScope
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.design.theme.SetBottomSheetListener
import com.homehuddle.common.router.OnLifecycleEvent
import kotlinx.coroutines.launch
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

class TripPostScreenHolder(
    private val tripPost: TripPostModel
) : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel: TripPostScreenViewModel by SharedDI.di.instance(arg = tripPost.id.orEmpty())
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        val scope = rememberCoroutineScope()
        val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )

        SetBottomSheetListener(bottomSheetState, onHide = {
            viewModel.sendIntent(TripPostScreenIntent.CloseBottomSheet)
        })
        scope.launch {
            if (viewState.bottomSheet != null) {
                bottomSheetState.show()
            } else {
                bottomSheetState.hide()
            }
        }

        OnLifecycleEvent(tripPostScope) { event ->
            when (event) {
                Lifecycle.State.Active -> viewModel.sendIntent(TripPostScreenIntent.OnResume)
                else -> Unit
            }
        }

        TripPostScreen(
            state = viewState,
            onBackClick = { viewModel.sendIntent(TripPostScreenIntent.GoBack) },
            onEditClick = { viewModel.sendIntent(TripPostScreenIntent.OnEditClick) },
            onDeleteClick = { viewModel.sendIntent(TripPostScreenIntent.OnDeleteClick) },
            onCancelDelete = { viewModel.sendIntent(TripPostScreenIntent.CloseBottomSheet) },
            onConfirmDeleteClick = { viewModel.sendIntent(TripPostScreenIntent.OnConfirmDelete) },
        )
    }
}
