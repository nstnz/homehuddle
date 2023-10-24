package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPost(
    val id: String,
    val tripId: String,
    val text: String,
    val photos: List<String>,
    val expenses: List<TripExpense>,
    val points: List<TripPoint>? = null,
    val date: String,
    val timestamp: Long
)
