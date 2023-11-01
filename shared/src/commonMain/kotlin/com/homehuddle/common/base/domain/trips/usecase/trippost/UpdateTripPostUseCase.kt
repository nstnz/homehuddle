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
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class UpdateTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
    private val json: Json,
    private val expenseRepository: TripExpenseRepository,
    private val firebaseStorage: FirebaseStorageImpl,
) {

    suspend operator fun invoke(
        tripId: String,
        tripPostModel: TripPostModel,
        bitmaps: List<Any>
    ): Unit = withContext(dispatcher) {
        val tripPostId = tripPostModel.id.orEmpty()
        val originalTripModel = repository.get(tripPostId)

        originalTripModel?.expenses?.forEach { expense ->
            if (!tripPostModel.expenses.contains(expense)) {
                expenseRepository.delete(expense.id)
            }
        }

        tripPostModel.expenses.filter { it.id.isNullOrEmpty() }.forEach { expense ->
            if (originalTripModel?.expenses?.contains(expense) == false) {
                expenseRepository.create(
                    TripExpense(
                        id = uuid4().toString(),
                        tripPostId = tripPostId,
                        createTs = getTimeMillis(),
                        ownerId = repository.getOwnerId(),
                        description = expense.description,
                        date = expense.date,
                        sum = expense.sum,
                        timestamp = expense.timestamp,
                        category = expense.category.name,
                        currencyCode = expense.currency?.id.orEmpty()
                    )
                )
            }
        }
        val photos = bitmaps.filterIsInstance<Bitmap>().map {
            val id = uuid4().toString()
            firebaseStorage.upload(id, it.toByteArray())
        } + bitmaps.filterIsInstance<String>()
        repository.update(
            TripPost(
                id = tripPostId,
                tripId = tripId,
                createTs = tripPostModel.createTs,
                editTs = getTimeMillis(),
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