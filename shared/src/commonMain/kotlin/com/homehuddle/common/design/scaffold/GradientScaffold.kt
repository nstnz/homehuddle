package com.homehuddle.common.design.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.homehuddle.common.design.snackbar.SnackbarComponent
import com.homehuddle.common.design.snackbar.SnackbarHost
import com.homehuddle.common.design.snackbar.SnackbarHostState
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.textDarkDisabled

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun GradientScaffold(
    gradient: Brush = AppTheme.gradients.background1(),
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = {
        SnackbarHost(
            hostState = it,
            snackbar = { data -> SnackbarComponent(data.title, data.description, data.isError) }
        )
    },
    bottomSheet: @Composable ColumnScope.() -> Unit = {
        Box(Modifier.size(1.dp))
    },
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    dialog: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    ModalBottomSheetLayout(
        sheetContent = {
            bottomSheet()
        },
        sheetElevation = AppTheme.elevations.flat,
        sheetShape = AppTheme.shapes.x4_5_top,
        sheetState = bottomSheetState,
        sheetBackgroundColor = Color.Transparent,
        scrimColor = AppTheme.colors.textDarkDisabled().copy(alpha = 0.25f),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient)
        ) {
            ScaffoldComponent(
                modifier = modifier,
                topBar = topBar,
                bottomBar = bottomBar,
                content = content,
                backgroundColor = Color.Transparent,
                scaffoldState = scaffoldState,
                snackbarHost = snackbarHost,
                dialog = dialog
            )
        }
    }
}
