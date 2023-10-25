package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPost(
    override val id: String?,
    val tripId: String,
    val text: String,
    val photos: List<String>,
    val expenses: List<TripExpense>,
    val points: List<TripPoint>? = null,
    val date: String,
    val timestamp: Long
): BaseModel<TripPost> {

    override fun copyId(id: String?): TripPost = this.copy(id = id)
}

