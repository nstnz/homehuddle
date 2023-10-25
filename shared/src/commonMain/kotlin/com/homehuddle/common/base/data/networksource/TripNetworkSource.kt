package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.mapper.mapToTrip
import com.homehuddle.common.base.data.model.Trip

internal class TripNetworkSource(
    storage: FirebaseFirestoreImpl
) : BaseNetworkSource<Trip>(storage, { it.mapToTrip() }) {

    override val name = "trips"
    override val parentIdName = "userId"
}