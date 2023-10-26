package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    override val id: String?,
    override val ownerId: String?,
    val name: String,
    val isMe: Boolean,
    val currencyCode: String
) : BaseDomainModel<UserModel>
