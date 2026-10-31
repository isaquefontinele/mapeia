package io.github.isaquefontinele.mapeia.config

/** OpenStreetMap servers the app can edit against. */
enum class OsmServer(
    /** Base URL of the OSM API v0.6 (`$apiBaseUrl/api/0.6/...`). */
    val apiBaseUrl: String,
    /** Base URL of the website, which also hosts the OAuth 2.0 endpoints. */
    val webBaseUrl: String,
) {
    DEVELOPMENT(
        apiBaseUrl = "https://master.apis.dev.openstreetmap.org",
        webBaseUrl = "https://master.apis.dev.openstreetmap.org",
    ),
    PRODUCTION(
        apiBaseUrl = "https://api.openstreetmap.org",
        webBaseUrl = "https://www.openstreetmap.org",
    ),
    ;

    companion object {
        /**
         * Resolves the server from the `OSM_SERVER` setting. A blank value means
         * "development for debug builds, production for release builds".
         */
        fun resolve(setting: String, isDebugBuild: Boolean): OsmServer =
            when (setting.trim().lowercase()) {
                "" -> if (isDebugBuild) DEVELOPMENT else PRODUCTION
                "dev", "development" -> DEVELOPMENT
                "prod", "production" -> PRODUCTION
                else -> throw IllegalArgumentException(
                    "Unknown OSM_SERVER '$setting'; expected 'dev' or 'production'",
                )
            }
    }
}
