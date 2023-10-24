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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Route
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent1
import com.homehuddle.common.design.theme.ignoreHorizontalParentPadding
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textDarkSecondary

@Composable
internal fun TripPostCardComponent(
    trip: Trip,
    tripPost: TripPost,
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = AppTheme.indents.x2)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(32.dp)
                    .background(AppTheme.colors.textDarkDisabled(), RoundedCornerShape(50))
            )
            SpacerComponent { x1 }
            Column {
                Row {
                    Text(
                        text = user.name + " for",
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
                Text(
                    text = "10.15.1243",
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDisabled()
                )
            }
        }

        if (tripPost.text.isNotEmpty()) {
            SpacerComponent { x2 }
            Text(
                text = tripPost.text,
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textDarkDefault(),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
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

        SpacerComponent { x3 }
        SocialComponent(tripPost)
    }
}

@Composable
private fun SocialComponent(
    tripPost: TripPost
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
    tripPost: TripPost
) {
    Row(
        modifier = Modifier
            .ignoreHorizontalParentPadding(AppTheme.indents.x2),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5)
    ) {
        SpacerComponent { x0_5 }
        tripPost.photos.forEach {
            Box(
                Modifier.size(160.dp)
                    .background(AppTheme.colors.textDarkDisabled(), AppTheme.shapes.x1_5)
            )
        }
        SpacerComponent { x0_5 }
    }
}

@Composable
private fun PointsComponent(
    tripPost: TripPost
) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = AppTheme.indents.x1_5),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when {
            tripPost.point != null -> {
                Image(
                    imageVector = Icons.Rounded.Place,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
                    modifier = Modifier.size(AppTheme.indents.x3)
                )
                SpacerComponent { x1 }
                Text(
                    text = tripPost.point.description,
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDisabled()
                )
            }
            !tripPost.fromToRoute.isNullOrEmpty() -> {
                Image(
                    imageVector = Icons.Rounded.Route,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
                    modifier = Modifier.size(AppTheme.indents.x3)
                )
                SpacerComponent { x1 }
                Text(
                    text = "Trip to " + tripPost.fromToRoute[1].description,
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDisabled()
                )
            }
            !tripPost.customRoute.isNullOrEmpty() -> {
                Image(
                    imageVector = Icons.Rounded.Map,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
                    modifier = Modifier.size(AppTheme.indents.x3)
                )
                SpacerComponent { x1 }
                Text(
                    text = tripPost.customRoute[0].description + " and more...",
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
    tripPost: TripPost
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