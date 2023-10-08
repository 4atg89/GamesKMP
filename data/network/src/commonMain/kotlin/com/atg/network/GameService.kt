package com.atg.network

import com.atg.network.model.GameDTO
import com.atg.network.model.ShortGameDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface GameService {
    suspend fun games(): List<ShortGameDTO>
    suspend fun game(id: Int): GameDTO
}

internal class GameServiceImpl(private val ktor: HttpClient) : GameService {
    override suspend fun games(): List<ShortGameDTO> =
        ktor.get(NetworkContract.Games.GAMES).body()

    override suspend fun game(id: Int): GameDTO =
        ktor.get(NetworkContract.Games.GAME) {
            url { parameters.append(NetworkContract.Query.ID, id.toString()) }
        }.body()
}