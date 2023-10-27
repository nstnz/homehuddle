package com.homehuddle.common.design.specific

import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.base.domain.general.model.TripExpenseCategory
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.utils.formatSum
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.transparent

@Composable
internal fun TripExpensesPieChartComponent(
    expenses: List<TripExpenseModel>,
    currencyModel: CurrencyModel?
) {
    val testPieChartData: List<PieChartData> =
        expenses
            .groupBy { it.category }
            .map {
                PieChartData(
                    partName = it.key.name,
                    data = it.value.sumOf { it.convertedSum },
                    color = getCategoryColor(it.key),
                )
            }
            .sortedBy { it.data }
            .take(5)

    Box(Modifier.fillMaxWidth()) {
        Column(
            Modifier.fillMaxWidth().height(120.dp)
                .verticalScroll(rememberScrollState(), enabled = false)
        ) {
            PieChart(
                modifier = Modifier.fillMaxSize().height(240.dp)
                    .offset(x = -135.dp + AppTheme.indents.x3, y = -90.dp),
                pieChartData = testPieChartData,
                ratioLineColor = AppTheme.colors.transparent(),
                textRatioStyle = AppTheme.typography.body3.copy(lineHeight = 0.sp, fontSize = 0.sp),
                descriptionStyle = AppTheme.typography.body3.copy(
                    lineHeight = 0.sp,
                    fontSize = 0.sp
                ),
                animation = TweenSpec(durationMillis = 0),
                outerCircularColor = AppTheme.colors.transparent(),
            )
        }
        Column(
            Modifier.fillMaxWidth().padding(start = 120.dp + AppTheme.indents.x6)
                .padding(end = AppTheme.indents.x3)
                .align(Alignment.CenterEnd)
        ) {
            testPieChartData.forEach {
                val category = TripExpenseCategory.valueOf(it.partName)
                Row {
                    TripExpenseCategoryComponent(
                        category,
                        selected = true,
                        size = 24.dp
                    )
                    SpacerComponent { x1 }
                    Text(
                        text = category.name + " (${it.data.formatSum() + currencyModel?.code})",
                        style = AppTheme.typography.body3,
                        color = AppTheme.colors.textDarkDisabled()
                    )
                }
                SpacerComponent { x1 }
            }
        }
    }
}