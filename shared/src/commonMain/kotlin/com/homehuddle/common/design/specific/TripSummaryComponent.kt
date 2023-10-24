package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Hiking
import androidx.compose.material.icons.rounded.OutlinedFlag
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent1
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun TripSummaryComponent(
    trip: Trip,
    modifier: Modifier = Modifier,
    iconColor: Color = AppTheme.colors.accent1(),
    textColor: Color = AppTheme.colors.textDarkDisabled()
) {
    Row(modifier.fillMaxWidth()) {
        Column {
            TripInfoComponent(
                text = "1500 km",
                icon = Icons.Rounded.Hiking,
                iconColor = iconColor,
                textColor = textColor
            )
            TripInfoComponent(
                text = "$200",
                icon = Icons.Rounded.CreditCard,
                iconColor = iconColor,
                textColor = textColor
            )
            TripInfoComponent(
                text = "5 countries",
                icon = Icons.Rounded.OutlinedFlag,
                iconColor = iconColor,
                textColor = textColor
            )
        }
        SpacerComponent { x3 }
        Column((Modifier.weight(1f)), horizontalAlignment = Alignment.End) {
            Text(
                text = "from 11 october till kkksksk jdlfksjdl kfjsdlffsd",
                style = AppTheme.typography.body3,
                color = textColor,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
internal fun TripInfoComponent(
    icon: ImageVector,
    text: String,
    iconColor: Color = AppTheme.colors.accent1(),
    textColor: Color = AppTheme.colors.textDarkDisabled()
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier.size(AppTheme.indents.x2)
        )
        SpacerComponent { x1 }
        Text(
            text = text,
            style = AppTheme.typography.body3,
            color = textColor
        )
    }
}