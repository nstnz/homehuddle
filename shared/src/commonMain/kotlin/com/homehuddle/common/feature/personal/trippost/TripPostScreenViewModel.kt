package com.homehuddle.common.feature.personal.trippost

import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router

internal class TripPostScreenViewModel(
    private val id: String,
    private val router: Router,
) : CoroutinesViewModel<TripPostScreenState, TripPostScreenIntent, TripPostScreenSingleEvent>() {

    override fun initialState(): TripPostScreenState = TripPostScreenState()

    override fun reduce(
        intent: TripPostScreenIntent,
        prevState: TripPostScreenState
    ): TripPostScreenState =
        prevState

    override suspend fun performSideEffects(
        intent: TripPostScreenIntent,
        state: TripPostScreenState
    ): TripPostScreenIntent? = when (intent) {
        TripPostScreenIntent.FirstLaunch -> {
            null
        }
    }
}