package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPoint(
    override val id: String?,
    override val ownerId: String?,
    val tripPostId: String,
    val description: String,
    val lat: Double,
    val lon: Double,
): BaseDataModel<TripPoint>