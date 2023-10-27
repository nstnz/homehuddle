package com.homehuddle.common.design.specific

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.design.bottomsheet.BottomSheetComponent
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun SelectTripBottomSheet(
    title: String,
    trips: List<TripModel>,
    selected: TripModel?,
    onSelect: (TripModel) -> Unit = {}
) {
    BottomSheetComponent(
        title = title,
    ) {
        TripsList(
            trips, selected, onSelect
        )
    }
}

@Composable
internal fun TripsList(
    trips: List<TripModel>,
    selected: TripModel?,
    onSelect: (TripModel) -> Unit = {}
) {
    Column(Modifier.fillMaxWidth()) {
        trips.forEach {
            TripComponent(
                selected = it.id == selected?.id,
                model = it,
                onSelect = onSelect
            )
            SpacerComponent { x1_5 }
        }
    }
}

@Composable
private fun TripComponent(
    selected: Boolean,
    model: TripModel,
    onSelect: (TripModel) -> Unit = {}
) {
    Column(Modifier.fillMaxWidth().noEffectsClickable { onSelect(model) }) {
        Text(
            text = model.name,
            color = if (selected) AppTheme.colors.accent2() else AppTheme.colors.textDarkDefault(),
            style = AppTheme.typography.body2Bold,
        )
        Text(
            text = model.description,
            color = AppTheme.colors.textDarkDisabled(),
            style = AppTheme.typography.body3,
            maxLines = 2
        )
    }
}