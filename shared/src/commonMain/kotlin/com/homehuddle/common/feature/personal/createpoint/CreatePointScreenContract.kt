package com.homehuddle.common.feature.personal.createpoint

import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.general.model.LocationModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPointModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class CreatePointScreenState(
    val trip: TripModel? = null,
    val model: TripPointModel? = null,
    val isCreateMode: Boolean = true,
    val userModel: UserModel? = null,
    val name: TextFieldValue = TextFieldValue(""),
    val description: TextFieldValue = TextFieldValue(""),
    val bottomSheet: BottomSheetType? = null,
    val trips: List<TripModel> = emptyList(),
    val searchedLocations: List<LocationModel> = emptyList(),
    val updateTs: Long? = 0,
    val currentLat: Double = 0.0,
    val currentLon: Double = 0.0,
) : State

internal sealed interface CreatePointScreenIntent : Intent {
    object CloseBottomSheet : CreatePointScreenIntent
    object OnResume : CreatePointScreenIntent
    object GoBack : CreatePointScreenIntent
    class OnSaveClick(val onCustomPointCreation: ((TripPointModel) -> Unit)?) :
        CreatePointScreenIntent

    data class Update(val model: TripPointModel?, val trip: TripModel?) :
        CreatePointScreenIntent

    data class UpdateUser(val userModel: UserModel?) : CreatePointScreenIntent
    data class UpdateTrips(val trips: List<TripModel>) : CreatePointScreenIntent
    data class OnNameChanged(val value: TextFieldValue) : CreatePointScreenIntent
    data class OnTripChanged(val value: TripModel) : CreatePointScreenIntent
    data class OnDescriptionChanged(val value: TextFieldValue) : CreatePointScreenIntent
    data class OnChangeLocation(val value: TripPointModel) : CreatePointScreenIntent
    data class OnLocationChanged(val value: LocationModel) : CreatePointScreenIntent
    data class OnSearchTextChanged(val value: String) : CreatePointScreenIntent
    data class OnLocationsFound(val searchedLocations: List<LocationModel>) : CreatePointScreenIntent
    object OnChangeTripClick : CreatePointScreenIntent
}

internal sealed interface CreatePointScreenSingleEvent : SingleEvent {

    object ShowError : CreatePointScreenSingleEvent
}

internal sealed interface BottomSheetType {
    data class SelectTrip(val trips: List<TripModel>, val selected: TripModel?) : BottomSheetType
    data class SelectLocation(val value: TripPointModel) : BottomSheetType
}