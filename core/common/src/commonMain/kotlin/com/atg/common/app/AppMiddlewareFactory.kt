package com.atg.common.app

import com.atg.common.Middleware
import com.atg.common.Route
import kotlin.reflect.KClass

interface AppMiddlewareFactory<A, S> {
    fun createForFeature(action: KClass<out Route>): List<Middleware<A, S>>
}
