package com.atg.games.di

import com.atg.games.GameRepository
import com.atg.games.GameRepositoryImpl
import com.atg.network.di.NetworkModule
import org.koin.dsl.module
import org.koin.ksp.generated.module


val gamesDataModule = module {
    includes(NetworkModule().module)
    single<GameRepository> { GameRepositoryImpl(get()) }
}
