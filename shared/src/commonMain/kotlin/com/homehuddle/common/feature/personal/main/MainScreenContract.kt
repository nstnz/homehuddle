package com.homehuddle.common.feature.personal.main

import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.base.domain.utils.flatten
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class MainScreenState(
    val tripsSelected: Boolean = true,
    val trips: List<TripModel> = listOf(),
    val user: UserModel? = null
) : State {

    val posts: List<TripPostModel>
        get() = trips.flatten { it.posts }
}

internal sealed interface MainScreenIntent : Intent {
    object FirstLaunch : MainScreenIntent
    object OnResume : MainScreenIntent
    object SelectPostsFilter : MainScreenIntent
    object SelectTripsFilter : MainScreenIntent
    data class UpdateUser(val user: UserModel) : MainScreenIntent
    data class UpdateTrips(val trips: List<TripModel>) : MainScreenIntent
}

internal sealed class MainScreenSingleEvent : SingleEvent