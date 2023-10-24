package com.homehuddle.common.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

internal object Gradients {

    @Composable
    fun background1() = Brush.verticalGradient(
        0.0f to AppTheme.colors.background1(),
        0.5f to Color(0xFF893E9C)
    )

    @Composable
    fun divider() = Brush.verticalGradient(
        0.0f to AppTheme.colors.background1().copy(alpha = 0.3f),
        1f to AppTheme.colors.background1().copy(alpha = 0.3f),
    )
}