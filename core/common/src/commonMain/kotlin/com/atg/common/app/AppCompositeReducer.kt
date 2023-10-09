package com.atg.common.app

import com.atg.common.Action
import com.atg.common.BindFeature
import com.atg.common.Feature
import com.atg.common.PostReducibleAction
import com.atg.common.PreReducibleAction
import com.atg.common.Reducer
import com.atg.common.Route
import com.atg.common.UnbindFeature
import kotlin.reflect.KClass
//import kotlin.reflect.full.findAnnotation

class AppCompositeReducer(reducerFactory: AppReducerFactory<Action, AppState>) : Reducer<Action, AppState> {

    private val reducerHolder = ReducerHolder(reducerFactory)
    override fun reduce(action: Action, state: AppState): AppState {
        when (action) {
            is BindFeature -> action.addFeature()
            is UnbindFeature -> action.removeFeature()
        }
//        return reducerHolder.state(action, state)
        return state
    }

    private fun BindFeature.addFeature(): Action = apply {
        reducerHolder.bind(this)
    }

    private fun UnbindFeature.removeFeature(): Action = apply {
        reducerHolder.unbind(this)
    }

//    private fun ReducerHolder.state(action: Action, state: AppState): AppState = when (action) {
//        is PreReducibleAction -> get(action)?.reduce(action, action.reduce(state)) ?: action.reduce(state)
//        is PostReducibleAction -> action.reduce(get(action)?.reduce(action, state) ?: state)
//        else -> get(action)?.reduce(action, state) ?: state
//    }
}


private class ReducerHolder(private val reducerFactory: AppReducerFactory<Action, AppState>) {

    private val nodes = HashMap<KClass<out Route>, Reducer<Action, AppState>?>()
    private val nodeCounter = HashMap<KClass<out Route>, Int>()

//    operator fun get(key: Action): Reducer<Action, AppState>? {
//        key::class.supertypes.forEach {
//            val kClass = it.classifier as? KClass<*> ?: return@forEach
//            val feature = kClass.findAnnotation<Feature>()?.feature
//            if (feature != null) return nodes[feature]
//        }
//        return null
//    }

    fun bind(action: BindFeature) {
        val counter = nodeCounter[action.feature] ?: 0
        nodes.getOrPut(action.feature) { reducerFactory.createForFeature(action.feature) }
        nodeCounter[action.feature] = counter.inc()
    }
    fun unbind(action: UnbindFeature) {
        val counter = nodeCounter[action.feature]?.dec() ?: throw IllegalStateException("there was no any bind for $action before unbind")
        if (counter == 0) {
            nodes.remove(action.feature)
            nodeCounter.remove(action.feature)
        }
        if (counter < 0) throw IllegalStateException("unbind was invoked frequently then bind $action before unbind")
    }

}