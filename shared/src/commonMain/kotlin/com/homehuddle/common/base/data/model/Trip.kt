package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    override val id: String?,
    override val ownerId: String?,
    override val createTs: Long? = null,
    override val editTs: Long? = null,
    val name: String,
    val description: String,
    val currencyCode: String?,
    val dateStart: String? = null,
    val timestampStart: Long? = null,
    val dateEnd: String? = null,
    val timestampEnd: Long? = null,
    val countries: String,
): BaseDataModel<Trip>