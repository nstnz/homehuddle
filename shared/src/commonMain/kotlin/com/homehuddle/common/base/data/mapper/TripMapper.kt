package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.UserModel

internal fun MutableMap<String, Any>?.mapToTrip(): Trip? =
    this?.let {
        Trip(
            id = this.get("id").toString(),
            name = this.get("name").toString(),
            description = this.get("description").toString(),
            userId = this.get("userId").toString(),
        )
    }

internal fun Trip?.mapToTripModel(userModel: UserModel): TripModel? =
    this?.let {
        TripModel(
            id = this.id,
            name = this.name,
            description = this.description,
            user = userModel,
            posts = emptyList()
        )
    }