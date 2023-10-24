import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.feature.general.splash.SplashScreenHolder
import com.homehuddle.common.router.Router
import moe.tlaster.precompose.navigation.rememberNavigator
import org.kodein.di.instance

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App() {
    val router: Router by SharedDI.di.instance()
    val navigator = rememberNavigator()


    Navigator(SplashScreenHolder)
}

expect fun getPlatformName(): String