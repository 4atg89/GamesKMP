package com.atg.common.app

import com.atg.common.Reducer

interface AppReducerFactory<A, S> {
    fun createForFeature(action: String): Reducer<A, S>?
}
