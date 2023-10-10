package com.homehuddle.common.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("ConstructorParameterNaming")
@Immutable
data class Elevations(
	val flat: Dp = 0.dp,
	val card: Dp = 6.dp,
	val secondaryCard: Dp = 3.dp,
)