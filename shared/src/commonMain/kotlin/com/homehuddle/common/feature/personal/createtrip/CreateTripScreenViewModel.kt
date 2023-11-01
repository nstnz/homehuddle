package com.homehuddle.common.feature.personal.createtrip

import androidx.compose.ui.text.input.TextFieldValue
import com.homehuddle.common.base.di.createTripScope
import com.homehuddle.common.base.domain.general.model.TripModel
import com.homehuddle.common.base.domain.general.usecase.GetMeUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.CreateTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.GetTripUseCase
import com.homehuddle.common.base.domain.trips.usecase.trip.UpdateTripUseCase
import com.homehuddle.common.base.domain.utils.formatDate
import com.homehuddle.common.base.ui.CoroutinesViewModel
import com.homehuddle.common.router.Router
import io.ktor.util.date.getTimeMillis

internal class CreateTripScreenViewModel(
    private val tripId: String?,
    private val router: Router,
    private val getMeUseCase: GetMeUseCase,
    private val getTripUseCase: GetTripUseCase,
    private val createTripUseCase: CreateTripUseCase,
    private val updateTripUseCase: UpdateTripUseCase,
) : CoroutinesViewModel<CreateTripScreenState, CreateTripScreenIntent, CreateTripScreenSingleEvent>() {

    override fun initialState(): CreateTripScreenState = CreateTripScreenState()

    override fun reduce(
        intent: CreateTripScreenIntent,
        prevState: CreateTripScreenState
    ): CreateTripScreenState = when (intent) {
        is CreateTripScreenIntent.UpdateUser -> prevState.copy(
            userModel = intent.userModel
        )
        is CreateTripScreenIntent.OnCountryDeleted -> prevState.copy(
            selectedCountries = prevState.selectedCountries.apply {
                this.remove(intent.countryModel)
            },
            updateTs = getTimeMillis()
        )
        is CreateTripScreenIntent.OnCountrySelected -> prevState.copy(
            selectedCountries = if (!prevState.selectedCountries.contains(intent.countryModel)) {
                prevState.selectedCountries.apply { this.add(intent.countryModel) }
            } else {
                prevState.selectedCountries
            },
            updateTs = getTimeMillis(),
            bottomSheet = null
        )
        is CreateTripScreenIntent.UpdateCurrency -> prevState.copy(
            currencyModel = intent.currencyModel
        )
        is CreateTripScreenIntent.Update -> prevState.copy(
            name = TextFieldValue(intent.tripModel?.name.orEmpty()),
            description = TextFieldValue(intent.tripModel?.description.orEmpty()),
            currencyModel = intent.tripModel?.currency ?: prevState.currencyModel,
            selectedCountries = intent.tripModel?.countries.orEmpty().toMutableList(),
            model = intent.tripModel ?: TripModel.createEmpty(
                intent.tripModel?.currency ?: prevState.currencyModel
            )
        )
        is CreateTripScreenIntent.OnChangeDescription -> prevState.copy(
            description = intent.text
        )
        CreateTripScreenIntent.OnAddCountry -> prevState.copy(
            bottomSheet = BottomSheetType.SelectCountry
        )
        is CreateTripScreenIntent.OnChangeName -> prevState.copy(
            name = intent.text
        )
        is CreateTripScreenIntent.OnFromDateSelected -> prevState.copy(
            model = prevState.model?.copy(
                timestampStart = intent.date,
                dateStart = intent.date.formatDate(),
            ),
            bottomSheet = null
        )
        is CreateTripScreenIntent.OnFromDateClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectFromDate(
                prevState.model?.timestampStart
            )
        )
        is CreateTripScreenIntent.OnToDateClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectToDate(
                prevState.model?.timestampEnd
            )
        )
        is CreateTripScreenIntent.OnToDateSelected -> prevState.copy(
            model = prevState.model?.copy(
                timestampEnd = intent.date,
                dateEnd = intent.date.formatDate(),
            ),
            bottomSheet = null
        )
        is CreateTripScreenIntent.OnCurrencyClick -> prevState.copy(
            bottomSheet = BottomSheetType.SelectCurrency(
                currencies = prevState.userModel?.allCurrencies.orEmpty(),
                selected = prevState.currencyModel
            )
        )
        is CreateTripScreenIntent.OnChangeCurrency -> prevState.copy(
            currencyModel = intent.value,
            bottomSheet = null
        )
        else -> prevState
    }

    override suspend fun performSideEffects(
        intent: CreateTripScreenIntent,
        state: CreateTripScreenState
    ): CreateTripScreenIntent? = when (intent) {
        CreateTripScreenIntent.OnResume -> {
            val user = getMeUseCase()
            sendIntent(CreateTripScreenIntent.UpdateUser(user))
            sendIntent(CreateTripScreenIntent.UpdateCurrency(user?.currency))
            tripId.takeIf { !it.isNullOrEmpty() }?.let {
                val trip = getTripUseCase(it)
                sendIntent(CreateTripScreenIntent.Update(trip))
            } ?: run {
                sendIntent(CreateTripScreenIntent.Update(TripModel.createEmpty(user?.currency)))
            }
            null
        }

        CreateTripScreenIntent.OnSaveClick -> {
            val model = state.model?.copy(
                description = state.description.text,
                name = state.name.text
            )
            val error = when {
                state.name.text.isEmpty() -> true
                else -> false
            }
            if (!error && model != null) {
                if (tripId.isNullOrEmpty()) {
                    createTripUseCase(
                        tripModel = model,
                        currencyModel = state.currencyModel,
                        countries = state.selectedCountries
                    )
                } else {
                    updateTripUseCase(
                        tripModel = model,
                        currencyModel = state.currencyModel,
                        countries = state.selectedCountries
                    )
                }
                router.back(createTripScope)
            } else {
                triggerSingleEvent(CreateTripScreenSingleEvent.ShowError)
            }
            null
        }

        CreateTripScreenIntent.GoBack -> {
            router.back(createTripScope)
            null
        }

        else -> null
    }
}