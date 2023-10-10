package com.homehuddle.common.design.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent1
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.theme.textLightDisabled

internal sealed interface ButtonColors {

    val backgroundColor: Color @Composable get
    val backgroundColorOnTap: Color @Composable get() = backgroundColor.copy(alpha = 0.7f)
    val backgroundColorDisabled: Color @Composable get
    val textColor: Color @Composable get
    val textColorDisabled: Color @Composable get
    val progressColor: Color @Composable get() = textColor
}

internal object PrimaryButtonColors : ButtonColors {

    override val backgroundColor: Color
        @Composable get() = AppTheme.colors.accent1()
    override val backgroundColorDisabled: Color
        @Composable get() = AppTheme.colors.accent1()
    override val textColor: Color
        @Composable get() = AppTheme.colors.textLightDefault()
    override val textColorDisabled: Color
        @Composable get() = AppTheme.colors.textLightDisabled()
}

internal object SecondaryButtonColors : ButtonColors {

    override val backgroundColor: Color
        @Composable get() = AppTheme.colors.accent1()
    override val backgroundColorDisabled: Color
        @Composable get() = AppTheme.colors.accent1()
    override val textColor: Color
        @Composable get() = AppTheme.colors.textLightDefault()
    override val textColorDisabled: Color
        @Composable get() = AppTheme.colors.textLightDisabled()
}