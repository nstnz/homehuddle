package com.homehuddle.common.design.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import getFont

internal object Typography {

    val body3 = TextStyle(
        fontFamily = getFont(),
        fontSize = 14.0.sp,
        letterSpacing = 0.15000000596046448.sp,
        lineHeight = 20.0.sp,
    )
    val body2 = TextStyle(
        fontFamily = getFont(),
        fontSize = 16.0.sp,
        letterSpacing = 0.15000000596046448.sp,
        lineHeight = 22.0.sp,
    )
    val body1 = TextStyle(
        fontFamily = getFont(),
        fontSize = 22.0.sp,
        letterSpacing = 0.15000000596046448.sp,
        lineHeight = 28.0.sp,
    )
    val large1 = TextStyle(
        fontFamily = getFont(),
        fontSize = 28.0.sp,
        letterSpacing = 0.15000000596046448.sp,
        lineHeight = 32.0.sp,
    )

    val body3Bold = body3.copy(
        fontWeight = FontWeight(1000)
    )

    val body2Bold = body2.copy(
        fontWeight = FontWeight(1000)
    )

    val body1Bold = body1.copy(
        fontWeight = FontWeight(1000)
    )
}
