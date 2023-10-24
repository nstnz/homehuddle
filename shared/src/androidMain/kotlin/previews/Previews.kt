import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.homehuddle.common.design.theme.AppTheme
import com.homehuddle.common.feature.general.login.LoginScreen
import com.homehuddle.common.feature.general.splash.SplashScreen
import com.homehuddle.common.feature.general.welcome.WelcomeScreen
import com.homehuddle.common.feature.personal.main.MainScreen
import com.homehuddle.common.feature.personal.main.MainScreenState

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

@Preview
@Composable
private fun MainScreenPreview() {
    AppTheme {
        MainScreen(MainScreenState())
    }
}