import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.homehuddle.common.design.mocks.mockCurrency
import com.homehuddle.common.design.mocks.mockTrip
import com.homehuddle.common.design.mocks.mockTripExpense
import com.homehuddle.common.design.mocks.mockTripPost
import com.homehuddle.common.design.mocks.mockUser
import com.homehuddle.common.design.specific.CalendarBottomSheet
import com.homehuddle.common.design.specific.SelectCurrencyBottomSheet
import com.homehuddle.common.design.specific.SelectTripBottomSheet
import com.homehuddle.common.design.specific.TripCardComponent
import com.homehuddle.common.design.specific.TripDailyExpensesComponent
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