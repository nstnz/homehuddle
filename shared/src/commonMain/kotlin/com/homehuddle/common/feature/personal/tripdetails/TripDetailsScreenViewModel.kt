package com.homehuddle.common.feature.personal.tripdetails

import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class TripDetailsScreenViewModel(
    private val router: Router,
) : CoroutinesViewModel<TripDetailsScreenState, TripDetailsScreenIntent, TripDetailsScreenSingleEvent>() {

    override fun initialState(): TripDetailsScreenState = TripDetailsScreenState()

    override fun reduce(
        intent: TripDetailsScreenIntent,
        prevState: TripDetailsScreenState
    ): TripDetailsScreenState =
        prevState

    override suspend fun performSideEffects(
        intent: TripDetailsScreenIntent,
        state: TripDetailsScreenState
    ): TripDetailsScreenIntent? = when (intent) {
        TripDetailsScreenIntent.FirstLaunch -> {
            null
        }
    }
}