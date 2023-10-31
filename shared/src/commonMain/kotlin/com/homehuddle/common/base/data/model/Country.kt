package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    override val id: String?,
    override val ownerId: String?,
    val name: String,
    val emoji: String,
    override val createTs: Long? = null,
    override val editTs: Long? = null,
) : BaseDataModel<Country>