package com.homehuddle.common.feature.personal.trippost

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class TripPostScreenState(
    val trip: Trip? = null,
    val tripPost: TripPost? = null,
    val selectedTab: TripPostTab = TripPostTab.All
) : State

internal sealed interface TripPostScreenIntent : Intent {
    object FirstLaunch : TripPostScreenIntent
}

internal sealed class TripPostScreenSingleEvent : SingleEvent

internal enum class TripPostTab {
    All,
    Photos,
    Map,
    Expenses
}