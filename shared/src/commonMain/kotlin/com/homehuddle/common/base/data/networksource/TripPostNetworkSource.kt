package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.model.TripPost

internal class TripPostNetworkSource(
    storage: FirebaseFirestoreImpl
) : BaseNetworkSource<TripPost>(storage) {

    override val name = "tripposts"

    override val parentIdName = "tripId"

    override fun map(map: MutableMap<String, Any>?): TripPost? = map?.let {
        TripPost(
            id = map.get("id")?.toString(),
            ownerId = map.get("ownerId")?.toString(),
            tripId = map.get("tripId")?.toString().orEmpty(),
            description = map.get("description")?.toString().orEmpty(),
            name = map.get("name")?.toString().orEmpty(),
            dateStart = map.get("dateStart")?.toString(),
            timestampStart = map.get("timestampStart")?.toString()?.toLongOrNull(),
            dateEnd = map.get("dateEnd")?.toString(),
            timestampEnd = map.get("timestampEnd")?.toString()?.toLongOrNull(),
            countries = map.get("countries")?.toString().orEmpty(),
            photos = map.get("photos")?.toString().orEmpty(),
        )
    }
}