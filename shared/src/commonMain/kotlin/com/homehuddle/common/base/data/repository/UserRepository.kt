package com.homehuddle.common.base.data.repository

import com.homehuddle.common.base.data.localsource.UserLocalSource

internal class UserRepository(
    private val userLocalSource: UserLocalSource
) {

    fun isLoggedIn() = userLocalSource.isLoggedIn()
    fun setLoggedIn(value: Boolean) = userLocalSource.setLoggedIn(value)
}