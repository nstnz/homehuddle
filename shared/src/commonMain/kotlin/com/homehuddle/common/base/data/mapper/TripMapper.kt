package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.UserModel
import dev.gitlive.firebase.firestore.DocumentSnapshot

internal fun DocumentSnapshot.mapToTrip(): Trip? =
    this.exists.takeIf { true }?.let {
        Trip(
            id = this.id,
            name = this.get("name"),
            description = this.get("description"),
            userId = this.get("userId"),
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