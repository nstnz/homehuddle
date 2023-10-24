package com.homehuddle.common.feature.general.splash

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.homehuddle.common.design.scaffold.GradientScaffold
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SplashScreen(
    onReady: () -> Unit = {}
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(1000)
        onReady()
    }

    GradientScaffold {
    }
}