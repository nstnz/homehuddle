package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.mapper.mapToTripPost
import com.homehuddle.common.base.data.model.TripPost

internal class TripPostNetworkSource(
    storage: FirebaseFirestoreImpl
) : BaseNetworkSource<TripPost>(storage, { it.mapToTripPost() }) {

    override val name = "tripposts"
    override val parentIdName = "tripId"
}