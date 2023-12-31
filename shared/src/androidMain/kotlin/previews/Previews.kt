import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.homehuddle.common.design.mocks.mockTrip
import com.homehuddle.common.design.mocks.mockTripExpense
import com.homehuddle.common.design.mocks.mockTripPoint
import com.homehuddle.common.design.mocks.mockTripPost
import com.homehuddle.common.design.mocks.mockUser
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.feature.general.login.LoginScreen
import com.homehuddle.common.feature.general.setup.SetupScreen
import com.homehuddle.common.feature.general.setup.SetupScreenState
import com.homehuddle.common.feature.general.splash.SplashScreen
import com.homehuddle.common.feature.general.welcome.WelcomeScreen
import com.homehuddle.common.feature.personal.createexpense.CreateExpenseScreen
import com.homehuddle.common.feature.personal.createexpense.CreateExpenseScreenState
import com.homehuddle.common.feature.personal.createpoint.CreatePointScreen
import com.homehuddle.common.feature.personal.createpoint.CreatePointScreenState
import com.homehuddle.common.feature.personal.createpost.CreatePostScreen
import com.homehuddle.common.feature.personal.createpost.CreatePostScreenState
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
private fun SetupScreenPreview() {
    AppTheme {
        SetupScreen(SetupScreenState(mockUser()))
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

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun TripDetailsScreenPreview() {
    AppTheme {
        TripDetailsScreen(
            TripDetailsScreenState(mockTrip(), selectedTab = TripDetailsTab.Overview)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun TripPostScreenPreview() {
    AppTheme {
        TripPostScreen(
            TripPostScreenState(
                mockTrip(), mockTripPost(),
            )
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

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun CreateTripScreenPreview() {
    AppTheme {
        CreateTripScreen(
            CreateTripScreenState(
                name = TextFieldValue("kjsdflj"),
                description = TextFieldValue("kjsd ksdnlkfjf jsldkf jflj"),
            )
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun CreatePostScreenPreview() {
    AppTheme {
        CreatePostScreen(
            CreatePostScreenState(
                name = TextFieldValue("kjsdflj"),
                description = TextFieldValue("kjsd ksdnlkfjf jsldkf jflj"),
                trip = mockTrip(),
                model = mockTripPost()
            )
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun CreateExpenseScreenPreview() {
    AppTheme {
        CreateExpenseScreen(
            CreateExpenseScreenState(
                trip = mockTrip(),
                description = TextFieldValue("kjsd ksdnlkfjf jsldkf jflj"),
                formattedSum = TextFieldValue(""),
                model = mockTripExpense(),
                userModel = mockUser(),
                isCreateMode = true
            )
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun CreatePointScreenPreview() {
    AppTheme {
        CreatePointScreen(
            CreatePointScreenState(
                trip = mockTrip(),
                description = TextFieldValue("kjsd ksdnlkfjf jsldkf jflj"),
                name = TextFieldValue("sdfjhkd"),
                model = mockTripPoint(),
                userModel = mockUser(),
                isCreateMode = true
            )
        )
    }
}