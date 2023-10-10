import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.ComposeUIViewController
import com.homehuddle.common.router.RootView

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { RootView() }

actual fun getFont(): FontFamily = FontFamily()