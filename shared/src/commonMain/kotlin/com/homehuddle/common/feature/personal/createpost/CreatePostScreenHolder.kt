package com.homehuddle.common.feature.personal.createpost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.base.di.createPostScope
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.router.OnLifecycleEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

internal class CreatePostScreenHolder(
    private val tripPost: TripPostModel?,
    private val screenMode: ScreenMode,
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: CreatePostScreenViewModel by SharedDI.di.instance(arg = tripPost?.id.orEmpty())
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(Unit) {
            viewModel.singleEvent.onEach { event ->
                when (event) {
                    is CreatePostScreenSingleEvent.ShowError -> {
                        snackbarHostState.showSnackbar("Errorrrr", isError = true)
                    }
                }
            }.collect()
        }

        OnLifecycleEvent(createPostScope) { event ->
            when (event) {
                Lifecycle.State.Active -> viewModel.sendIntent(CreatePostScreenIntent.OnResume)
                else -> Unit
            }
        }

        CreatePostScreen(
            state = viewState,
            screenMode = screenMode,
            snackbarHostState = snackbarHostState,
            onNameChanged = { viewModel.sendIntent(CreatePostScreenIntent.OnChangeName(it)) },
            onDescriptionChanged = {
                viewModel.sendIntent(
                    CreatePostScreenIntent.OnChangeDescription(
                        it
                    )
                )
            },
            onFromDateSelected = { viewModel.sendIntent(CreatePostScreenIntent.OnFromDateSelected(it)) },
            onToDateSelected = { viewModel.sendIntent(CreatePostScreenIntent.OnToDateSelected(it)) },
            onSaveClick = { viewModel.sendIntent(CreatePostScreenIntent.OnSaveClick) },
            onBackClick = { viewModel.sendIntent(CreatePostScreenIntent.GoBack) }
        )
    }
}
