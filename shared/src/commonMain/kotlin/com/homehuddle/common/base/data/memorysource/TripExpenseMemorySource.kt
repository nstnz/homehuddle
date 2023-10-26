package com.homehuddle.common.base.data.memorysource

import com.homehuddle.common.base.data.model.TripExpense

internal class TripExpenseMemorySource() : BaseMemorySource<TripExpense>() {

    override val parentIdName: String?
        get() = null
}