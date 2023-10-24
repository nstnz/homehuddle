package com.homehuddle.common.feature.personal.main

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.homehuddle.common.design.mocks.mockTrip
import com.homehuddle.common.design.mocks.mockTripPost
import com.homehuddle.common.design.mocks.mockUser
import com.homehuddle.common.design.navbar.NavigationBarComponent
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.specific.TripCardComponent
import com.homehuddle.common.design.specific.TripPostCardComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.topbar.DefaultNavComponent

@Composable
internal fun MainScreen(
    state: MainScreenState,
    onTripsFilterSelected: () -> Unit = {},
    onPostsFilterSelected: () -> Unit = {},
) {
    GradientScaffold(
        topBar = {
            DefaultNavComponent(
                showBackButton = false,
                showAddButton = true,
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
                    items(10) {
                        if (state.tripsSelected) {
                            TripCardComponent(
                                trip = mockTrip(),
                                ownedByUser = true
                            )
                        } else {
                            TripPostCardComponent(
                                trip = mockTrip(),
                                user = mockUser(),
                                tripPost = mockTripPost()
                            )
                        }
                        Spacer(
                            Modifier.fillMaxWidth().height(AppTheme.indents.x0_125)
                                .background(AppTheme.colors.textDarkDisabled().copy(alpha = 0.1f))
                        )
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
            text = "Trips",
            color = if (state.tripsSelected) AppTheme.colors.textDarkDefault() else AppTheme.colors.textDarkDisabled(),
            style = AppTheme.typography.body2Bold,
            modifier = Modifier.noEffectsClickable { onTripsFilterSelected() }
        )
        Text(
            text = "Posts",
            color = if (!state.tripsSelected) AppTheme.colors.textDarkDefault() else AppTheme.colors.textDarkDisabled(),
            style = AppTheme.typography.body2Bold,
            modifier = Modifier.noEffectsClickable { onPostsFilterSelected() }
        )
    }
}