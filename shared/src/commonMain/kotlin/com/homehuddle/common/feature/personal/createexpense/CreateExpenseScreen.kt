package com.homehuddle.common.feature.personal.createexpense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.base.domain.general.model.TripExpenseCategory
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.utils.Texts
import com.homehuddle.common.design.button.PrimaryButtonComponent
import com.homehuddle.common.design.input.LinedTextInputComponent
import com.homehuddle.common.design.input.TextInputComponent
import com.homehuddle.common.design.input.getTextColorsDark
import com.homehuddle.common.design.loader.LoaderComponent
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.specific.CalendarBottomSheet
import com.homehuddle.common.design.specific.SelectCurrencyBottomSheet
import com.homehuddle.common.design.specific.SelectDateComponent
import com.homehuddle.common.design.specific.SelectTripBottomSheet
import com.homehuddle.common.design.specific.SelectTripComponent
import com.homehuddle.common.design.specific.TripExpenseCategoryComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkBorder
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.topbar.DefaultNavComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CreateExpenseScreen(
    state: CreateExpenseScreenState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onCategoryClick: (TripExpenseCategory) -> Unit = {},
    onSumChanged: (TextFieldValue) -> Unit = {},
    onDescriptionChanged: (TextFieldValue) -> Unit = {},
    onCurrencyClick: () -> Unit = {},
    onChangeCurrency: (CurrencyModel) -> Unit = {},
    onTripClick: () -> Unit = {},
    onChangeTrip: (TripModel) -> Unit = {},
    onDateClick: () -> Unit = {},
    onChangeDate: (Long?) -> Unit = {},
) {
    GradientScaffold(
        snackbarHostState = snackbarHostState,
        bottomSheetState = bottomSheetState,
        bottomSheet = {
            when (state.bottomSheet) {
                is BottomSheetType.SelectCurrency -> SelectCurrencyBottomSheet(
                    title = "Select currency",
                    currencies = state.bottomSheet.currencies,
                    selected = state.bottomSheet.selected,
                    onSelect = onChangeCurrency
                )
                is BottomSheetType.SelectTrip -> SelectTripBottomSheet(
                    title = "Select trip",
                    trips = state.bottomSheet.trips,
                    selected = state.bottomSheet.selected,
                    onSelect = onChangeTrip
                )
                is BottomSheetType.SelectDate -> CalendarBottomSheet(
                    state.bottomSheet.timestamp,
                    onChangeDate,
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
                title = if (state.isCreateMode) "Add expense" else "Edit expense"
            )
        }
    ) {
        Column(
            Modifier.fillMaxSize()
                .background(AppTheme.colors.background2(), AppTheme.shapes.x4_5_top)
                .padding(horizontal = AppTheme.indents.x3)
        ) {
            if (state.model == null) {
                LoaderComponent()
            } else {
                Column(Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState())) {
                    SpacerComponent { x3 }
                    Row(Modifier.fillMaxWidth()) {
                        LinedTextInputComponent(
                            label = "Enter the sum you've spent",
                            modifier = Modifier.weight(1f),
                            value = state.formattedSum,
                            onValueChange = {
                                onSumChanged(it.copy(text = it.text.replace(",", ".")))
                            },
                            maxLength = 10,
                            placeholder = "0",
                            colors = getTextColorsDark(),
                            style = AppTheme.typography.body1Bold,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            )
                        )
                        SpacerComponent { x2 }

                        Column(
                            modifier = Modifier.width(120.dp)
                                .noEffectsClickable { onCurrencyClick() }
                        ) {
                            Text(
                                text = state.model?.currency?.name.orEmpty(),
                                color = AppTheme.colors.textDarkDisabled(),
                                style = AppTheme.typography.body3,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                modifier = Modifier
                                    .width(120.dp)
                                    .background(
                                        AppTheme.colors.textDarkBorder(),
                                        AppTheme.shapes.x1
                                    )
                                    .padding(
                                        top = AppTheme.indents.x1,
                                        bottom = AppTheme.indents.x2
                                    )
                                    .padding(horizontal = AppTheme.indents.x2),
                                text = state.model?.currency?.code.orEmpty(),
                                color = AppTheme.colors.textDarkDefault(),
                                style = AppTheme.typography.body1Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    state.trip?.let {
                        SpacerComponent { x4 }
                        SelectTripComponent(state.trip) {
                            if (state.isCreateMode) {
                                onTripClick()
                            }
                        }
                    }
                    state.model?.date?.let {
                        SpacerComponent { x2 }
                        SelectDateComponent(state.model?.date) {
                            onDateClick()
                        }
                    }
                    SpacerComponent { x2 }
                    Row {
                        Text(
                            modifier = Modifier,
                            text = "Selected category:",
                            style = AppTheme.typography.body3,
                            color = AppTheme.colors.textDarkDisabled()
                        )
                        SpacerComponent { x1 }
                        Text(
                            modifier = Modifier,
                            text = state.model?.category?.name.orEmpty(),
                            style = AppTheme.typography.body3Bold,
                            color = AppTheme.colors.textDarkDefault(),
                        )
                    }
                    SpacerComponent { x2 }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5)
                    ) {
                        TripExpenseCategory.values().toList().chunked(5).forEach {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                it.forEach {
                                    TripExpenseCategoryComponent(
                                        it,
                                        state.model?.category == it,
                                        AppTheme.indents.x6
                                    ) {
                                        onCategoryClick(it)
                                    }
                                }
                            }
                        }
                    }
                    SpacerComponent { x4 }
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
                        minHeight = AppTheme.indents.x8
                    )
                }
                SpacerComponent { x3 }
                PrimaryButtonComponent(
                    text = Texts.Save,
                    onClick = onSaveClick,
                    modifier = Modifier.padding(bottom = AppTheme.indents.x3)
                )
            }
        }
    }
}