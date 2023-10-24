package com.homehuddle.common.feature.personal.trippost

import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class TripPostScreenState(
    val trip: TripModel? = null,
    val tripPost: TripPostModel? = null,
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