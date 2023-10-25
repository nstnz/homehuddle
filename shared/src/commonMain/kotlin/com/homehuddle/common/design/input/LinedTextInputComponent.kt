package com.homehuddle.common.design.input

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.homehuddle.common.design.input.internal.OutlinedTextField
import com.homehuddle.common.design.input.internal.TextInputState
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent4
import com.homehuddle.common.design.theme.ignoreHorizontalParentPadding
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textDarkSecondary
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.theme.textLightDisabled
import com.homehuddle.common.design.theme.textLightSecondary
import com.homehuddle.common.design.theme.transparent

@Composable
internal fun LinedTextInputComponent(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    label: String = "",
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textFieldState: TextInputState = TextInputState.Default,
    hasClearButton: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLength: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    minHeight: Dp = AppTheme.indents.x6,
    minWidth: Dp = TextFieldDefaults.MinWidth,
    enabled: Boolean = true,
    style: TextStyle = AppTheme.typography.body2,
    colors: TextFieldColors = getTextColorsLight(),
    innerPadding: Dp = AppTheme.indents.x1
) {

    val interactionSource = remember { MutableInteractionSource() }
    Column {
        val error = when (textFieldState) {
            TextInputState.Default -> null
            is TextInputState.Error -> textFieldState.error
        }
        val hasError = error != null
        val background = AppTheme.colors.transparent()

        if (label.isNotEmpty()) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = label,
                style = AppTheme.typography.body3,
                color = colors
                    .labelColor(
                        enabled = true,
                        error = hasError,
                        interactionSource = interactionSource
                    ).value
            )
        }

        OutlinedTextField(
            innerPadding = innerPadding,
            interactionSource = interactionSource,
            value = value,
            onValueChange = { currentText ->
                if (currentText.text.length <= maxLength) {
                    onValueChange(currentText)
                }
            },
            placeholder = {
                Text(
                    text = placeholder,
                    style = style,
                )
            },
            isError = textFieldState is TextInputState.Error,
            modifier = modifier
                .ignoreHorizontalParentPadding(AppTheme.indents.x1)
                .fillMaxWidth()
                .defaultMinSize(
                    minHeight = minHeight,
                    minWidth = minWidth,
                )
                .background(
                    shape = AppTheme.shapes.x1,
                    color = background,
                ),
            textStyle = style,
            singleLine = singleLine,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            colors = colors,
            shape = AppTheme.shapes.x1,
            trailingIcon = trailingIcon ?: getTrailingIcon(
                hasClearButton,
                value,
                onValueChange
            ),
            enabled = enabled
        )

        Spacer(
            modifier.fillMaxWidth()
                .height(AppTheme.indents.x0_25)
                .background(colors.labelColor(
                    enabled = true,
                    error = hasError,
                    interactionSource = interactionSource
                ).value, RoundedCornerShape(50))
        )

        ErrorContent(
            hasError = hasError,
            error = error
        )
    }
}

@Composable
internal fun getTextColorsDark() =
    TextFieldDefaults.outlinedTextFieldColors(
        textColor = AppTheme.colors.textDarkDefault(),
        focusedBorderColor = AppTheme.colors.transparent(),
        unfocusedBorderColor = AppTheme.colors.transparent(),
        focusedLabelColor = AppTheme.colors.textDarkSecondary(),
        unfocusedLabelColor = AppTheme.colors.textDarkDisabled(),
        placeholderColor = AppTheme.colors.textDarkDisabled(),
        cursorColor = AppTheme.colors.accent4(),
        disabledTextColor = AppTheme.colors.textDarkDefault(),
        disabledBorderColor = AppTheme.colors.transparent(),
        disabledLabelColor = AppTheme.colors.textDarkDefault(),
        backgroundColor = AppTheme.colors.transparent(),
    )

@Composable
internal fun getTextColorsLight() =
    TextFieldDefaults.outlinedTextFieldColors(
        textColor = AppTheme.colors.textLightDefault(),
        focusedBorderColor = AppTheme.colors.transparent(),
        unfocusedBorderColor = AppTheme.colors.transparent(),
        focusedLabelColor = AppTheme.colors.textLightSecondary(),
        unfocusedLabelColor = AppTheme.colors.textLightDisabled(),
        placeholderColor = AppTheme.colors.textLightDisabled(),
        cursorColor = AppTheme.colors.accent4(),
        disabledTextColor = AppTheme.colors.textLightDefault(),
        disabledBorderColor = AppTheme.colors.transparent(),
        disabledLabelColor = AppTheme.colors.textLightDefault(),
        backgroundColor = AppTheme.colors.transparent()
    )
