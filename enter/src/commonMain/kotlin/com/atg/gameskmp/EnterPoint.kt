package com.atg.gameskmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.atg.annotations.ReduxFeature
import com.atg.common.Action
import com.atg.common.app.AppStore
import com.atg.details.GameDetails
import com.atg.games.GamesList
import com.atg.games.GamesViewModel
import com.atg.games.di.gameUiModule
import com.atg.games.redux.GamesAction
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
fun Enter() {
    KoinApplication(application = { modules(appModule()) }) {
        MyApplicationTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {

                val koin = getKoin()
                LaunchedEffect(Unit) {
                    val store = koin.get<AppStore>()
//                println("store = ${store::class}")
                }
                val navigator = rememberNavigator()
                NavHost(
                    navigator = navigator,
                    navTransition = NavTransition(),
                    initialRoute = "/home"
                ) {
                    scene("/home", navTransition = NavTransition()) {
                        rememberKoinModules { listOf(gameUiModule) }
                        val viewModel = koinViewModel(GamesViewModel::class)
                        val commandProps = viewModel.gamesCommandProps.value
                        GamesList(viewModel)
                        LaunchedEffect(commandProps) {
                            val destination = commandProps.sideEffect ?: return@LaunchedEffect
                            when (val effect = destination.sideEffect) {
                                is GamesAction.OpenGameCommand -> navigator.navigate("/details/${effect.id}")
                                else -> Unit
                            }
                            destination.consumed.invoke()
                        }
                    }
                    scene("/details/{id}") { entry ->
                        GameDetails(entry.path<Int>("id")!!, navigator)
                    }
                }
            }
        }
    }
}

//@ReduxFeature("HiFromHere20")
//interface Bla
@ReduxFeature(GamesViewModel::class, "GamesViewModelInterface")
sealed interface SealedInterface : Action {

    class SealedInterfaceClass(val id: Int) : SealedInterface
    data object SealedInterfaceObj : SealedInterface
}

@ReduxFeature(GamesViewModel::class, "GamesViewModelClass")
sealed class SealedClass : Action {
    class SealedClassClass(val id: Int) : SealedClass()
    data object SealedClassObj : SealedClass()
}
