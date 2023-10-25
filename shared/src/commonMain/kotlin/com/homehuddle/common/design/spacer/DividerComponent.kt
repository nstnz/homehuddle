package com.homehuddle.common.design.spacer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.textDarkBorder

@Composable
fun DividerComponent() {
    Spacer(
        Modifier.fillMaxWidth().height(AppTheme.indents.x0_125)
            .background(
                AppTheme.colors.textDarkBorder()
            )
    )
}