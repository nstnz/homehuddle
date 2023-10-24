package com.homehuddle.common.feature.personal.tripdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.design.mocks.mockTripExpense
import com.homehuddle.common.design.mocks.mockTripPost
import com.homehuddle.common.design.mocks.mockUser
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.specific.TripDailyExpensesComponent
import com.homehuddle.common.design.specific.TripPhotoComponent
import com.homehuddle.common.design.specific.TripPostCompactCardComponent
import com.homehuddle.common.design.specific.TripSummaryComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.topbar.DefaultNavComponent

@Composable
internal fun TripDetailsScreen(
    state: TripDetailsScreenState,
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
            Text(
                text = state.trip?.name.orEmpty(),
                style = AppTheme.typography.body1Bold,
                color = AppTheme.colors.textLightDefault(),
                textAlign = TextAlign.End,
                modifier = Modifier.padding(horizontal = AppTheme.indents.x3)
            )
            state.trip?.let {
                SpacerComponent { x1 }
                TripSummaryComponent(
                    state.trip,
                    modifier = Modifier.padding(horizontal = AppTheme.indents.x3),
                    iconColor = AppTheme.colors.textLightDefault(),
                    textColor = AppTheme.colors.textLightDefault()
                )
            }
            SpacerComponent { x2 }
            Box(
                Modifier.fillMaxSize()
                    .background(
                        AppTheme.colors.background2(),
                        RoundedCornerShape(
                            topStart = AppTheme.indents.x4,
                            topEnd = AppTheme.indents.x4
                        )
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
                        when (state.selectedTab) {
                            TripDetailsTab.All -> AllPostsComponent(state.trip)
                            TripDetailsTab.Photos -> AllPhotosComponent(state.trip)
                            TripDetailsTab.Map -> {}
                            TripDetailsTab.Expenses -> AllExpensesComponent(state.trip)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AllExpensesComponent(trip: Trip) {
    LazyColumn(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x2)
    ) {
        items(10) {
            TripDailyExpensesComponent(
                date = "10.1000.1039211",
                expenses = listOf(
                    mockTripExpense(),
                    mockTripExpense(),
                    mockTripExpense(),
                    mockTripExpense(),
                ),
            )
        }
        item {
            SpacerComponent { x3 }
        }
    }
}

@Composable
private fun AllPhotosComponent(trip: Trip) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = AppTheme.indents.x3),
        columns = GridCells.Adaptive(minSize = 160.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5),
    ) {
        items(10) {
            TripPhotoComponent(160.dp, AppTheme.indents.x1_5)
        }
    }
}

@Composable
private fun AllPostsComponent(trip: Trip) {
    LazyColumn(
        Modifier.fillMaxSize()
            .padding(horizontal = AppTheme.indents.x3),
        verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x2)
    ) {
        items(10) {
            TripPostCompactCardComponent(
                trip = trip,
                user = mockUser(),
                tripPost = mockTripPost()
            )
            Spacer(
                Modifier.fillMaxWidth().height(AppTheme.indents.x0_125)
                    .background(
                        AppTheme.colors.textDarkDisabled()
                            .copy(alpha = 0.1f)
                    )
            )
        }
        item {
            SpacerComponent { x3 }
        }
    }
}

@Composable
private fun TabsComponent(
    state: TripDetailsScreenState,
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
        TripDetailsTab.values().forEach {
            Text(
                text = it.name,
                color = if (state.selectedTab == it) AppTheme.colors.textDarkDefault() else AppTheme.colors.textDarkDisabled(),
                style = AppTheme.typography.body2Bold,
                modifier = Modifier.noEffectsClickable {
                    when (it) {
                        TripDetailsTab.All -> onAllFilterSelected()
                        TripDetailsTab.Photos -> onPhotosFilterSelected()
                        TripDetailsTab.Map -> onMapFilterSelected()
                        TripDetailsTab.Expenses -> onExpensesFilterSelected()
                    }
                }
            )
        }
    }
}