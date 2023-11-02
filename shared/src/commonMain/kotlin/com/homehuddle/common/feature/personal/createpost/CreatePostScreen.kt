package com.homehuddle.common.feature.personal.createpost

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.CountryModel
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.utils.Texts
import com.homehuddle.common.design.button.PrimaryButtonComponent
import com.homehuddle.common.design.input.LinedTextInputComponent
import com.homehuddle.common.design.input.TextInputComponent
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.specific.CalendarBottomSheet
import com.homehuddle.common.design.specific.CountriesChipsComponent
import com.homehuddle.common.design.specific.SelectCountryBottomSheet
import com.homehuddle.common.design.specific.SelectTripBottomSheet
import com.homehuddle.common.design.specific.SelectTripComponent
import com.homehuddle.common.design.specific.TripExpenseCategoryComponent
import com.homehuddle.common.design.specific.TripPhotoComponent
import com.homehuddle.common.design.specific.getCategoryColor
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent4
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.dashedBorder
import com.homehuddle.common.design.theme.ignoreHorizontalParentPadding
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkBorder
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textDarkSecondary
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.topbar.DefaultNavComponent
import dev.icerock.moko.media.Bitmap

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CreatePostScreen(
    state: CreatePostScreenState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    onNameChanged: (TextFieldValue) -> Unit = {},
    onDescriptionChanged: (TextFieldValue) -> Unit = {},
    onFromDateSelected: (Long?) -> Unit = {},
    onToDateSelected: (Long?) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onTripClick: () -> Unit = {},
    onFromDateClick: () -> Unit = {},
    onToDateClick: () -> Unit = {},
    onAddNewExpense: () -> Unit = {},
    onDeleteExpense: (TripExpenseModel) -> Unit = {},
    onAddNewCountry: () -> Unit = {},
    onSelectCountry: (CountryModel) -> Unit = {},
    onDeleteCountry: (CountryModel) -> Unit = {},
    onAddPhotoClick: () -> Unit = {},
    onDeletePhotoClick: (Any) -> Unit = {},
    onChangeTrip: (TripModel) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }

    GradientScaffold(
        snackbarHostState = snackbarHostState,
        bottomSheetState = bottomSheetState,
        bottomSheet = {
            when (state.bottomSheet) {
                BottomSheetType.SelectCountry -> SelectCountryBottomSheet(
                    title = "Select country",
                    countries = state.userModel?.allCountries.orEmpty(),
                    onSelect = onSelectCountry
                )
                is BottomSheetType.SelectFromDate -> CalendarBottomSheet(
                    state.bottomSheet.timestamp,
                    onFromDateSelected,
                )
                is BottomSheetType.SelectToDate -> CalendarBottomSheet(
                    state.bottomSheet.timestamp,
                    onToDateSelected,
                )
                is BottomSheetType.SelectTrip -> SelectTripBottomSheet(
                    title = "Select trip",
                    trips = state.bottomSheet.trips,
                    selected = state.bottomSheet.selected,
                    onSelect = onChangeTrip
                )
                null -> {}
            }
        },
        topBar = {
            DefaultNavComponent(
                showBackButton = true,
                showAddButton = false,
                elementsColor = AppTheme.colors.textLightDefault(),
                onBackClick = onBackClick,
                title = if (state.isCreateMode) "Add post" else "Edit post"
            )
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(
                Modifier.fillMaxSize()
            ) {
                LinedTextInputComponent(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.indents.x3)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = state.name,
                    onValueChange = {
                        onNameChanged(it)
                    },
                    placeholder = Texts.TripPostName,
                    label = Texts.TripPostLabel,
                    style = AppTheme.typography.body1Bold
                )
                SpacerComponent { x3 }

                Column(
                    Modifier.fillMaxSize().background(
                        AppTheme.colors.background2(),
                        AppTheme.shapes.x4_5_top
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        Modifier.fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = AppTheme.indents.x3),
                    ) {
                        state.trip?.let {
                            SpacerComponent { x4 }
                            SelectTripComponent(state.trip) {
                                if (state.isCreateMode) {
                                    onTripClick()
                                }
                            }
                        }

                        SpacerComponent { x2 }
                        SelectDatesComponent(
                            from = state.model?.dateStart.orEmpty(),
                            to = state.model?.dateEnd.orEmpty(),
                            onFromClick = onFromDateClick,
                            onToClick = onToDateClick
                        )

                        SpacerComponent { x2 }
                        PostBlock(state, onDescriptionChanged, onAddPhotoClick, onDeletePhotoClick)

                        SpacerComponent { x3 }
                        SelectCountriesComponent(
                            state.model?.countries.orEmpty(),
                            onClick = onAddNewCountry,
                            onDelete = onDeleteCountry
                        )

                        PointsBlock()
                        ExpensesBlock(state.model, onAddNewExpense, onDeleteExpense)
                        SpacerComponent { x10 }
                    }
                    PrimaryButtonComponent(
                        text = Texts.Save,
                        onClick = onSaveClick,
                        modifier = Modifier.padding(horizontal = AppTheme.indents.x3)
                            .padding(bottom = AppTheme.indents.x3)
                    )
                }
            }
        }
    }
}

@Composable
private fun PointsBlock() {
    SpacerComponent { x3 }
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Points",
        style = AppTheme.typography.body2Bold,
        color = AppTheme.colors.textDarkDefault()
    )
}

@Composable
private fun ExpensesBlock(
    tripPostModel: TripPostModel?,
    onAddNewExpense: () -> Unit = {},
    onDelete: (TripExpenseModel) -> Unit = {}
) {
    SpacerComponent { x3 }
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Expenses",
        style = AppTheme.typography.body2Bold,
        color = AppTheme.colors.textDarkDefault()
    )
    SpacerComponent { x1_5 }
    BoxWithConstraints {
        val width = (this.maxWidth - AppTheme.indents.x1_5) / 2
        val size = DpSize(width, AppTheme.indents.x8_5)
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5)
            ) {
                NewExpenseButton(Modifier.size(size), onAddNewExpense)
                tripPostModel?.expenses?.getOrNull(0)
                    ?.let { ExpenseButton(it, Modifier.size(size), onDelete) }
            }
            tripPostModel?.expenses?.drop(1)?.chunked(2)?.forEach {
                SpacerComponent { x1_5 }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5)
                ) {
                    it.getOrNull(0)?.let { ExpenseButton(it, Modifier.size(size), onDelete) }
                    it.getOrNull(1)?.let { ExpenseButton(it, Modifier.size(size), onDelete) }
                }
            }
        }
    }
}

@Composable
private fun ExpenseButton(
    expenseModel: TripExpenseModel,
    modifier: Modifier,
    onDelete: (TripExpenseModel) -> Unit = {}
) {
    Column(
        modifier
            .background(
                getCategoryColor(expenseModel.category).copy(alpha = 0.06f),
                AppTheme.shapes.x1_5
            )
            .padding(AppTheme.indents.x1),
    ) {
        Row(Modifier.fillMaxWidth()) {
            TripExpenseCategoryComponent(
                expenseModel.category,
                selected = true,
                size = AppTheme.indents.x3,
            )
            SpacerComponent { x1 }
            Text(
                text = expenseModel.category.name,
                color = AppTheme.colors.textDarkDisabled(),
                style = AppTheme.typography.body3,
                maxLines = 1
            )
            Spacer(Modifier.weight(1f))
            Image(
                Icons.Rounded.Clear,
                contentDescription = null,
                modifier = Modifier.size(AppTheme.indents.x3)
                    .noEffectsClickable { onDelete(expenseModel) },
                colorFilter = ColorFilter.tint(
                    AppTheme.colors.textDarkDisabled()
                )
            )
        }
        SpacerComponent { x0_5 }
        Text(
            text = expenseModel.formattedSum,
            color = AppTheme.colors.textDarkDefault(),
            style = AppTheme.typography.body2Bold,
            maxLines = 1
        )
    }
}

@Composable
private fun NewExpenseButton(
    modifier: Modifier,
    onClick: () -> Unit = {}
) {
    Row(modifier
        .noEffectsClickable { onClick() }
        .dashedBorder(
            width = 1.dp,
            color = AppTheme.colors.textDarkBorder(),
            shape = AppTheme.shapes.x1_5,
            on = 8.dp,
            off = 4.dp
        )
        .padding(AppTheme.indents.x1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            Icons.Rounded.AddCircleOutline,
            contentDescription = null,
            modifier = Modifier.size(AppTheme.indents.x3),
            colorFilter = ColorFilter.tint(
                AppTheme.colors.textDarkDisabled()
            )
        )
        SpacerComponent { x1 }
        Text(
            text = "Add expense",
            color = AppTheme.colors.textDarkSecondary(),
            style = AppTheme.typography.body3,
        )
    }
}

@Composable
private fun PostBlock(
    state: CreatePostScreenState,
    onDescriptionChanged: (TextFieldValue) -> Unit = {},
    onAddPhotoClick: () -> Unit = {},
    onDeletePhotoClick: (Any) -> Unit = {},
) {
    TextInputComponent(
        modifier = Modifier.fillMaxWidth(),
        value = state.description,
        onValueChange = {
            onDescriptionChanged(it)
        },
        singleLine = false,
        label = Texts.TripDescriptionLabel,
        placeholder = Texts.TripDescription,
        style = AppTheme.typography.body2,
        minHeight = AppTheme.indents.x15
    )

    SpacerComponent { x2 }
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Add some photos",
        style = AppTheme.typography.body3,
        color = AppTheme.colors.textDarkDisabled()
    )
    SpacerComponent { x1 }
    val size = AppTheme.indents.x11
    Box( Modifier
        .fillMaxWidth()
        .height(size)
        .ignoreHorizontalParentPadding(AppTheme.indents.x3)) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(size)
                .horizontalScroll(rememberScrollState()),
        ) {
            SpacerComponent { x3 }
            Column(
                Modifier
                    .size(size)
                    .noEffectsClickable { onAddPhotoClick() }
                    .dashedBorder(
                        width = 1.dp,
                        color = AppTheme.colors.textDarkBorder(),
                        shape = AppTheme.shapes.x1_5,
                        on = 8.dp,
                        off = 4.dp
                    )
                    .padding(horizontal = AppTheme.indents.x1),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.weight(1f))
                Image(
                    Icons.Rounded.AddCircleOutline,
                    contentDescription = null,
                    modifier = Modifier.size(AppTheme.indents.x3),
                    colorFilter = ColorFilter.tint(
                        AppTheme.colors.textDarkDisabled()
                    )
                )
                SpacerComponent { x1 }
                Text(
                    text = "Add photo",
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDefault(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.weight(1f))
            }
            SpacerComponent { x1_5 }
            state.bitmaps.forEach {
                Box {
                    TripPhotoComponent(
                        size,
                        AppTheme.indents.x1_5,
                        it as? Bitmap,
                        it as? String,
                    )
                    Image(
                        Icons.Rounded.Cancel,
                        contentDescription = null,
                        modifier = Modifier
                            .noEffectsClickable { onDeletePhotoClick(it) }
                            .padding(AppTheme.indents.x0_5)
                            .size(AppTheme.indents.x3).align(Alignment.TopEnd),
                        colorFilter = ColorFilter.tint(
                            AppTheme.colors.textDarkDisabled()
                        )
                    )
                }
                SpacerComponent { x1_5 }
            }
            SpacerComponent { x1_5 }
        }
    }
}

@Composable
private fun SelectCountriesComponent(
    countries: List<CountryModel>,
    onClick: () -> Unit = {},
    onDelete: (CountryModel) -> Unit = {},
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Countries",
        style = AppTheme.typography.body2Bold,
        color = AppTheme.colors.textDarkDefault()
    )
    SpacerComponent { x1_5 }
    CountriesChipsComponent(
        countries, onClick, onDelete
    )
}

@Composable
internal fun SelectDatesComponent(
    from: String,
    to: String,
    onFromClick: () -> Unit = {},
    onToClick: () -> Unit = {}
) {
    Row(Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier,
            text = "Dates:",
            style = AppTheme.typography.body3,
            color = AppTheme.colors.textDarkDisabled()
        )
        SpacerComponent { x1 }
        Text(
            text = from,
            style = AppTheme.typography.body3Bold,
            color = AppTheme.colors.accent4(),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.noEffectsClickable { onFromClick() }
        )
        SpacerComponent { x0_5 }
        Text(
            text = "—",
            style = AppTheme.typography.body3Bold,
            color = AppTheme.colors.textDarkDisabled(),
        )
        SpacerComponent { x0_5 }
        Text(
            text = to,
            style = AppTheme.typography.body3Bold,
            color = AppTheme.colors.accent4(),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.noEffectsClickable { onToClick() }
        )
    }
}