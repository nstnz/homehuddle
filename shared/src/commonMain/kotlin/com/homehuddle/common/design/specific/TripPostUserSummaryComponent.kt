package com.homehuddle.common.design.specific

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent1
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun TripPostUserSummaryComponent(
    trip: TripModel,
    tripPost: TripPostModel,
    user: UserModel,
    modifier: Modifier = Modifier,
    textColor: Color = AppTheme.colors.textDarkDefault(),
    accentColor: Color = AppTheme.colors.accent1(),
    hintColor: Color = AppTheme.colors.textDarkDisabled(),
    textStyle: TextStyle = AppTheme.typography.body2,
    accentTextStyle: TextStyle = AppTheme.typography.body2Bold,
    hintTextStyle: TextStyle = AppTheme.typography.body3,
    imageSize: Dp = AppTheme.indents.x4,
    padding: Dp = AppTheme.indents.x1,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier.size(imageSize)
                .background(AppTheme.colors.textDarkDisabled(), RoundedCornerShape(50))
        )
        SpacerComponent { padding }
        Column {
            Row {
                Text(
                    text = user.name + " for",
                    style = textStyle,
                    color = textColor
                )
                SpacerComponent { x0_5 }
                Text(
                    text = trip.name,
                    style = accentTextStyle,
                    color = accentColor
                )
            }
            Text(
                text = tripPost.dateStart.orEmpty(),
                style = hintTextStyle,
                color = hintColor
            )
        }
    }
}