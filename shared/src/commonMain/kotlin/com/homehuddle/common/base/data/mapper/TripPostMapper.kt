package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import dev.gitlive.firebase.firestore.DocumentSnapshot

internal fun DocumentSnapshot.mapToTripPost(): TripPost? =
    this.exists.takeIf { true }?.let {
        TripPost(
            id = this.id,
            tripId = this.get("tripId"),
            text = this.get("text"),
            expenses = emptyList(),
            photos = emptyList(),
            date = this.get("date"),
            timestamp = this.get("timestamp"),
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