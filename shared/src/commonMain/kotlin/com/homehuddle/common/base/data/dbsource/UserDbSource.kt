package com.homehuddle.common.base.data.dbsource

import com.homehuddle.AppDatabaseQueries
import com.homehuddle.UsersDao
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.flow.Flow

internal class UserDbSource(
    private val queries: AppDatabaseQueries
) : BaseDbSource<UsersDao>() {

    override fun getFlow(): Flow<List<UsersDao>> {
        return queries.getUsers()
            .asFlow()
            .mapToList()
    }

    override suspend fun create(model: UsersDao) {
        queries.createOrUpdateUser(
            id = model.id,
            name = model.name,
            currencyCode = model.currencyCode,
            ownerId = model.ownerId,
            visitedCountries = model.visitedCountries,
            createTs = getTimeMillis(),
            editTs = null
        )
    }

    override suspend fun delete(id: String?) {
        queries.deleteUser(id.orEmpty())
    }

    override suspend fun get(id: String?): UsersDao? =
        queries.getUser(id.orEmpty()).executeAsOneOrNull()

    override suspend fun getByParent(id: String?): List<UsersDao> {
        return queries.getUsersByParent(id.orEmpty()).executeAsList()
    }

    override suspend fun getByOwner(id: String?): List<UsersDao> {
        return queries.getUsersByOwner(id.orEmpty()).executeAsList()
    }

    override suspend fun deleteByParent(id: String?) {
        queries.deleteUsersByParent(id.orEmpty())
    }

    override suspend fun clear() {
        queries.clearUsers()
    }
}