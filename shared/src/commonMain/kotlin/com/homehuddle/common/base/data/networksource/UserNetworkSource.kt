package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.mapper.mapToUser
import com.homehuddle.common.base.data.model.User

internal class UserNetworkSource(
    storage: FirebaseFirestoreImpl
) : BaseNetworkSource<User>(storage, { it.mapToUser() }) {

    override val name = "users"
}