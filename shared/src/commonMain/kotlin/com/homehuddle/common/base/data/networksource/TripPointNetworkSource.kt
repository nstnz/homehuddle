package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.mapper.mapToTripPoint
import com.homehuddle.common.base.data.model.TripPoint

internal class TripPointNetworkSource(
    storage: FirebaseFirestoreImpl
) : BaseNetworkSource<TripPoint>(storage, { it.mapToTripPoint() }) {

    override val name = "trippoints"
    override val parentIdName = "tripPostId"
}