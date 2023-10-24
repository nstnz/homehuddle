package com.homehuddle.common.base.data.localsource

import com.russhwolf.settings.set
import getSettings

private const val Name = "UserLocalSource"

private const val LoggedInKey = "LoggedInKey"
private const val UserIdKey = "UserIdKey"
private const val UserNameKey = "UserNameKey"
private const val UserFamilyIdKey = "UserFamilyIdKey"

internal class UserLocalSource() {

    private val settings by lazy {
        getSettings(Name)
    }

    fun isLoggedIn() = settings.getBoolean(LoggedInKey, false)
    fun setLoggedIn(value: Boolean) = settings.set(LoggedInKey, value)
    fun getUserId() = settings.getStringOrNull(UserIdKey)
    fun setUserId(value: String) = settings.set(UserIdKey, value)
    fun getUserFamilyId() = settings.getStringOrNull(UserFamilyIdKey)
    fun setUserFamilyId(value: String) = settings.set(UserFamilyIdKey, value)
    fun getUserName() = settings.getStringOrNull(UserNameKey)
    fun setUserName(value: String) = settings.set(UserNameKey, value)
}