package com.homehuddle.common.design.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.InOuterShadow
import com.homehuddle.common.design.theme.noEffectsClickable

@Composable
internal fun ButtonComponent(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    state: ButtonState = ButtonState.DEFAULT,
    buttonColors: ButtonColors,
) {
    val buttonShape = AppTheme.shapes.x2_5

    Surface(
        shape = buttonShape,
        color = backgroundColor(buttonColors, state),
        contentColor = buttonColors.textColor,
        modifier = modifier
            .noEffectsClickable {
                onClick()
            }
    ) {
        InOuterShadow(
            Modifier
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(AppTheme.indents.x7)
                    .border(
                        AppTheme.indents.x0_5,
                        textColor(buttonColors, state),
                        AppTheme.shapes.x2_5
                    )
            ) {
                val contentModifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = AppTheme.indents.x2)

                ButtonContent(
                    modifier = contentModifier,
                    icon = icon,
                    text = text,
                    textStyle = AppTheme.typography.body1,
                    colors = buttonColors,
                    state = state,
                )
            }
        }
    }
}

@Composable
internal fun ButtonContent(
    modifier: Modifier,
    text: String,
    icon: @Composable (() -> Unit)?,
    textStyle: TextStyle,
    colors: ButtonColors,
    state: ButtonState,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            icon()
            SpacerComponent { x1 }
        }
        Text(
            text = text,
            style = textStyle,
            color = textColor(colors, state),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
internal fun backgroundColor(colors: ButtonColors, state: ButtonState): Color =
    when (state) {
        ButtonState.DEFAULT,
        -> colors.backgroundColor

        ButtonState.DISABLED -> colors.backgroundColorDisabled
    }

@Composable
private fun textColor(colors: ButtonColors, state: ButtonState): Color = when (state) {
    ButtonState.DEFAULT,
    -> colors.textColor

    ButtonState.DISABLED -> colors.textColorDisabled
}