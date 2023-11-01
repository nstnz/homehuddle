package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Route
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun TripPostCardComponent(
    trip: TripModel,
    tripPost: TripPostModel,
    user: UserModel?,
    onClick: (TripPostModel) -> Unit = {},
    modifier: Modifier = Modifier,
    showFullInfo: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .noEffectsClickable { onClick(tripPost) }
            .padding(bottom = AppTheme.indents.x2)
    ) {
        if (!showFullInfo) {
            TripPostUserSummaryComponent(trip, tripPost)
        }

        if (tripPost.description.isNotEmpty()) {
            SpacerComponent { x2 }
            Text(
                text = tripPost.description,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textDarkDefault(),
                maxLines = if (showFullInfo) 6 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )
            SpacerComponent { x1 }
        }

        if (tripPost.expenses.isNotEmpty()) {
            ExpensesComponent(tripPost)
        }

        if (tripPost.hasPoints) {
            PointsComponent(tripPost)
        }

        if (tripPost.photos.isNotEmpty()) {
            SpacerComponent { x2 }
            PhotosComponent(tripPost)
        }

        if (!showFullInfo) {
            TripSocialComponent(12, 124, canSubscribe = false, canLike = true,
                paddingTop = AppTheme.indents.x3)
        }
    }
}

@Composable
private fun PhotosComponent(
    tripPost: TripPostModel,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5)
    ) {
        tripPost.photos.forEach {
            TripPhotoComponent(160.dp, AppTheme.indents.x1_5, url = it)
        }
    }
}

@Composable
private fun PointsComponent(
    tripPost: TripPostModel
) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = AppTheme.indents.x1_5),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when {
            tripPost.points?.size == 1 -> {
                Image(
                    imageVector = Icons.Rounded.Place,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
                    modifier = Modifier.size(AppTheme.indents.x3)
                )
                SpacerComponent { x1 }
                Text(
                    text = tripPost.points[0].description,
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDisabled()
                )
            }

            tripPost.points?.size == 2 -> {
                Image(
                    imageVector = Icons.Rounded.Route,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
                    modifier = Modifier.size(AppTheme.indents.x3)
                )
                SpacerComponent { x1 }
                Text(
                    text = "Trip to " + tripPost.points[1].description,
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDisabled()
                )
            }

            (tripPost.points?.size ?: 0) > 2 -> {
                Image(
                    imageVector = Icons.Rounded.Map,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
                    modifier = Modifier.size(AppTheme.indents.x3)
                )
                SpacerComponent { x1 }
                Text(
                    text = tripPost.points?.get(0)?.description + " and more...",
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDisabled()
                )
            }
        }

        Spacer(Modifier.weight(1f))
        Image(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
            modifier = Modifier.size(AppTheme.indents.x3)
        )
    }
}

@Composable
private fun ExpensesComponent(
    tripPost: TripPostModel
) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = AppTheme.indents.x1_5),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val sum = tripPost.expenses.sumOf { it.sum }.toString()

        Image(
            imageVector = Icons.Rounded.CreditCard,
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
            modifier = Modifier.size(AppTheme.indents.x3)
        )
        SpacerComponent { x1 }
        Text(
            text = "Spent $sum",
            style = AppTheme.typography.body3,
            color = AppTheme.colors.textDarkDisabled()
        )
        Spacer(Modifier.weight(1f))
        Image(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
            modifier = Modifier.size(AppTheme.indents.x3)
        )
    }
}