package com.atg.gameskmp.reducer

import com.atg.common.Action
import com.atg.common.Reducer
import com.atg.common.app.AppReducerFactory
import com.atg.common.app.AppState
import org.koin.core.Koin
import org.koin.core.qualifier.named

class KoinReducerFactory(private val koin: Koin) : AppReducerFactory<Action, AppState> {

    override fun createForFeature(action: String): Reducer<Action, AppState>? =
        koin.getOrNull<List<Reducer<Action, AppState>>>(named(action + Reducer::class.simpleName))?.firstOrNull()
}