package com.atg.games.di

import com.atg.games.GamesViewModel
import org.koin.dsl.module

val gameUiModule = module {
    factory { GamesViewModel(get()) }
}