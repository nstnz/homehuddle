package com.homehuddle.common.feature.personal.createpoint

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPointModel
import com.homehuddle.common.base.domain.utils.Texts
import com.homehuddle.common.design.button.PrimaryButtonComponent
import com.homehuddle.common.design.input.LinedTextInputComponent
import com.homehuddle.common.design.input.TextInputComponent
import com.homehuddle.common.design.input.getTextColorsDark
import com.homehuddle.common.design.loader.LoaderComponent
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.specific.PointComponent
import com.homehuddle.common.design.specific.SelectTripBottomSheet
import com.homehuddle.common.design.specific.SelectTripComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.topbar.DefaultNavComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CreatePointScreen(
    state: CreatePointScreenState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onNameChanged: (TextFieldValue) -> Unit = {},
    onDescriptionChanged: (TextFieldValue) -> Unit = {},
    onTripClick: () -> Unit = {},
    onChangeTrip: (TripModel) -> Unit = {},
    onChangeLocation: (TripPointModel) -> Unit = {},
) {
    GradientScaffold(
        snackbarHostState = snackbarHostState,
        bottomSheetState = bottomSheetState,
        bottomSheet = {
            when (state.bottomSheet) {
                is BottomSheetType.SelectLocation -> {}
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
                title = if (state.isCreateMode) "Add location" else "Edit location"
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
                    LinedTextInputComponent(
                        label = "Enter the name of the location",
                        modifier = Modifier.fillMaxWidth(),
                        value = state.name,
                        onValueChange = { onNameChanged(it) },
                        placeholder = "Name",
                        colors = getTextColorsDark(),
                        style = AppTheme.typography.body1Bold,
                        singleLine = false
                    )
                    state.trip?.let {
                        SpacerComponent { x4 }
                        SelectTripComponent(state.trip) {
                            if (state.isCreateMode) {
                                onTripClick()
                            }
                        }
                    }

                    SpacerComponent { x4 }
                    PointComponent(
                        model = state.model,
                        onClick = onChangeLocation
                    )

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
                        minHeight = AppTheme.indents.x10
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