package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPointModel(
    override val id: String?,
    override val ownerId: String?,
    val description: String,
    val lat: Double,
    val lon: Double,
    val tripPostId: String?,
): BaseDomainModel<TripPointModel>
