package com.homehuddle.common.feature.personal.tripdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.design.bottomsheet.BottomSheetComponent
import com.homehuddle.common.design.button.SecondaryButtonComponent
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.spacer.DividerComponent
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.specific.TripDailyExpensesComponent
import com.homehuddle.common.design.specific.TripExpensesCardComponent
import com.homehuddle.common.design.specific.TripExpensesPieChartComponent
import com.homehuddle.common.design.specific.TripPhotoComponent
import com.homehuddle.common.design.specific.TripPostCompactCardComponent
import com.homehuddle.common.design.specific.TripSocialComponent
import com.homehuddle.common.design.specific.TripSummaryComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.theme.textLightSecondary
import com.homehuddle.common.design.topbar.DefaultNavComponent
import com.homehuddle.common.feature.personal.main.AddNewItemBottomSheet

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun TripDetailsScreen(
    state: TripDetailsScreenState,
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    onAllFilterSelected: () -> Unit = {},
    onPhotosFilterSelected: () -> Unit = {},
    onMapFilterSelected: () -> Unit = {},
    onExpensesFilterSelected: () -> Unit = {},
    onOverviewFilterSelected: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onEditClick: (TripModel?) -> Unit = {},
    onDeleteClick: (TripModel?) -> Unit = {},
    onAddClick: (TripModel?) -> Unit = {},
    onAddTripClick: () -> Unit = {},
    onAddTripPostClick: () -> Unit = {},
    onAddExpensesClick: () -> Unit = {},
    onAddLocationsClick: () -> Unit = {},
    onConfirmDeleteClick: (TripModel?) -> Unit = {},
    onCloseBottomSheet: () -> Unit = {},
    onPostClick: (TripPostModel) -> Unit = {},
) {
    GradientScaffold(
        bottomSheetState = bottomSheetState,
        bottomSheet = {
            when (state.bottomSheet) {
                BottomSheetType.AddNewItem -> AddNewItemBottomSheet(
                    onAddTripClick,
                    onAddTripPostClick,
                    onAddExpensesClick,
                    onAddLocationsClick
                )
                BottomSheetType.ConfirmDelete -> BottomSheetComponent(
                    title = "Are you sure you want to delete trip?",
                    description = "This cant be undone",
                    topButton = "Delete",
                    bottomButton = "Cancel",
                    topButtonClick = { onConfirmDeleteClick(state.trip) },
                    bottomButtonClick = { onCloseBottomSheet() }
                )
                null -> {}
            }
        },
        topBar = {
            DefaultNavComponent(
                showBackButton = true,
                showAddButton = true,
                showEditButton = true,
                elementsColor = AppTheme.colors.textLightDefault(),
                onBackClick = onBackClick,
                onAddClick = { onAddClick(state.trip) },
                onEditClick = { onEditClick(state.trip) },
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
                TripSocialComponent(
                    12, 124,
                    paddingTop = AppTheme.indents.x1,
                    modifier = Modifier.padding(horizontal = AppTheme.indents.x3),
                    iconColor = AppTheme.colors.textLightDefault(),
                    textColor = AppTheme.colors.textLightSecondary(),
                    textStyle = AppTheme.typography.body2,
                    iconSize = AppTheme.indents.x3_5,
                    canSubscribe = true,
                    canLike = true
                )
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
                        onExpensesFilterSelected,
                        onOverviewFilterSelected
                    )
                    state.trip?.let {
                        when (state.selectedTab) {
                            TripDetailsTab.All -> AllPostsComponent(state.trip, onPostClick)
                            TripDetailsTab.Photos -> AllPhotosComponent(state.trip)
                            TripDetailsTab.Map -> {}
                            TripDetailsTab.Expenses -> AllExpensesComponent(state.trip)
                            TripDetailsTab.Overview -> OverviewComponent(state.trip, onDeleteClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OverviewComponent(trip: TripModel, onDeleteClick: (TripModel) -> Unit) {
    Column(Modifier.fillMaxSize().padding(horizontal = AppTheme.indents.x3)) {
        if (trip.description.isNotEmpty()) {
            Text(
                text = trip.description,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textDarkDefault()
            )
        }
        Spacer(Modifier.weight(1f))
        SecondaryButtonComponent("Delete", { onDeleteClick(trip) })
        SpacerComponent { x3 }
    }
}

@Composable
private fun AllExpensesComponent(trip: TripModel) {
    LazyColumn(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x2)
    ) {
        item {
            TripExpensesPieChartComponent(trip.expenses, trip.user?.currency)
            SpacerComponent { x3 }
        }
        val expenses = trip.expenses.groupBy { it.date }
        items(expenses.size) {
            val date = expenses.keys.toList()[it]
            val values = expenses.values.toList()[it]
            TripDailyExpensesComponent(
                date = date.orEmpty(),
                expenses = values,
                userModel = trip.user
            )
        }
        item {
            SpacerComponent { x3 }
        }
    }
}

@Composable
private fun AllPhotosComponent(trip: TripModel) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = AppTheme.indents.x3),
        columns = GridCells.Adaptive(minSize = 160.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5),
    ) {
        items(trip.photos) {
            TripPhotoComponent(160.dp, AppTheme.indents.x1_5, url = it)
        }
    }
}

@Composable
private fun AllPostsComponent(
    trip: TripModel,
    onPostClick: (TripPostModel) -> Unit = {},
) {
    LazyColumn(
        Modifier.fillMaxSize()
            .padding(horizontal = AppTheme.indents.x3),
        verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x2)
    ) {
        items(trip.posts) {
            when {
                it.isOnlyExpenses -> TripExpensesCardComponent(
                    trip = trip,
                    tripPost = it,
                    showSocialHeader = false,
                    onClick = onPostClick
                )
                else -> TripPostCompactCardComponent(
                    trip = trip,
                    tripPost = it,
                    showTrip = false,
                    onClick = onPostClick
                )
            }
            DividerComponent()
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
    onOverviewFilterSelected: () -> Unit = {},
) {
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
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
                        TripDetailsTab.Overview -> onOverviewFilterSelected()
                    }
                }
            )
        }
    }
}