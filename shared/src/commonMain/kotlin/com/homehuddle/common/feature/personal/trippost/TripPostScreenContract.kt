package com.homehuddle.common.feature.personal.trippost

import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class TripPostScreenState(
    val trip: TripModel? = null,
    val tripPost: TripPostModel? = null,
    val bottomSheet: BottomSheetType? = null,
) : State

internal sealed interface TripPostScreenIntent : Intent {
    object GoBack : TripPostScreenIntent
    object OnResume : TripPostScreenIntent
    object OnEditClick : TripPostScreenIntent
    object OnDeleteClick : TripPostScreenIntent
    object OnConfirmDelete : TripPostScreenIntent
    object CloseBottomSheet : TripPostScreenIntent
    data class Update(val trip: TripModel?, val postModel: TripPostModel?) : TripPostScreenIntent
}

internal sealed class TripPostScreenSingleEvent : SingleEvent

internal sealed interface BottomSheetType {
    object ConfirmDelete : BottomSheetType
}