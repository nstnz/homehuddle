package com.homehuddle.common.feature.general.setup

import com.homehuddle.common.base.domain.general.model.CountryModel
import com.homehuddle.common.base.domain.general.model.CurrencyModel
import com.homehuddle.common.base.domain.general.model.UserModel
import com.homehuddle.common.base.ui.Intent
import com.homehuddle.common.base.ui.SingleEvent
import com.homehuddle.common.base.ui.State

internal data class SetupScreenState(
    val userModel: UserModel? = null,
    val selectedCurrency: CurrencyModel? = null,
    val selectedCountries: MutableList<CountryModel> = mutableListOf(),
    val bottomSheet: BottomSheetType? = null,
    val updateTs: Long? = 0
) : State

internal sealed interface SetupScreenIntent : Intent {
    data class Load(
        val userModel: UserModel? = null,
    ) : SetupScreenIntent

    object Save : SetupScreenIntent
    object OnAddCountry : SetupScreenIntent
    object OnCurrencyClick : SetupScreenIntent
    object CloseBottomSheet : SetupScreenIntent
    data class OnChangeCurrency(val value: CurrencyModel) : SetupScreenIntent
    data class OnSelectCountry(val countryModel: CountryModel) : SetupScreenIntent
    data class OnDeleteCountry(val countryModel: CountryModel) : SetupScreenIntent
}

internal sealed class SetupScreenSingleEvent : SingleEvent

internal sealed interface BottomSheetType {
    data class SelectCurrency(val currencies: List<CurrencyModel>, val selected: CurrencyModel?) :
        BottomSheetType

    object SelectCountry : BottomSheetType
}