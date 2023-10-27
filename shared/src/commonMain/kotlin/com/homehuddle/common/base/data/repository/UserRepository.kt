package com.homehuddle.common.base.data.repository

import com.homehuddle.UsersDao
import com.homehuddle.common.base.data.dbsource.UserDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.UserMemorySource
import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.base.data.networksource.UserNetworkSource
import com.homehuddle.common.base.domain.general.model.UserModel

internal class UserRepository(
    private val userLocalSource: UserLocalSource,
    networkSource: UserNetworkSource,
    memorySource: UserMemorySource,
    dbSource: UserDbSource,
) : BaseRepository<User, UserModel, UsersDao, UserNetworkSource, UserDbSource, UserMemorySource>(
    networkSource,
    memorySource,
    dbSource,
    userLocalSource
) {

    override val refreshTimestampsDiff: Long
        get() = Long.MAX_VALUE

    suspend fun getCurrentUser() = get(getOwnerId())

    fun saveCurrentUser(user: User) = saveCurrentUser(
        UserModel(
            id = user.id,
            ownerId = user.ownerId,
            name = user.name,
            isMe = true,
            currencyCode = user.currencyCode,
        )
    )

    fun saveCurrentUser(user: UserModel) = userLocalSource.setUser(user)

    fun isLoggedIn() = userLocalSource.isLoggedIn()

    override suspend fun mapToDbModel(model: User?): UsersDao? = model?.let {
        UsersDao(
            id = it.id.orEmpty(),
            name = it.name,
            currencyCode = it.currencyCode,
            ownerId = it.ownerId.orEmpty()
        )
    }

    override suspend fun mapToDomainModel(model: UsersDao?): UserModel? = model?.let {
        UserModel(
            isMe = it.ownerId == getOwnerId(),
            id = it.id,
            name = it.name.orEmpty(),
            currencyCode = it.currencyCode.orEmpty(),
            ownerId = it.ownerId
        )
    }

    override suspend fun mapToDbModel(model: UserModel?): UsersDao? = model?.let {
        UsersDao(
            id = it.id.orEmpty(),
            name = it.name,
            currencyCode = it.currencyCode,
            ownerId = it.ownerId
        )
    }

    override suspend fun clear() {
        userLocalSource.clear()
        super.clear()
    }
}