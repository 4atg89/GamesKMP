package com.atg.gameskmp

import com.atg.games.di.gamesDataModule
import org.koin.dsl.module

fun appModule() = module {
    includes(gamesDataModule)
}