package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPointModel(
    override val id: String?,
    override val ownerId: String?,
    override val createTs: Long? = null,
    override val editTs: Long? = null,
    val description: String,
    val lat: Double,
    val lon: Double,
    val tripPostId: String?,
): BaseDomainModel<TripPointModel>
