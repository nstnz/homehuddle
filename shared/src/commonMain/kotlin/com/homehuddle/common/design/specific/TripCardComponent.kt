package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.ignoreHorizontalParentPadding
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textDarkSecondary

@Composable
internal fun TripCardComponent(
    trip: Trip,
    ownedByUser: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(bottom = AppTheme.indents.x2)
    ) {
        Text(
            text = trip.name,
            style = AppTheme.typography.body1Bold,
            color = AppTheme.colors.textDarkDefault()
        )

        SpacerComponent { x0_5 }
        TripSummaryComponent(trip)
        SpacerComponent { x2 }
        Text(
            text = trip.description,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textDarkDefault(),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        SpacerComponent { x1 }
        PhotosComponent(trip)
        SpacerComponent { x3 }
        SocialComponent(trip)
    }
}

@Composable
private fun SocialComponent(
    trip: Trip
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1f))
        SocialComponent("340", Icons.Rounded.ChatBubbleOutline)
        SpacerComponent { x4 }
        SocialComponent("1213", Icons.Rounded.FavoriteBorder)
        SpacerComponent { x4 }
        SocialComponent("23", Icons.Rounded.PersonOutline)
    }
}

@Composable
private fun SocialComponent(
    text: String,
    icon: ImageVector
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = AppTheme.typography.body3,
            color = AppTheme.colors.textDarkDisabled()
        )
        SpacerComponent { x1 }
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppTheme.colors.textDarkSecondary()),
            modifier = Modifier.size(AppTheme.indents.x3)
        )
    }
}

@Composable
private fun PhotosComponent(
    trip: Trip
) {
    Row(
        modifier = Modifier
            .ignoreHorizontalParentPadding(AppTheme.indents.x2),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5)
    ) {
        SpacerComponent { x0_5 }
        repeat(5) {
            Box(
                Modifier.size(160.dp)
                    .background(AppTheme.colors.textDarkDisabled(), AppTheme.shapes.x1_5)
            )
        }
        SpacerComponent { x0_5 }
    }
}