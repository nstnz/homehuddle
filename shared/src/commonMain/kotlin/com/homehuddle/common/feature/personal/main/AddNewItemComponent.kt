package com.homehuddle.common.feature.personal.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import com.homehuddle.common.base.domain.utils.Texts
import com.homehuddle.common.design.bottomsheet.BottomSheetComponent
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.noEffectsClickable
import com.homehuddle.common.design.theme.textLightDefault

@Composable
internal fun AddNewItemBottomSheet(
    onAddTripClick: () -> Unit = {},
    onAddTripPostClick: () -> Unit = {},
    onAddExpensesClick: () -> Unit = {},
    onAddLocationsClick: () -> Unit = {},
) {
    BottomSheetComponent(
        title = Texts.AddNewItemTitle,
        customContent = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x2)
            ) {
                ItemButton(Texts.Trip, AppTheme.gradients.tripGradient(), onAddTripClick)
                ItemButton(Texts.Post, AppTheme.gradients.tripPostGradient(), onAddTripPostClick)
            }
            SpacerComponent { x2 }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.indents.x2)
            ) {
                ItemButton(Texts.Expenses, AppTheme.gradients.expenseGradient(), onAddExpensesClick)
                ItemButton(
                    Texts.Locations,
                    AppTheme.gradients.locationGradient(),
                    onAddLocationsClick
                )
            }
        }
    )
}

@Composable
private fun RowScope.ItemButton(
    text: String,
    background: Brush,
    onClick: () -> Unit = {}
) {
    Box(
        Modifier.weight(1f)
            .height(AppTheme.indents.x8)
            .noEffectsClickable { onClick() }
            .background(background, AppTheme.shapes.x2_5)
            .padding(AppTheme.indents.x2),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = AppTheme.colors.textLightDefault(),
            style = AppTheme.typography.body2Bold,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}