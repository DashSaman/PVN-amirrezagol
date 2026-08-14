# Xray Family — Major Client Ecosystem / Reuse Roles

Research date: 2026-08-14

State: `IN-RESEARCH`; client-reference map only. Underlying engines and client applications have separate licenses.

## Upstream client list vs PVNetwork selection

The current Xray-core README lists a large ecosystem across Windows, Android, Apple platforms, Linux, routers and wrapper projects. PVNetwork should not research every listed project at equal depth. Prioritize clients that contribute a distinct architecture/platform lesson and keep a long-tail reference index for later `COMPLETE-REFERENCE-v2` expansion.

## Tier A — primary architecture/UX references

### v2rayN

Repository: `2dust/v2rayN`

Pinned revision already recorded:

`e01717d8326a4f5060b335523590c5fda943fe03`

Application license: GPLv3.

Why it matters:

- major desktop client;
- explicit multi-core architecture rather than one hard-wired Xray process;
- shared `ServiceLib` separated from Avalonia desktop UI;
- profile/group/subscription/core generation/test layers;
- SQLite-related persistence and backup/sync dependencies;
- strong source-level evidence for desktop routing/TUN/DNS regressions.

Important current failure lesson: the pinned 2026-08-10 head fixes a TUN self-address rule that could unintentionally block the TUN resolver address and make system DNS look broken while the proxy path remained healthy. The commit also restores regression tests around that behavior.

PVNetwork lesson:

- treat TUN routing and DNS as separately verifiable product systems;
- a healthy proxy core is not proof that system name resolution is healthy;
- generated engine configs require semantic regression tests.

Reuse classification: **REFERENCE-ONLY by default for a closed product because of GPLv3**. Underlying cores require separate review.

Existing dossier:

`research/upstreams/client-references/V2RAYN_INDEX.md`

### v2rayNG

Repository: `2dust/v2rayNG`

Current pinned head during this review:

`e8a82d9810ca1cf97a3cc8a9b9525a9f21955807`

Application license previously reviewed: GPLv3.

Why it matters:

- one of the major Android Xray clients;
- current source/localization explicitly exposes profile import by QR, clipboard and local source plus manual creation for several protocol types;
- current resources distinguish share-link export from generated full-configuration export;
- Android-specific product lifecycle, VpnService/core bridge, routing/TUN and profile-management behavior are valuable references.

Current head includes a localization audit across maintained catalogs, demonstrating that terminology/import/export labels evolve even in mature clients.

Reuse classification: **REFERENCE-ONLY by default for a closed product because of GPLv3**.

Required future split dossier:

- app/module tree;
- database/profile persistence;
- Android service/core lifecycle;
- complete menus/screens;
- import/subscription parser map;
- issues/regressions;
- Android TV/accessibility/localization evidence.

### Hiddify App

Repository: `hiddify/hiddify-app`

Current license reviewed directly from `LICENSE.md`: “Hiddify Extended GNU General Public License v3” with additional conditions including source/fork/release/attribution/interface/naming requirements and **non-commercial use unless prior written consent is obtained**.

Why it matters:

- polished multi-platform UX reference;
- strong profile/subscription onboarding/reference experience;
- useful Persian/localization terminology and mobile/desktop product-flow reference;
- demonstrates how a technically broad core can be hidden behind simpler user flows.

PVNetwork restriction:

**Do not copy Hiddify application code/UI/branding into a commercial closed PVNetwork product without separate permission/license review.** Treat it as product/UX/issue reference.

### Karing

Repository: `KaringX/karing`

License research already recorded: GPLv3-or-later plus an additional naming/association condition.

Why it matters:

- Flutter/Dart multi-platform reference;
- many explicit import/QR/backup/sync/settings/accessibility screens;
- SQLite and secure-storage dependencies;
- iCloud/WebDAV/LAN backup/sync concepts;
- tray/hotkey/window integration;
- Persian documentation/translation evidence;
- telemetry/crash-report dependency flags that PVNetwork must privacy-review independently.

Reuse classification: **REFERENCE-ONLY by default for a closed product**.

Existing dossier:

`research/upstreams/client-references/KARING.md`

### NekoBox Android

Source previously inspected as a real Kotlin codebase with `app/` and `libcore/` layers, Room database entities/migrations and Android DataStore/preferences.

Important importer lesson from source research: some imported subscription/config forms can preserve endpoint/outbound data while not representing every routing semantic. PVNetwork must explicitly detect/report lossy imports.

License previously reviewed: GPLv3-or-later.

Reuse classification: **REFERENCE-ONLY by default**; preserve source-level importer/database lessons.

Dedicated large dossier write was previously connector-blocked; do not retry the same document unchanged.

### Throne

Repository/source previously reviewed as active C++/CMake code with areas such as core/API/config/database/stats/system/UI and third-party dependencies.

License previously reviewed: GPL-3.0.

Why it matters:

- desktop native/C++ architecture reference;
- useful contrast with v2rayN Avalonia and Flutter/Tauri clients;
- database/config/core/UI separation.

Reuse classification: **REFERENCE-ONLY by default**.

Existing dossier:

`research/upstreams/client-references/THRONE.md`

### Happ

The Xray-core README currently lists Happ for Apple platforms/macOS/tvOS and also shows Happ as a project sponsor/product reference.

Previously reviewed `Happ-proxy/happ-desktop` GitHub repository is not confirmed as a complete licensed desktop application source and GitHub does not provide a clear application license there.

Why it matters:

- product/Apple/tvOS UX reference;
- useful evidence that Xray-style connectivity is being delivered on TV form factors;
- not currently a source-reuse candidate without canonical licensed source.

Reuse classification: **PRODUCT/UX REFERENCE ONLY** until source/license provenance is established.

Existing dossier:

`research/upstreams/client-references/HAPP.md`

## Tier B — cross-engine / adjacent references

### Clash Verge Rev

TypeScript + Rust/Tauri desktop reference, current research license GPL-3.0.

Value:

- routing/profile/subscription UX;
- process/native boundary;
- modern desktop settings/log/status model.

It is not an Xray-core GUI in the narrow sense; use it to learn product architecture/routing UX, not as proof of Xray behavior.

### FlClash

Flutter/Dart frontend with platform-specific core hosting and shared controller architecture; GPL-3.0.

Value:

- cross-platform UI and state architecture;
- Riverpod separation;
- Drift/SQLite persistence;
- Android FFI/library vs desktop subprocess contrast.

### Amnezia Client

GPL application with a separate MIT AmneziaWG core. Value is multi-engine orchestration rather than Xray-specific UI reuse.

## Xray-specific wrapper projects

The current Xray README lists wrapper/integration projects including:

- `XTLS/libXray`;
- Android wrapper/library projects;
- SDK/API wrappers.

These can be more relevant to PVNetwork than copying a full GPL GUI because they may provide a narrower core integration boundary. Each wrapper needs an independent source/license/platform/API audit before selection.

## Router/UI long tail

Current upstream README also lists OpenWrt/AsusWRT-style GUIs and other platform clients. These become important in `COMPLETE-REFERENCE-v2` for server/router/admin UI/menu and installation research, but are not all first-priority PVNetwork application code references.

## Product architecture lessons common across clients

1. **Core != client application.** License and lifecycle must be reviewed separately.
2. **Canonical profile != engine config.** Mature clients generate/translate engine configs from product state.
3. **TUN/DNS/routing bugs can exist while the core is healthy.** Product regression tests must cover the system network layer.
4. **Import formats are semantically unequal.** Endpoint links, subscriptions and full configs require different lossiness rules.
5. **Cross-platform clients often use different core-hosting models per OS.** A single UI framework does not eliminate native networking lifecycle work.
6. **Localization is not just string count.** Persian/RTL/mixed technical tokens must be separately tested.
7. **Client popularity does not grant code-reuse rights.** Most primary GUI references here are GPL/custom/reference-only for a closed commercial product.

## PVNetwork research recommendation

For Xray-family implementation research, separate these candidate layers:

### Core candidate

- Xray-core itself — MPL-2.0, subject to dependency/platform review.

### Narrow wrapper candidates

- audit `libXray` and other maintained wrappers independently.

### UI/architecture references

- v2rayN;
- v2rayNG;
- Hiddify;
- Karing;
- NekoBox;
- Throne;
- Happ product behavior;
- selected cross-engine clients.

Do not make the commercial PVNetwork app a fork of a reference GUI unless the owner explicitly chooses a compatible open-source distribution model.

## Remaining gaps

- dedicated v2rayNG source/storage/service/menu dossier;
- dedicated NekoBox split dossiers after prior connector block;
- current `libXray` API/license/platform audit;
- current X-flutter/OneXray/other maintained source evaluation;
- current issues/release regressions for each Tier A client;
- Apple/tvOS client source/license landscape beyond product listings;
- screenshot/menu/accessibility catalog — mandatory later under v2 contract.
