package com.homehuddle.common.design.specific

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.general.model.TripExpenseCategory
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.utils.Texts
import com.homehuddle.common.base.domain.utils.formatSum
import com.homehuddle.common.design.bottomsheet.BottomSheetComponent
import com.homehuddle.common.design.input.LinedTextInputComponent
import com.homehuddle.common.design.input.TextInputComponent
import com.homehuddle.common.design.input.getTextColorsDark
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textDarkSecondary

@Composable
internal fun CreateExpenseBottomSheet(
    trip: TripModel,
    expenseModel: TripExpenseModel,
    isCreateMode: Boolean,
    onSaveClick: (TripExpenseModel, TripModel) -> Unit = { _, _ -> }
) {
    val model = remember { mutableStateOf(expenseModel.copy()) }
    val sumValue = remember {
        mutableStateOf(
            TextFieldValue(
                model.value.formattedSum.orEmpty(),
                selection = TextRange(
                    model.value.formattedSum.orEmpty().length
                )
            )
        )
    }

    BottomSheetComponent(
        title = if (isCreateMode) "Add expense" else "Edit expense",
        topButton = "Save",
        topButtonClick = {
            if (model.value.sum > 0.0) {
                onSaveClick(model.value, trip)
            }
        }
    ) {
        LinedTextInputComponent(
            label = "Enter the sum you've spent",
            modifier = Modifier
                .fillMaxWidth().align(Alignment.CenterHorizontally),
            value = sumValue.value,
            onValueChange = {
                val newTV = it.copy(text = it.text.replace(",", "."))
                when {
                    newTV.text.endsWith(".") &&
                            newTV.text.count { it == '.' } > 1 -> {
                        model.value = model.value.copy(sum = newTV.text.formatSum())
                        sumValue.value = newTV.copy(
                            text = model.value.formattedSum.orEmpty(),
                            selection = TextRange(
                                model.value.formattedSum.orEmpty().length
                            )
                        )
                    }
                    newTV.text == "." -> {
                        sumValue.value = newTV.copy(
                            text = "0.",
                            selection = TextRange(2)
                        )
                    }
                    newTV.text.endsWith(".") -> sumValue.value = it
                    newTV.text.split(".").getOrNull(1)?.length == 3 -> {
                        //do nothing
                    }
                    else -> {
                        model.value = model.value.copy(sum = it.text.formatSum())
                        sumValue.value = it.copy(
                            text = model.value.formattedSum.orEmpty(),
                            selection = TextRange(
                                model.value.formattedSum.orEmpty().length
                            )
                        )
                    }
                }
            },
            maxLength = 10,
            placeholder = "0",
            colors = getTextColorsDark(),
            style = AppTheme.typography.body1Bold,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )
        SpacerComponent { x4 }
        SelectTripComponent(trip, {})
        SpacerComponent { x2 }
        Row {
            Text(
                modifier = Modifier,
                text = "Selected category:",
                style = AppTheme.typography.body3,
                color = AppTheme.colors.textDarkDisabled()
            )
            SpacerComponent { x1 }
            Text(
                modifier = Modifier,
                text = model.value.category.name,
                style = AppTheme.typography.body3Bold,
                color = AppTheme.colors.textDarkSecondary()
            )
        }
        SpacerComponent { x2 }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AppTheme.indents.x1_5)
        ) {
            TripExpenseCategory.values().toList().chunked(5).forEach {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    it.forEach {
                        TripExpenseCategoryComponent(
                            it,
                            model.value.category == it,
                            AppTheme.indents.x6
                        ) {
                            model.value = model.value.copy(category = it)
                        }
                    }
                }
            }
        }
        SpacerComponent { x4 }
        TextInputComponent(
            modifier = Modifier.fillMaxWidth().height(AppTheme.indents.x10),
            value = TextFieldValue(
                model.value.description,
                selection = TextRange(model.value.description.length)
            ),
            onValueChange = {
                model.value = model.value.copy(description = it.text)
            },
            singleLine = false,
            label = Texts.TripDescriptionLabel,
            placeholder = Texts.TripDescription,
            style = AppTheme.typography.body2,
            minHeight = AppTheme.indents.x10
        )
    }
}