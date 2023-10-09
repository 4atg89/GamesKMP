package com.atg.common

import com.atg.common.app.AppState


inline fun <reified T> AppState.subState() =
    states.filterIsInstance<T>().firstOrNull() as T