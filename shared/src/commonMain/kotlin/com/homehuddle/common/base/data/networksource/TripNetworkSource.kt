package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.model.Trip

internal class TripNetworkSource(
    storage: FirebaseFirestoreImpl
) : BaseNetworkSource<Trip>(storage) {

    override val name = "trips"
    override val parentIdName = "userId"

    override fun map(map: MutableMap<String, Any>?): Trip? = map?.let {
        Trip(
            id = map["id"]?.toString(),
            ownerId = map.get("ownerId")?.toString(),
            name = map.get("name")?.toString().orEmpty(),
            description = map.get("description")?.toString().orEmpty(),
            currencyCode = map.get("currencyCode")?.toString().orEmpty(),
            dateStart = map.get("dateStart")?.toString(),
            timestampStart = map.get("timestampStart")?.toString()?.toLongOrNull(),
            dateEnd = map.get("dateEnd")?.toString(),
            timestampEnd = map.get("timestampEnd")?.toString()?.toLongOrNull(),
        )
    }
}