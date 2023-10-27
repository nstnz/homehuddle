package com.homehuddle.common.base.data.repository

import com.homehuddle.CountriesDao
import com.homehuddle.common.base.data.dbsource.CountryDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.CountryMemorySource
import com.homehuddle.common.base.data.model.Country
import com.homehuddle.common.base.data.networksource.CountryNetworkSource
import com.homehuddle.common.base.domain.general.model.CountryModel

internal class CountryRepository(
    userLocalSource: UserLocalSource,
    networkSource: CountryNetworkSource,
    memorySource: CountryMemorySource,
    dbSource: CountryDbSource,
) : BaseRepository<Country, CountryModel, CountriesDao, CountryNetworkSource, CountryDbSource, CountryMemorySource>(
    networkSource,
    memorySource,
    dbSource,
    userLocalSource
) {

    override val refreshTimestampsDiff: Long
        get() = 1000

    override suspend fun mapToDbModel(model: Country?): CountriesDao? = model?.let {
        CountriesDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            name = it.name,
            emoji = it.emoji,
        )
    }

    override suspend fun mapToDomainModel(model: CountriesDao?): CountryModel? = model?.let {
        CountryModel(
            id = it.id,
            ownerId = it.ownerId,
            name = it.name.orEmpty(),
            emoji = it.emoji.orEmpty(),
        )
    }

    override suspend fun mapToDbModel(model: CountryModel?): CountriesDao? = model?.let {
        CountriesDao(
            id = it.id.orEmpty(),
            ownerId = it.ownerId,
            name = it.name,
            emoji = it.emoji,
        )
    }
}