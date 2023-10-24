package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.TripPoint
import com.homehuddle.common.base.domain.general.model.TripPointModel
import dev.gitlive.firebase.firestore.DocumentSnapshot

internal fun DocumentSnapshot.mapToTripPoint(): TripPoint? =
    this.exists.takeIf { true }?.let {
        TripPoint(
            id = this.id,
            tripPostId = this.get("tripPostId"),
            description = this.get("description"),
            lat = this.get("lat"),
            lon = this.get("lon"),
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