package com.homehuddle.common.feature.general.setup

import com.homehuddle.common.base.domain.general.usecase.GetMeUseCase
import com.homehuddle.common.base.domain.general.usecase.SaveMeUseCase
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

internal class SetupScreenViewModel(
    private val router: Router,
    private val getMeUseCase: GetMeUseCase,
    private val saveMeUseCase: SaveMeUseCase,
) : CoroutinesViewModel<SetupScreenState, SetupScreenIntent, SetupScreenSingleEvent>() {

    init {
        viewModelScope.launch {
            sendIntent(
                SetupScreenIntent.Load(
                    userModel = getMeUseCase()
                )
            )
        }
    }

    override fun initialState(): SetupScreenState = SetupScreenState()

    override fun reduce(
        intent: SetupScreenIntent,
        prevState: SetupScreenState
    ): SetupScreenState = when (intent) {
        is SetupScreenIntent.Load -> prevState.copy(
            userModel = intent.userModel,
            selectedCountries = intent.userModel?.visitedCountries.orEmpty().toMutableList(),
            selectedCurrency = intent.userModel?.currency
        )
        SetupScreenIntent.CloseBottomSheet -> prevState.copy(
            bottomSheet = null
        )

        is SetupScreenIntent.OnCurrencyClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectCurrency(
                currencies = prevState.userModel?.allCurrencies.orEmpty(),
                selected = prevState.selectedCurrency
            )
        )
        is SetupScreenIntent.OnChangeCurrency -> prevState.copy(
            selectedCurrency = intent.value,
            bottomSheet = null
        )
        SetupScreenIntent.OnAddCountry -> prevState.copy(
            bottomSheet = BottomSheetType.SelectCountry
        )
        is SetupScreenIntent.OnDeleteCountry -> prevState.copy(
            selectedCountries = prevState.selectedCountries.apply {
                this.remove(intent.countryModel)
            },
            updateTs = getTimeMillis()
        )
        is SetupScreenIntent.OnSelectCountry -> prevState.copy(
            selectedCountries = if (!prevState.selectedCountries.contains(intent.countryModel)) {
                prevState.selectedCountries.apply { this.add(intent.countryModel) }
            } else {
                prevState.selectedCountries
            },
            updateTs = getTimeMillis(),
            bottomSheet = null
        )
        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: SetupScreenIntent,
        state: SetupScreenState
    ): SetupScreenIntent? = when (intent) {
        is SetupScreenIntent.Load -> null
        SetupScreenIntent.Save -> {
            saveMeUseCase(
                state.userModel?.copy(
                    visitedCountries = state.selectedCountries,
                    currency = state.selectedCurrency
                )
            )
            router.navigateToMainScreen()
            null
        }

        else -> null
    }
}