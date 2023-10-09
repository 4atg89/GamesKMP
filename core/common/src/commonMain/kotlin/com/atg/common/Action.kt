package com.atg.common

import com.atg.common.app.AppState
import kotlinx.coroutines.CoroutineScope

interface Action

interface PreReducibleAction : Action {
    fun reduce(state: AppState): AppState
}

interface PostReducibleAction : Action {
    fun reduce(state: AppState): AppState
}

interface ThunkAction : Action {
    fun execute(scope: CoroutineScope, store: Store<Action, AppState>, provider: RepositoryProvider)
}


class AddStateAction(private val newState: State) : PreReducibleAction {
    override fun reduce(state: AppState): AppState =
        state.copy(states = state.states + newState)

}

class RemoveStateAction(private val stateToRemove: State) : PostReducibleAction {

    override fun reduce(state: AppState): AppState =
        state.copy(states = state.states - stateToRemove)

}