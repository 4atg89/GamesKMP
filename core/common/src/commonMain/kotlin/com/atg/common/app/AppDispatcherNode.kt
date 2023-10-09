package com.atg.common.app

import com.atg.common.Action
import com.atg.common.Dispatcher
import com.atg.common.Middleware
import com.atg.common.Store

data class AppDispatcherNode(
    private val middleware: Middleware<Action, Store<Action, AppState>>,
    private val next: Dispatcher<Action, Store<Action, AppState>> = Dispatcher { action, _ -> action }
) : Dispatcher<Action, Store<Action, AppState>> {

    override fun dispatch(action: Action, store: Store<Action, AppState>): Action {
        return middleware.dispatch(action, store, next)
    }
}
