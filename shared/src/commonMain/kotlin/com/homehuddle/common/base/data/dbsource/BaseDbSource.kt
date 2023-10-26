package com.homehuddle.common.base.data.dbsource

import com.homehuddle.common.base.data.repository.BaseSource

internal abstract class BaseDbSource<DbModel>: BaseSource<DbModel> {

    override val parentIdName: String?
        get() = null

    override suspend fun update(model: DbModel) = create(model)
}