package com.homehuddle.common.base.data.repository

import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.UserMemorySource
import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.base.data.networksource.UserNetworkSource

internal class UserRepository(
    private val userLocalSource: UserLocalSource,
    private val userNetworkSource: UserNetworkSource,
    private val userMemorySource: UserMemorySource,
) {

    fun isLoggedIn() =
        userLocalSource.isLoggedIn()

    fun setLoggedIn(value: Boolean) =
        userLocalSource.setLoggedIn(value)

    suspend fun getUser(userId: String) =
        userMemorySource.getUser() ?: userNetworkSource.getUser(userId)?.also {
            userLocalSource.setUserId(it.id)
            userLocalSource.setUserName(it.name)
        }

    suspend fun saveUser(user: User) =
        userNetworkSource.createUser(user).also {
            userLocalSource.setUserId(user.id)
            userLocalSource.setUserName(user.name)
            userMemorySource.setUser(user)
        }
}