package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPoint(
    val id: String,
    val tripPostId: String,
    val description: String,
    val lat: Double,
    val lon: Double,
)
