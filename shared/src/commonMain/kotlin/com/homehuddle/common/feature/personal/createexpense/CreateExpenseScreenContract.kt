package com.homehuddle.common.feature.personal.createexpense

import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.base.domain.general.model.TripExpenseCategory
import com.homehuddle.common.base.domain.general.model.TripExpenseModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class CreateExpenseScreenState(
    val trip: TripModel? = null,
    val model: TripExpenseModel? = null,
    val isCreateMode: Boolean = true,
    val userModel: UserModel? = null,
    val formattedSum: TextFieldValue = TextFieldValue(""),
    val description: TextFieldValue = TextFieldValue(""),
    val bottomSheet: BottomSheetType? = null,
    val trips: List<TripModel> = emptyList()
) : State

internal sealed interface CreateExpenseScreenIntent : Intent {
    object CloseBottomSheet : CreateExpenseScreenIntent
    object OnResume : CreateExpenseScreenIntent
    object GoBack : CreateExpenseScreenIntent
    class OnSaveClick(val onCustomExpenseCreation: ((TripExpenseModel) -> Unit)?) : CreateExpenseScreenIntent
    data class Update(val model: TripExpenseModel?, val trip: TripModel?) :
        CreateExpenseScreenIntent

    data class UpdateUser(val userModel: UserModel?) : CreateExpenseScreenIntent
    data class UpdateTrips(val trips: List<TripModel>) : CreateExpenseScreenIntent
    data class OnSumChanged(val value: TextFieldValue) : CreateExpenseScreenIntent
    data class OnCurrencyChanged(val value: CurrencyModel) : CreateExpenseScreenIntent
    data class OnTripChanged(val value: TripModel) : CreateExpenseScreenIntent
    data class OnDescriptionChanged(val value: TextFieldValue) : CreateExpenseScreenIntent
    data class OnCategoryChanged(val category: TripExpenseCategory) : CreateExpenseScreenIntent
    data class OnDateChanged(val date: Long?) : CreateExpenseScreenIntent
    object OnChangeCurrencyClick : CreateExpenseScreenIntent
    object OnChangeTripClick : CreateExpenseScreenIntent
    object OnDateClick : CreateExpenseScreenIntent
}

internal sealed interface CreateExpenseScreenSingleEvent : SingleEvent {

    object ShowError : CreateExpenseScreenSingleEvent
}

internal sealed interface BottomSheetType {
    data class SelectCurrency(val currencies: List<CurrencyModel>, val selected: CurrencyModel?) :
        BottomSheetType

    data class SelectTrip(val trips: List<TripModel>, val selected: TripModel?) : BottomSheetType
    data class SelectDate(val timestamp: Long?) : BottomSheetType
}