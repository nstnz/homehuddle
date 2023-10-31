package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    override val id: String?,
    override val ownerId: String?,
    override val createTs: Long? = null,
    override val editTs: Long? = null,
    val name: String,
    val currencyCode: String,
    val visitedCountries: String,
) : BaseDataModel<User>
