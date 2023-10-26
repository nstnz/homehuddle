package com.homehuddle.common.base.data.networksource

import FirebaseFirestoreImpl
import com.homehuddle.common.base.data.model.BaseDataModel
import com.homehuddle.common.base.data.repository.BaseSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal abstract class BaseNetworkSource<T>(
    private val storage: FirebaseFirestoreImpl,
) : BaseSource<T> where T : BaseDataModel<T> {

    abstract val name: String

    abstract fun map(map: MutableMap<String, Any>?): T?

    override suspend fun create(model: T) {
        storage.create(name, model)
    }

    override suspend fun update(model: T) {
        storage.update(name, model)
    }

    override suspend fun delete(id: String?) {
        storage.delete(name, id.orEmpty())
    }

    override suspend fun get(id: String?): T? {
        return map(storage.get(name, id.orEmpty()))
    }

    override suspend fun getByParent(id: String?): List<T> {
        return storage.get(name, parentIdName.orEmpty() to id.orEmpty())
            .mapNotNull { map(it) }
    }

    override suspend fun deleteByParent(id: String?) {
        storage.delete(name, parentIdName.orEmpty() to id.orEmpty())
    }

    override fun getFlow(): Flow<List<T>> {
        return emptyFlow()
    }

    override suspend fun clear() {
        //do nothing
    }

    override suspend fun getByOwner(id: String?): List<T> {
        return storage.get(name, "ownerId" to id.orEmpty()).mapNotNull { map(it) }
    }
}