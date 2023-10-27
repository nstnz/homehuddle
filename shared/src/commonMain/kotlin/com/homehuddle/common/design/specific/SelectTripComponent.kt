package com.homehuddle.common.design.specific

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textDarkSecondary

@Composable
internal fun SelectTripComponent(
    trip: TripModel,
    onClick: () -> Unit = {}
) {
    Row(Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier,
            text = "Trip:",
            style = AppTheme.typography.body3,
            color = AppTheme.colors.textDarkDisabled()
        )
        SpacerComponent { x1 }
        Text(
            modifier = Modifier.weight(1f).noEffectsClickable { onClick() },
            text = trip.name,
            style = AppTheme.typography.body3Bold,
            color = AppTheme.colors.textDarkSecondary()
        )
    }
}