package com.homehuddle.common.design.specific

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.ignoreHorizontalParentPadding
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

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
        TripSocialComponent(12,124, canSubscribe = true, canLike = true)
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