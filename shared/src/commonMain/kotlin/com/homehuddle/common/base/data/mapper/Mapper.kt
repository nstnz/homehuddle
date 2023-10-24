package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.model.User
import dev.gitlive.firebase.firestore.DocumentSnapshot

internal fun DocumentSnapshot.mapToUser(): User? =
    this.exists.takeIf { true }?.let {
        User(
            id = this.id,
            name = this.get("name"),
        )
    }

internal fun DocumentSnapshot.mapToTrip(): Trip? =
    this.exists.takeIf { true }?.let {
        Trip(
            id = this.id,
            name = this.get("name"),
            description = this.get("description"),
        )
    }