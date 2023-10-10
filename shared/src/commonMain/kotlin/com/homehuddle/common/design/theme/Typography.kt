package com.homehuddle.common.design.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import getFont

internal object Typography {

    val body3 = TextStyle(
        fontFamily = getFont(),
        fontSize = 16.0.sp,
        letterSpacing = 0.15000000596046448.sp,
        lineHeight = 20.0.sp,
    )
    val body2 = TextStyle(
        fontFamily = getFont(),
        fontSize = 20.0.sp,
        letterSpacing = 0.15000000596046448.sp,
        lineHeight = 24.0.sp,
    )
    val body1 = TextStyle(
        fontFamily = getFont(),
        fontSize = 32.0.sp,
        letterSpacing = 0.15000000596046448.sp,
        lineHeight = 40.0.sp,
    )
    val large3 = TextStyle(
        fontFamily = getFont(),
        fontSize = 48.0.sp,
        letterSpacing = 0.15000000596046448.sp,
        lineHeight = 56.0.sp,
    )
    val large2 = TextStyle(
        fontFamily = getFont(),
        fontSize = 44.0.sp,
        letterSpacing = 0.15000000596046448.sp,
        lineHeight = 48.0.sp,
    )
    val large1 = TextStyle(
        fontFamily = getFont(),
        fontSize = 40.0.sp,
        letterSpacing = 0.15000000596046448.sp,
        lineHeight = 44.0.sp,
    )
}
