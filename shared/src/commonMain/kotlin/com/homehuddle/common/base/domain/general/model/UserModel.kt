package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: String?,
    val name: String,
    val isMe: Boolean,
    val currencyCode: String
)
