package com.homehuddle.common.feature.personal.createtrip

import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.trips.usecase.GetTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.SaveTripUseCase
import com.homehuddle.common.base.domain.utils.formatDate
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

internal class CreateTripScreenViewModel(
    private val tripId: String?,
    private val router: Router,
    private val getTripUseCase: GetTripUseCase,
    private val saveTripUseCase: SaveTripUseCase,
) : CoroutinesViewModel<CreateTripScreenState, CreateTripScreenIntent, CreateTripScreenSingleEvent>() {

    init {
        tripId.takeIf { !it.isNullOrEmpty() }?.let {
            viewModelScope.launch {
                sendIntent(CreateTripScreenIntent.Update(getTripUseCase(it)))
            }
        }
    }

    override fun initialState(): CreateTripScreenState = CreateTripScreenState()

    override fun reduce(
        intent: CreateTripScreenIntent,
        prevState: CreateTripScreenState
    ): CreateTripScreenState = when (intent) {
        is CreateTripScreenIntent.Update -> prevState.copy(
            name = TextFieldValue(intent.tripModel?.name.orEmpty()),
            description = TextFieldValue(intent.tripModel?.description.orEmpty()),
            dateStart = intent.tripModel?.dateStart,
            dateEnd = intent.tripModel?.dateEnd,
            timestampStart = intent.tripModel?.timestampStart,
            timestampEnd = intent.tripModel?.timestampEnd,
        )

        is CreateTripScreenIntent.OnChangeDescription -> prevState.copy(
            description = intent.text
        )

        is CreateTripScreenIntent.OnChangeName -> prevState.copy(
            name = intent.text
        )

        is CreateTripScreenIntent.OnFromDateSelected -> prevState.copy(
            timestampStart = intent.date,
            dateStart = intent.date.formatDate()
        )

        is CreateTripScreenIntent.OnToDateSelected -> prevState.copy(
            timestampEnd = intent.date,
            dateEnd = intent.date.formatDate()
        )

        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: CreateTripScreenIntent,
        state: CreateTripScreenState
    ): CreateTripScreenIntent? = when (intent) {
        CreateTripScreenIntent.OnResume -> {
            tripId.takeIf { !it.isNullOrEmpty() }?.let {
                sendIntent(CreateTripScreenIntent.Update(getTripUseCase(it)))
            }
            null
        }
        CreateTripScreenIntent.OnSaveClick -> {
            val error = when {
                state.name.text.isEmpty() -> true
                else -> false
            }
            if (!error) {
                saveTripUseCase(
                    id = tripId?.takeIf { it.isNotEmpty() },
                    name = state.name.text,
                    description = state.description.text,
                    dateStart = state.dateStart,
                    dateEnd = state.dateEnd,
                    timestampStart = state.timestampStart,
                    timestampEnd = state.timestampEnd,
                )
                router.back()
            } else {
                triggerSingleEvent(CreateTripScreenSingleEvent.ShowError)
            }
            null
        }

        CreateTripScreenIntent.GoBack -> {
            router.back()
            null
        }

        else -> null
    }
}