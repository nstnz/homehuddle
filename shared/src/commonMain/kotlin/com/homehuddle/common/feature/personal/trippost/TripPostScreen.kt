package com.homehuddle.common.feature.personal.trippost

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.design.bottomsheet.BottomSheetComponent
import com.homehuddle.common.design.button.SecondaryButtonComponent
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.specific.TripExpensesCardComponent
import com.homehuddle.common.design.specific.TripPhotoComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.ignoreHorizontalParentPadding
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.topbar.DefaultNavComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun TripPostScreen(
    state: TripPostScreenState,
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    onBackClick: () -> Unit = {},
    onEditClick: (TripPostModel?) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onConfirmDeleteClick: () -> Unit = {},
    onCancelDelete: () -> Unit = {},
) {
    GradientScaffold(
        topBar = {
            DefaultNavComponent(
                showBackButton = true,
                showEditButton = true,
                elementsColor = AppTheme.colors.textLightDefault(),
                onBackClick = onBackClick,
                onEditClick = { onEditClick(state.tripPost) },
            )
        },
        bottomSheetState = bottomSheetState,
        bottomSheet = {
            when (state.bottomSheet) {
                BottomSheetType.ConfirmDelete -> BottomSheetComponent(
                    title = "Are you sure you want to delete post?",
                    description = "This cant be undone",
                    topButton = "Delete",
                    bottomButton = "Cancel",
                    topButtonClick = { onConfirmDeleteClick() },
                    bottomButtonClick = { onCancelDelete() }
                )
                null -> {}
            }
        },
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            state.trip?.let {
                state.tripPost?.let {
                    Text(
                        text = state.tripPost.name,
                        style = AppTheme.typography.body1Bold,
                        color = AppTheme.colors.textLightDefault(),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = AppTheme.indents.x3)
                    )
                    SpacerComponent { x0_5 }
                    Text(
                        text = state.tripPost.dateStart + " - " + state.tripPost.dateEnd,
                        style = AppTheme.typography.body3,
                        color = AppTheme.colors.textLightDefault(),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = AppTheme.indents.x3)
                    )
                }
            }
            SpacerComponent { x2 }
            Box(
                Modifier.fillMaxSize()
                    .background(
                        AppTheme.colors.background2(),
                        AppTheme.shapes.x4_5_top
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(Modifier.fillMaxSize()) {
                    SpacerComponent { x3 }
                    state.trip?.let {
                        state.tripPost?.let {
                            AllPostComponent(state, onDeleteClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AllPostComponent(
    state: TripPostScreenState,
    onDeleteClick: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth().padding(horizontal = AppTheme.indents.x3)
            .verticalScroll(rememberScrollState())
    ) {
        state.trip?.let {
            state.tripPost?.let {
                if (it.photos.isNotEmpty()) {
                    PhotosComponent(it, 320.dp)
                    SpacerComponent { x2 }
                }
                if (it.description.isNotEmpty()) {
                    Text(
                        text = it.description,
                        style = AppTheme.typography.body2,
                        color = AppTheme.colors.textDarkDefault(),
                    )
                    SpacerComponent { x6 }
                }

                if (it.hasExpenses) {
                    TripExpensesCardComponent(
                        expenses = it.expenses,
                        userModel = state.trip.user,
                        modifier = Modifier.fillMaxWidth()
                    )
                    SpacerComponent { x6 }
                }
                SpacerComponent { x10 }
                SecondaryButtonComponent("Delete", { onDeleteClick() })
                SpacerComponent { x3 }
            }
        }
    }
}

@Composable
private fun PhotosComponent(
    tripPost: TripPostModel,
    size: Dp
) {
    Box(Modifier.fillMaxWidth().ignoreHorizontalParentPadding(AppTheme.indents.x3)) {
        Row(
            modifier = Modifier.fillMaxWidth().height(size).horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5)
        ) {
            SpacerComponent { x1_5 }
            tripPost.photos.forEach {
                TripPhotoComponent(
                    size, AppTheme.indents.x1_5, url = it
                )
            }
            SpacerComponent { x1_5 }
        }
    }
}