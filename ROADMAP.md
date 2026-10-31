# Roadmap

Total planned work to a first public beta: **~110–140 hours**.

## Milestone 0 — Foundation (~10h)
- [x] KMP + Compose Multiplatform skeleton (Android + iOS), Koin, SQLDelight
- [x] GitHub Actions: build Android, build iOS framework, run shared tests
- [ ] `secrets.properties.example` + build config for OSM dev vs. production server

## Milestone 1 — Map (~14h)
- [ ] MapLibre Compose map with configurable vector tile style
- [ ] User location (on-device only), follow mode
- [ ] Render downloaded OSM nodes/ways as quest pins

## Milestone 2 — OSM data layer (~18h)
- [ ] Overpass client: download features for a bounding box
- [ ] OSM API v0.6 client: read element versions, open/close changesets, upload diffs
- [ ] OSM data model (nodes, ways, tags, versions) + SQLDelight cache
- [ ] Conflict handling (element changed upstream since download)

## Milestone 3 — Sign-in (~8h)
- [ ] OAuth 2.0 + PKCE flow on Android (Custom Tabs) and iOS (ASWebAuthenticationSession)
- [ ] Secure token storage (Keystore / Keychain)
- [ ] Sign-out and token revocation

## Milestone 4 — Quest engine (~20h)
- [ ] Declarative quest API: element filter, question, answer type, tag mapping
- [ ] Answer types: yes/no, single choice, number, "not here anymore"
- [ ] Offline edit queue; upload batching into one changeset with a good comment
- [ ] Undo last answers before upload

## Milestone 5 — First quest pack: accessibility (~16h)
- [ ] Wheelchair access (shops, amenities, entrances)
- [ ] Accessible toilets
- [ ] Tactile paving at crossings
- [ ] Kerb type/height at crossings
- [ ] Step-free entrances

## Milestone 6 — Second quest pack: common interest (~14h)
- [ ] Drinking water points (exists / working)
- [ ] Public toilets (fee, opening hours basics)
- [ ] Benches (backrest), shelters (covered)
- [ ] Bicycle parking (capacity, covered)
- [ ] Recycling points (accepted materials)

## Milestone 7 — Polish & beta (~16h)
- [ ] Onboarding explaining OSM, ODbL and privacy
- [ ] Localization: Portuguese (pt-BR) and English
- [ ] App accessibility pass: VoiceOver / TalkBack, Dynamic Type, contrast
- [ ] Personal stats (local only): answers count, areas improved
- [ ] TestFlight + Play internal testing

## Later
- Area "campaigns" (e.g. "map every pharmacy entrance in my neighborhood")
- Re-survey quests using `check_date`
- iPad and desktop layouts
- Community-contributed quest packs
