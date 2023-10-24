package com.homehuddle.common.base.data.networksource

import com.homehuddle.common.base.data.mapper.mapToUser
import com.homehuddle.common.base.data.model.User
import com.homehuddle.common.base.domain.utils.safeGet
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

internal class UserNetworkSource() {

    private val name = "users"

    private val storage by lazy {
        Firebase.firestore
    }

    suspend fun getUser(userId: String): User? {
        return storage.collection(name)
            .document(userId)
            .safeGet()
            ?.mapToUser()
    }

    suspend fun createUser(user: User) {
        storage.collection(name)
            .document(user.id)
            .set(user)
    }
}