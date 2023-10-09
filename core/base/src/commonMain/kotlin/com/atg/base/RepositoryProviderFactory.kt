package com.atg.base

import com.atg.common.RepositoryProvider
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import kotlin.reflect.KClass

class RepositoryProviderFactory(private val koin: Koin) : RepositoryProvider, KoinComponent {
    override fun provide(clazz: KClass<*>): Any =
        koin.get(clazz)

}