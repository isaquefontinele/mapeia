package io.github.isaquefontinele.mapeia.data

import app.cash.sqldelight.db.SqlDriver

internal const val DATABASE_NAME = "mapeia.db"

/** Creates the platform-specific SQLite driver backing [MapeiaDatabase]. */
interface DatabaseDriverFactory {
    fun create(): SqlDriver
}
