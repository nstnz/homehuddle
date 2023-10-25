package com.homehuddle.common.base.data.repository

import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.mapper.mapToUserModel
import com.homehuddle.common.base.data.memorysource.UserMemorySource
import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.base.data.networksource.UserNetworkSource
import com.homehuddle.common.base.domain.general.model.UserModel

internal class UserRepository(
    private val userLocalSource: UserLocalSource,
    userNetworkSource: UserNetworkSource,
    userMemorySource: UserMemorySource,
) : BaseRepository<User, UserModel, UserNetworkSource, UserMemorySource>(
    userNetworkSource,
    userMemorySource
) {

    override suspend fun map(model: User?): UserModel? {
        return model.mapToUserModel(isMe = isMe(model?.id))
    }

    fun isLoggedIn() = userLocalSource.isLoggedIn()

    fun isMe(userId: String?) = userId != null && userId == userLocalSource.getUserId()

    suspend fun getMe() = get(userLocalSource.getUserId())

    suspend fun getUser(userId: String?) = get(userId)

    suspend fun saveMe(user: User) =
        create(user).also {
            it?.let { userLocalSource.setUser(it) }
        }
}