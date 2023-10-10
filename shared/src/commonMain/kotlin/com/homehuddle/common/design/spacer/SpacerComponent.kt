package com.homehuddle.common.design.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.Indents

@Composable
internal fun SpacerComponent(
	sizeSelector: @Composable Indents.() -> Dp,
) {
	Spacer(modifier = Modifier.size(AppTheme.indents.sizeSelector()))
}