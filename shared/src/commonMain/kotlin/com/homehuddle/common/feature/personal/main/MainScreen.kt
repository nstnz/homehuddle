package com.homehuddle.common.feature.personal.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.utils.Texts
import com.homehuddle.common.design.navbar.NavigationBarComponent
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.spacer.DividerComponent
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.specific.EmptyStateComponent
import com.homehuddle.common.design.specific.TripCardComponent
import com.homehuddle.common.design.specific.TripPostCompactCardComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.topbar.DefaultNavComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MainScreen(
    state: MainScreenState,
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    onTripsFilterSelected: () -> Unit = {},
    onPostsFilterSelected: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onAddTripClick: () -> Unit = {},
    onAddTripPostClick: () -> Unit = {},
    onAddExpensesClick: () -> Unit = {},
    onAddLocationsClick: () -> Unit = {},
    onTripClick: (TripModel) -> Unit = {},
    onTripPostClick: (TripPostModel) -> Unit = {},
) {
    GradientScaffold(
        bottomSheetState = bottomSheetState,
        bottomSheet = {
            AddNewItemBottomSheet(
                onAddTripClick, onAddTripPostClick, onAddExpensesClick, onAddLocationsClick
            )
        },
        topBar = {
            DefaultNavComponent(
                showBackButton = false,
                showAddButton = true,
                onAddClick = onAddClick,
            )
        }
    ) {
        Box(
            Modifier.fillMaxSize()
                .background(
                    AppTheme.colors.background2(),
                    RoundedCornerShape(topStart = AppTheme.indents.x4, topEnd = AppTheme.indents.x4)
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(Modifier.fillMaxSize()) {
                TabsComponent(state, onTripsFilterSelected, onPostsFilterSelected)
                LazyColumn(
                    Modifier.fillMaxSize()
                        .padding(horizontal = AppTheme.indents.x3),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x2)
                ) {
                    if (state.tripsSelected) {
                        if (state.trips.isEmpty()) {
                            item {
                                SpacerComponent { x20 }
                                EmptyStateComponent(
                                    "Add new trip!",
                                    "jhajksd khaldskhal ksdhlkadhl ka",
                                    "Add trip",
                                    onAddTripClick
                                )
                            }
                        } else {
                            items(state.trips) {
                                TripCardComponent(
                                    trip = it,
                                    onClick = onTripClick
                                )
                                DividerComponent()
                            }
                        }
                    } else {
                        if (state.posts.isEmpty()) {
                            item {
                                SpacerComponent { x20 }
                                EmptyStateComponent(
                                    "Add new post!",
                                    "jhajksd khaldskhal ksdhlkadhl ka",
                                    "Add post",
                                    onAddTripPostClick
                                )
                            }
                        } else {
                            items(state.posts) { post ->
                                TripPostCompactCardComponent(
                                    trip = state.trips.first { it.id == post.tripId },
                                    tripPost = post,
                                    onClick = onTripPostClick
                                )
                                DividerComponent()
                            }
                        }
                    }
                    item {
                        SpacerComponent { x5 + x8 }
                    }
                }
            }

            NavigationBarComponent(
                personalTabSelected = true,
                personalTabClick = {},
                feedTabClick = {},
                settingsTabClick = {},
                searchTabClick = {},
            )
        }
    }
}

@Composable
private fun TabsComponent(
    state: MainScreenState,
    onTripsFilterSelected: () -> Unit = {},
    onPostsFilterSelected: () -> Unit = {},
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.indents.x3, vertical = AppTheme.indents.x2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x4)
    ) {
        Text(
            text = Texts.Trips,
            color = if (state.tripsSelected) AppTheme.colors.textDarkDefault() else AppTheme.colors.textDarkDisabled(),
            style = AppTheme.typography.body2Bold,
            modifier = Modifier.noEffectsClickable { onTripsFilterSelected() }
        )
        Text(
            text = Texts.Posts,
            color = if (!state.tripsSelected) AppTheme.colors.textDarkDefault() else AppTheme.colors.textDarkDisabled(),
            style = AppTheme.typography.body2Bold,
            modifier = Modifier.noEffectsClickable { onPostsFilterSelected() }
        )
    }
}