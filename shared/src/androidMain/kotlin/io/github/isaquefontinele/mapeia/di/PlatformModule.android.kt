package io.github.isaquefontinele.mapeia.di

import io.github.isaquefontinele.mapeia.data.AndroidDatabaseDriverFactory
import io.github.isaquefontinele.mapeia.data.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val platformModule: Module = module {
    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(get()) }
}
