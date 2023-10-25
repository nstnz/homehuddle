import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.homehuddle.common.feature.general.splash.SplashScreenHolder

@Composable
fun App() {
    Navigator(SplashScreenHolder)
}

expect fun getPlatformName(): String