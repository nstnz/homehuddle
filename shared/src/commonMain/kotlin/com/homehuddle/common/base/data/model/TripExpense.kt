package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TripExpense(
    val id: String,
    val sum: Double,
    val currencyCode: String,
    val description: String
)
