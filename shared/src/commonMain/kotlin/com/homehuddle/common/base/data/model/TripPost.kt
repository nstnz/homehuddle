package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPost(
    val id: String,
    val tripId: String,
    val text: String,
    val photos: List<String>,
    val expenses: List<TripExpense>,
    val point: TripPoint? = null,
    val fromToRoute: List<TripPoint>? = null,
    val customRoute: List<TripPoint>? = null,
) {

    val hasPoints: Boolean
        get() = point != null || !fromToRoute.isNullOrEmpty() || !customRoute.isNullOrEmpty()

    val hasPhotos: Boolean
        get() = photos.isNotEmpty()

    val hasExpenses: Boolean
        get() = expenses.isNotEmpty()
}
