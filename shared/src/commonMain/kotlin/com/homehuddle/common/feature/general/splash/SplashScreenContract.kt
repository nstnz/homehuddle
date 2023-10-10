package com.homehuddle.common.feature.general.splash

import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal object SplashScreenState : State

internal sealed interface SplashScreenIntent : Intent {
    object Load : SplashScreenIntent
}

internal sealed class SplashScreenSingleEvent : SingleEvent