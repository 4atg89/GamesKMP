import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.atg.gameskmp.MainJvm
import com.atg.gameskmp.appModule
import moe.tlaster.precompose.PreComposeWindow
import org.koin.core.context.startKoin

fun main() = application {
    startKoin { modules(appModule()) }
    PreComposeWindow(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 800.dp, height = 400.dp)
    ) {
        MainJvm()
    }
}