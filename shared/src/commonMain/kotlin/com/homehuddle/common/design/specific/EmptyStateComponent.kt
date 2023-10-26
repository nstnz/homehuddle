package com.homehuddle.common.design.specific

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.homehuddle.common.design.button.PrimaryButtonComponent
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.textDarkDefault
import com.homehuddle.common.design.theme.textDarkSecondary

@Composable
internal fun EmptyStateComponent(
    title: String,
    description: String,
    actionButtonText: String,
    onActionClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = AppTheme.indents.x3),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = AppTheme.typography.body1Bold,
            color = AppTheme.colors.textDarkDefault(),
            textAlign = TextAlign.Center
        )
        SpacerComponent { x1 }
        Text(
            text = description,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textDarkSecondary(),
            textAlign = TextAlign.Center
        )
        SpacerComponent { x3 }
        PrimaryButtonComponent(
            text = actionButtonText,
            onClick = onActionClick
        )
    }
}