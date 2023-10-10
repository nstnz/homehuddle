package com.homehuddle.common.design.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.transparent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun SimpleImage(
    res: String,
    modifier: Modifier = Modifier,
    colorMultiply: Color? = null
) {
    Image(
        painterResource(res),
        null,
        modifier = modifier,
        contentScale = ContentScale.FillBounds,
        colorFilter = colorMultiply?.let {
            ColorFilter.lighting(
                multiply = it,
                add = AppTheme.colors.transparent()
            )
        }
    )
}