package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    override val id: String?,
    override val ownerId: String?,
    val name: String,
    val currencyCode: String,
) : BaseDataModel<User>
