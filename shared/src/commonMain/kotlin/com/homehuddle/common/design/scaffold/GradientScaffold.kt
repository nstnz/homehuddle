package com.homehuddle.common.design.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.snackbar.SnackbarComponent
import com.homehuddle.common.design.snackbar.SnackbarHost
import com.homehuddle.common.design.snackbar.SnackbarHostState

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
	dialog: @Composable () -> Unit = {},
	content: @Composable (PaddingValues) -> Unit,
) {
	Column(modifier = Modifier
		.fillMaxSize()
		.background(brush = gradient)) {
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
