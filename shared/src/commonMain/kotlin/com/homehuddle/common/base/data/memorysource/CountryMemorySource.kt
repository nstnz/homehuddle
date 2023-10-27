package com.homehuddle.common.base.data.memorysource

import com.homehuddle.common.base.data.model.Country

internal class CountryMemorySource() : BaseMemorySource<Country>() {

    override val parentIdName: String?
        get() = null
}