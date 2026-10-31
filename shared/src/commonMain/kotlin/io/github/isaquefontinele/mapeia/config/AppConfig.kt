package io.github.isaquefontinele.mapeia.config

/** Build-time configuration resolved for the running build type. */
data class AppConfig(
    val osmServer: OsmServer,
    /** OAuth 2.0 client id registered on [osmServer]. Empty when not configured. */
    val oauthClientId: String,
    val overpassApiUrl: String,
    val mapStyleUrl: String,
) {
    companion object {
        fun create(isDebugBuild: Boolean): AppConfig = create(
            isDebugBuild = isDebugBuild,
            osmServerSetting = BuildSecrets.OSM_SERVER,
            devClientId = BuildSecrets.OSM_DEV_OAUTH_CLIENT_ID,
            productionClientId = BuildSecrets.OSM_PRODUCTION_OAUTH_CLIENT_ID,
            overpassApiUrl = BuildSecrets.OVERPASS_API_URL,
            mapStyleUrl = BuildSecrets.MAP_STYLE_URL,
        )

        internal fun create(
            isDebugBuild: Boolean,
            osmServerSetting: String,
            devClientId: String,
            productionClientId: String,
            overpassApiUrl: String,
            mapStyleUrl: String,
        ): AppConfig {
            val server = OsmServer.resolve(osmServerSetting, isDebugBuild)
            return AppConfig(
                osmServer = server,
                oauthClientId = when (server) {
                    OsmServer.DEVELOPMENT -> devClientId
                    OsmServer.PRODUCTION -> productionClientId
                },
                overpassApiUrl = overpassApiUrl,
                mapStyleUrl = mapStyleUrl,
            )
        }
    }
}
