package com.homehuddle.common.base.data.memorysource

import com.homehuddle.common.base.data.model.BaseDataModel
import com.homehuddle.common.base.data.repository.BaseSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.transform

internal abstract class BaseMemorySource<T>(): BaseSource<T> where T : BaseDataModel<T> {

    private var items: MutableList<T>? = null
    private val privateItemsFlow: MutableSharedFlow<MutableList<T>?> = MutableSharedFlow()

    override fun getFlow(): Flow<List<T>> = privateItemsFlow.transform {  }

    override suspend fun getByParent(id: String?): List<T> {
        return emptyList()
    }

    override suspend fun deleteByParent(id: String?) {
    }

    override suspend fun create(model: T) {
        this.items?.add(0, model)
        privateItemsFlow.tryEmit(items)
    }

    override suspend fun update(model: T) {
        val index = this.items?.indexOfFirst { it.id == model.id }
        index?.let {
            this.items?.set(index, model)
        } ?: kotlin.run {
            create(model)
        }
        privateItemsFlow.tryEmit(items)
    }

    override suspend fun delete(id: String?) {
        this.items?.removeAll { it.id == id }
        privateItemsFlow.tryEmit(items)
    }

    override suspend fun get(id: String?) = items?.firstOrNull { it.id == id }

    override suspend fun clear() {
        items = null
        privateItemsFlow.tryEmit(items)
    }

    override suspend fun getByOwner(id: String?): List<T> = emptyList()
}