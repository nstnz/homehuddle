package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    override val id: String?,
    val name: String,
    val currencyCode: String,
): BaseModel<User> {

    override fun copyId(id: String?): User = this.copy(id = id)
}

