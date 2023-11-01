package com.homehuddle.common.design.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun PrimaryButtonComponent(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    icon: @Composable (() -> Unit)? = null,
    state: ButtonState = ButtonState.DEFAULT,
) {
    ButtonComponent(
        text = text,
        onClick = onClick,
        modifier = modifier,
        state = state,
        icon = icon,
        buttonColors = PrimaryButtonColors
    )
}