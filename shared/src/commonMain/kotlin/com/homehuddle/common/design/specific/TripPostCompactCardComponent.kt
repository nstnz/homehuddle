package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Hiking
import androidx.compose.material.icons.rounded.HistoryEdu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent4
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkBorder
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun TripPostCompactCardComponent(
    trip: TripModel,
    tripPost: TripPostModel,
    onClick: (TripPostModel) -> Unit = {},
    modifier: Modifier = Modifier,
    showSocialHeader: Boolean = false,
    showTrip: Boolean = true,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .noEffectsClickable { onClick(tripPost) }
            .padding(bottom = AppTheme.indents.x2)
    ) {
        if (showSocialHeader) {
            TripPostUserSummaryComponent(
                trip = trip,
                tripPost = tripPost
            )
            SpacerComponent { x2 }
        }
        Row(Modifier.fillMaxWidth()) {
            Box(
                Modifier
                    .size(AppTheme.indents.x6)
                    .background(AppTheme.colors.textDarkBorder(), RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = Icons.Rounded.HistoryEdu,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
                    modifier = Modifier.size(AppTheme.indents.x6 * 0.65f)
                )
            }
            SpacerComponent { x2 }
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = tripPost.name,
                    style = AppTheme.typography.body2Bold,
                    color = AppTheme.colors.textDarkDefault(),
                )
                Row {
                    if (showTrip) {
                        Text(
                            text = trip.name,
                            style = AppTheme.typography.body3,
                            color = AppTheme.colors.accent4(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textDecoration = TextDecoration.Underline
                        )
                        Text(
                            text = ", ",
                            style = AppTheme.typography.body3,
                            color = AppTheme.colors.textDarkDisabled(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = tripPost.dateStart.orEmpty() + " - " + tripPost.dateEnd.orEmpty(),
                        style = AppTheme.typography.body3,
                        color = AppTheme.colors.textDarkDisabled(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        if (tripPost.description.isNotEmpty()) {
            SpacerComponent { x0_5 }
            Text(
                text = tripPost.description,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textDarkDefault(),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = AppTheme.indents.x8).fillMaxWidth()
            )
        }

        if (tripPost.hasExpenses || tripPost.hasPoints) {
            SpacerComponent { x1 }
            Row(
                modifier = Modifier.padding(start = AppTheme.indents.x8).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                if (tripPost.hasPoints) {
                    TripInfoComponent(
                        text = "${tripPost.distance} km",
                        icon = Icons.Rounded.Hiking
                    )
                    SpacerComponent { x3 }
                }
                if (tripPost.hasExpenses) {
                    TripInfoComponent(
                        text = tripPost.formattedTotalSpent,
                        icon = Icons.Rounded.CreditCard
                    )
                }
            }
        }

        if (tripPost.hasPhotos) {
            SpacerComponent { x1 }
            PhotosComponent(tripPost)
        }

        TripSocialComponent(
            12, 124, canSubscribe = false, canLike = true,
            paddingTop = AppTheme.indents.x1
        )
    }
}

@Composable
private fun PhotosComponent(
    tripPost: TripPostModel
) {
    Row(
        modifier = Modifier.padding(start = AppTheme.indents.x8).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1)
    ) {
        tripPost.photos.forEach {
            TripPhotoComponent(
                64.dp, AppTheme.indents.x1, url = it
            )
        }
    }
}