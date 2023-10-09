package com.atg.common.app

import com.atg.common.Action
import com.atg.common.BindFeature
import com.atg.common.Dispatcher
import com.atg.common.Feature
import com.atg.common.Middleware
import com.atg.common.Route
import com.atg.common.Store
import kotlin.reflect.KClass
//import kotlin.reflect.full.findAnnotation

private typealias FoldOperation = (Int, Middleware<Action, Store<Action, AppState>>, Dispatcher<Action, Store<Action, AppState>>) -> Dispatcher<Action, Store<Action, AppState>>

class AppCompositeMiddleware(middlewareFactory: AppMiddlewareFactory<Action, Store<Action, AppState>>) : Middleware<Action, Store<Action, AppState>> {

    private val nodeHolder = DispatcherHolder(middlewareFactory)
    override fun dispatch(action: Action, store: Store<Action, AppState>, next: Dispatcher<Action, Store<Action, AppState>>): Action = when (action) {
        is BindFeature -> next.dispatch(action.addFeature(next), store)
        else -> middlewares(action, store, next)
    }

    private fun BindFeature.addFeature(next: Dispatcher<Action, Store<Action, AppState>>): Action = apply {
        nodeHolder.bind(this, next)
    }

//    private fun UnbindFeature.removeFeature(): Action = apply {
//        middleware.remove(action)?.filterIsInstance<ThunkMiddleware<*,*>>()?.forEach { it.scope.cancel() }
//        println("feature Middleware $action is removed, middleware -> $middleware")
//    }

    private fun middlewares(action: Action, store: Store<Action, AppState>, next: Dispatcher<Action, Store<Action, AppState>>): Action =
        next.dispatch(action, store)
//        nodeHolder[action]?.dispatch(action, store) ?: next.dispatch(action, store)

}

private class DispatcherHolder(private val middlewareFactory: AppMiddlewareFactory<Action, Store<Action, AppState>>) {

    private val nodes = HashMap<KClass<out Route>, Dispatcher<Action, Store<Action, AppState>>>()

//    operator fun get(key: Action): Dispatcher<Action, Store<Action, AppState>>? {
//        key::class.supertypes.forEach {
//            val kClass = it.classifier as? KClass<*> ?: return@forEach
//            val feature = kClass.findAnnotation<Feature>()?.feature
//            if (feature != null) return nodes[feature]
//        }
//        return null
//    }

    fun bind(action: BindFeature, next: Dispatcher<Action, Store<Action, AppState>>) =
        nodes.getOrPut(action.feature) { action.mapFeature(next) }

    private fun BindFeature.mapFeature(next: Dispatcher<Action, Store<Action, AppState>>): Dispatcher<Action, Store<Action, AppState>> {
        val middlewares = middlewareFactory.createForFeature(feature)
        if (middlewares.isEmpty()) return AppDispatcherNode({ action, store, nextD -> nextD.dispatch(action, store) }, next)
        val operation: FoldOperation = { index, middleware, node -> if (index == middlewares.lastIndex) node else AppDispatcherNode(middleware, node) }
        return middlewares.foldRightIndexed(AppDispatcherNode(middlewares.last(), next), operation)
    }

}