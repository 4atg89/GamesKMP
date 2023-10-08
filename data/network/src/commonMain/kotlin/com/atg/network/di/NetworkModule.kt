package com.atg.network.di

import com.atg.network.GameService
import com.atg.network.GameServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan(value = "com.atg.network.di")
class NetworkModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Single
    fun kJson(): Json = Json {
        explicitNulls = false
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Single
    fun ktorClient(json: Json) = HttpClient {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 20000
            requestTimeoutMillis = 20000
        }
        install(ContentNegotiation) { json(json) }
        defaultRequest { url("https://www.freetogame.com") }
    }

    @Single
    fun gameService(ktor: HttpClient) : GameService = GameServiceImpl(ktor)
}