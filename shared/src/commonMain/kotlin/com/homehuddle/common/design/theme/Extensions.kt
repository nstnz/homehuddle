package com.homehuddle.common.design.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.homehuddle.common.design.spacer.SpacerComponent

@Composable
internal fun Colors.toMaterialColors(): androidx.compose.material.Colors =
    androidx.compose.material.Colors(
        primary = background1(),
        primaryVariant = background1(),
        secondary = background1(),
        secondaryVariant = background1(),
        background = background2(),
        surface = background2(),
        error = accent5(),
        onPrimary = textDarkDefault(),
        onSecondary = textDarkDefault(),
        onBackground = textDarkDefault(),
        onSurface = textDarkDefault(),
        onError = textLightDefault(),
        isLight = true,
    )

@Composable
internal fun Modifier.noEffectsClickable(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) =
    this.clickable(
        onClick = onClick,
        interactionSource = interactionSource,
        indication = null
    )

@Composable
fun Modifier.ignoreHorizontalParentPadding(horizontal: Dp): Modifier {
    val rtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    return this.layout { measurable, constraints ->
        val overridenWidth = constraints.maxWidth + 2 * horizontal.roundToPx()
        val placeable = measurable.measure(constraints.copy(maxWidth = overridenWidth))
        layout(placeable.width, placeable.height) {
            if (rtl) {
                placeable.place(2 * horizontal.roundToPx(), 0)
            } else {
                placeable.place(0, 0)
            }
        }
    }
}

@Composable
fun InOuterShadow(
    modifier: Modifier,
    color: Color = AppTheme.colors.accent1(),
    cornersRadius: Dp = AppTheme.indents.x2_5,
    offsetY: Dp = 4.dp,
    addDarkness: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier
            .background(color, RoundedCornerShape(cornersRadius)),
    ) {
        Column(
            Modifier
                .background(
                    if (addDarkness)
                        Color(0x40000000) else AppTheme.colors.transparent(),
                    RoundedCornerShape(cornersRadius)
                ),
        ) {
            content()
            SpacerComponent { offsetY }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SetBottomSheetListener(
    bottomSheetState: ModalBottomSheetState,
    onShow: () -> Unit = {},
    onHide: () -> Unit = {},
) {
    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.isVisible }.collect { isVisible ->
            if (!isVisible) {
                onHide()
            } else {
                onShow()
            }
        }
    }
}