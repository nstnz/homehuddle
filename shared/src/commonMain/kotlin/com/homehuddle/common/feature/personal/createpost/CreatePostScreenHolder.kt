package com.homehuddle.common.feature.personal.createpost

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
import com.homehuddle.common.base.di.createPostScope
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.ui.collectAsStateLifecycleAware
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.design.theme.SetBottomSheetListener
import com.homehuddle.common.router.OnLifecycleEvent
import dev.icerock.moko.media.compose.BindMediaPickerEffect
import dev.icerock.moko.media.compose.rememberMediaPickerControllerFactory
import dev.icerock.moko.media.picker.MediaSource
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import moe.tlaster.precompose.lifecycle.Lifecycle
import org.kodein.di.instance

internal class CreatePostScreenHolder(
    private val tripPost: TripPostModel?,
) : Screen {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel: CreatePostScreenViewModel by SharedDI.di.instance(arg = tripPost?.id.orEmpty())
        val viewState by viewModel.viewState.collectAsStateLifecycleAware()

        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )

        val mediaFactory = rememberMediaPickerControllerFactory()
        val picker = remember(mediaFactory) { mediaFactory.createMediaPickerController() }
        val coroutineScope = rememberCoroutineScope()
        val permissionFactory = rememberPermissionsControllerFactory()
        val permissionController =
            remember(permissionFactory) { permissionFactory.createPermissionsController() }
        BindEffect(permissionController)
        BindMediaPickerEffect(picker)

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

        SetBottomSheetListener(bottomSheetState, onHide = {
            viewModel.sendIntent(CreatePostScreenIntent.CloseBottomSheet)
        })
        scope.launch {
            if (viewState.bottomSheet != null) {
                bottomSheetState.show()
            } else {
                bottomSheetState.hide()
            }
        }

        CreatePostScreen(
            state = viewState,
            bottomSheetState = bottomSheetState,
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
            onBackClick = { viewModel.sendIntent(CreatePostScreenIntent.GoBack) },
            onFromDateClick = { viewModel.sendIntent(CreatePostScreenIntent.OnFromDateClick) },
            onToDateClick = { viewModel.sendIntent(CreatePostScreenIntent.OnToDateClick) },
            onAddNewCountry = { viewModel.sendIntent(CreatePostScreenIntent.OnAddNewCountry) },
            onAddNewExpense = { viewModel.sendIntent(CreatePostScreenIntent.OnAddNewExpense) },
            onAddPhotoClick = {
                coroutineScope.launch {
                    try {
                        permissionController.providePermission(Permission.GALLERY)
                        val result = picker.pickImage(MediaSource.GALLERY)
                        viewModel.sendIntent(CreatePostScreenIntent.OnAddPhoto(result))
                    } catch (_: Exception) {

                    }
                }
            },
            onTripClick = { viewModel.sendIntent(CreatePostScreenIntent.OnTripClick) },
            onChangeTrip = { viewModel.sendIntent(CreatePostScreenIntent.OnChangeTrip(it)) },
            onDeleteCountry = { viewModel.sendIntent(CreatePostScreenIntent.OnDeleteCountry(it)) },
            onDeleteExpense = { viewModel.sendIntent(CreatePostScreenIntent.OnDeleteExpense(it)) },
            onDeletePhotoClick = { viewModel.sendIntent(CreatePostScreenIntent.OnDeletePhotoClick(it)) },
            onSelectCountry = { viewModel.sendIntent(CreatePostScreenIntent.OnSelectCountry(it)) },
        )
    }
}
