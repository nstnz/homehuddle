package com.homehuddle.common.design.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.homehuddle.common.design.image.IconType
import com.homehuddle.common.design.image.SimpleIcon
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textLightDefault

@Composable
internal fun DefaultNavComponent(
    onBackClick: () -> Unit = {},
    title: String = "",
    showBackButton: Boolean = true,
) {
    NavBarComponent(
        modifier = Modifier.height(AppTheme.indents.x8),
        title = title,
        navigationIcon = {
            if (showBackButton) {
                /*SimpleIcon(
                    tint = AppTheme.colors.textLightDefault(),
                    type = IconType.IcClose,
                    modifier = Modifier.size(AppTheme.indents.x6)
                        .noEffectsClickable { onBackClick() },
                )*/
            }
        }
    )
}

@Composable
internal fun NavBarComponent(
    title: String,
    modifier: Modifier = Modifier,
    titleColor: Color = AppTheme.colors.textLightDefault(),
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    NavBarComponent(
        title = {
            Text(
                text = title,
                style = AppTheme.typography.large3,
                color = titleColor,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
    )
}

@Composable
private fun NavBarComponent(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.padding(horizontal = AppTheme.indents.x2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationIcon?.invoke()
        Row(
            Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            title()
        }
    }
}