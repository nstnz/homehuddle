package com.homehuddle.common.design.datepicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.domain.utils.Texts
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background1
import com.homehuddle.common.design.theme.ignoreHorizontalParentPadding
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textDarkBorder
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkDisabled
import com.mohamedrejeb.calf.ui.datepicker.AdaptiveDatePicker
import com.mohamedrejeb.calf.ui.datepicker.UIKitDisplayMode
import com.mohamedrejeb.calf.ui.datepicker.rememberAdaptiveDatePickerState

@Composable
internal fun TwoDatesPicker(
    showFromState: Boolean? = null,
    dateStart: String? = null,
    dateEnd: String? = null,
    modifier: Modifier = Modifier,
    onFromClick: () -> Unit = {},
    onToClick: () -> Unit = {},
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.weight(1f)
                .noEffectsClickable { onFromClick() }
                .border(
                    BorderStroke(
                        AppTheme.indents.x0_25,
                        if (showFromState == true) {
                            AppTheme.colors.background1()
                        } else {
                            AppTheme.colors.textDarkBorder()
                        }
                    ),
                    AppTheme.shapes.x1
                )
                .padding(AppTheme.indents.x2),
            text = dateStart ?: Texts.FromDate,
            style = AppTheme.typography.body2,
            color = if (dateStart.isNullOrEmpty()) AppTheme.colors.textDarkDisabled() else AppTheme.colors.textDarkDefault()
        )
        SpacerComponent { x1_5 }
        Text(
            text = "—",
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textDarkDefault(),
        )
        SpacerComponent { x1_5 }
        Text(
            modifier = Modifier.weight(1f)
                .noEffectsClickable { onToClick() }
                .border(
                    BorderStroke(
                        AppTheme.indents.x0_25,
                        if (showFromState == false) {
                            AppTheme.colors.background1()
                        } else {
                            AppTheme.colors.textDarkBorder()
                        }
                    ),
                    AppTheme.shapes.x1
                )
                .padding(AppTheme.indents.x2),
            text = dateEnd ?: Texts.ToDate,
            style = AppTheme.typography.body2,
            color = if (dateEnd.isNullOrEmpty()) AppTheme.colors.textDarkDisabled() else AppTheme.colors.textDarkDefault()
        )
    }
}

@Composable
internal fun OneDatePicker(
    dateStart: String? = null,
    modifier: Modifier = Modifier,
    onFromClick: () -> Unit = {},
) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.weight(1f)
                .noEffectsClickable { onFromClick() }
                .border(
                    BorderStroke(
                        AppTheme.indents.x0_25,
                        AppTheme.colors.textDarkBorder()
                    ),
                    AppTheme.shapes.x1
                )
                .padding(AppTheme.indents.x2),
            text = dateStart ?: Texts.FromDate,
            style = AppTheme.typography.body2,
            color = if (dateStart.isNullOrEmpty()) AppTheme.colors.textDarkDisabled() else AppTheme.colors.textDarkDefault()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CustomDatePicker(
    initialDate: Long? = null,
    modifier: Modifier = Modifier,
    onDatePicked: (Long?) -> Unit = {}
) {
    Box(modifier.fillMaxWidth().clipToBounds()) {
        val state = rememberAdaptiveDatePickerState(
            initialMaterialDisplayMode = DisplayMode.Picker,
            initialUIKitDisplayMode = UIKitDisplayMode.Picker,
            initialSelectedDateMillis = initialDate
        )
        if (state.selectedDateMillis != initialDate) {
            onDatePicked(state.selectedDateMillis)
        }

        Column(
            Modifier.heightIn(max = 290.dp)
                .verticalScroll(rememberScrollState(), enabled = false)
        ) {
            AdaptiveDatePicker(
                state = state,
                modifier = Modifier.ignoreHorizontalParentPadding(AppTheme.indents.x2_5)
                    .offset(y = -124.dp),
                showModeToggle = false
            )
        }
    }
}