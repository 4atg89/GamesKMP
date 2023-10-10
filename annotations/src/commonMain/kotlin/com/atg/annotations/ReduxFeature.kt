package com.atg.annotations

import kotlin.reflect.KClass


@Target(AnnotationTarget.CLASS)
annotation class ReduxFeature(val clazz: KClass<*>, val name: String)