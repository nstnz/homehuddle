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

    fun isMe(userId: String) = userId == userLocalSource.getUserId()

    fun getMe() = userMemorySource.getUser()

    suspend fun getUser(userId: String) =
        if (isMe(userId)) {
            getMe()
        } else {
            userNetworkSource.getUser(userId)
        }

    suspend fun saveMe(user: User) =
        userNetworkSource.createUser(user).also {
            userLocalSource.setUser(user)
            userMemorySource.setUser(user)
        }
}