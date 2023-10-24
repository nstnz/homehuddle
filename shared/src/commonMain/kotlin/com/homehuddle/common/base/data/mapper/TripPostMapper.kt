package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel

internal fun MutableMap<String, Any>?.mapToTripPost(): TripPost? =
    this?.let {
        TripPost(
            id = this.get("id").toString(),
            tripId = this.get("tripId").toString(),
            text = this.get("text").toString(),
            expenses = emptyList(),
            photos = emptyList(),
            date = this.get("date").toString(),
            timestamp = this.get("timestamp").toString().toLong(),
        )
    }

internal fun TripPost?.mapToTripPostModel(tripModel: TripModel): TripPostModel? =
    this?.let {
        TripPostModel(
            id = this.id,
            tripId = tripModel.id,
            user = tripModel.user,
            text = this.text,
            expenses = this.expenses.mapNotNull { it.mapToTripExpenseModel() },
            points = this.points?.mapNotNull { it.mapToTripPointModel() },
            photos = this.photos,
            date = this.date,
            timestamp = this.timestamp,
        )
    }