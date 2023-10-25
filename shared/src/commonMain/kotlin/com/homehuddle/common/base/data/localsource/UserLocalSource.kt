package com.homehuddle.common.base.data.localsource

import com.homehuddle.common.base.domain.general.model.UserModel
import com.russhwolf.settings.set
import getSettings

private const val Name = "UserLocalSource"

private const val LoggedInKey = "LoggedInKey"
private const val UserIdKey = "UserIdKey"
private const val UserNameKey = "UserNameKey"
private const val UserCurrencyCodeKey = "UserCurrencyCodeKey"

internal class UserLocalSource() {

    private val settings by lazy {
        getSettings(Name)
    }

    fun setUser(user: UserModel) {
        setLoggedIn(true)
        setUserId(user.id)
        setUserName(user.name)
        setUserCurrencyCode(user.currencyCode)
    }

    fun isLoggedIn() = settings.getBoolean(LoggedInKey, false) && !getUserId().isNullOrEmpty()
    fun setLoggedIn(value: Boolean) = settings.set(LoggedInKey, value)
    fun getUserId() = settings.getStringOrNull(UserIdKey)
    fun setUserId(value: String?) = settings.set(UserIdKey, value)
    fun getUserCurrencyCode() = settings.getStringOrNull(UserCurrencyCodeKey)
    fun setUserCurrencyCode(value: String) = settings.set(UserCurrencyCodeKey, value)
    fun getUserName() = settings.getStringOrNull(UserNameKey)
    fun setUserName(value: String) = settings.set(UserNameKey, value)
}