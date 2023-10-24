package com.homehuddle.common.design.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.theme.transparent

@Composable
internal fun DefaultNavComponent(
    onBackClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    title: String = "",
    showBackButton: Boolean = true,
    showAddButton: Boolean = false,
    bgColor: Color = AppTheme.colors.transparent(),
    elementsColor: Color = AppTheme.colors.textLightDefault()
) {
    NavBarComponents(
        modifier = Modifier.height(AppTheme.indents.x8)
            .background(bgColor),
        title = title,
        titleColor = elementsColor,
        navigationIcon = if (showBackButton) {
            {
                Box(
                    Modifier.height(AppTheme.indents.x8).width(AppTheme.indents.x6)
                        .noEffectsClickable { onBackClick() },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Image(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(AppTheme.indents.x3),
                        colorFilter = ColorFilter.tint(elementsColor)
                    )
                }
            }
        } else {
            null
        },
        rightIcon = if (showAddButton) {
            {
                Box(
                    Modifier.height(AppTheme.indents.x8).width(AppTheme.indents.x6)
                        .noEffectsClickable { onAddClick() },
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        modifier = Modifier.size(AppTheme.indents.x3),
                        colorFilter = ColorFilter.tint(elementsColor)
                    )
                }
            }
        } else {
            null
        }
    )
}

@Composable
internal fun NavBarComponents(
    title: String,
    titleColor: Color = AppTheme.colors.textLightDefault(),
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null,
) {
    NavBarComponent(
        title = {
            Text(
                text = title,
                style = AppTheme.typography.large1,
                color = titleColor,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        rightIcon = rightIcon
    )
}

@Composable
private fun NavBarComponent(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null,
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
        rightIcon?.invoke()
    }
}