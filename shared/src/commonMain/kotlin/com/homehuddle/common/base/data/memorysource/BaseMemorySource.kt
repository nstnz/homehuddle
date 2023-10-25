package com.homehuddle.common.base.data.memorysource

import com.homehuddle.common.base.data.model.BaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal open class BaseMemorySource<T> where T : BaseModel<T> {

    private var items: MutableList<T>? = null
    private val privateItemsFlow: MutableSharedFlow<MutableList<T>?> = MutableSharedFlow()

    val itemsFlow: Flow<MutableList<T>?> = privateItemsFlow

    fun setItems(items: MutableList<T>) {
        this.items = items
        privateItemsFlow.tryEmit(items)
    }

    fun create(item: T) {
        this.items?.add(0, item)
        privateItemsFlow.tryEmit(items)
    }

    fun update(item: T) {
        val index = this.items?.indexOfFirst { it.id == item.id }
        index?.let {
            this.items?.set(index, item)
        } ?: kotlin.run {
            create(item)
        }
        privateItemsFlow.tryEmit(items)
    }

    fun delete(id: String?) {
        this.items?.removeAll { it.id == id }
        privateItemsFlow.tryEmit(items)
    }

    fun get(id: String?) = items?.firstOrNull { it.id == id }

    fun clear() {
        items = null
        privateItemsFlow.tryEmit(items)
    }
}