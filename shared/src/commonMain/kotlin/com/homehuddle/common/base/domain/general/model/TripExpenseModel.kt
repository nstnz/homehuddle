package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
data class TripExpenseModel(
    val id: String?,
    val sum: Double,
    val formattedSum: String,
    val description: String,
    val tripPostId: String?,
    val date: String,
    val timestamp: Long
)
