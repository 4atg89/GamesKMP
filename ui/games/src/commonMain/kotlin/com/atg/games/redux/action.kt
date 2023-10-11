package com.atg.games.redux

import com.atg.common.Action
import com.atg.games.model.ShortGame


sealed interface GamesAction : Action {
    data object LoadAction : GamesAction
    class LoadedAction(val games: List<ShortGame>) : GamesAction

}