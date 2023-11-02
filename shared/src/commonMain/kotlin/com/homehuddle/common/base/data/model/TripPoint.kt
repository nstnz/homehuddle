package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPoint(
    override val id: String?,
    override val ownerId: String?,
    override val createTs: Long? = null,
    override val editTs: Long? = null,
    val tripPostId: String,
    val name: String,
    val description: String,
    val address: String,
    val lat: Double,
    val lon: Double,
): BaseDataModel<TripPoint>