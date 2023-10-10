import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.homehuddle.common.R
import com.homehuddle.common.router.RootView

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = RootView()

actual fun getFont(): FontFamily = FontFamily(Font(R.font.comfortaa))
