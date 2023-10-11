//package com.atg.base.flow
//
//import androidx.compose.runtime.MutableState
//import com.atg.base.ConsumableEffect
//import com.atg.base.ConsumeEffect
//import com.atg.base.SideEffect
//import com.atg.common.State
//import com.atg.common.app.AppState
//import com.atg.common.app.AppStore
//import com.atg.common.flow.sideEffects
//import com.atg.common.flow.typedState
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.filter
//import kotlinx.coroutines.flow.filterNotNull
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.onEach
//import moe.tlaster.precompose.viewmodel.ViewModel
//import moe.tlaster.precompose.viewmodel.viewModelScope
//
//context (ViewModel)
//inline fun <reified T : State> Flow<AppState>.bind(crossinline each: (T) -> Unit) =
//    typedState<T>()
//        .onEach { each(it) }
//        .launchIn(viewModelScope)
//
//context (ViewModel)
//inline fun <reified T : State, reified R> Flow<AppState>.bind(props: MutableState<R>, crossinline transform: (T) -> R) =
//    typedState<T>()
//        .onEach { props.value = transform(it) }
//        .launchIn(viewModelScope)
//
//context (ViewModel)
//inline fun <reified T : State, reified R> Flow<AppState>.bindNotTestedYet(props: MutableState<R>, crossinline transform: (T) -> R) =
//    bind<T> { props.value = transform(it) }
//
//context (ViewModel)
//inline fun <reified T : SideEffect> AppStore.sideEffect(crossinline each: (ConsumableEffect<T>) -> Unit) =
//    hotState().sideEffects<T>()
//        .map { it.firstOrNull() }
//        .map { ConsumableEffect(it) { it?.let { dispatch(ConsumeEffect(it)) } } }
//        .onEach { each(it) }
//        .launchIn(viewModelScope)