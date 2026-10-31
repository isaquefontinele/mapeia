package io.github.isaquefontinele.mapeia.di

import io.github.isaquefontinele.mapeia.config.AppConfig
import io.github.isaquefontinele.mapeia.config.BuildInfo
import io.github.isaquefontinele.mapeia.data.DatabaseDriverFactory
import io.github.isaquefontinele.mapeia.data.MapeiaDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

/** Bindings that only exist on a given platform (SQLite driver, [BuildInfo], ...). */
internal expect val platformModule: Module

val sharedModule: Module = module {
    includes(platformModule)
    single { AppConfig.create(get<BuildInfo>().isDebugBuild) }
    single { MapeiaDatabase(get<DatabaseDriverFactory>().create()) }
}
