package com.atg.base

import com.atg.common.Action
import com.atg.common.PreReducibleAction
import com.atg.common.app.AppState

interface SideEffect : Action

class ConsumableEffect<T : SideEffect>(
    val sideEffect: T?,
    val consumed: () -> Unit
) : SideEffect

class ConsumeEffect<T : SideEffect>(private val sideEffect: T) : SideEffect, PreReducibleAction {
    override fun reduce(state: AppState): AppState {
        return state.copy(sideEffect = state.sideEffect - sideEffect)
    }
}