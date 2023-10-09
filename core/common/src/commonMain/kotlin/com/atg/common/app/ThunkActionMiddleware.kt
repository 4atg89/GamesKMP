package com.atg.common.app

import com.atg.common.Action
import com.atg.common.Dispatcher
import com.atg.common.RepositoryProvider
import com.atg.common.Store
import com.atg.common.ThunkAction
import com.atg.common.ThunkMiddleware
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob

class ThunkActionMiddleware(private val provider: RepositoryProvider) : ThunkMiddleware<Action, Store<Action, AppState>> {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    override val scope = CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    override fun dispatch(action: Action, store: Store<Action, AppState>, next: Dispatcher<Action, Store<Action, AppState>>): Action {
        if (action is ThunkAction) action.execute(scope, store, provider)
        return next.dispatch(action, store)
    }
}