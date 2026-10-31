# CLAUDE.md

Guidance for Claude Code (and other contributors) working in this repository.

## Project

Mapeia is an open-source (MIT) Kotlin Multiplatform app for Android and iOS that turns
OpenStreetMap contributions into quick "quests", focused on accessibility and
common-interest places. See [README.md](README.md), [ROADMAP.md](ROADMAP.md) and
[docs/REQUIREMENTS.md](docs/REQUIREMENTS.md).

Hard constraints from the requirements:

- **No project server** (NFR-01). The app talks only to the OSM API, Overpass and the
  configured tile source.
- **No analytics or trackers** (NFR-02). Location never leaves the device except as a
  requested bounding box.
- **No secrets in the repo** (NFR-07). Only `secrets.properties.example` is tracked; the
  real `secrets.properties` is gitignored and must never be committed.
- Debug builds default to the OSM **development** server (FR-10).
- Shared code should be ≥ 80% of Kotlin code; the quest engine must be fully unit-tested
  (NFR-05).

## Stack

- Kotlin Multiplatform (Kotlin 2.4), targets: Android, `iosArm64`, `iosSimulatorArm64`
- Compose Multiplatform for UI (Material 3)
- Koin for DI, SQLDelight for local storage
- Planned: MapLibre Compose (maps), Ktor (OSM API v0.6, Overpass),
  kotlinx.serialization, OAuth 2.0 + PKCE, Turbine for flow tests
- Build: Gradle 9 (wrapper), AGP 9, version catalog in `gradle/libs.versions.toml`
- JDK 17, Android Studio, Xcode 16+ for iOS

## Module layout

```
androidApp/   Thin Android host (Application + Activity). Starts Koin, shows App().
iosApp/       Thin Xcode host (SwiftUI). Starts Koin, embeds MainViewController().
composeApp/   Compose Multiplatform UI shared by both hosts (KMP library).
              Also builds the `ComposeApp` iOS framework consumed by iosApp.
shared/       Non-UI shared code: data (SQLDelight), DI modules, and later
              OSM clients, quest engine and map state.
```

Dependency direction: `androidApp` / `iosApp` → `composeApp` → `shared`.
`shared` must never depend on Compose.

AGP 9 does not allow `com.android.application` in a KMP module, so the KMP modules use
`com.android.kotlin.multiplatform.library` and the Android app lives in `androidApp/`.

Base package: `io.github.isaquefontinele.mapeia`.

## Build and test

```bash
./gradlew :androidApp:assembleDebug            # Android debug APK
./gradlew :androidApp:installDebug             # install on a device/emulator
./gradlew :shared:testAndroidHostTest          # shared tests on the JVM (fast)
./gradlew :shared:iosSimulatorArm64Test        # shared tests on the iOS simulator
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64   # iOS framework only
open iosApp/iosApp.xcodeproj                   # run the iOS app from Xcode
```

The Xcode project calls `./gradlew :composeApp:embedAndSignAppleFrameworkForXcode` in a
build phase, so building in Xcode also builds the Kotlin framework.

A `local.properties` with `sdk.dir=...` (gitignored) is needed if `ANDROID_HOME` is not set.

## Configuration and secrets

`:shared:generateBuildSecrets` reads the untracked `secrets.properties` (template:
`secrets.properties.example`) and generates the internal `BuildSecrets` object in
`shared/build/generated/`. Every key has a default, so the build and CI work without the
file; never make a key mandatory. App code reads configuration through `AppConfig`
(injected by Koin), which picks the `OsmServer`: development for debug builds,
production for release builds, unless `OSM_SERVER` overrides it. New keys go in the
generator's defaults map, `secrets.properties.example` and `AppConfig`.

## Conventions

- Code, comments, docs, commit messages and issues are in **English**.
- Shared logic goes in `commonMain`; use `expect`/`actual` or Koin platform modules
  (`platformModule`) only for genuinely platform-specific pieces.
- Tests use `kotlin.test` in `commonTest`. Platform test helpers live in
  `androidHostTest` / `iosTest`.
- SQLDelight schemas live in `shared/src/commonMain/sqldelight/`; the generated database is
  `io.github.isaquefontinele.mapeia.data.MapeiaDatabase`.
- Add dependencies through the version catalog, never with inline coordinates.
- Keep commits small and focused; reference the GitHub issue (`Closes #N`) in the commit
  that finishes it.
- Respect OSM API and Overpass usage policies (rate limits, User-Agent, caching).
