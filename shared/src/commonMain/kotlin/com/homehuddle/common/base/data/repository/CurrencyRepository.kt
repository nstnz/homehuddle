package com.homehuddle.common.base.data.repository

import com.homehuddle.CurrenciesDao
import com.homehuddle.common.base.data.dbsource.CurrencyDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.CurrencyMemorySource
import com.homehuddle.common.base.data.model.Currency
import com.homehuddle.common.base.data.networksource.CurrencyNetworkSource
import com.homehuddle.common.base.domain.general.model.CurrencyModel

internal class CurrencyRepository(
    userLocalSource: UserLocalSource,
    networkSource: CurrencyNetworkSource,
    memorySource: CurrencyMemorySource,
    dbSource: CurrencyDbSource,
) : BaseRepository<Currency, CurrencyModel, CurrenciesDao, CurrencyNetworkSource, CurrencyDbSource, CurrencyMemorySource>(
    networkSource,
    memorySource,
    dbSource,
    userLocalSource
) {

    override val refreshTimestampsDiff: Long
        get() = 1000

    override suspend fun mapToDbModel(model: Currency?): CurrenciesDao? = model?.let {
        CurrenciesDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            name = it.name,
            code = it.code,
            rate = it.rate
        )
    }

    override suspend fun mapToDomainModel(model: CurrenciesDao?): CurrencyModel? = model?.let {
        CurrencyModel(
            id = it.id,
            ownerId = it.ownerId,
            name = it.name.orEmpty(),
            code = it.code.orEmpty(),
            rate = it.rate ?: 1.0
        )
    }

    override suspend fun mapToDbModel(model: CurrencyModel?): CurrenciesDao? = model?.let {
        CurrenciesDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            name = it.name,
            code = it.code,
            rate = it.rate
        )
    }
}