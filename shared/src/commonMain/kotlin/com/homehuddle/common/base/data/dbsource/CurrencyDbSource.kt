package com.homehuddle.common.base.data.dbsource

import com.homehuddle.AppDatabaseQueries
import com.homehuddle.CurrenciesDao
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

internal class CurrencyDbSource(
    private val queries: AppDatabaseQueries
) : BaseDbSource<CurrenciesDao>() {

    override fun getFlow(): Flow<List<CurrenciesDao>> {
        return queries.getCurrencies()
            .asFlow()
            .mapToList()
    }

    override suspend fun create(model: CurrenciesDao) {
        queries.createOrUpdateCurrency(
            id = model.id,
            name = model.name,
            code = model.code,
            ownerId = model.ownerId,
            rate = model.rate
        )
    }

    override suspend fun delete(id: String?) {
        queries.deleteCurrency(id.orEmpty())
    }

    override suspend fun get(id: String?): CurrenciesDao? =
        queries.getCurrency(id.orEmpty()).executeAsOneOrNull()

    override suspend fun getByParent(id: String?): List<CurrenciesDao> {
        return queries.getCurrenciesByParent().executeAsList()
    }

    override suspend fun getByOwner(id: String?): List<CurrenciesDao> {
        return queries.getCurrenciesByOwner().executeAsList()
    }

    override suspend fun deleteByParent(id: String?) {
        queries.deleteCurrenciesByParent()
    }

    override suspend fun clear() {
        queries.clearCurrencies()
    }
}