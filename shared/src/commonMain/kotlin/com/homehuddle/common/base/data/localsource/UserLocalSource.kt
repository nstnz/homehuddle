package com.homehuddle.common.base.data.localsource

import com.russhwolf.settings.set
import getSettings

private const val Name = "UserLocalSource"

private const val LoggedInKey = "LoggedInKey"

internal class UserLocalSource() {

    private val settings by lazy {
        getSettings(Name)
    }

    fun isLoggedIn() = settings.getBoolean(LoggedInKey, false)
    fun setLoggedIn(value: Boolean) = settings.set(LoggedInKey, value)
}