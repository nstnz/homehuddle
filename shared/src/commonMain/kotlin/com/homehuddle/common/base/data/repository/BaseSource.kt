package com.homehuddle.common.base.data.repository

import kotlinx.coroutines.flow.Flow

internal interface BaseSource<Model> {

    val parentIdName: String?

    fun getFlow(): Flow<List<Model>>
    suspend fun create(model: Model)
    suspend fun update(model: Model)
    suspend fun delete(id: String?)
    suspend fun get(id: String?): Model?
    suspend fun getByParent(id: String?): List<Model>
    suspend fun getByOwner(id: String?): List<Model>
    suspend fun deleteByParent(id: String?)
    suspend fun clear()
}