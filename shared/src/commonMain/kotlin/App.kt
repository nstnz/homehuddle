import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.homehuddle.common.base.di.SharedDI
import com.homehuddle.common.feature.general.login.LoginScreenHolder
import com.homehuddle.common.feature.general.splash.SplashScreenHolder
import com.homehuddle.common.feature.general.welcome.WelcomeScreenHolder
import com.homehuddle.common.router.Arg1
import com.homehuddle.common.router.Arg2
import com.homehuddle.common.router.Arg3
import com.homehuddle.common.router.Router
import com.homehuddle.common.router.Routes
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.kodein.di.instance

@Composable
fun App() {
    val router: Router by SharedDI.di.instance()
    val navigator = rememberNavigator()
    router.init(navigator)

    Box(Modifier.fillMaxSize()) {
        NavHost(
            navigator = navigator,
            initialRoute = Routes.Splash.name,
            navTransition = NavTransition(
                createTransition = EnterTransition.None,
                resumeTransition = EnterTransition.None,
                destroyTransition = ExitTransition.None,
                pauseTransition = ExitTransition.None,
            )
        ) {
            Routes.values().forEach { route ->
                val content: @Composable (BackStackEntry) -> Unit = {
                    val arg1 = it.queryString?.map?.get(Arg1)?.firstOrNull()
                    val arg2 = it.queryString?.map?.get(Arg2)?.firstOrNull()
                    val arg3 = it.queryString?.map?.get(Arg3)?.firstOrNull()

                    when (route) {
                        Routes.Splash -> SplashScreenHolder()
                        Routes.Welcome -> WelcomeScreenHolder()
                        Routes.Login -> LoginScreenHolder()
                        Routes.Main -> SplashScreenHolder()
                    }
                }
                scene(route.name) {
                    content(it)
                }
            }
        }
    }
}

expect fun getPlatformName(): String