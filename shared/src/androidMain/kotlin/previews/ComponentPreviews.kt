import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homehuddle.common.design.bottomsheet.BottomSheetComponent
import com.homehuddle.common.design.mocks.mockTrip
import com.homehuddle.common.design.mocks.mockTripExpense
import com.homehuddle.common.design.mocks.mockTripPost
import com.homehuddle.common.design.mocks.mockUser
import com.homehuddle.common.design.specific.TripCardComponent
import com.homehuddle.common.design.specific.TripDailyExpensesComponent
import com.homehuddle.common.design.specific.TripPostCardComponent
import com.homehuddle.common.design.specific.TripPostCompactCardComponent
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.design.theme.accent2

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
private fun BottomSheetComponentPreview() {
    AppTheme {
        BottomSheetComponent(
            title = "Hkshka hakls flajfalksf ",
            description = "khslfkj jfkdfjsdlkjfskdjfskldjflsdjf",
            topButton = "Yhjdj",
            bottomButton = "Thjskls",
            customContent = {
                Box(modifier = Modifier.fillMaxWidth().height(100.dp)
                    .background(AppTheme.colors.accent2()))
            }
        )
    }
}