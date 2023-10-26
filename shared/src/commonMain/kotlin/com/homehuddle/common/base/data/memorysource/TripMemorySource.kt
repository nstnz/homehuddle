package com.homehuddle.common.base.data.memorysource

import com.homehuddle.common.base.data.model.Trip

internal class TripMemorySource() : BaseMemorySource<Trip>() {

    override val parentIdName: String?
        get() = null
}