package com.homehuddle.common.feature.personal.createtrip

import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.base.domain.general.model.TripModel
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
    val fromDateSelected: Boolean? = null,
    val currencyModel: CurrencyModel? = null,
) : State

internal sealed interface CreateTripScreenIntent : Intent {
    data class UpdateCurrency(val currencyModel: CurrencyModel?) : CreateTripScreenIntent
    data class Update(val tripModel: TripModel?) : CreateTripScreenIntent
    data class OnChangeName(val text: TextFieldValue) : CreateTripScreenIntent
    data class OnChangeDescription(val text: TextFieldValue) : CreateTripScreenIntent
    data class OnFromDateSelected(val date: Long?) : CreateTripScreenIntent
    data class OnToDateSelected(val date: Long?) : CreateTripScreenIntent
    object OnSaveClick : CreateTripScreenIntent
    object GoBack : CreateTripScreenIntent
    object OnResume : CreateTripScreenIntent
    object OnFromDateClick : CreateTripScreenIntent
    object OnToDateClick : CreateTripScreenIntent
}

internal sealed interface CreateTripScreenSingleEvent : SingleEvent {

    object ShowError : CreateTripScreenSingleEvent
}