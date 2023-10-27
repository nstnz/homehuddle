package com.homehuddle.common.design.specific

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.base.domain.utils.formatSum
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.textDarkDefault

@Composable
internal fun TripDailyExpensesComponent(
    date: String,
    expenses: List<TripExpenseModel>,
    userModel: UserModel?,
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
                    text = expenses.sumOf { it.convertedSum }.formatSum().orEmpty() + userModel?.currency?.code,
                    color = AppTheme.colors.textDarkDefault(),
                    style = AppTheme.typography.body2Bold
                )
            }
            SpacerComponent { x1 }
            TripExpensesCardComponent(
                expenses = expenses,
                userModel = userModel,
                modifier = Modifier.padding(horizontal = AppTheme.indents.x3)
            )
        }
    }
}