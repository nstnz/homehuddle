package com.homehuddle.common.feature.personal.tripdetails

import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class TripDetailsScreenState(
    val trip: TripModel? = null,
    val selectedTab: TripDetailsTab = TripDetailsTab.All,
    val bottomSheet: BottomSheetType? = null,
) : State {

    val posts: List<TripPostModel>
        get() = trip?.posts.orEmpty()
}

internal sealed interface TripDetailsScreenIntent : Intent {
    object OnResume : TripDetailsScreenIntent
    object GoBack : TripDetailsScreenIntent
    object AllFilterSelected : TripDetailsScreenIntent
    object CloseBottomSheet : TripDetailsScreenIntent
    object ExpensesFilterSelected : TripDetailsScreenIntent
    object MapFilterSelected : TripDetailsScreenIntent
    object PhotosFilterSelected : TripDetailsScreenIntent
    object OverviewFilterSelected : TripDetailsScreenIntent
    data class UpdateTrip(val trip: TripModel?) : TripDetailsScreenIntent
    data class TryDeleteTrip(val trip: TripModel?) : TripDetailsScreenIntent
    data class ConfirmDeleteTrip(val trip: TripModel?) : TripDetailsScreenIntent
    data class EditTrip(val trip: TripModel?) : TripDetailsScreenIntent
    data class AddItemClick(val trip: TripModel?) : TripDetailsScreenIntent
    object AddTripClick : TripDetailsScreenIntent
    object AddTripPostClick : TripDetailsScreenIntent
    object AddExpensesClick : TripDetailsScreenIntent
    object AddLocationsClick : TripDetailsScreenIntent
    data class OnPostClick(val post: TripPostModel) : TripDetailsScreenIntent
}

internal sealed class TripDetailsScreenSingleEvent : SingleEvent

internal enum class TripDetailsTab {
    All,
    Photos,
    Map,
    Expenses,
    Overview
}

internal sealed interface BottomSheetType {
    object AddNewItem : BottomSheetType
    object ConfirmDelete : BottomSheetType
}