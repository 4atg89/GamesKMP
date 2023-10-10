package com.atg.common

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Feature(val feature: KClass<out Action>)


class BindFeature(val feature: KClass<out Action>) : Action
class UnbindFeature(val feature: KClass<out Action>) : Action

