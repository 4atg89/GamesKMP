package com.atg.games.redux

import com.atg.base.ConsumableEffect
import com.atg.common.State
import com.atg.games.model.ShortGame

data class GamesState(val games: List<ShortGame> = emptyList()) : State

data class GamesProps(
    val games: List<ShortGame> = emptyList(),
    val openGameCommand: (Int) -> Unit = {},
)
data class GamesCommandProps(
    val sideEffect: ConsumableEffect<GamesAction.OpenGameCommand>? = null
)