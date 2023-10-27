package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPostModel(
    override val id: String?,
    override val ownerId: String?,
    val tripId: String?,
    val user: UserModel?,
    val name: String,
    val description: String,
    val photos: List<String>,
    val expenses: List<TripExpenseModel>,
    val points: List<TripPointModel>? = null,
    val dateStart: String? = null,
    val timestampStart: Long? = null,
    val dateEnd: String? = null,
    val timestampEnd: Long? = null
) : BaseDomainModel<TripPostModel> {

    val isMine: Boolean
        get() = user?.isMe ?: false

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

    val isOnlyExpenses: Boolean
        get() = !hasPoints && !hasPhotos && hasExpenses && description.isEmpty()
}
