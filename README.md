# Mapeia

**Map your neighborhood, one quick question at a time.** Mapeia is a Kotlin Multiplatform app (Android + iOS) that turns OpenStreetMap contributions into small, answerable "quests" — *Is there a ramp at this entrance? Is this drinking fountain working? Is this bench covered?* — with a focus on **accessibility** and **points of common interest**.

> 🇧🇷 *App de missões para contribuir com o OpenStreetMap, com foco em acessibilidade e pontos de interesse comum (bebedouros, banheiros, bicicletários, rampas).*

> **Status:** early development. See [ROADMAP.md](ROADMAP.md).

## Why

OpenStreetMap is the open map that powers countless apps, but most of the world's accessibility information is still missing. Editing OSM with a full editor is intimidating. Quest-style apps solve this beautifully on Android (StreetComplete), but iOS users have few comparable options, and accessibility mapping is usually just one category among many.

Mapeia is:

- **Cross-platform from day one** — one shared Kotlin core for Android and iOS.
- **Accessibility-first** — wheelchair access, step-free entrances, tactile paving, accessible toilets, kerb heights.
- **Common-interest-first** — drinking water, public toilets, benches, shade, bicycle parking, recycling points.
- **Privacy-respecting** — there is **no Mapeia server**. You sign in with your own OpenStreetMap account; your answers go straight to OSM as normal map edits.

## Who it's for

- People who want to improve their city's map in a few minutes a day.
- Wheelchair users, parents with strollers, cyclists, and anyone who benefits from better accessibility data.
- Local OSM communities running mapping campaigns.

## How it works

1. Mapeia downloads OSM data for the area around you (Overpass API).
2. It finds features with missing or outdated tags and turns each gap into a quest.
3. You answer with one tap (Yes / No / options / photo-free).
4. Answers are queued **offline** and uploaded as an OSM changeset when you choose.
5. Everything you contribute is under OSM's open license (ODbL) and immediately usable by everyone.

## Quest examples

| Quest | OSM tag |
|---|---|
| Is this entrance step-free? | `wheelchair=*`, `entrance=*` |
| Is there a wheelchair-accessible toilet here? | `toilets:wheelchair=*` |
| Does this crossing have tactile paving? | `tactile_paving=*` |
| Is this drinking water point working? | `amenity=drinking_water` + `check_date` |
| Does this bench have a backrest? | `backrest=*` |
| How many bikes fit in this rack? | `capacity=*` |

## Built with

- **Kotlin Multiplatform** — shared domain, quest engine, OSM client, storage
- **Compose Multiplatform** — shared UI for Android and iOS
- **MapLibre Compose** — vector maps on Android and iOS
- **Ktor** — HTTP client (OSM API v0.6, Overpass API)
- **SQLDelight** — offline cache of downloaded data and pending edits
- **kotlinx.serialization**, **kotlinx.coroutines**, **Koin** (DI)
- OAuth 2.0 + PKCE for OpenStreetMap sign-in (public client, no secrets)
- Map tiles: configurable vector tile source (e.g. OpenFreeMap)
- CI: GitHub Actions; tests with `kotlin.test` and Turbine

## Architecture

```
composeApp/         Compose Multiplatform UI (Android + iOS)
iosApp/             Thin Xcode host app
shared/
  osm/              OSM API v0.6 + Overpass clients, OAuth2 PKCE
  quests/           Quest definitions, applicability rules, answer → tag changes
  data/             SQLDelight cache, edit queue, changeset uploader
  map/              Map state, tile config, feature layers
```

Each quest is a small declarative class: *which elements it applies to* (an Overpass-like filter), *the question*, *the answer type*, and *how an answer maps to tag changes*. Adding a quest should take well under an hour.

## Getting started

Requirements: JDK 17+, Android Studio, Xcode 16+ (for iOS, macOS only).

```bash
git clone https://github.com/isaquefontinele/mapeia.git
cd mapeia
cp secrets.properties.example secrets.properties   # fill in your own OSM OAuth client id
./gradlew :composeApp:installDebug                 # Android
open iosApp/iosApp.xcodeproj                       # iOS
```

To test without touching the real map, point the app to the **OSM development server** (`master.apis.dev.openstreetmap.org`) in `secrets.properties`. Development builds use it by default.

## Privacy

- No analytics, no Mapeia backend, no account other than your own OSM account.
- Your OSM edits are public by nature (that's how OSM works) and are tied to your OSM username.
- Location is used only on-device to find quests near you and is never sent anywhere except as the bounding box of the map area being downloaded.

## Data & attribution

Map data © OpenStreetMap contributors, available under the [Open Database License](https://www.openstreetmap.org/copyright). Mapeia is an independent project and is not affiliated with the OpenStreetMap Foundation. It's inspired by StreetComplete's concept but shares no code with it.

## Contributing

Quest ideas, translations and code are welcome. See [ROADMAP.md](ROADMAP.md) and [docs/REQUIREMENTS.md](docs/REQUIREMENTS.md).

## License

Code: [MIT](LICENSE) © 2026 Isaque Sales Domingos Fontinele. Map data: ODbL (OpenStreetMap contributors).
