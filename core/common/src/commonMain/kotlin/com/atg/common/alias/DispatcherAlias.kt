package com.atg.common.alias

import com.atg.common.Action
import com.atg.common.Dispatcher
import com.atg.common.Store
import com.atg.common.app.AppState

typealias NextDispatcher = Dispatcher<Action, Store<Action, AppState>>