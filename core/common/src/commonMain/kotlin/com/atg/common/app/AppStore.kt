package com.atg.common.app

import com.atg.common.Action
import com.atg.common.Dispatcher
import com.atg.common.Middleware
import com.atg.common.Reducer
import com.atg.common.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AppStore(
    private val middleware: Middleware<Action, Store<Action, AppState>>,
    private val reducer: Reducer<Action, AppState>
) : Store<Action, AppState> {

    private val theState = MutableStateFlow(AppState())

    private val theLastChain = Dispatcher<Action, Store<Action, AppState>> { action, _ ->
        theState.value = reducer.reduce(action, state); action.apply { println("AppStore -> ${this@AppStore} and state -> ${theState.value}") }
    }

    override fun dispatch(vararg actions: Action) {
        actions.forEach {
            println("feature Middleware -> ${it::class.simpleName}")
            middleware.dispatch(it, this, theLastChain)
        }
    }

    override val state: AppState
        get() = theState.value

    override fun hotState(): Flow<AppState> =
        theState

}