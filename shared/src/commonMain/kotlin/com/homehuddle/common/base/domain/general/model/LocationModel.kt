package com.homehuddle.common.base.domain.general.model

data class LocationModel(
    val name: String,
    val description: String,
    val address: String,
    val lat: Double,
    val lon: Double,
)
