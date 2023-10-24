package com.homehuddle.common.feature.general.welcome

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.homehuddle.common.design.button.PrimaryButtonComponent
import com.homehuddle.common.design.scaffold.GradientScaffold

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun WelcomeScreen(
    onProceedClick: () -> Unit
) {
    GradientScaffold {
        PrimaryButtonComponent("Proceed", onProceedClick)
    }
}