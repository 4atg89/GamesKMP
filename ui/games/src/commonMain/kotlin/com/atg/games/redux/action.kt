package com.atg.games.redux

import com.atg.base.SideEffect
import com.atg.common.Action
import com.atg.games.model.ShortGame

//todo think up something on replace to reflection (not obvious)
sealed interface GamesAction: Action {

    data object LoadAction : GamesAction
    class LoadedAction(val games: List<ShortGame>) : GamesAction
    class OpenGameAction(val id: Int) : GamesAction

    sealed interface GamesSideEffect : SideEffect
    class OpenGameCommand(val id: Int) : GamesSideEffect
}
