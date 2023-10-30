package com.homehuddle.common.design.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.homehuddle.common.design.button.PrimaryButtonComponent
import com.homehuddle.common.design.button.SecondaryButtonComponent
import com.homehuddle.common.design.spacer.SpacerComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.background2
import com.homehuddle.common.design.theme.textDarkDefault

@Composable
internal fun BottomSheetComponent(
    title: String,
    description: String? = null,
    topButton: String? = null,
    bottomButton: String? = null,
    fixedHeight: Dp? = null,
    topButtonClick: () -> Unit = {},
    bottomButtonClick: () -> Unit = {},
    customContent: @Composable (ColumnScope.() -> Unit)? = null
) {
    Column(
        Modifier.fillMaxWidth()
            .background(AppTheme.colors.background2(), AppTheme.shapes.x4_5_top)
            .padding(AppTheme.indents.x3)
    ) {
        Text(
            text = title,
            color = AppTheme.colors.textDarkDefault(),
            style = AppTheme.typography.body1Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        if (!description.isNullOrEmpty()) {
            SpacerComponent { x1 }
            Text(
                text = description,
                color = AppTheme.colors.textDarkDefault(),
                style = AppTheme.typography.body2,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        SpacerComponent { x3 }

        if (customContent != null) {
            Column(
                modifier = if (fixedHeight != null) {
                    Modifier.fillMaxWidth()
                        .height(fixedHeight)
                        .verticalScroll(rememberScrollState())
                } else {
                    Modifier.fillMaxWidth()
                        .heightIn(max = 500.dp)
                        .verticalScroll(rememberScrollState())
                }
            ) {
                customContent.invoke(this)
                SpacerComponent { x3 }
            }
        }

        if (!topButton.isNullOrEmpty()) {
            SpacerComponent { x1 }
            PrimaryButtonComponent(topButton, topButtonClick)
        }

        if (!bottomButton.isNullOrEmpty()) {
            SpacerComponent { x1 }
            SecondaryButtonComponent(bottomButton, bottomButtonClick)
        }
    }
}