package com.homehuddle.common.design.specific

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.ignoreHorizontalParentPadding
import com.homehuddle.common.design.theme.textDarkDefault

@Composable
internal fun TripCardComponent(
    trip: TripModel,
    user: UserModel,
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
        TripSocialComponent(12, 124, canSubscribe = true, canLike = true)
    }
}

@Composable
private fun PhotosComponent(
    trip: TripModel
) {
    Row(
        modifier = Modifier
            .ignoreHorizontalParentPadding(AppTheme.indents.x2),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5)
    ) {
        SpacerComponent { x0_5 }
        trip.photos.forEach {
            TripPhotoComponent(160.dp, AppTheme.indents.x1_5)
        }
        SpacerComponent { x0_5 }
    }
}