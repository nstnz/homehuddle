package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.mapper.mapToUser
import com.homehuddle.common.base.data.model.User

internal class UserNetworkSource(
    private val storage: FirebaseFirestoreImpl
) {

    private val name = "users"

    suspend fun getUser(userId: String): User? {
        return storage.get(name, userId)
            ?.mapToUser()
    }

    suspend fun createUser(user: User) {
        storage.createOrUpdate(
            name, user.id, user
        )
    }
}