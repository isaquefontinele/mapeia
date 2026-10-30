package io.github.isaquefontinele.mapeia.data

import app.cash.sqldelight.db.SqlDriver
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/** Creates an empty in-memory database with [MapeiaDatabase.Schema] applied. */
expect fun createInMemoryDriver(): SqlDriver

class SettingQueriesTest {
    private lateinit var driver: SqlDriver
    private lateinit var queries: SettingQueries

    @BeforeTest
    fun setUp() {
        driver = createInMemoryDriver()
        queries = MapeiaDatabase(driver).settingQueries
    }

    @AfterTest
    fun tearDown() {
        driver.close()
    }

    @Test
    fun missingKeyReturnsNull() {
        assertNull(queries.selectValue("osm_server").executeAsOneOrNull())
    }

    @Test
    fun upsertInsertsAndReplacesValue() {
        queries.upsert("osm_server", "dev")
        queries.upsert("osm_server", "production")

        assertEquals("production", queries.selectValue("osm_server").executeAsOne())
    }

    @Test
    fun deleteRemovesValue() {
        queries.upsert("osm_server", "dev")
        queries.delete("osm_server")

        assertNull(queries.selectValue("osm_server").executeAsOneOrNull())
    }
}
