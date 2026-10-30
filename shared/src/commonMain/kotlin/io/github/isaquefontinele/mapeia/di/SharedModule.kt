package io.github.isaquefontinele.mapeia.di

import io.github.isaquefontinele.mapeia.data.DatabaseDriverFactory
import io.github.isaquefontinele.mapeia.data.MapeiaDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

/** Bindings that only exist on a given platform (SQLite driver, Android context, ...). */
internal expect val platformModule: Module

val sharedModule: Module = module {
    includes(platformModule)
    single { MapeiaDatabase(get<DatabaseDriverFactory>().create()) }
}
