package com.homehuddle.common.design.specific

import androidx.compose.runtime.Composable
import com.homehuddle.common.design.bottomsheet.BottomSheetComponent
import com.homehuddle.common.design.datepicker.CustomDatePicker

@Composable
internal fun CalendarBottomSheet(
    timestamp: Long?,
    onDatePicked: (Long?) -> Unit = {}
) {
    BottomSheetComponent(
        title = "Choose date"
    ) {
        CustomDatePicker(
            initialDate = timestamp,
            onDatePicked = onDatePicked,
        )
    }
}