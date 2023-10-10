package com.homehuddle.common.feature.general.login

import androidx.compose.runtime.Composable
import com.homehuddle.common.design.button.PrimaryButtonComponent
import com.homehuddle.common.design.scaffold.GradientScaffold

@Composable
internal fun LoginScreen(
    onLoginClick: () -> Unit
) {
    GradientScaffold {
        PrimaryButtonComponent("Login", onLoginClick)
    }
}