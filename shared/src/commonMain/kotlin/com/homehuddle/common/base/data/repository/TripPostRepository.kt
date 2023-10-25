package com.homehuddle.common.base.data.repository

import com.homehuddle.common.base.data.mapper.mapToTripPostModel
import com.homehuddle.common.base.data.memorysource.BaseMemorySource
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.networksource.TripExpenseNetworkSource
import com.homehuddle.common.base.data.networksource.TripPointNetworkSource
import com.homehuddle.common.base.data.networksource.TripPostNetworkSource
import com.homehuddle.common.base.domain.general.model.TripPostModel

internal class TripPostRepository(
    private val tripRepository: TripRepository,
    tripPostNetworkSource: TripPostNetworkSource,
    private val tripExpenseNetworkSource: TripExpenseNetworkSource,
    private val tripPointNetworkSource: TripPointNetworkSource,
) : BaseRepository<TripPost, TripPostModel, TripPostNetworkSource, BaseMemorySource<TripPost>>(
    tripPostNetworkSource,
    null
) {

    override suspend fun map(model: TripPost?): TripPostModel? {
        return tripRepository.get(model?.tripId)?.let {
            model.mapToTripPostModel(it)
        }
    }

    override suspend fun transform(model: TripPost?): TripPost? {
        return model?.copy(
            expenses = tripExpenseNetworkSource.getByParent(model.id),
            points = tripPointNetworkSource.getByParent(model.id)
        )
    }

    override suspend fun delete(id: String?) {
        tripPointNetworkSource.deleteByParent(id)
        tripExpenseNetworkSource.deleteByParent(id)
        super.delete(id)
    }
}