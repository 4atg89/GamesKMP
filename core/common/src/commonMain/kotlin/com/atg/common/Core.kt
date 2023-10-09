package com.atg.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

fun interface Reducer<A, S> {
    fun reduce(action: A, state: S): S

}

//todo check maybe it's not required
interface State


fun interface Middleware<A, S> {
    fun dispatch(action: A, store: S, next: Dispatcher<A, S>): A
}

// todo REDUX REDO 3 redo somehow to not have scope here
interface ThunkMiddleware<A, S>: Middleware<A, S> {
    val scope: CoroutineScope
}

fun interface Dispatcher<A, S> {
    fun dispatch(action: A, store: S): A
}

interface StateProvider<S> {
    val state: S

    fun hotState(): Flow<S>
}


interface Store<A, S> : StateProvider<S> {
    fun dispatch(vararg actions: A)

}