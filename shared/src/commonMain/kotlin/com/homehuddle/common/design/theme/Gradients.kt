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

    @Composable
    fun tripGradient() = Brush.horizontalGradient(
        0.0f to Color(0xff548AD8),
        1f to Color(0xff8A4BD3),
    )

    @Composable
    fun tripPostGradient() = Brush.horizontalGradient(
        0.0f to Color(0xff893E9C),
        1f to Color(0xffF82B73),
    )

    @Composable
    fun expenseGradient() = Brush.horizontalGradient(
        0.0f to Color(0xffF33E62),
        1f to Color(0xffF79334),
    )

    @Composable
    fun locationGradient() = Brush.horizontalGradient(
        0.0f to Color(0xffD39646),
        1f to Color(0xffCCCCCD),
    )
}