package com.atg.gameskmp.middleware

import com.atg.common.Action
import com.atg.common.Middleware
import com.atg.common.Route
import com.atg.common.Store
import com.atg.common.app.AppMiddlewareFactory
import com.atg.common.app.AppState
import org.koin.core.Koin
import org.koin.core.qualifier.named
import kotlin.reflect.KClass

class KoinMiddlewareFactory(private val koin: Koin) :
    AppMiddlewareFactory<Action, Store<Action, AppState>> {

    override fun createForFeature(action: KClass<out Route>): List<Middleware<Action, Store<Action, AppState>>> =
        koin.getOrNull(named(action.simpleName + Middleware::class.simpleName)) ?: emptyList()

}