package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    override val id: String?,
    override val ownerId: String?,
    val name: String,
    val description: String,
    val dateStart: String? = null,
    val timestampStart: Long? = null,
    val dateEnd: String? = null,
    val timestampEnd: Long? = null
): BaseDataModel<Trip>