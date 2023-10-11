package com.atg.games.redux

import com.atg.common.alias.NextDispatcher
import com.atg.common.Action
import com.atg.common.Store
import com.atg.common.ThunkMiddleware
import com.atg.common.alias.TheStore
import com.atg.common.app.AppState
import com.atg.games.GameRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class GamesMiddleware(
    private val repository: GameRepository
) : ThunkMiddleware<Action, Store<Action, AppState>> {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // todo redo
        throwable.printStackTrace()
    }
    override val scope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    override fun dispatch(action: Action, store: TheStore, next: NextDispatcher): Action = when (action) {
        is GamesAction.LoadAction -> next.dispatch(action.fetchGames(store), store)
        is GamesAction.OpenGameAction -> next.dispatch(action.openGame(store), store)
        else -> next.dispatch(action, store)
    }

    private fun GamesAction.LoadAction.fetchGames(store: Store<Action, AppState>) = apply {
        scope.launch { store.dispatch(GamesAction.LoadedAction(repository.games())) }
            .invokeOnCompletion { it?.let { } }
    }

    private fun GamesAction.OpenGameAction.openGame(store: Store<Action, AppState>) = apply {
        store.dispatch(GamesAction.OpenGameCommand(id))
    }
}