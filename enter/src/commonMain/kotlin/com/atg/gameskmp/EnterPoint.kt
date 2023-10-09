package com.atg.gameskmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.atg.common.app.AppCompositeReducer
import com.atg.common.app.AppStore
import com.atg.details.GameDetails
import com.atg.games.GamesList
import com.atg.games.GamesViewModel
import com.atg.games.di.gameUiModule
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
            LaunchedEffect(Unit ){
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
                    GamesList(viewModel) { navigator.navigate("/details/$it") }
                }
                scene("/details/{id}") { entry ->
                    GameDetails(entry.path<Int>("id")!!, navigator)
                }
            }
        }
    }
    }
}