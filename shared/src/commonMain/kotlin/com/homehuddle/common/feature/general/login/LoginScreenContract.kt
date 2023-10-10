package com.homehuddle.common.feature.general.login

import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal object LoginScreenState : State

internal sealed interface LoginScreenIntent : Intent {
    object Load : LoginScreenIntent
    object MakeLogin : LoginScreenIntent
}

internal sealed class LoginScreenSingleEvent : SingleEvent