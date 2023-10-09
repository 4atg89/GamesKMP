package com.atg.common

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Feature(val feature: KClass<out Route>)

interface Route

class BindFeature(val feature: KClass<out Route>) : Action
class UnbindFeature(val feature: KClass<out Route>) : Action

