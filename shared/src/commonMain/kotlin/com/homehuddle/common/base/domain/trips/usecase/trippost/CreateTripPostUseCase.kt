package com.homehuddle.common.base.domain.trips.usecase.trippost

import FirebaseStorageImpl
import com.benasher44.uuid.uuid4
import com.homehuddle.common.base.data.model.TripExpense
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.data.repository.TripExpenseRepository
import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.photosToJsonString
import com.homehuddle.common.base.domain.general.model.toJsonString
import dev.icerock.moko.media.Bitmap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class CreateTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
    private val json: Json,
    private val expenseRepository: TripExpenseRepository,
    private val firebaseStorage: FirebaseStorageImpl,
) {

    suspend operator fun invoke(
        tripId: String,
        tripPostModel: TripPostModel,
        bitmaps: List<Bitmap>
    ): Unit = withContext(dispatcher) {
        val tripPostId = uuid4().toString()
        tripPostModel.expenses.forEach {
            expenseRepository.create(
                TripExpense(
                    id = uuid4().toString(),
                    tripPostId = tripPostId,
                    ownerId = repository.getOwnerId(),
                    description = it.description,
                    date = it.date,
                    sum = it.sum,
                    timestamp = it.timestamp,
                    category = it.category.name,
                    currencyCode = it.currency?.id.orEmpty()
                )
            )
        }
        val photos = bitmaps.map {
            val id = uuid4().toString()
            firebaseStorage.upload(id, it.toByteArray())
        }
        repository.create(
            TripPost(
                id = tripPostId,
                tripId = tripId,
                ownerId = repository.getOwnerId(),
                name = tripPostModel.name,
                description = tripPostModel.description,
                dateStart = tripPostModel.dateStart,
                dateEnd = tripPostModel.dateEnd,
                timestampStart = tripPostModel.timestampStart,
                timestampEnd = tripPostModel.timestampEnd,
                countries = tripPostModel.countries.toJsonString(json).orEmpty(),
                photos = photos.photosToJsonString(json).orEmpty(),
            )
        )
    }
}