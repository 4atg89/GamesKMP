package com.atg.common

import kotlin.reflect.KClass

//todo redo not to allow to fetch everything or provide differenet way
interface RepositoryProvider {
    fun provide(clazz: KClass<*>): Any
}
