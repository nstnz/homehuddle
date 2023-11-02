package com.homehuddle.common.base.domain.general.model

import kotlinx.serialization.Serializable

@Serializable
data class TripPointModel(
    override val id: String?,
    override val ownerId: String?,
    override val createTs: Long? = null,
    override val editTs: Long? = null,
    val name: String,
    val description: String,
    val address: String,
    val lat: Double,
    val lon: Double,
    val tripPostId: String?,
) : BaseDomainModel<TripPointModel> {

    companion object {
        fun createEmpty(): TripPointModel = TripPointModel(
            id = null,
            ownerId = null,
            name = "",
            description = "",
            address = "",
            tripPostId = null,
            lat = 0.0,
            lon = 0.0,
        )
    }
}
