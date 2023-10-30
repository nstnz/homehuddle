package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPost(
    override val id: String?,
    override val ownerId: String?,
    val tripId: String,
    val name: String,
    val description: String,
    val dateStart: String? = null,
    val timestampStart: Long? = null,
    val dateEnd: String? = null,
    val timestampEnd: Long? = null,
    val countries: String
): BaseDataModel<TripPost>
