package com.homehuddle.common.base.data.model

data class LocationResult(
    val dist: Double,
    val position: LocationResultLatLon,
    val address: LocationResultAddress,
)

data class LocationResultAddress(
    val freeformAddress: String,
    val country: String,
)

data class LocationResultLatLon(
    val lat: Double,
    val lon: Double,
)
