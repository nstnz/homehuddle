package com.homehuddle.common.feature.personal.trippost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.design.mocks.mockUser
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.specific.TripDailyExpensesComponent
import com.homehuddle.common.design.specific.TripPhotoComponent
import com.homehuddle.common.design.specific.TripPostCardComponent
import com.homehuddle.common.design.specific.TripPostUserSummaryComponent
import com.homehuddle.common.design.specific.TripSocialComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.theme.textLightSecondary
import com.homehuddle.common.design.topbar.DefaultNavComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun TripPostScreen(
    state: TripPostScreenState,
    onAllFilterSelected: () -> Unit = {},
    onPhotosFilterSelected: () -> Unit = {},
    onMapFilterSelected: () -> Unit = {},
    onExpensesFilterSelected: () -> Unit = {},
) {
    GradientScaffold(
        topBar = {
            DefaultNavComponent(
                showBackButton = true,
                showAddButton = true,
                elementsColor = AppTheme.colors.textLightDefault()
            )
        }
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            state.trip?.let {
                state.tripPost?.let {
                    TripPostUserSummaryComponent(
                        state.trip, state.tripPost,
                        user = mockUser(),
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = AppTheme.indents.x3),
                        textColor = AppTheme.colors.textLightDefault(),
                        accentColor = AppTheme.colors.textLightDefault(),
                        hintColor = AppTheme.colors.textLightSecondary(),
                        textStyle = AppTheme.typography.body1,
                        hintTextStyle = AppTheme.typography.body3,
                        accentTextStyle = AppTheme.typography.body1Bold,
                        imageSize = AppTheme.indents.x7,
                        padding = AppTheme.indents.x2,
                    )
                    SpacerComponent { x1 }
                    TripSocialComponent(
                        12, 124,
                        modifier = Modifier.padding(horizontal = AppTheme.indents.x3),
                        iconColor = AppTheme.colors.textLightDefault(),
                        textColor = AppTheme.colors.textLightSecondary(),
                        textStyle = AppTheme.typography.body2,
                        iconSize = AppTheme.indents.x3_5,
                        canSubscribe = false,
                        canLike = true
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
                    TabsComponent(
                        state,
                        onAllFilterSelected,
                        onPhotosFilterSelected,
                        onMapFilterSelected,
                        onExpensesFilterSelected
                    )
                    state.trip?.let {
                        state.tripPost?.let {
                            when (state.selectedTab) {
                                TripPostTab.All -> AllPostComponent(state)
                                TripPostTab.Photos -> AllPhotosComponent(state.tripPost)
                                TripPostTab.Map -> {}
                                TripPostTab.Expenses -> AllExpensesComponent(state.tripPost)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AllExpensesComponent(tripPost: TripPostModel) {
    TripDailyExpensesComponent(
        date = tripPost.dateStart.orEmpty(),
        expenses = tripPost.expenses,
    )
}

@Composable
private fun AllPhotosComponent(tripPost: TripPostModel) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = AppTheme.indents.x3),
        columns = GridCells.Adaptive(minSize = 160.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5),
    ) {
        items(tripPost.photos) {
            TripPhotoComponent(160.dp, AppTheme.indents.x1_5)
        }
    }
}

@Composable
private fun AllPostComponent(state: TripPostScreenState) {
    Column(
        Modifier.fillMaxWidth().padding(horizontal = AppTheme.indents.x3)
            .verticalScroll(rememberScrollState())
    ) {
        state.trip?.let {
            state.tripPost?.let {
                TripPostCardComponent(
                    trip = state.trip,
                    tripPost = state.tripPost,
                    user = mockUser(),
                    modifier = Modifier.fillMaxWidth(),
                    showFullInfo = true
                )
            }
        }

    }
}

@Composable
private fun TabsComponent(
    state: TripPostScreenState,
    onAllFilterSelected: () -> Unit = {},
    onPhotosFilterSelected: () -> Unit = {},
    onMapFilterSelected: () -> Unit = {},
    onExpensesFilterSelected: () -> Unit = {},
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.indents.x3, vertical = AppTheme.indents.x2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x4)
    ) {
        TripPostTab.values().forEach {
            Text(
                text = it.name,
                color = if (state.selectedTab == it) AppTheme.colors.textDarkDefault() else AppTheme.colors.textDarkDisabled(),
                style = AppTheme.typography.body2Bold,
                modifier = Modifier.noEffectsClickable {
                    when (it) {
                        TripPostTab.All -> onAllFilterSelected()
                        TripPostTab.Photos -> onPhotosFilterSelected()
                        TripPostTab.Map -> onMapFilterSelected()
                        TripPostTab.Expenses -> onExpensesFilterSelected()
                    }
                }
            )
        }
    }
}