package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.model.TripExpense

internal class TripExpenseNetworkSource(
    storage: FirebaseFirestoreImpl
) : BaseNetworkSource<TripExpense>(storage) {

    override val name = "tripexpenses"
    override val parentIdName = "tripPostId"

    override fun map(map: MutableMap<String, Any>?): TripExpense? = map?.let {
        TripExpense(
            id = map.get("id")?.toString(),
            ownerId = map.get("ownerId")?.toString(),
            sum = map.get("sum")?.toString()?.toDouble() ?: 0.0,
            tripPostId = map.get("tripPostId")?.toString().orEmpty(),
            description = map.get("description")?.toString().orEmpty(),
            currencyCode = map.get("currencyCode")?.toString().orEmpty(),
            timestamp = map.get("timestamp")?.toString()?.toLong(),
            date = map.get("date")?.toString(),
        )
    }
}