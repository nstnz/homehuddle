package com.homehuddle.common.feature.personal.tripdetails

import com.homehuddle.common.base.data.model.Trip
import com.homehuddle.common.base.data.model.TripPost
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class TripDetailsScreenState(
    val trip: Trip? = null,
    val selectedTab: TripDetailsTab = TripDetailsTab.All
) : State {

    val posts: List<TripPost>
        get() = listOf()
}

internal sealed interface TripDetailsScreenIntent : Intent {
    object FirstLaunch : TripDetailsScreenIntent
}

internal sealed class TripDetailsScreenSingleEvent : SingleEvent

internal enum class TripDetailsTab {
    All,
    Photos,
    Map,
    Expenses
}