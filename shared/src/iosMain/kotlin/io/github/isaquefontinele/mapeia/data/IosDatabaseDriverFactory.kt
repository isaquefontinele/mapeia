package io.github.isaquefontinele.mapeia.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

internal class IosDatabaseDriverFactory : DatabaseDriverFactory {
    override fun create(): SqlDriver = NativeSqliteDriver(MapeiaDatabase.Schema, DATABASE_NAME)
}
