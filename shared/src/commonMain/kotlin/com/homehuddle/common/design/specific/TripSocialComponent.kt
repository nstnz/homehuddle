package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textDarkSecondary

@Composable
internal fun TripSocialComponent(
    likes: Int,
    subs: Int,
    canSubscribe: Boolean,
    canLike: Boolean,
    onLikeClick: () -> Unit = {},
    onSubsClick: () -> Unit = {},
    textColor: Color = AppTheme.colors.textDarkDisabled(),
    iconColor: Color = AppTheme.colors.textDarkSecondary(),
    textStyle: TextStyle = AppTheme.typography.body3,
    iconSize: Dp = AppTheme.indents.x3,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x4)
    ) {
        Spacer(Modifier.weight(1f))
        if (canLike) {
            SocialComponent(
                likes.toString(),
                Icons.Rounded.FavoriteBorder,
                textColor,
                iconColor,
                textStyle,
                iconSize,
                modifier = Modifier.noEffectsClickable { onLikeClick() }
            )
        }
        if (canSubscribe) {
            SocialComponent(
                subs.toString(),
                Icons.Rounded.PersonOutline,
                textColor,
                iconColor,
                textStyle,
                iconSize,
                modifier = Modifier.noEffectsClickable { onSubsClick() }
            )
        }
    }
}

@Composable
private fun SocialComponent(
    text: String,
    icon: ImageVector,
    textColor: Color = AppTheme.colors.textDarkDisabled(),
    iconColor: Color = AppTheme.colors.textDarkSecondary(),
    textStyle: TextStyle = AppTheme.typography.body3,
    iconSize: Dp = AppTheme.indents.x3,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
        SpacerComponent { x1 }
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier.size(iconSize)
        )
    }
}