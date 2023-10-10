package com.homehuddle.common.design.image

import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun SimpleIcon(
    res: String,
    tint: Color? = null,
    modifier: Modifier = Modifier,
) {

    Icon(
        painterResource(res),
        contentDescription = "",
        modifier = modifier,
        tint = tint ?: LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    )
}