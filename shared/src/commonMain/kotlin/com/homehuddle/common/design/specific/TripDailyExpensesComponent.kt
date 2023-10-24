package com.homehuddle.common.design.specific

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homehuddle.common.base.data.model.TripExpense
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.textDarkDefault

@Composable
internal fun TripDailyExpensesComponent(
    date: String,
    expenses: List<TripExpense>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            Modifier.fillMaxWidth()
                .padding(bottom = AppTheme.indents.x2)
        ) {
            Row(
                Modifier.fillMaxWidth()
                    .background(AppTheme.gradients.divider())
                    .padding(vertical = AppTheme.indents.x1)
                    .padding(horizontal = AppTheme.indents.x3),
            ) {
                Text(
                    text = date,
                    color = AppTheme.colors.textDarkDefault(),
                    style = AppTheme.typography.body2
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = expenses.sumOf { it.sum }.toString(),
                    color = AppTheme.colors.textDarkDefault(),
                    style = AppTheme.typography.body2Bold
                )
            }
            SpacerComponent { x1 }
            expenses.forEach {
                Row(
                    Modifier.fillMaxWidth()
                        .padding(horizontal = AppTheme.indents.x3),
                ) {
                    Text(
                        text = it.sum.toString(),
                        color = AppTheme.colors.textDarkDefault(),
                        modifier = Modifier.width(120.dp),
                        style = AppTheme.typography.body2Bold
                    )
                    SpacerComponent { x1 }
                    Text(
                        text = it.description,
                        color = AppTheme.colors.textDarkDefault(),
                        modifier = Modifier.weight(1f),
                        style = AppTheme.typography.body2
                    )
                }
            }
        }
    }
}