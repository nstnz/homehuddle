package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.model.User

internal class UserNetworkSource(
    storage: FirebaseFirestoreImpl
) : BaseNetworkSource<User>(storage) {

    override val name = "users"

    override val parentIdName: String?
        get() = null

    override fun map(map: MutableMap<String, Any>?): User? = map?.let {
        User(
            id = map["id"]?.toString(),
            ownerId = map.get("ownerId")?.toString(),
            name = map["name"]?.toString().orEmpty(),
            currencyCode = map["currencyCode"]?.toString().orEmpty(),
            visitedCountries = map["visitedCountries"]?.toString().orEmpty(),
        )
    }
}