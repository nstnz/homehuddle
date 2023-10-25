import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.homehuddle.common.design.mocks.mockTrip
import com.homehuddle.common.design.mocks.mockTripPost
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.feature.general.login.LoginScreen
import com.homehuddle.common.feature.general.splash.SplashScreen
import com.homehuddle.common.feature.general.welcome.WelcomeScreen
import com.homehuddle.common.feature.personal.createtrip.CreateTripScreen
import com.homehuddle.common.feature.personal.createtrip.CreateTripScreenState
import com.homehuddle.common.feature.personal.main.AddNewItemBottomSheet
import com.homehuddle.common.feature.personal.main.MainScreen
import com.homehuddle.common.feature.personal.main.MainScreenState
import com.homehuddle.common.feature.personal.tripdetails.TripDetailsScreen
import com.homehuddle.common.feature.personal.tripdetails.TripDetailsScreenState
import com.homehuddle.common.feature.personal.tripdetails.TripDetailsTab
import com.homehuddle.common.feature.personal.trippost.TripPostScreen
import com.homehuddle.common.feature.personal.trippost.TripPostScreenState
import com.homehuddle.common.feature.personal.trippost.TripPostTab

@Preview
@Composable
private fun SplashScreenPreview() {
    AppTheme {
        SplashScreen()
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    AppTheme {
        WelcomeScreen({})
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreen({ })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun MainScreenPreview() {
    AppTheme {
        MainScreen(MainScreenState())
    }
}

@Preview
@Composable
private fun TripDetailsScreenPreview() {
    AppTheme {
        TripDetailsScreen(
            TripDetailsScreenState(mockTrip(), selectedTab = TripDetailsTab.Overview)
        )
    }
}

@Preview
@Composable
private fun TripPostScreenPreview() {
    AppTheme {
        TripPostScreen(
            TripPostScreenState(mockTrip(), mockTripPost(),
                selectedTab = TripPostTab.All)
        )
    }
}

@Preview
@Composable
private fun AddNewItemBottomSheetPreview() {
    AppTheme {
        AddNewItemBottomSheet()
    }
}

@Preview
@Composable
private fun CreateTripScreenPreview() {
    AppTheme {
        CreateTripScreen(CreateTripScreenState(
            name = TextFieldValue("kjsdflj"),
            description = TextFieldValue("kjsd ksdnlkfjf jsldkf jflj"),
            fromDateSelected = true
        ))
    }
}