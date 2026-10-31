import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.sqldelight)
}

// Values from the untracked secrets.properties, with defaults so the build (and CI)
// works without the file. See secrets.properties.example.
val buildSecrets: Provider<Map<String, String>> = providers
    .fileContents(rootProject.layout.projectDirectory.file("secrets.properties"))
    .asText
    .orElse("")
    .map { text ->
        // Defaults live inside the lambda so it captures no script state (configuration cache).
        val defaults = mapOf(
            "OSM_SERVER" to "",
            "OSM_DEV_OAUTH_CLIENT_ID" to "",
            "OSM_PRODUCTION_OAUTH_CLIENT_ID" to "",
            "OVERPASS_API_URL" to "https://overpass-api.de/api/interpreter",
            "MAP_STYLE_URL" to "https://tiles.openfreemap.org/styles/liberty",
        )
        val properties = Properties().apply { load(text.reader()) }
        defaults.mapValues { (key, default) ->
            properties.getProperty(key)?.trim()?.takeIf { it.isNotEmpty() } ?: default
        }
    }

val generateBuildSecrets = tasks.register("generateBuildSecrets") {
    val values = buildSecrets
    val outputDir = layout.buildDirectory.dir("generated/buildSecrets/commonMain/kotlin")
    inputs.property("values", values)
    outputs.dir(outputDir)

    doLast {
        val server = values.get().getValue("OSM_SERVER").lowercase()
        require(server in setOf("", "dev", "development", "prod", "production")) {
            "OSM_SERVER in secrets.properties must be 'dev' or 'production', was '$server'"
        }
        val constants = values.get().entries.joinToString("\n") { (key, value) ->
            val escaped = value.replace("\\", "\\\\").replace("\"", "\\\"").replace("$", "\\$")
            "    const val $key: String = \"$escaped\""
        }
        val file = outputDir.get().file("io/github/isaquefontinele/mapeia/config/BuildSecrets.kt").asFile
        file.parentFile.mkdirs()
        file.writeText(
            """
            |// Generated from secrets.properties by :shared:generateBuildSecrets. Do not edit.
            |package io.github.isaquefontinele.mapeia.config
            |
            |internal object BuildSecrets {
            |$constants
            |}
            |""".trimMargin(),
        )
    }
}

kotlin {
    android {
        namespace = "io.github.isaquefontinele.mapeia.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTest {}
    }

    iosArm64()
    iosSimulatorArm64()

    jvmToolchain(17)

    sourceSets {
        commonMain {
            kotlin.srcDir(generateBuildSecrets)
        }
        commonMain.dependencies {
            api(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.sqldelight.coroutines.extensions)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }
        getByName("androidHostTest").dependencies {
            implementation(libs.sqldelight.sqlite.driver)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
    }
}

sqldelight {
    databases {
        create("MapeiaDatabase") {
            packageName.set("io.github.isaquefontinele.mapeia.data")
        }
    }
}
