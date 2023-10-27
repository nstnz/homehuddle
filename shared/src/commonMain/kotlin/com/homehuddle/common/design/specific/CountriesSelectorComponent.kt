package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.CountryModel
import com.homehuddle.common.design.input.TextInputComponent
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent2
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkBorder
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun CountriesSelectorComponent(
    countries: List<CountryModel>,
    selectedCountries: List<CountryModel>,
    onClick: (CountryModel, Boolean) -> Unit = { _, _ -> }
) {
    val text = remember { mutableStateOf(TextFieldValue("")) }

    Column {
        Box {
            TextInputComponent(
                modifier = Modifier.fillMaxWidth(),
                value = text.value,
                onValueChange = {
                    text.value = it
                },
                singleLine = true,
                placeholder = "Search",
                style = AppTheme.typography.body2,
            )
            Image(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppTheme.colors.textDarkDisabled()),
                modifier = Modifier
                    .padding(end = AppTheme.indents.x2)
                    .size(AppTheme.indents.x3)
                    .background(AppTheme.colors.background2())
                    .align(Alignment.CenterEnd)
            )
        }

        SpacerComponent { x1_5 }
        Column(modifier = Modifier.fillMaxWidth()) {
            countries
                .filter { it.name.lowercase().startsWith(text.value.text.lowercase()) }
                .sortedBy { it.name }
                .forEach {
                    CountrySelectorComponent(it, selectedCountries.contains(it), onClick)
                    SpacerComponent { x1_5 }
                }
        }
    }
}


@Composable
internal fun CountrySelectorComponent(
    country: CountryModel,
    selected: Boolean,
    onClick: (CountryModel, Boolean) -> Unit = { _, _ -> }
) {
    Row(
        Modifier.fillMaxWidth().noEffectsClickable { onClick(country, !selected) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier.size(AppTheme.indents.x6)
                .background(
                    if (selected) AppTheme.colors.accent2()
                        .copy(alpha = 0.3f) else AppTheme.colors.textDarkBorder(),
                    RoundedCornerShape(50)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = country.emoji,
                style = AppTheme.typography.body2
            )
        }
        Spacer(Modifier.width(32.dp))
        SpacerComponent { x2 }
        Column(Modifier.weight(1f)) {
            Text(
                text = country.name,
                color = if (selected) AppTheme.colors.accent2() else AppTheme.colors.textDarkDefault(),
                style = AppTheme.typography.body2Bold,
                maxLines = 2
            )
        }
    }
}