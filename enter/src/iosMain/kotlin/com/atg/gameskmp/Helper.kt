package com.atg.gameskmp

import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

//class HelperKt {

// example
class ClientHelper : KoinComponent {
//    private val client: HttpClient by inject()
//    fun client(): HttpClient = client
}

fun initKoin() {
    // start Koin
    startKoin {
        modules(appModule())
    }
}
//}