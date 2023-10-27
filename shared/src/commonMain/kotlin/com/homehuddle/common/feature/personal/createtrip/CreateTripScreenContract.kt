package com.homehuddle.common.feature.personal.createtrip

import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.general.model.CountryModel
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class CreateTripScreenState(
    val name: TextFieldValue = TextFieldValue(""),
    val description: TextFieldValue = TextFieldValue(""),
    val dateStart: String? = null,
    val timestampStart: Long? = null,
    val dateEnd: String? = null,
    val timestampEnd: Long? = null,
    val currencyModel: CurrencyModel? = null,
    val userModel: UserModel? = null,
    val selectedCountries: MutableList<CountryModel> = mutableListOf(),
    val bottomSheet: BottomSheetType? = null,
    val updateTs: Long? = 0
) : State

internal sealed interface CreateTripScreenIntent : Intent {
    data class UpdateUser(val userModel: UserModel?) : CreateTripScreenIntent
    data class UpdateCurrency(val currencyModel: CurrencyModel?) : CreateTripScreenIntent
    data class Update(val tripModel: TripModel?) : CreateTripScreenIntent
    data class OnChangeName(val text: TextFieldValue) : CreateTripScreenIntent
    data class OnChangeDescription(val text: TextFieldValue) : CreateTripScreenIntent
    data class OnFromDateSelected(val date: Long?) : CreateTripScreenIntent
    data class OnToDateSelected(val date: Long?) : CreateTripScreenIntent
    data class OnChangeCurrency(val value: CurrencyModel) : CreateTripScreenIntent
    data class OnCountrySelected(val countryModel: CountryModel, val selected: Boolean) :
        CreateTripScreenIntent

    object OnSaveClick : CreateTripScreenIntent
    object GoBack : CreateTripScreenIntent
    object OnResume : CreateTripScreenIntent
    object CloseBottomSheet : CreateTripScreenIntent
    object OnFromDateClick : CreateTripScreenIntent
    object OnToDateClick : CreateTripScreenIntent
    object OnCurrencyClick : CreateTripScreenIntent
}

internal sealed interface CreateTripScreenSingleEvent : SingleEvent {

    object ShowError : CreateTripScreenSingleEvent
}

internal sealed interface BottomSheetType {
    data class SelectCurrency(val currencies: List<CurrencyModel>, val selected: CurrencyModel?) :
        BottomSheetType

    data class SelectFromDate(val timestamp: Long?) : BottomSheetType
    data class SelectToDate(val timestamp: Long?) : BottomSheetType
}