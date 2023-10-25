package com.homehuddle.common.base.data.repository

import com.homehuddle.common.base.data.memorysource.BaseMemorySource
import com.homehuddle.common.base.data.model.BaseModel
import com.homehuddle.common.base.data.networksource.BaseNetworkSource

internal abstract class BaseRepository<DataModel, DomainModel, NetSource, MemSource>(
    private val networkSource: NetSource,
    private val memorySource: MemSource? = null,
) where NetSource : BaseNetworkSource<DataModel>,
        DataModel : BaseModel<DataModel>,
        MemSource : BaseMemorySource<DataModel> {

    abstract suspend fun map(model: DataModel?): DomainModel?

    open suspend fun transform(model: DataModel?): DataModel? {
        return model
    }

    suspend fun create(model: DataModel): DomainModel? {
        return map(networkSource.create(model).also {
            memorySource?.create(it)
        })
    }

    suspend fun createOrUpdate(model: DataModel): DomainModel? {
        return if (model.id.isNullOrEmpty()) create(model)
        else update(model)
    }

    suspend fun update(model: DataModel): DomainModel? {
        return map(networkSource.update(model).also {
            memorySource?.update(it)
        })
    }

    open suspend fun delete(id: String?) {
        networkSource.delete(id).also {
            memorySource?.delete(id)
        }
    }

    suspend fun get(id: String?): DomainModel? {
        return map(
            transform(memorySource?.get(id) ?: networkSource.get(id)?.also {
                memorySource?.create(it)
            })
        )
    }

    suspend fun getByParent(id: String?): List<DomainModel> {
        return networkSource.getByParent(id)
            .mapNotNull { map(transform(it)) }
    }

    suspend fun deleteByParent(id: String?) {
        networkSource.deleteByParent(id)
    }
}