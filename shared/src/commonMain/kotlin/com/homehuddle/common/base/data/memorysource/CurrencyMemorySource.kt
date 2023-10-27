package com.homehuddle.common.base.data.memorysource

import com.homehuddle.common.base.data.model.Currency

internal class CurrencyMemorySource() : BaseMemorySource<Currency>() {

    override val parentIdName: String?
        get() = null
}