package com.homehuddle.common.base.data.dbsource

import com.homehuddle.AppDatabaseQueries
import com.homehuddle.TripExpensesDao
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.flow.Flow

internal class TripExpenseDbSource(
    private val queries: AppDatabaseQueries
) : BaseDbSource<TripExpensesDao>() {

    override fun getFlow(): Flow<List<TripExpensesDao>> = queries.getTripExpenses()
        .asFlow()
        .mapToList()

    override suspend fun create(model: TripExpensesDao) {
        queries.createOrUpdateTripExpense(
            id = model.id,
            ownerId = model.ownerId,
            tripPostId = model.tripPostId,
            sum = model.sum,
            currencyCode = model.currencyCode,
            description = model.description,
            date = model.date,
            timestamp = model.timestamp,
            category = model.category,
            createTs = getTimeMillis(),
            editTs = null
        )
    }

    override suspend fun delete(id: String?) {
        queries.deleteTripExpense(id.orEmpty())
    }

    override suspend fun get(id: String?): TripExpensesDao? =
        queries.getTripExpense(id.orEmpty()).executeAsOneOrNull()

    override suspend fun getByParent(id: String?): List<TripExpensesDao> {
        return queries.getTripExpensesByParent(id.orEmpty()).executeAsList()
    }

    override suspend fun getByOwner(id: String?): List<TripExpensesDao> {
        return queries.getTripExpensesByOwner(id.orEmpty()).executeAsList()
    }

    override suspend fun deleteByParent(id: String?) {
        queries.deleteTripExpensesByParent(id.orEmpty())
    }

    override suspend fun clear() {
        queries.clearTripExpenses()
    }
}