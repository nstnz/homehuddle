package com.homehuddle.common.feature.general.welcome

import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal object WelcomeScreenState : State

internal sealed interface WelcomeScreenIntent : Intent {
    object Load : WelcomeScreenIntent
    object Proceed : WelcomeScreenIntent
}

internal sealed class WelcomeScreenSingleEvent : SingleEvent