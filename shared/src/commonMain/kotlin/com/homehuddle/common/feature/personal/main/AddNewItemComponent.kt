package com.homehuddle.common.feature.personal.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent3

@Composable
internal fun AddNewItemComponent() {
    Box(
        Modifier.fillMaxWidth()
            .height(200.dp)
            .background(AppTheme.colors.accent3(), AppTheme.shapes.x4_5_top)
            .padding(horizontal = AppTheme.indents.x3)
    )
}