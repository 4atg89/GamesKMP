package com.atg.network

object NetworkContract {
    private const val API = "/api"

    object Query {
        const val ID = "id"
    }
    object Games {
        const val GAMES = "$API/games"
        const val GAME = "$API/game"
    }

}
