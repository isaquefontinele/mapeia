# Requirements — Mapeia

## 1. Goal
Let anyone improve OpenStreetMap accessibility and common-interest data through short, guided questions on Android and iOS, without a dedicated backend.

## 2. Stakeholders
- **Contributors** — people answering quests.
- **Data consumers** — anyone using OSM (routing apps, accessibility maps).
- **OSM community** — expects good-quality, well-commented changesets.

## 3. Functional requirements

| ID | Requirement |
|---|---|
| FR-01 | Show a map centered on the user's location with available quests as pins. |
| FR-02 | Download OSM data for the visible area via Overpass and cache it offline. |
| FR-03 | Detect missing/outdated tags and generate quests from declarative definitions. |
| FR-04 | Answer quests with one or two taps; support "can't tell" and "doesn't exist anymore". |
| FR-05 | Queue answers offline; upload as a single changeset with a descriptive comment and `created_by` tag. |
| FR-06 | Sign in with the user's own OSM account via OAuth 2.0 + PKCE; store tokens securely. |
| FR-07 | Handle edit conflicts when an element changed upstream. |
| FR-08 | Allow undoing answers before upload. |
| FR-09 | Filter quests by category (accessibility, water, toilets, seating, bikes, recycling). |
| FR-10 | Switch between OSM development and production servers (dev by default in debug builds). |
| FR-11 | Available in Portuguese (pt-BR) and English. |

## 4. Non-functional requirements

| ID | Requirement |
|---|---|
| NFR-01 | **No project-owned server**; the app talks only to OSM/Overpass and the configured tile source. |
| NFR-02 | No analytics, no third-party trackers. Location never leaves the device except as the requested bounding box. |
| NFR-03 | Works offline after downloading an area; uploads resume when connectivity returns. |
| NFR-04 | Respect OSM API and Overpass usage policies (rate limits, User-Agent, caching). |
| NFR-05 | Shared code ≥ 80% of total Kotlin code; quest engine fully unit-tested. |
| NFR-06 | The app itself meets accessibility basics: screen readers, dynamic type, 4.5:1 contrast, 44pt/48dp touch targets. |
| NFR-07 | No secrets in the repository; OAuth client id provided through an untracked `secrets.properties`. |

## 5. Out of scope
- Storing or processing other users' personal data.
- Photo uploads (privacy and moderation cost); may be revisited later.
- A full-featured OSM editor (geometry editing, relations).

## 6. Data & licensing
- OSM data is ODbL; the app shows attribution on the map at all times.
- Contributions are made under the user's OSM account and the OSM contributor terms.

## 7. Acceptance criteria for beta
- At least 10 quests across both packs working end-to-end against the OSM dev server.
- Successful changeset upload on Android and iOS; conflicts handled without data loss.
- pt-BR and English UI; VoiceOver and TalkBack usable for the full quest flow.
