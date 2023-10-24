package com.homehuddle.common.design.specific

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun TripPhotoComponent(
    size: Dp,
    radius: Dp = AppTheme.indents.x1_5
) {
    Box(
        Modifier.size(size)
            .background(
                AppTheme.colors.textDarkDisabled(),
                RoundedCornerShape(radius)
            )
    )
}