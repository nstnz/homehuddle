package com.homehuddle.common.base.domain.trips.usecase.trippost

import FirebaseStorageImpl
import com.benasher44.uuid.uuid4
import com.homehuddle.common.base.data.repository.TripExpenseRepository
import com.homehuddle.common.base.data.repository.TripPostRepository
import com.homehuddle.common.base.data.repository.TripRepository
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import dev.icerock.moko.media.Bitmap
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class CreateTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
    private val expenseRepository: TripExpenseRepository,
    private val tripRepository: TripRepository,
    private val firebaseStorage: FirebaseStorageImpl,
) {

    suspend operator fun invoke(
        trip: TripModel?,
        tripPostModel: TripPostModel,
        bitmaps: List<Bitmap>
    ): Unit = withContext(dispatcher) {
        val tripPostId = uuid4().toString()
        tripPostModel.expenses.forEach {
            expenseRepository.create(
                it.copy(
                    id = uuid4().toString(),
                    tripPostId = tripPostId,
                    createTs = getTimeMillis(),
                    ownerId = repository.getOwnerId(),
                )
            )
        }
        val photos = bitmaps.map {
            val id = uuid4().toString()
            firebaseStorage.upload(id, it.toByteArray())
        }
        repository.create(
            tripPostModel.copy(
                id = tripPostId,
                tripId = trip?.id.orEmpty(),
                ownerId = repository.getOwnerId(),
                createTs = getTimeMillis(),
                photos = photos
            )
        )

        trip?.let {
            tripRepository.update(
                it.copy(
                    editTs = getTimeMillis()
                )
            )
        }
    }
}