package com.atg.gameskmp.reducer

import com.atg.common.Action
import com.atg.common.Reducer
import com.atg.common.Route
import com.atg.common.app.AppReducerFactory
import com.atg.common.app.AppState
import org.koin.core.Koin
import org.koin.core.qualifier.named
import kotlin.reflect.KClass

class KoinReducerFactory(private val koin: Koin) : AppReducerFactory<Action, AppState> {

    override fun createForFeature(route: KClass<out Route>): Reducer<Action, AppState>? =
        koin.getOrNull<List<Reducer<Action, AppState>>>(named(route.simpleName + Reducer::class.simpleName))?.firstOrNull()
}