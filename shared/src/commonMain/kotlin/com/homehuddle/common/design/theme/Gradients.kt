package com.homehuddle.common.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

internal object Gradients {

    @Composable
    fun background1() = Brush.verticalGradient(
        0.0f to AppTheme.colors.accent4(),
        0.7f to AppTheme.colors.background2()
    )

    @Composable
    fun pageEnd() = Brush.horizontalGradient(
        0.3f to AppTheme.colors.textLightDefault(),
        1f to Color(0xffe3e3e3)
    )

    @Composable
    fun pageStart() = Brush.horizontalGradient(
        0.0f to Color(0x55b5b5b5),
        1f to AppTheme.colors.transparent(),
    )

    @Composable
    fun coverStart() = Brush.horizontalGradient(
        0.0f to Color(0xffffffff),
        1f to AppTheme.colors.transparent(),
    )

    @Composable
    fun pageCover() = Brush.verticalGradient(
        0f to AppTheme.colors.transparent(),
        1f to Color(0xff000000),
    )
}