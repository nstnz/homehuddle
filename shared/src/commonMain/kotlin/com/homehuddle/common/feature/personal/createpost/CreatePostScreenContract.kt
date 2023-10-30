package com.homehuddle.common.feature.personal.createpost

import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.general.model.CountryModel
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.TripPostModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State
import dev.icerock.moko.media.Bitmap

internal data class CreatePostScreenState(
    val isCreateMode: Boolean = true,
    val model: TripPostModel? = null,
    val name: TextFieldValue = TextFieldValue(""),
    val description: TextFieldValue = TextFieldValue(""),
    val trip: TripModel? = null,
    val bottomSheet: BottomSheetType? = null,
    val trips: List<TripModel> = emptyList(),
    val userModel: UserModel? = null,
    val updateTs: Long? = 0,
    val bitmaps: MutableList<Bitmap> = mutableListOf()
) : State

internal sealed interface CreatePostScreenIntent : Intent {
    data class UpdateUser(val userModel: UserModel?) : CreatePostScreenIntent
    data class UpdateTrips(val trips: List<TripModel>) : CreatePostScreenIntent
    data class Update(val model: TripPostModel?, val trip: TripModel?) : CreatePostScreenIntent
    data class OnChangeName(val text: TextFieldValue) : CreatePostScreenIntent
    data class OnChangeDescription(val text: TextFieldValue) : CreatePostScreenIntent
    data class OnFromDateSelected(val date: Long?) : CreatePostScreenIntent
    data class OnToDateSelected(val date: Long?) : CreatePostScreenIntent
    data class OnChangeTrip(val item: TripModel) : CreatePostScreenIntent
    data class OnAddExpense(val item: TripExpenseModel) : CreatePostScreenIntent
    data class OnSelectCountry(val item: CountryModel) : CreatePostScreenIntent
    data class OnDeleteCountry(val item: CountryModel) : CreatePostScreenIntent
    data class OnDeleteExpense(val item: TripExpenseModel) : CreatePostScreenIntent
    data class OnDeletePhotoClick(val item: Bitmap) : CreatePostScreenIntent
    object OnSaveClick : CreatePostScreenIntent
    object GoBack : CreatePostScreenIntent
    object OnResume : CreatePostScreenIntent
    object CloseBottomSheet : CreatePostScreenIntent
    object OnFromDateClick : CreatePostScreenIntent
    object OnToDateClick : CreatePostScreenIntent
    object OnAddNewCountry : CreatePostScreenIntent
    object OnAddNewExpense : CreatePostScreenIntent
    data class OnAddPhoto(val bitmap: Bitmap?) : CreatePostScreenIntent
    object OnTripClick : CreatePostScreenIntent
}

internal sealed interface CreatePostScreenSingleEvent : SingleEvent {

    object ShowError : CreatePostScreenSingleEvent
}

internal sealed interface BottomSheetType {
    data class SelectTrip(val trips: List<TripModel>, val selected: TripModel?) : BottomSheetType
    data class SelectFromDate(val timestamp: Long?) : BottomSheetType
    data class SelectToDate(val timestamp: Long?) : BottomSheetType
    object SelectCountry : BottomSheetType
}