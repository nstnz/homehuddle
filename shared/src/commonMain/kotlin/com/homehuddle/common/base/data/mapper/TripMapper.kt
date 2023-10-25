package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.UserModel

internal fun MutableMap<String, Any>?.mapToTrip(): Trip? =
    this?.let {
        Trip(
            id = this["id"]?.toString(),
            name = this.get("name")?.toString().orEmpty(),
            description = this.get("description")?.toString().orEmpty(),
            userId = this.get("userId")?.toString(),
            dateStart = this.get("dateStart")?.toString(),
            timestampStart = this.get("timestampStart")?.toString()?.toLongOrNull(),
            dateEnd = this.get("dateEnd")?.toString(),
            timestampEnd = this.get("timestampEnd")?.toString()?.toLongOrNull(),
        )
    }

internal fun Trip?.mapToTripModel(userModel: UserModel): TripModel? =
    this?.let {
        TripModel(
            id = this.id,
            name = this.name,
            description = this.description,
            user = userModel,
            posts = emptyList(),
            dateStart = this.dateStart,
            dateEnd = this.dateEnd,
            timestampStart = this.timestampStart,
            timestampEnd = this.timestampEnd,
        )
    }