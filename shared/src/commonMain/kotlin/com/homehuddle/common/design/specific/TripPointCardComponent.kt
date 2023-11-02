package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPointModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent5
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textLightDefault

@Composable
internal fun TripPointCardComponent(
    trip: TripModel,
    tripPost: TripPostModel,
    showSocialHeader: Boolean,
    onClick: (TripPostModel) -> Unit = {},
    modifier: Modifier = Modifier,
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
        }
        tripPost.points.orEmpty().forEachIndexed { index, model ->
            if (showSocialHeader || index != 0) {
                SpacerComponent { x2 }
            }
            PointComponent(model = model)
        }

        TripSocialComponent(
            12, 124, canSubscribe = false, canLike = true,
            paddingTop = AppTheme.indents.x1
        )
    }
}

@Composable
internal fun PointComponent(
    model: TripPointModel,
    onClick: (TripPointModel) -> Unit = {}
) {
    Row(Modifier.fillMaxWidth().noEffectsClickable { onClick(model) }) {
        val size = AppTheme.indents.x6
        Box(
            Modifier
                .size(size)
                .background(AppTheme.colors.accent5(), RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = Icons.Rounded.Place,
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppTheme.colors.textLightDefault()),
                modifier = Modifier.size(size * 0.65f)
            )
        }
        SpacerComponent { x2 }
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = model.address.takeIf { it.isNotEmpty() }
                        ?: model.name.takeIf { it.isNotEmpty() }
                        ?: "Add location",
                    style = AppTheme.typography.body2Bold,
                    color = AppTheme.colors.textDarkDefault(),
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = when {
                        model.lat == 0.0 && model.lon == 0.0 -> ""
                        else -> model.description.takeIf { it.isNotEmpty() }
                            ?: (model.lat.toString() + "," + model.lon.toString())
                    },
                    style = AppTheme.typography.body3,
                    color = AppTheme.colors.textDarkDisabled(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}