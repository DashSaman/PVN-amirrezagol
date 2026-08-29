# Client Source / Reuse Matrix

Snapshot: **2026-08-29**

Purpose: engineering reference for a future PVNetwork cross-platform client. `DIRECT-CANDIDATE` means “eligible for a deeper audit,” not “approved for production.”

## Executive matrix

| Project | Verified source status | Main stack / role | License observed | Best value to PVNetwork | Reuse decision |
|---|---|---|---|---|---|
| Karing (`KaringX/karing`) | Full public app source | Flutter + modified sing-box | GPL v3-or-later + name/association restriction | strongest whole-product cross-platform reference; subscriptions, routing, storage, UI, platform coverage | **GPL-ONLY / REFERENCE-ONLY for non-GPL PVNetwork** |
| Hiddify (`hiddify/hiddify-app`) | Full public app source | Flutter + Go/core bindings | Hiddify Extended GPLv3; explicit fork/source/attribution/UI/name conditions and non-commercial restriction | feature-oriented Flutter architecture, remote profiles, multi-platform core bridge | **REFERENCE-ONLY unless written permission and license strategy** |
| v2rayNG (`2dust/v2rayNG`) | Full public app source | Android/Kotlin + Xray/v2fly | GPL-3.0 | Android VPN lifecycle, config parsing/import, Xray integration, background service lessons | **GPL-ONLY / REFERENCE-ONLY** |
| v2rayN (`2dust/v2rayN`) | Full public app source | C# desktop + multiple cores | GPL-3.0 | desktop process/core orchestration, profile/routing UX | **GPL-ONLY / REFERENCE-ONLY** |
| AmneziaVPN (`amnezia-vpn/amnezia-client`) | Full public client source | C++/Qt/QML, CMake/Conan | GPL-3.0; third-party licenses separate | mature desktop+mobile native integration, service/privilege handling, protocol modularity | **GPL-ONLY / REFERENCE-ONLY** |
| Happ (`Happ-proxy/*`) | Public repos verified, but app source tree not present in inspected Android/desktop repos | Xray-powered end-user client | no reusable app-source license established because source is not present | UX, routing behavior, protocol/support expectations, release packaging | **NO-SOURCE / REFERENCE-ONLY** |
| V2Box end-user app | No authoritative public source tree verified | end-user multi-protocol client | not established | UX/compatibility benchmark only | **NO-SOURCE / REFERENCE-ONLY** |
| `SagerNet/v2box` | Public but archived library/migration repo; not official V2Box GUI source | Go migration helper around sing-box | inspect separately if ever needed | historical migration logic only | **NOT A V2BOX APP BASE** |
| `imanheidary/v2box` | Full public independent plugin source | Flutter plugin; Android/iOS/desktop folders; dual Xray/sing-box bridge | **MIT for plugin code** | high-value candidate for platform interface, method channel, link parsing, ping/traffic APIs and dual-core abstraction | **DIRECT-CANDIDATE after audit** |
| NPV Tunnel / NapsternetV | No authoritative application source verified | proprietary/end-user tunnel client | not established | UX/config compatibility observation only | **NO-SOURCE / REFERENCE-ONLY** |
| NekoBox for Android (`MatsuriDayo/NekoBoxForAndroid`) | Public historical source; upstream repo archived | Android + sing-box | GPL family; verify exact pinned revision before any use | historical Android sing-box/TUN patterns and config UX | **ARCHIVED GPL REFERENCE** |
| FlClash (`chen08209/FlClash`) | Full public source | Flutter + Go/Mihomo; FFI on Android, process/socket on desktop | GPL-3.0 | extremely useful engine-boundary architecture pattern and adaptive Flutter UX | **GPL-ONLY / REFERENCE-ONLY** |
| Clash Verge Rev | Full public source | Tauri/Rust/TypeScript + Mihomo | GPL-3.0 | desktop system-proxy/TUN/service/update UX and Tauri packaging lessons | **GPL-ONLY / REFERENCE-ONLY** |
| sing-box | Full public core source | Go universal proxy engine | GPL v3-or-later + naming restriction | broad protocol engine/capability reference | **ENGINE WITH GPL OBLIGATIONS; boundary/legal review required** |
| Xray-core | Full public core source | Go proxy engine | MPL-2.0 | VLESS/VMess/Trojan/REALITY/Vision/XHTTP family and mature engine | **STRONG ENGINE CANDIDATE; MPL obligations remain** |
| Mihomo | Full public core source | Go rule-based proxy engine | GPL family; verify exact revision | routing/rules/provider ecosystem and broad modern protocol coverage | **ENGINE WITH GPL OBLIGATIONS; boundary/legal review required** |

---

## 1. Karing — primary product reference

Canonical source: https://github.com/KaringX/karing

Observed repository facts:

- Flutter application.
- README describes Karing as a sing-box GUI based on Flutter.
- Current source exposes `lib/app`, `lib/screens`, `lib/i18n`, `lib/builders` and a substantial application layer.
- README currently lists Windows, Android, Linux, iOS, macOS and tvOS requirements.
- It supports multiple subscription/config ecosystems and includes routing groups, node groups, backup/sync and beginner-mode concepts.
- It uses a modified Karing-maintained sing-box core.

High-value source areas to study:

- `lib/app/modules/server_manager.dart` — large server/profile orchestration surface.
- `lib/app/modules/setting_manager.dart` — centralized application/network settings patterns.
- `lib/app/modules/proxy_cluster.dart` — proxy grouping/cluster concepts.
- `lib/app/modules/remote_config.dart` and `remote_config_manager.dart` — remote configuration boundaries.
- `lib/app/local_storage/` — persistence patterns.
- `lib/app/runtime/` — runtime boundaries.
- `lib/screens/my_profiles_screen.dart` — profile/subscription UX.
- `lib/i18n/` — broad localization reference, including Persian in the project.
- `pubspec.yaml` — real dependency inventory for a mature Flutter network app (secure storage, SQLite, connectivity, device info, charts, WebView, etc.).

License decision:

`LICENSE.md` states GPL v3-or-later and adds that derivative works may not use the name or imply association without consent.

Therefore:

- studying structure/behavior: **YES**;
- copying code into a closed/proprietary PVNetwork client: **NO**;
- copying code into a GPL-compatible PVNetwork distribution: potentially possible subject to full GPL and dependency obligations;
- copying Karing branding/assets/UI identity: **NO**.

PVNetwork lesson:

Use Karing as the **functional benchmark and architecture reference**, not the code foundation.

---

## 2. Hiddify — strong architecture, restrictive direct reuse

Canonical source: https://github.com/hiddify/hiddify-app

Observed source structure includes:

- `lib/core/`
- `lib/features/`
- `lib/hiddifycore/`
- `lib/singbox/`
- `lib/bootstrap.dart`
- Riverpod/state infrastructure
- native platform work in addition to Flutter/Go.

Its public description identifies it as a multi-platform proxy frontend supporting a broad set of modern protocols and subscription formats.

License decision is critical:

The repository's `LICENSE.md` is labeled **Hiddify Extended GNU General Public License v3** and currently adds explicit conditions including:

- source must be published as a GitHub fork of Hiddify when code is used;
- releases must use GitHub Actions;
- attribution requirements;
- restrictions on similar name/UI in app stores;
- **non-commercial use only unless prior written consent**;
- share-alike/fork requirements.

For PVNetwork this means:

- direct code reuse for an independent commercial application: **DO NOT PLAN ON IT** without written permission/legal approval;
- architecture and public behavior study: **YES**;
- reproduce Hiddify UI/branding: **NO**;
- clean-room implementation of generic product concepts: **YES**.

Best lessons:

- feature-first Flutter organization;
- separating core bindings from UI features;
- remote profile UX;
- multi-platform packaging and native bridge organization;
- broad config compatibility.

---

## 3. v2rayNG — Android/Xray reference

Canonical source: https://github.com/2dust/v2rayNG

License: GPL-3.0.

Role:

- Android-only client, not a cross-platform base.
- Strong reference for Android service lifecycle, Xray/v2fly integration, Android VPN permission/TUN behavior, profile import/export, geo assets and Android-specific troubleshooting.

PVNetwork decision:

- direct source copy into a non-GPL PVNetwork client: **NO**;
- use to define Android PlatformAdapter behavior and tests: **YES**;
- use protocol/config behavior as an interoperability reference: **YES**.

Do not make shared business logic depend on Android/v2rayNG classes.

---

## 4. v2rayN — desktop orchestration reference

Canonical source: https://github.com/2dust/v2rayN

Current public description: GUI client for Windows, Linux and macOS supporting Xray, sing-box and others.

License: GPL-3.0.

Best lessons:

- external-core lifecycle and version management;
- desktop system proxy behavior;
- profile/routing UI;
- logs/diagnostics;
- multi-core selection.

Decision: **REFERENCE-ONLY unless PVNetwork adopts GPL for affected work.**

---

## 5. AmneziaVPN — native/platform integration benchmark

Canonical source: https://github.com/amnezia-vpn/amnezia-client

Observed stack in current upstream documentation:

- C++17/C++20
- Qt 6 / QML / Qt Quick
- CMake / Conan
- libssh / OpenSSL
- multiple engine/protocol integrations
- desktop + mobile scope

License: GPL-3.0; third-party components have separate terms.

Best areas to study:

- privileged service architecture;
- OS-specific networking and tunnel setup;
- C++/QML separation;
- server provisioning workflows (where relevant);
- packaging/signing/installers;
- native crash/recovery behavior;
- dependency inventory discipline (`THIRD_PARTY_LICENSES.md`).

Decision:

- do not use Amnezia as PVNetwork UI base unless intentionally choosing Qt+GPL;
- use as a native integration and operational robustness reference.

---

## 6. Happ — binary/product reference, not source base

Canonical organization: https://github.com/Happ-proxy

Verified public repositories include:

- `Happ-proxy/happ-android`
- `Happ-proxy/happ-desktop`
- `Happ-proxy/happ-ios`

At the 2026-08-29 snapshot, the inspected Android repository root contained only `.idea`, `README.md`, and a `release` file; desktop contained only README/release material. This is not a usable application source tree.

Public README states Happ is powered by Xray and supports flexible routing plus VLESS Reality, VMess, Trojan, Shadowsocks and SOCKS.

Decision:

- source reuse: **NO-SOURCE**;
- use release behavior/UI as a comparison target: **YES**;
- do not decompile/reconstruct Happ code for PVNetwork.

Useful product lessons to capture later in UX testing:

- simplicity of importing and choosing nodes;
- routing controls exposed to normal users;
- connection state presentation;
- how much protocol complexity is hidden by default.

---

## 7. V2Box — source identity problem

No authoritative public source repository for the end-user V2Box app was verified in this snapshot.

Search results include unrelated repositories with `v2box` in their name. They must be classified by provenance before use.

### 7.1 `SagerNet/v2box`

https://github.com/SagerNet/v2box

- archived by its owner;
- Go library/migration code around sing-box;
- **not verified as the source of the V2Box GUI app**.

Do not use it as evidence of V2Box app architecture.

### 7.2 `imanheidary/v2box` — independent MIT Flutter plugin

https://github.com/imanheidary/v2box

This is a separate project and currently the most interesting direct-reuse candidate discovered by this survey.

Its `LICENSE` is MIT.

Repository structure includes platform directories for:

- Android
- iOS
- Linux
- macOS
- Windows

and a Flutter-facing API layer including:

- `lib/v2ray_box.dart`
- `lib/v2ray_box_method_channel.dart`
- `lib/v2ray_box_platform_interface.dart`
- `lib/src/`

The README describes a dual-core Xray/sing-box plugin and documents features such as config-link parsing/validation, latency tests, per-app proxy on Android, notifications and traffic state.

Why it is valuable:

- MIT license is materially easier to combine with a PVNetwork-owned application than GPL application code;
- it already expresses a platform-interface shape similar to what PVNetwork needs;
- it can be studied as a candidate bridge instead of rebuilding every Flutter/native call from zero.

Why it is **not yet approved**:

- repository is relatively small/new compared with Karing/v2rayNG;
- underlying Xray and sing-box binaries/libraries retain their own licenses;
- native build scripts, artifact provenance, signing, security, lifecycle reliability and App Store compatibility require independent verification;
- the API must be checked against PVNetwork's canonical EngineAdapter rather than allowed to become the architecture itself.

Decision: **DIRECT-CANDIDATE for a bounded spike/audit, not wholesale adoption.**

---

## 8. NPV Tunnel / NapsternetV

The app is also referred to in public material as NapsternetV / NPV Tunnel.

No authoritative public application source tree was verified.

Searches surface binary-release mirrors, config repositories and third-party reverse-engineering/decryption tools. None of those are acceptable as canonical PVNetwork implementation source.

Decision:

- direct source reuse: **NO**;
- unofficial decryption/reverse-engineering scripts: **DO NOT IMPORT** into PVNetwork;
- public UI/behavior/interoperability observations: **REFERENCE-ONLY**.

---

## 9. NekoBox for Android

Canonical historical source: https://github.com/MatsuriDayo/NekoBoxForAndroid

The upstream repository is archived/read-only in current search results.

Value:

- historical Android sing-box client architecture;
- Android VPN/TUN and profile management lessons;
- a large body of issue/fork history.

Risk:

- archived code drifts from current Android APIs, sing-box changes and Store requirements;
- GPL obligations remain.

Decision: **historical reference only; do not base new PVNetwork code on it.**

---

## 10. FlClash — important cross-platform architecture reference

Canonical source: https://github.com/chen08209/FlClash

Public upstream identifies it as a Flutter multi-platform client for Android, Windows, macOS and Linux based on ClashMeta/Mihomo.

License: GPL-3.0.

A particularly useful documented architecture pattern is:

- Android: Go core compiled as a shared library and called via FFI;
- desktop: core runs as a separate process and communicates over a socket;
- a Dart-side controller selects a shared core interface by platform.

That pattern strongly supports PVNetwork's existing “stable adapter boundary + real platform differences” architecture.

Decision:

- copy code: **GPL-ONLY**;
- learn from the library-vs-process boundary and adaptive UI: **YES**.

---

## 11. Clash Verge Rev

Canonical source: https://github.com/clash-verge-rev/clash-verge-rev

Stack: Tauri/Rust/TypeScript + Mihomo; desktop-oriented Windows/macOS/Linux.

License: GPL-3.0.

Best lessons:

- desktop process supervision;
- system proxy/TUN control;
- tray/menu integration;
- configuration/rule editing;
- update/package lifecycle;
- desktop diagnostics.

Decision: **desktop UX/operations reference, not PVNetwork cross-platform code base.**

---

# Core license implications

## sing-box

Canonical: https://github.com/SagerNet/sing-box

Observed license: GPL v3-or-later plus a name/association restriction.

Treat linking/bundling/in-process use as a license-sensitive architecture choice. A separate executable/process boundary may simplify product architecture and updates, but it does **not automatically eliminate license obligations**. Legal review is still required.

## Xray-core

Canonical: https://github.com/XTLS/Xray-core

Observed license: Mozilla Public License 2.0.

This is materially more permissive for combination with independently licensed product code than GPL application code, while changes to MPL-covered files and required notices/source obligations still matter.

PVNetwork already has repository evidence around a host-supplied Xray process boundary. Preserve that separation unless a later audited design proves a better platform-specific integration.

## Mihomo

Canonical: https://github.com/MetaCubeX/mihomo

Treat as a GPL-family engine and audit the exact revision/license before any bundled integration.

---

# What we can reuse safely first

Priority is based on **legal clarity + architectural value**, not popularity.

### Tier A — best direct-code candidate

1. `imanheidary/v2box` **MIT plugin code**, after code/security/maintenance review.
   - platform interface shape;
   - method-channel abstraction;
   - config-link parsing;
   - ping/traffic API design;
   - bounded native bridge pieces that survive review.

Do not assume the included/generated core binaries inherit MIT. They do not; every core keeps its own license.

### Tier B — use specifications/APIs, not competitor app code

2. Xray-core's public APIs/config model under MPL-2.0, preferably behind PVNetwork adapters.
3. OS vendor VPN/network APIs and permissively licensed Flutter ecosystem packages after individual audit.
4. standardized share-link/config formats and documented core APIs implemented independently in PVNetwork-owned code.

### Tier C — study and clean-room reimplement

5. Karing architecture, subscription UX, routing UX, sync/backup patterns.
6. Hiddify feature organization and profile/core boundary.
7. FlClash library-vs-process platform strategy.
8. v2rayNG Android lifecycle and import behavior.
9. Amnezia native service/privilege/packaging patterns.
10. v2rayN and Clash Verge Rev desktop orchestration.

### Tier D — behavior-only references

11. Happ.
12. V2Box official end-user app.
13. NPV Tunnel/NapsternetV.

---

# Explicit do-not-copy list

Do not copy into PVNetwork without a new written license/provenance decision:

- Karing Dart/UI/source files;
- Karing branding, mascot, screenshots or distinctive UI identity;
- Hiddify source/UI/branding;
- v2rayNG source;
- v2rayN source;
- Amnezia client source;
- NekoBox source;
- FlClash source;
- Clash Verge Rev source;
- closed-source Happ/V2Box/NPV application code or decompiled artifacts;
- third-party subscription/config decryption scripts found in unrelated repositories.

---

# Engineering acceptance rule for any future reusable dependency

Before a dependency moves from candidate to approved:

1. pin exact tag/commit and hashes;
2. capture license and copyright notices at that pin;
3. audit transitive/native dependencies;
4. verify repository provenance and maintainer identity;
5. inspect security-sensitive native/platform code;
6. run tests on every claimed platform;
7. run real VPN/proxy lifecycle tests, including permission denial, crash/restart, sleep/wake and network change;
8. verify no plaintext reusable secrets/log leakage;
9. confirm app-store redistribution constraints;
10. generate SBOM and attribution material;
11. ensure dependency API sits behind PVNetwork-owned adapters;
12. provide a replacement/removal path so one third-party project cannot lock the product architecture.

Only then may the repository call the dependency **APPROVED FOR IMPLEMENTATION**.
