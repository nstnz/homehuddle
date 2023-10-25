package com.homehuddle.common.feature.personal.createtrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.utils.Texts
import com.homehuddle.common.design.button.PrimaryButtonComponent
import com.homehuddle.common.design.datepicker.TwoDatesPicker
import com.homehuddle.common.design.input.LinedTextInputComponent
import com.homehuddle.common.design.input.TextInputComponent
import com.homehuddle.common.design.scaffold.GradientScaffold
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.invokeOnCompletion
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.topbar.DefaultNavComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CreateTripScreen(
    state: CreateTripScreenState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onNameChanged: (TextFieldValue) -> Unit = {},
    onDescriptionChanged: (TextFieldValue) -> Unit = {},
    onFromDateSelected: (Long?) -> Unit = {},
    onToDateSelected: (Long?) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        invokeOnCompletion { focusRequester.requestFocus() }
    }

    GradientScaffold(
        snackbarHostState = snackbarHostState,
        topBar = {
            DefaultNavComponent(
                showBackButton = true,
                showAddButton = false,
                elementsColor = AppTheme.colors.textLightDefault(),
                onBackClick = onBackClick
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
                    placeholder = Texts.TripName,
                    label = Texts.TripNameLabel,
                    style = AppTheme.typography.body1Bold
                )
                SpacerComponent { x3 }

                Column(
                    Modifier.fillMaxSize()
                        .background(
                            AppTheme.colors.background2(),
                            AppTheme.shapes.x4_5_top
                        )
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = AppTheme.indents.x3),
                ) {
                    SpacerComponent { x3 }
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

                    SpacerComponent { x3 }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = Texts.TripDatesLabel,
                        style = AppTheme.typography.body3,
                        color = AppTheme.colors.textDarkDisabled()
                    )
                    SpacerComponent { x2 }
                    TwoDatesPicker(
                        modifier = Modifier.fillMaxWidth(),
                        dateStart = state.dateStart,
                        dateEnd = state.dateEnd,
                        timestampStart = state.timestampStart,
                        timestampEnd = state.timestampEnd,
                        showFromState = state.fromDateSelected,
                        onFromDatePicked = onFromDateSelected,
                        onToDatePicked = onToDateSelected,
                    )
                    SpacerComponent { x3 }
                }
            }

            PrimaryButtonComponent(
                text = Texts.Save,
                onClick = onSaveClick,
                modifier = Modifier.padding(horizontal = AppTheme.indents.x3)
                    .padding(bottom = AppTheme.indents.x3)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}