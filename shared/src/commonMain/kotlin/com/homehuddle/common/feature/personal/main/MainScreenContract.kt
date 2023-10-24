package com.homehuddle.common.feature.personal.main

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class MainScreenState(
    val tripsSelected: Boolean = true,
    val trips: List<Trip> = listOf()
) : State {

    val posts: List<TripPost>
        get() = listOf()
}

internal sealed interface MainScreenIntent : Intent {
    object FirstLaunch: MainScreenIntent
    object OnResume: MainScreenIntent
    object SelectPostsFilter: MainScreenIntent
    object SelectTripsFilter: MainScreenIntent
}

internal sealed class MainScreenSingleEvent : SingleEvent