package com.atg.common.app

import com.atg.common.Reducer
import com.atg.common.Route
import kotlin.reflect.KClass

interface AppReducerFactory<A, S> {
    fun createForFeature(route: KClass<out Route>): Reducer<A, S>?
}
