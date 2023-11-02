package com.homehuddle.common.design.specific

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.homehuddle.common.base.domain.general.model.LocationModel
import com.homehuddle.common.design.bottomsheet.BottomSheetComponent
import com.homehuddle.common.design.input.TextInputComponent
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent5
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.textDarkDisabled

@Composable
internal fun SelectLocationBottomSheet(
    title: String,
    selectedLocation: LocationModel,
    onSelect: (LocationModel) -> Unit = {}
) {
    val location = remember { mutableStateOf(selectedLocation) }

    BottomSheetComponent(
        title = title,
        fixedHeight = 500.dp,
        topButton = "Save",
        topButtonClick = {
            onSelect(location.value)
        }
    ) {
        val text = remember { mutableStateOf(TextFieldValue("")) }
        Column(Modifier.fillMaxWidth().height(500.dp)) {
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
            Box(Modifier.fillMaxWidth().weight(1f).background(AppTheme.colors.accent5()))
            SpacerComponent { x2 }
            PointComponent(
                model = location.value,
            )
            SpacerComponent { x3 }
        }
    }
}