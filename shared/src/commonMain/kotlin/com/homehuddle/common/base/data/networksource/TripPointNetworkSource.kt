package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.model.TripPoint

internal class TripPointNetworkSource(
    storage: FirebaseFirestoreImpl
) : BaseNetworkSource<TripPoint>(storage) {

    override val name = "trippoints"
    override val parentIdName = "tripPostId"

    override fun map(map: MutableMap<String, Any>?): TripPoint? = map?.let {
        TripPoint(
            id = map.get("id")?.toString(),
            ownerId = map.get("ownerId")?.toString(),
            tripPostId = map.get("tripPostId")?.toString().orEmpty(),
            description = map.get("description")?.toString().orEmpty(),
            lat = map.get("lat")?.toString()?.toDouble() ?: 0.0,
            lon = map.get("lon")?.toString()?.toDouble() ?: 0.0,
        )
    }
}