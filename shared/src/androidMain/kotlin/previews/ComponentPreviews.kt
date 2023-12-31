import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.homehuddle.common.base.domain.general.model.LocationModel
import com.homehuddle.common.design.mocks.mockCountry
import com.homehuddle.common.design.mocks.mockCurrency
import com.homehuddle.common.design.mocks.mockTrip
import com.homehuddle.common.design.mocks.mockTripExpense
import com.homehuddle.common.design.mocks.mockTripPost
import com.homehuddle.common.design.mocks.mockUser
import com.homehuddle.common.design.specific.CalendarBottomSheet
import com.homehuddle.common.design.specific.CountriesSelectorComponent
import com.homehuddle.common.design.specific.SelectCurrencyBottomSheet
import com.homehuddle.common.design.specific.SelectLocationBottomSheet
import com.homehuddle.common.design.specific.SelectTripBottomSheet
import com.homehuddle.common.design.specific.TripCardComponent
import com.homehuddle.common.design.specific.TripDailyExpensesComponent
import com.homehuddle.common.design.specific.TripExpensesCardComponent
import com.homehuddle.common.design.specific.TripPostCardComponent
import com.homehuddle.common.design.specific.TripPostCompactCardComponent
import com.homehuddle.common.design.theme.AppTheme

@Preview
@Composable
private fun MyTripCardPreview() {
    AppTheme {
        TripCardComponent(
            trip = mockTrip(),
        )
    }
}

@Preview
@Composable
private fun TripPostCardPreview() {
    AppTheme {
        TripPostCardComponent(
            trip = mockTrip(),
            tripPost = mockTripPost(),
            user = mockUser()
        )
    }
}

@Preview
@Composable
private fun TripPostCompactCardPreview() {
    AppTheme {
        TripPostCompactCardComponent(
            trip = mockTrip(),
            tripPost = mockTripPost(),
            showSocialHeader = true
        )
    }
}

@Preview
@Composable
private fun TripExpenseCardComponentPreview() {
    AppTheme {
        TripExpensesCardComponent(
            trip = mockTrip(),
            tripPost = mockTripPost(),
            showSocialHeader = true
        )
    }
}

@Preview
@Composable
private fun TripDailyExpensesPreview() {
    AppTheme {
        TripDailyExpensesComponent(
            date = "10.1000.10392",
            expenses = listOf(
                mockTripExpense(),
                mockTripExpense(),
                mockTripExpense(),
                mockTripExpense(),
            ),
            userModel = mockUser()
        )
    }
}

@Preview
@Composable
private fun CalendarBottomSheetPreview() {
    AppTheme {
        CalendarBottomSheet(
            timestamp = null,
        )
    }
}

@Preview
@Composable
private fun SelectCurrencyBottomSheetPreview() {
    AppTheme {
        SelectCurrencyBottomSheet(
            title = "Select currency",
            currencies = listOf(
                mockCurrency(),
                mockCurrency().copy(code = "$"),
                mockCurrency().copy(code = "US$"),
            ),
            selected = mockCurrency(),
            onSelect = {}
        )
    }
}

@Preview
@Composable
private fun SelectTripBottomSheetPreview() {
    AppTheme {
        SelectTripBottomSheet(
            title = "Select trip",
            trips = listOf(
                mockTrip(),
                mockTrip().copy(name = "sdkj lskjsl"),
                mockTrip().copy(name = "US$ kdjhfl kds"),
            ),
            selected = mockTrip(),
            onSelect = {}
        )
    }
}

@Preview
@Composable
private fun CountriesSelectorComponentPreview() {
    AppTheme {
        CountriesSelectorComponent(
            countries = listOf(
                mockCountry(),
                mockCountry(),
                mockCountry(),
                mockCountry(),
            ),
            selectedCountries = emptyList()
        )
    }
}

@Preview
@Composable
private fun SelectLocationBottomSheetPreview() {
    AppTheme {
        SelectLocationBottomSheet(
            title = "Select location",
            selectedLocation = LocationModel("slf",
                "", "",
                0.0, 1.0),
        )
    }
}