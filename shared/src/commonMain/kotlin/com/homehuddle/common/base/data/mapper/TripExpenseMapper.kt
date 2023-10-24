package com.homehuddle.common.base.data.mapper

import com.homehuddle.common.base.data.model.TripExpense
import com.homehuddle.common.base.domain.general.model.TripExpenseModel

internal fun MutableMap<String, Any>?.mapToTripExpense(): TripExpense? =
    this?.let {
        TripExpense(
            id = this.get("id").toString(),
            sum = this.get("sum").toString().toDouble(),
            tripPostId = this.get("tripPostId").toString(),
            description = this.get("description").toString(),
            currencyCode = this.get("currencyCode").toString(),
            timestamp = this.get("timestamp").toString().toLong(),
            date = this.get("date").toString(),
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