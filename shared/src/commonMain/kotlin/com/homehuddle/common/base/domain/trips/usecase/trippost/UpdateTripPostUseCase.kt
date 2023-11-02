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

internal class UpdateTripPostUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: TripPostRepository,
    private val tripRepository: TripRepository,
    private val expenseRepository: TripExpenseRepository,
    private val firebaseStorage: FirebaseStorageImpl,
) {

    suspend operator fun invoke(
        trip: TripModel?,
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
                    expense.copy(
                        id = uuid4().toString(),
                        tripPostId = tripPostId,
                        createTs = getTimeMillis(),
                        ownerId = repository.getOwnerId(),
                    )
                )
            }
        }
        val photos = bitmaps.filterIsInstance<Bitmap>().map {
            val id = uuid4().toString()
            firebaseStorage.upload(id, it.toByteArray())
        } + bitmaps.filterIsInstance<String>()

        repository.update(
            tripPostModel.copy(
                id = tripPostId,
                tripId = trip?.id.orEmpty(),
                ownerId = repository.getOwnerId(),
                photos = photos,
                editTs = getTimeMillis(),
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