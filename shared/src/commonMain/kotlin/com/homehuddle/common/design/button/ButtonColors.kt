package com.homehuddle.common.design.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent1
import com.homehuddle.common.design.theme.textDarkBorder
import com.homehuddle.common.design.theme.textDarkDisabled
import com.homehuddle.common.design.theme.textDarkSecondary
import com.homehuddle.common.design.theme.textLightDefault
import com.homehuddle.common.design.theme.textLightDisabled

internal sealed interface ButtonColors {

    val backgroundColor: Brush @Composable get
    val backgroundColorOnTap: Brush @Composable get() = backgroundColor
    val backgroundColorDisabled: Color @Composable get
    val textColor: Color @Composable get
    val textColorDisabled: Color @Composable get
    val progressColor: Color @Composable get() = textColor
}

internal object PrimaryButtonColors : ButtonColors {

    override val backgroundColor: Brush
        @Composable get() = AppTheme.gradients.buttonGradient()
    override val backgroundColorDisabled: Color
        @Composable get() = AppTheme.colors.accent1()
    override val textColor: Color
        @Composable get() = AppTheme.colors.textLightDefault()
    override val textColorDisabled: Color
        @Composable get() = AppTheme.colors.textLightDisabled()
}

internal object SecondaryButtonColors : ButtonColors {

    override val backgroundColor: Brush
        @Composable get() = AppTheme.gradients.button2Gradient()
    override val backgroundColorDisabled: Color
        @Composable get() = AppTheme.colors.textDarkBorder()
    override val textColor: Color
        @Composable get() = AppTheme.colors.textDarkSecondary()
    override val textColorDisabled: Color
        @Composable get() = AppTheme.colors.textDarkDisabled()
}