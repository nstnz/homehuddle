package com.homehuddle.common.design.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Suppress("ConstructorParameterNaming")
@Immutable
data class Shapes internal constructor(
    val x0_25: CornerBasedShape = RoundedCornerShape(2.dp),
    val x0_5: CornerBasedShape = RoundedCornerShape(4.dp),
    val x0_75: CornerBasedShape = RoundedCornerShape(6.dp),
    val x1: CornerBasedShape = RoundedCornerShape(8.dp),
    val x1_5: CornerBasedShape = RoundedCornerShape(12.dp),
    val x1_75: CornerBasedShape = RoundedCornerShape(14.dp),
    val x2: CornerBasedShape = RoundedCornerShape(16.dp),
    val x2_25: CornerBasedShape = RoundedCornerShape(18.dp),
    val x2_5: CornerBasedShape = RoundedCornerShape(20.dp),
    val x3: CornerBasedShape = RoundedCornerShape(24.dp),
    val x3_5: CornerBasedShape = RoundedCornerShape(28.dp),
    val x3_75: CornerBasedShape = RoundedCornerShape(30.dp),
    val x4_5: CornerBasedShape = RoundedCornerShape(36.dp),
    val x4_5_top: CornerBasedShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    val x4_5_bottom: CornerBasedShape = RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp),
    val x4_top: CornerBasedShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    val x5: CornerBasedShape = RoundedCornerShape(40.dp),
)