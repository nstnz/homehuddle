package com.homehuddle.common.base.data.repository

import com.homehuddle.common.base.data.dbsource.BaseDbSource
import com.homehuddle.common.base.data.localsource.UserLocalSource
import com.homehuddle.common.base.data.memorysource.BaseMemorySource
import com.homehuddle.common.base.data.model.BaseDataModel
import com.homehuddle.common.base.data.networksource.BaseNetworkSource
import com.homehuddle.common.base.domain.general.model.BaseDomainModel
import io.github.aakira.napier.Napier
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull

internal abstract class BaseRepository<NetworkModel, DomainModel, DbModel, NetSource, DbSource, MemSource>(
    private val networkSource: NetSource,
    private val memorySource: MemSource,
    private val dbSource: DbSource,
    private val userLocalSource: UserLocalSource,
) where NetSource : BaseNetworkSource<NetworkModel>,
        NetworkModel : BaseDataModel<NetworkModel>,
        DbSource : BaseDbSource<DbModel>,
        MemSource : BaseMemorySource<NetworkModel>,
        DomainModel : BaseDomainModel<DomainModel> {

    abstract suspend fun mapToDbModel(model: NetworkModel?): DbModel?

    abstract suspend fun mapToDomainModel(model: DbModel?): DomainModel?

    abstract suspend fun mapToDbModel(model: DomainModel?): DbModel?

    abstract val refreshTimestampsDiff: Long

    suspend fun refresh() {
        val name = this::class.simpleName.orEmpty()
        Napier.d(tag = name, message = "refresh")
        dbSource.getByOwner(getOwnerId())
        val lastUpdateTimestamp = userLocalSource.getLastUpdateTimestamp(name)
        if (lastUpdateTimestamp == 0L || getTimeMillis() - lastUpdateTimestamp > refreshTimestampsDiff) {
            userLocalSource.updateLastUpdateTimestamp(name)
            val data = networkSource.getByOwner(getOwnerId())
            data.forEach {
                mapToDbModel(it)?.let { dbSource.create(it) }
            }
        }
    }

    fun getUserItemsFlow(): Flow<List<DomainModel>> {
        return dbSource.getFlow()
            .distinctUntilChanged()
            .mapNotNull { list ->
                list.mapNotNull { mapToDomainModel(it) }
            }
    }

    fun getExternalItemsFlow(): Flow<List<DomainModel>> {
        return memorySource.getFlow()
            .distinctUntilChanged()
            .mapNotNull { list ->
                list.mapNotNull { mapToDomainModel(mapToDbModel(it)) }
            }
    }

    suspend fun create(model: NetworkModel): Boolean {
        Napier.d(tag = this::class.simpleName, message = "create")
        return try {
            mapToDbModel(model)?.let { dbSource.create(it) }
            networkSource.create(model)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun update(model: NetworkModel): Boolean {
        Napier.d(tag = this::class.simpleName, message = "update")
        return try {
            mapToDbModel(model)?.let { dbSource.update(it) }
            networkSource.update(model)
            true
        } catch (e: Exception) {
            false
        }
    }

    open suspend fun delete(id: String?) {
        Napier.d(tag = this::class.simpleName, message = "delete: $id")
        memorySource.delete(id)
        dbSource.delete(id)
        networkSource.delete(id)
    }

    suspend fun get(id: String?): DomainModel? {
        Napier.d(tag = this::class.simpleName, message = "get: $id")
        val localModel = getUserItemsFlow().firstOrNull()?.firstOrNull { it.id == id }
        //    ?: getExternalItemsFlow().firstOrNull()?.firstOrNull { it.id == id }
        return if (localModel != null) {
            localModel
        } else {
            val networkModel = networkSource.get(id)
            val dbModel = mapToDbModel(networkModel)
            if (getOwnerId() == networkModel?.ownerId) {
                dbModel?.let {
                    dbSource.create(dbModel)
                    mapToDomainModel(it)
                }
            } else {
                networkModel?.let {
                    memorySource.create(networkModel)
                    mapToDomainModel(mapToDbModel(it))
                }
            }
        }
    }

    suspend fun getByParent(id: String?): List<DomainModel> {
        Napier.d(tag = this::class.simpleName, message = "getByParent: $id")
        val networkModels = networkSource.getByParent(id)
        return networkModels.map { networkModel ->
            val dbModel = mapToDbModel(networkModel)
            if (networkModel.ownerId == getOwnerId()) {
                dbModel?.let {
                    dbSource.create(dbModel)
                    mapToDomainModel(dbModel)
                }
            } else {
                networkModel.let {
                    memorySource.create(networkModel)
                    mapToDomainModel(mapToDbModel(it))
                }
            }
        }.filterNotNull()
    }

    suspend fun deleteByParent(id: String?) {
        Napier.d(tag = this::class.simpleName, message = "deleteByParent: $id")
        networkSource.deleteByParent(id)
        memorySource.deleteByParent(id)
        dbSource.deleteByParent(id)
    }

    suspend open fun clear() {
        Napier.d(tag = this::class.simpleName, message = "clear")
        memorySource.clear()
        dbSource.clear()
    }

    fun getOwnerId() = userLocalSource.getUserId().orEmpty()
}