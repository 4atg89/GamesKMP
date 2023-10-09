package com.atg.common.flow

import com.atg.common.Action
import com.atg.common.app.AppState
import com.atg.common.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

inline fun <reified T : State> Flow<AppState>.typedState(): Flow<T> =
    map { it.states.find { state -> state is T } as? T }
        .filterNotNull()
        .distinctUntilChanged()

inline fun <reified T : Action> Flow<AppState>.sideEffects(): Flow<List<T>> =
    map { it.sideEffect.filterIsInstance<T>() }
        .distinctUntilChanged()