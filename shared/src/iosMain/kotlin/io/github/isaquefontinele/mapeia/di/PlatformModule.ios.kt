package io.github.isaquefontinele.mapeia.di

import io.github.isaquefontinele.mapeia.config.iosBuildInfo
import io.github.isaquefontinele.mapeia.data.DatabaseDriverFactory
import io.github.isaquefontinele.mapeia.data.IosDatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val platformModule: Module = module {
    single { iosBuildInfo() }
    single<DatabaseDriverFactory> { IosDatabaseDriverFactory() }
}
