package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.TripExpense
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import dev.gitlive.firebase.firestore.DocumentSnapshot

internal fun DocumentSnapshot.mapToTripExpense(): TripExpense? =
    this.exists.takeIf { true }?.let {
        TripExpense(
            id = this.id,
            sum = this.get("sum"),
            tripPostId = this.get("tripPostId"),
            description = this.get("description"),
            currencyCode = this.get("currencyCode"),
            timestamp = this.get("timestamp"),
            date = this.get("date"),
        )
    }

internal fun TripExpense?.mapToTripExpenseModel(): TripExpenseModel? =
    this?.let {
        TripExpenseModel(
            id = this.id,
            sum = this.sum,
            description = this.description,
            formattedSum = "${this.sum} ${this.currencyCode}",
            tripPostId = this.tripPostId,
            date = this.date,
            timestamp = this.timestamp,
        )
    }