package com.atg.common.app

import com.atg.common.Action
import com.atg.common.State

data class AppState(val states: Set<State> = emptySet(), val sideEffect: List<Action> = emptyList()) : State
