package com.homehuddle.common.design.specific

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.design.bottomsheet.BottomSheetComponent
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun SelectCurrencyBottomSheet(
    title: String,
    currencies: List<CurrencyModel>,
    selected: CurrencyModel?,
    onSelect: (CurrencyModel) -> Unit = {}
) {
    BottomSheetComponent(
        title = title,
    ) {
        CurrenciesList(
            currencies, selected, onSelect
        )
    }
}

@Composable
internal fun CurrenciesList(
    currencies: List<CurrencyModel>,
    selected: CurrencyModel?,
    onSelect: (CurrencyModel) -> Unit = {}
) {
    Column(Modifier.fillMaxWidth()) {
        currencies.forEach {
            CurrencyComponent(
                selected = it.id == selected?.id,
                model = it,
                onSelect = onSelect
            )
            SpacerComponent { x1 }
        }
    }
}

@Composable
internal fun CurrencyComponent(
    selected: Boolean,
    model: CurrencyModel,
    onSelect: (CurrencyModel) -> Unit = {}
) {
    Row(Modifier.fillMaxWidth()
        .noEffectsClickable { onSelect(model) }) {
        Text(
            text = model.code,
            color = if (selected) AppTheme.colors.accent2() else AppTheme.colors.textDarkDefault(),
            style = AppTheme.typography.body1Bold,
            maxLines = 1,
            modifier = Modifier.width(80.dp)
        )
        SpacerComponent { x2 }
        Column(Modifier.weight(1f)) {
            Text(
                text = model.name,
                color = if (selected) AppTheme.colors.accent2() else AppTheme.colors.textDarkDefault(),
                style = AppTheme.typography.body2Bold,
                maxLines = 1
            )
            Text(
                text = model.id.orEmpty(),
                color = AppTheme.colors.textDarkDisabled(),
                style = AppTheme.typography.body3,
                maxLines = 1
            )
        }
    }
}