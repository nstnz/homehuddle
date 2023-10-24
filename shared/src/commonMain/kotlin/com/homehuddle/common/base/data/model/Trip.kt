package com.homehuddle.common.base.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    val id: String,
    val userId: String,
    val name: String,
    val description: String,
)
