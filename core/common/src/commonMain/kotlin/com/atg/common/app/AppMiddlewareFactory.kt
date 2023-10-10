package com.atg.common.app

import com.atg.common.Middleware

interface AppMiddlewareFactory<A, S> {
    fun createForFeature(action: String): List<Middleware<A, S>>
}
