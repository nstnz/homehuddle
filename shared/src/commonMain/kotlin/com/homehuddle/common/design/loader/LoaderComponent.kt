package com.homehuddle.common.design.loader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent5

@Composable
internal fun LoaderComponent() {
    Box(
        Modifier.fillMaxSize(),
        Alignment.Center
    ) {
        CircularProgressIndicator(
            Modifier.size(AppTheme.indents.x10),
            color = AppTheme.colors.accent5(),
            strokeWidth = 8.dp,
        )
    }
}