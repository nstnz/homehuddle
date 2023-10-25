package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.mapper.mapToTripExpense
import com.homehuddle.common.base.data.model.TripExpense

internal class TripExpenseNetworkSource(
    storage: FirebaseFirestoreImpl
) : BaseNetworkSource<TripExpense>(storage, { it.mapToTripExpense() }) {

    override val name = "tripexpenses"
    override val parentIdName = "tripPostId"
}