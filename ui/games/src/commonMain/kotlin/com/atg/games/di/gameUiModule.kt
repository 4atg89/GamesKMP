package com.atg.games.di

import com.atg.common.Middleware
import com.atg.common.Reducer
import com.atg.games.GamesViewModel
import com.atg.games.redux.GamesAction
import com.atg.games.redux.GamesMiddleware
import com.atg.games.redux.GamesReducer
import org.koin.core.qualifier.named
import org.koin.dsl.module

val gameUiModule = module {
    factory(named(GamesAction::class.qualifiedName!! + Middleware::class.simpleName)) { listOf(GamesMiddleware(get())) }
    factory(named(GamesAction::class.qualifiedName!! + Reducer::class.simpleName)) { listOf(GamesReducer()) }
    factory { GamesViewModel(get()) }
}