package com.homehuddle.common.feature.personal.createpost

import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.trips.usecase.GetTripPostUseCase
import com.homehuddle.common.base.domain.trips.usecase.GetTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.SaveTripPostUseCase
import com.homehuddle.common.base.domain.utils.formatDate
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class CreatePostScreenViewModel(
    private val tripPostId: String?,
    private val router: Router,
    private val getTripUseCase: GetTripUseCase,
    private val getTripPostUseCase: GetTripPostUseCase,
    private val saveTripPostUseCase: SaveTripPostUseCase,
) : CoroutinesViewModel<CreatePostScreenState, CreatePostScreenIntent, CreatePostScreenSingleEvent>() {

    override fun initialState(): CreatePostScreenState = CreatePostScreenState()

    override fun reduce(
        intent: CreatePostScreenIntent,
        prevState: CreatePostScreenState
    ): CreatePostScreenState = when (intent) {
        is CreatePostScreenIntent.Update -> prevState.copy(
            name = TextFieldValue(intent.model?.name.orEmpty()),
            description = TextFieldValue(intent.model?.description.orEmpty()),
            dateStart = intent.model?.dateStart,
            dateEnd = intent.model?.dateEnd,
            timestampStart = intent.model?.timestampStart,
            timestampEnd = intent.model?.timestampEnd,
        )

        is CreatePostScreenIntent.OnChangeDescription -> prevState.copy(
            description = intent.text
        )

        is CreatePostScreenIntent.OnChangeName -> prevState.copy(
            name = intent.text
        )

        is CreatePostScreenIntent.OnFromDateSelected -> prevState.copy(
            timestampStart = intent.date,
            dateStart = intent.date.formatDate()
        )

        is CreatePostScreenIntent.OnToDateSelected -> prevState.copy(
            timestampEnd = intent.date,
            dateEnd = intent.date.formatDate()
        )

        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: CreatePostScreenIntent,
        state: CreatePostScreenState
    ): CreatePostScreenIntent? = when (intent) {
        CreatePostScreenIntent.OnResume -> {
            tripPostId.takeIf { !it.isNullOrEmpty() }?.let {
                sendIntent(CreatePostScreenIntent.Update(getTripPostUseCase(it)))
            }
            null
        }

        CreatePostScreenIntent.OnSaveClick -> {
            val error = when {
                state.name.text.isEmpty() -> true
                else -> false
            }
            if (!error) {
                router.back()
            } else {
                triggerSingleEvent(CreatePostScreenSingleEvent.ShowError)
            }
            null
        }

        CreatePostScreenIntent.GoBack -> {
            router.back()
            null
        }

        else -> null
    }
}