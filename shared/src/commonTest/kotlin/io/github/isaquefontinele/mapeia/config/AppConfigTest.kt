package io.github.isaquefontinele.mapeia.config

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AppConfigTest {
    private fun config(isDebugBuild: Boolean, osmServer: String = "") = AppConfig.create(
        isDebugBuild = isDebugBuild,
        osmServerSetting = osmServer,
        devClientId = "dev-id",
        productionClientId = "prod-id",
        overpassApiUrl = "https://overpass.example/api/interpreter",
        mapStyleUrl = "https://tiles.example/style.json",
    )

    @Test
    fun debugBuildsDefaultToDevelopmentServer() {
        val config = config(isDebugBuild = true)

        assertEquals(OsmServer.DEVELOPMENT, config.osmServer)
        assertEquals("dev-id", config.oauthClientId)
    }

    @Test
    fun releaseBuildsDefaultToProductionServer() {
        val config = config(isDebugBuild = false)

        assertEquals(OsmServer.PRODUCTION, config.osmServer)
        assertEquals("prod-id", config.oauthClientId)
    }

    @Test
    fun settingOverridesBuildType() {
        assertEquals(OsmServer.PRODUCTION, config(isDebugBuild = true, osmServer = "production").osmServer)
        assertEquals(OsmServer.DEVELOPMENT, config(isDebugBuild = false, osmServer = " Dev ").osmServer)
    }

    @Test
    fun unknownSettingIsRejected() {
        assertFailsWith<IllegalArgumentException> { OsmServer.resolve("staging", isDebugBuild = true) }
    }

    @Test
    fun urlsArePassedThrough() {
        val config = config(isDebugBuild = true)

        assertEquals("https://overpass.example/api/interpreter", config.overpassApiUrl)
        assertEquals("https://tiles.example/style.json", config.mapStyleUrl)
    }
}
