package com.homehuddle.common.design.navbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Feed
import androidx.compose.material.icons.rounded.ImportContacts
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun NavigationBarComponent(
    personalTabSelected: Boolean = false,
    feedTabSelected: Boolean = false,
    settingsTabSelected: Boolean = false,
    searchTabSelected: Boolean = false,
    personalTabClick: () -> Unit,
    feedTabClick: () -> Unit,
    settingsTabClick: () -> Unit,
    searchTabClick: () -> Unit,
) {
    NavigationBarComponent(
        items = listOf(
            PersonalNavigationItem(selected = personalTabSelected, onClick = personalTabClick),
            FeedNavigationItem(selected = feedTabSelected, onClick = feedTabClick),
            SearchNavigationItem(selected = searchTabSelected, onClick = searchTabClick),
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
            .fillMaxWidth()
            .padding(horizontal = AppTheme.indents.x3)
            .padding(bottom = AppTheme.indents.x3)
            .height(AppTheme.indents.x8)
            .shadow(
                24.dp,
                RoundedCornerShape(50),
                clip = false,
                spotColor = AppTheme.colors.textDarkDisabled().copy(alpha = 0.5f)
            )
            .background(AppTheme.colors.background2(), RoundedCornerShape(50))
            .padding(vertical = AppTheme.indents.x2, horizontal = AppTheme.indents.x3),
        verticalAlignment = CenterVertically
    ) {
        BottomNavigationItem(items[0])
        Spacer(Modifier.weight(1f))
        BottomNavigationItem(items[1])
        Spacer(Modifier.weight(1f))
        BottomNavigationItem(items[2])
        Spacer(Modifier.weight(1f))
        BottomNavigationItem(items[3])
    }
}


@Composable
private fun BottomNavigationItem(
    item: NavigationItem,
) {
    Column(Modifier
        .noEffectsClickable { item.onClick() }) {

        Image(
            imageVector = item.icon,
            contentDescription = null,
            modifier = Modifier.size(AppTheme.indents.x3),
            colorFilter = ColorFilter.tint(
                if (item.selected) AppTheme.colors.textDarkDefault() else AppTheme.colors.textDarkDisabled()
            )
        )
    }
}

internal interface NavigationItem {
    val selected: Boolean
    val onClick: () -> Unit
    val icon: ImageVector
    val text: String
}

private data class PersonalNavigationItem(
    override val selected: Boolean,
    override val onClick: () -> Unit = {},
    override val icon: ImageVector = Icons.Rounded.ImportContacts,
    override val text: String = "Personal"
) : NavigationItem

private data class FeedNavigationItem(
    override val selected: Boolean,
    override val onClick: () -> Unit = {},
    override val icon: ImageVector = Icons.Rounded.Feed,
    override val text: String = "Feed"
) : NavigationItem

private data class SettingsNavigationItem(
    override val selected: Boolean,
    override val onClick: () -> Unit = {},
    override val icon: ImageVector = Icons.Rounded.Settings,
    override val text: String = "Settings"
) : NavigationItem

private data class SearchNavigationItem(
    override val selected: Boolean,
    override val onClick: () -> Unit = {},
    override val icon: ImageVector = Icons.Rounded.Search,
    override val text: String = "Search"
) : NavigationItem