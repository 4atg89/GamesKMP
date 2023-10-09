package com.atg.gameskmp

import com.atg.base.RepositoryProviderFactory
import com.atg.common.Action
import com.atg.common.RepositoryProvider
import com.atg.common.Store
import com.atg.common.app.AppCompositeMiddleware
import com.atg.common.app.AppCompositeReducer
import com.atg.common.app.AppMiddlewareFactory
import com.atg.common.app.AppReducerFactory
import com.atg.common.app.AppState
import com.atg.common.app.AppStore
import com.atg.common.app.ThunkActionMiddleware
import com.atg.games.di.gameUiModule
import com.atg.games.di.gamesDataModule
import com.atg.gameskmp.middleware.KoinMiddlewareFactory
import com.atg.gameskmp.reducer.KoinReducerFactory
import org.koin.dsl.module

fun appModule() = module {
    includes(gamesDataModule/*, gameUiModule*/)
    single { AppCompositeMiddleware(get()) }
    single { AppCompositeReducer(get()) }
    single { KoinMiddlewareFactory(getKoin()) as AppMiddlewareFactory<Action, Store<Action, AppState>> }
    single { KoinReducerFactory(getKoin()) as AppReducerFactory<Action, AppState> }
    single { AppStore(get<AppCompositeMiddleware>(), get<AppCompositeReducer>()) }
    single<RepositoryProvider> { RepositoryProviderFactory(get()) }
    single { ThunkActionMiddleware(get()) }
}