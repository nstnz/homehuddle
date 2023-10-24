package com.homehuddle.common.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

internal object Gradients {

    @Composable
    fun background1() = Brush.verticalGradient(
        0.0f to AppTheme.colors.background1(),
        0.7f to AppTheme.colors.background1()
    )
}