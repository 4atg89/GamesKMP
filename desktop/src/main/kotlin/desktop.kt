import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.atg.gameskmp.MainJvm
import moe.tlaster.precompose.PreComposeWindow

fun main() = application {
    PreComposeWindow(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 800.dp, height = 400.dp)
    ) {
        MainJvm()
    }
}