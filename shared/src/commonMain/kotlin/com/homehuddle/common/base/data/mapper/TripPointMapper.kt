package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.TripPoint
import com.homehuddle.common.base.domain.general.model.TripPointModel

internal fun MutableMap<String, Any>?.mapToTripPoint(): TripPoint? =
    this?.let {
        TripPoint(
            id = this.get("id")?.toString(),
            tripPostId = this.get("tripPostId")?.toString().orEmpty(),
            description = this.get("description")?.toString().orEmpty(),
            lat = this.get("lat")?.toString()?.toDouble() ?: 0.0,
            lon = this.get("lon")?.toString()?.toDouble() ?: 0.0,
        )
    }

internal fun TripPoint?.mapToTripPointModel(): TripPointModel? =
    this?.let {
        TripPointModel(
            id = this.id,
            lat = this.lat,
            description = this.description,
            lon = this.lon,
            tripPostId = this.tripPostId,
        )
    }