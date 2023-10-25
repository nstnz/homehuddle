package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.model.BaseModel

internal abstract class BaseNetworkSource<T>(
    private val storage: FirebaseFirestoreImpl,
    private val mappingFunc: (MutableMap<String, Any>?) -> T?
) where T : BaseModel<T> {

    abstract val name: String
    open val parentIdName: String? = null

    suspend fun create(model: T): T {
        return model.copyId(
            id = storage.create(name, model)
        )
    }

    suspend fun update(model: T): T {
        return model.also {
            storage.update(name, model)
        }
    }

    suspend fun delete(id: String?) {
        storage.delete(name, id.orEmpty())
    }

    suspend fun get(id: String?): T? {
        return mappingFunc(storage.get(name, id.orEmpty()))
    }

    suspend fun getByParent(id: String?): List<T> {
        return storage.get(name, parentIdName.orEmpty() to id.orEmpty())
            .mapNotNull { mappingFunc(it) }
    }

    suspend fun deleteByParent(id: String?) {
        storage.delete(name, parentIdName.orEmpty() to id.orEmpty())
    }
}