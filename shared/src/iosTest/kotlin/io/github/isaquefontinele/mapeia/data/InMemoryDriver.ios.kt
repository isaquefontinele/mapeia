package io.github.isaquefontinele.mapeia.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver

actual fun createInMemoryDriver(): SqlDriver = inMemoryDriver(MapeiaDatabase.Schema)
