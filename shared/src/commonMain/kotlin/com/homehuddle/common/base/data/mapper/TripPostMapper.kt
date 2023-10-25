package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel

internal fun MutableMap<String, Any>?.mapToTripPost(): TripPost? =
    this?.let {
        TripPost(
            id = this.get("id")?.toString(),
            tripId = this.get("tripId")?.toString().orEmpty(),
            text = this.get("text")?.toString().orEmpty(),
            name = this.get("name")?.toString().orEmpty(),
            expenses = emptyList(),
            photos = emptyList(),
            dateStart = this.get("dateStart")?.toString(),
            timestampStart = this.get("timestampStart")?.toString()?.toLongOrNull(),
            dateEnd = this.get("dateEnd")?.toString(),
            timestampEnd = this.get("timestampEnd")?.toString()?.toLongOrNull(),
        )
    }

internal fun TripPost?.mapToTripPostModel(tripModel: TripModel): TripPostModel? =
    this?.let {
        TripPostModel(
            id = this.id,
            tripId = tripModel.id,
            user = tripModel.user,
            name = this.name,
            description = this.text,
            expenses = this.expenses.mapNotNull { it.mapToTripExpenseModel() },
            points = this.points?.mapNotNull { it.mapToTripPointModel() },
            photos = this.photos,
            dateStart = this.dateStart,
            dateEnd = this.dateEnd,
            timestampStart = this.timestampStart,
            timestampEnd = this.timestampEnd,
        )
    }