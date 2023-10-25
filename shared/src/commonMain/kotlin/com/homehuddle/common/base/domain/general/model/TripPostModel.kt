package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPostModel(
    val id: String?,
    val tripId: String?,
    val user: UserModel,
    val text: String,
    val photos: List<String>,
    val expenses: List<TripExpenseModel>,
    val points: List<TripPointModel>? = null,
    val date: String?,
    val timestamp: Long?
) {

    val isMine: Boolean
        get() = user.isMe

    val hasPoints: Boolean
        get() = !points.isNullOrEmpty()

    val hasPhotos: Boolean
        get() = photos.isNotEmpty()

    val hasExpenses: Boolean
        get() = expenses.isNotEmpty()

    val distance: Double
        get() = 100500.0

    val totalSpent: Double
        get() = expenses.sumOf { it.sum }
}
