package com.atg.games.redux

import com.atg.common.Action
import com.atg.common.Reducer
import com.atg.common.app.AppState
import com.atg.common.subState

class GamesReducer: Reducer<Action, AppState> {
    override fun reduce(action: Action, state: AppState) = when (action) {
        is GamesAction.LoadedAction -> action.reduce(state)
        is GamesAction.OpenGameCommand -> action.reduce(state)
        else -> state
    }

    private fun GamesAction.LoadedAction.reduce(state: AppState): AppState {
        val oldSubState = requireNotNull(state.subState<GamesState>()) { "the ${GamesState::class} is null in ${this::class}" }
        val newSubState = oldSubState.copy(games = games)
        val states = state.states - oldSubState + newSubState
        return state.copy(states = states)
    }

    private fun GamesAction.OpenGameCommand.reduce(state: AppState): AppState =
        state.copy(sideEffect = state.sideEffect + this)
}