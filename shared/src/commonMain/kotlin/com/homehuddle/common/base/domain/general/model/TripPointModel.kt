package com.homehuddle.common.base.domain.general.model

data class TripPointModel(
    val id: String,
    val description: String,
    val lat: Double,
    val lon: Double,
    val tripPostId: String,
)
