package com.homehuddle.common.design.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Modifier
import com.homehuddle.common.design.image.AnimationType
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.InOuterShadow
import com.homehuddle.common.design.theme.accent2
import com.homehuddle.common.design.theme.accent3
import com.homehuddle.common.design.theme.noEffectsClickable

@Composable
internal fun NavigationBarComponent(
    mainTabSelected: Boolean = false,
    booksTabSelected: Boolean = false,
    settingsTabSelected: Boolean = false,
    mainTabClick: () -> Unit,
    booksTabClick: () -> Unit,
    settingsTabClick: () -> Unit,
) {
    NavigationBarComponent(
        items = listOf(
            BooksNavigationItem(selected = booksTabSelected, onClick = booksTabClick),
            MainNavigationItem(selected = mainTabSelected, onClick = mainTabClick),
            SettingsNavigationItem(selected = settingsTabSelected, onClick = settingsTabClick),
        )
    )
}

@Composable
private fun NavigationBarComponent(
    items: List<NavigationItem>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Bottom
    ) {
        BottomNavigationItem(items[0])
        Spacer(Modifier.weight(1f))
        BottomNavigationItem(items[1])
        Spacer(Modifier.weight(1f))
        BottomNavigationItem(items[2])
    }
}


@Composable
private fun BottomNavigationItem(
    item: NavigationItem,
) {
    Column(Modifier
        .noEffectsClickable { item.onClick() }) {
        InOuterShadow(
            modifier = Modifier,
            cornersRadius = AppTheme.indents.x3,
            addDarkness = true,
            color = if (item is MainNavigationItem) AppTheme.colors.accent2() else AppTheme.colors.accent3(),
        ) {
            Box(
                Modifier
                    .size(if (item.selected) AppTheme.indents.x10 else AppTheme.indents.x8_5)
                    .background(
                        if (item is MainNavigationItem) AppTheme.colors.accent2() else AppTheme.colors.accent3(),
                        AppTheme.shapes.x3
                    )
            ) {
                /*AnimatedComponent(
                    type = item.icon,
                    modifier = Modifier.align(Alignment.Center)
                        .fillMaxSize(),
                    isPlaying = item.selected
                )*/
            }
        }

        SpacerComponent { x0_5 }

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = item.text,
            color = if (item is MainNavigationItem) AppTheme.colors.accent2() else AppTheme.colors.accent3(),
            style = AppTheme.typography.body2
        )
    }
}

internal interface NavigationItem {
    val selected: Boolean
    val onClick: () -> Unit
    val icon: AnimationType
    val text: String
}

private data class MainNavigationItem(
    override val selected: Boolean,
    override val onClick: () -> Unit = {},
    override val icon: AnimationType = AnimationType.MainMenuIcon,
    override val text: String = "Home"
) : NavigationItem

private data class BooksNavigationItem(
    override val selected: Boolean,
    override val onClick: () -> Unit = {},
    override val icon: AnimationType = AnimationType.BooksIcon,
    override val text: String = "Books"
) : NavigationItem

private data class SettingsNavigationItem(
    override val selected: Boolean,
    override val onClick: () -> Unit = {},
    override val icon: AnimationType = AnimationType.SettingsIcon,
    override val text: String = "Settings"
) : NavigationItem