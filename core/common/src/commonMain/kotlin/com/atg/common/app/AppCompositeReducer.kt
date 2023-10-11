package com.atg.common.app

import com.atg.common.Action
import com.atg.common.BindFeature
import com.atg.common.PostReducibleAction
import com.atg.common.PreReducibleAction
import com.atg.common.Reducer
import com.atg.common.UnbindFeature

class AppCompositeReducer(reducerFactory: AppReducerFactory<Action, AppState>) : Reducer<Action, AppState> {

    private val reducerHolder = ReducerHolder(reducerFactory)
    override fun reduce(action: Action, state: AppState): AppState {
        when (action) {
            is BindFeature -> action.addFeature()
            is UnbindFeature -> action.removeFeature()
        }
        return reducerHolder.state(action, state)
    }

    private fun BindFeature.addFeature(): Action = apply {
        reducerHolder.bind(this)
    }

    private fun UnbindFeature.removeFeature(): Action = apply {
        reducerHolder.unbind(this)
    }

    private fun ReducerHolder.state(action: Action, state: AppState): AppState = when (action) {
        is PreReducibleAction -> get(action)?.reduce(action, action.reduce(state)) ?: action.reduce(state)
        is PostReducibleAction -> action.reduce(get(action)?.reduce(action, state) ?: state)
        else -> get(action)?.reduce(action, state) ?: state
    }
}


private class ReducerHolder(private val reducerFactory: AppReducerFactory<Action, AppState>) {

    private val nodes = HashMap<String, Reducer<Action, AppState>?>()
    private val nodeCounter = HashMap<String, Int>()

    operator fun get(key: Action): Reducer<Action, AppState>? {
        val action = key::class.qualifiedName?.substringBeforeLast(".")
        return nodes[action]
    }

    fun bind(action: BindFeature) {
        val feature = action.feature.qualifiedName!!
        val counter = nodeCounter[feature] ?: 0
        nodes.getOrPut(feature) { reducerFactory.createForFeature(action.feature.qualifiedName!!) }
        nodeCounter[feature] = counter.inc()
    }
    fun unbind(action: UnbindFeature) {
        val feature = action.feature.qualifiedName!!
        val counter = nodeCounter[feature]?.dec() ?: throw IllegalStateException("there was no any bind for $action before unbind")
        if (counter == 0) {
            nodes.remove(feature)
            nodeCounter.remove(feature)
        }
        if (counter < 0) throw IllegalStateException("unbind was invoked frequently then bind $action before unbind")
    }

}