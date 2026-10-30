package io.github.isaquefontinele.mapeia

import io.github.isaquefontinele.mapeia.di.sharedModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/** Starts Koin with every app module. Called once by each platform host at startup. */
fun initKoin(appDeclaration: KoinAppDeclaration) {
    startKoin {
        appDeclaration()
        modules(sharedModule)
    }
}

/** No-arg variant for Swift, which cannot use Kotlin default arguments. */
fun initKoin() = initKoin {}
