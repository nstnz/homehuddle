package com.homehuddle.common.base.data.dbsource

import com.homehuddle.AppDatabaseQueries
import com.homehuddle.CountriesDao
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

internal class CountryDbSource(
    private val queries: AppDatabaseQueries
) : BaseDbSource<CountriesDao>() {

    override fun getFlow(): Flow<List<CountriesDao>> {
        return queries.getCountries()
            .asFlow()
            .mapToList()
    }

    override suspend fun create(model: CountriesDao) {
        queries.createOrUpdateCountry(
            id = model.id,
            name = model.name,
            emoji = model.emoji,
            ownerId = model.ownerId,
        )
    }

    override suspend fun delete(id: String?) {
        queries.deleteCountry(id.orEmpty())
    }

    override suspend fun get(id: String?): CountriesDao? =
        queries.getCountry(id.orEmpty()).executeAsOneOrNull()

    override suspend fun getByParent(id: String?): List<CountriesDao> {
        return queries.getCountriesByParent().executeAsList()
    }

    override suspend fun getByOwner(id: String?): List<CountriesDao> {
        return queries.getCountriesByOwner().executeAsList()
    }

    override suspend fun deleteByParent(id: String?) {
        queries.deleteCountriesByParent()
    }

    override suspend fun clear() {
        queries.clearCountries()
    }
}