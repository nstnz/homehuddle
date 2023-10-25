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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Hiking
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent1
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun TripPostCompactCardComponent(
    trip: TripModel,
    tripPost: TripPostModel,
    onClick: (TripPostModel) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .noEffectsClickable { onClick(tripPost) }
            .padding(bottom = AppTheme.indents.x2)
    ) {
        if (tripPost.text.isNotEmpty()) {
            Text(
                text = tripPost.text,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textDarkDefault(),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        } else {
            Row {
                Text(
                    text = "Added for",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textDarkDefault()
                )
                SpacerComponent { x0_5 }
                Text(
                    text = trip.name,
                    style = AppTheme.typography.body2Bold,
                    color = AppTheme.colors.accent1()
                )
            }
        }

        if (tripPost.hasExpenses || tripPost.hasPoints) {
            SpacerComponent { x1 }
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (tripPost.hasPoints) {
                    TripInfoComponent(
                        text = "${tripPost.distance} km",
                        icon = Icons.Rounded.Hiking
                    )
                    SpacerComponent { x3 }
                }
                if (tripPost.hasExpenses) {
                    TripInfoComponent(
                        text = "${tripPost.totalSpent} ${trip.user.currencyCode}",
                        icon = Icons.Rounded.CreditCard
                    )
                }
            }
        }

        if (tripPost.hasPhotos) {
            SpacerComponent { x1_5 }
            PhotosComponent(tripPost)
        }

        SpacerComponent { x1 }
        TripSocialComponent(12, 124, canSubscribe = false, canLike = true)
    }
}

@Composable
private fun PhotosComponent(
    tripPost: TripPostModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1)
    ) {
        tripPost.photos.forEach {
            Box(
                Modifier.size(64.dp)
                    .background(AppTheme.colors.textDarkDisabled(), AppTheme.shapes.x1)
            )
        }
    }
}