import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.homehuddle.common.design.mocks.mockTrip
import com.homehuddle.common.design.mocks.mockTripPost
import com.homehuddle.common.design.mocks.mockUser
import com.homehuddle.common.design.specific.TripCardComponent
import com.homehuddle.common.design.specific.TripPostCardComponent
import com.homehuddle.common.design.specific.TripPostCompactCardComponent
import com.homehuddle.common.design.theme.AppTheme

@Preview
@Composable
private fun MyTripCardPreview() {
    AppTheme {
        TripCardComponent(
            trip = mockTrip(),
            ownedByUser = true
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
            user = mockUser()
        )
    }
}