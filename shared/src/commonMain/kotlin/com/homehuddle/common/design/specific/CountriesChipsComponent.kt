package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.general.model.CountryModel
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.dashedBorder
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkBorder
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
@OptIn(ExperimentalLayoutApi::class)
internal fun CountriesChipsComponent(
    countries: List<CountryModel>,
    onAddClick: () -> Unit,
    onDelete: (CountryModel) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5),
        verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier
                .noEffectsClickable { onAddClick() }
                .dashedBorder(
                    width = 1.dp,
                    color = AppTheme.colors.textDarkBorder(),
                    shape = RoundedCornerShape(50),
                    on = 8.dp,
                    off = 4.dp
                )
                .padding(horizontal = AppTheme.indents.x2, vertical = AppTheme.indents.x1)
        ) {
            Image(
                Icons.Rounded.AddCircleOutline,
                contentDescription = null,
                modifier = Modifier.size(AppTheme.indents.x3),
                colorFilter = ColorFilter.tint(
                    AppTheme.colors.textDarkDisabled()
                )
            )
            SpacerComponent { x1 }
            Text(
                text = "Add country",
                style = AppTheme.typography.body3,
                color = AppTheme.colors.textDarkDefault()
            )
        }
        countries.forEach {
            Row(
                Modifier
                    .background(AppTheme.colors.textDarkBorder(), RoundedCornerShape(50))
                    .padding(horizontal = AppTheme.indents.x2, vertical = AppTheme.indents.x1)
            ) {
                Text(
                    text = "${it.emoji}  ${it.name}",
                    style = AppTheme.typography.body3Bold,
                    color = AppTheme.colors.textDarkDefault()
                )
                SpacerComponent { x1 }
                Image(
                    Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.size(AppTheme.indents.x3)
                        .noEffectsClickable { onDelete(it) },
                    colorFilter = ColorFilter.tint(
                        AppTheme.colors.textDarkDisabled()
                    )
                )
            }
        }
    }
}