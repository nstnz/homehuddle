package com.homehuddle.common.base.data.memorysource

import com.homehuddle.common.base.data.model.User

internal class UserMemorySource() {

    private var user: User? = null

    fun setUser(user: User) {
        this.user = user
    }

    fun getUser() = user

    fun clear() {
        user = null
    }
}