package com.homehuddle.common.base.data.memorysource

import com.homehuddle.common.base.data.model.TripPost

internal class TripPostMemorySource() : BaseMemorySource<TripPost>() {

    override val parentIdName: String?
        get() = null
}