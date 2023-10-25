package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSubscription(
    override val id: String?,
): BaseModel<UserSubscription> {

    override fun copyId(id: String?): UserSubscription = this.copy(id = id)
}

