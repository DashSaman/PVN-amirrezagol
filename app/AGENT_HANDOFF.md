# Agent Handoff — Cross-Platform Client / Source Reuse Research

Date: **2026-08-29**

Repository: `DashSaman/PVN-amirrezagol`

Status: **CLIENT-SOURCE-RESEARCH COMPLETE FOR CURRENT SCOPE; IMPLEMENTATION NOT STARTED BY THIS SLICE.**

## Read first

1. `app/README.md`
2. `app/KARING_DEEP_SOURCE_ANALYSIS.md`
3. `app/KARING_PLATFORM_IMPLEMENTATION_ANALYSIS.md`
4. `app/CLIENT_SOURCE_REUSE_MATRIX.md`
5. `app/CROSS_PLATFORM_ARCHITECTURE_RECOMMENDATION.md`
6. `docs/ARCHITECTURE.md`
7. `docs/PROJECT_STATE.md`
8. existing `core/foundation`, `apps/desktop`, and `engines/*-adapter` source/tests

Do not reopen the completed 93-entry protocol research campaign merely because this app/client research exists.

## Owner intent captured

The eventual goal is a new PVNetwork-owned cross-platform VPN/proxy client informed by mature applications such as Karing, V2Box, v2rayNG, Happ, NPV/NV Tunnel and other strong upstream clients.

The goal is **not** to cosmetically rebrand a competitor fork. We need reusable source only when provenance/license allow it and clean-room learning elsewhere.

Karing is the highest-priority implementation reference in this client-research stream. Its per-platform behavior must be understood beyond its Flutter UI because its actual tunnel lifecycle is delegated to OS-specific services/extensions/helpers.

## Research conclusions

### Architecture

- Current PVNetwork architecture should be preserved.
- Repository already uses Kotlin/Gradle.
- `core:foundation` uses Kotlin Multiplatform and stores common product domains under `commonMain`.
- `apps:desktop` is a Compose Desktop shell.
- engine modules already exist for WireGuard, OpenVPN, Xray, Mihomo and OpenConnect.
- Therefore the recommended default is **Kotlin Multiplatform + Compose Multiplatform**, not a wholesale Flutter rewrite.
- Flutter remains a possible UI experiment only if a platform/UX spike proves a concrete advantage; it must sit above PVNetwork-owned contracts rather than become the network architecture.

### Karing platform-source result

Current Karing public source proves a recurring boundary:

```text
product UI/config
  -> thin platform integration
  -> VPN service / packet tunnel / system extension / helper
  -> protocol core
```

Per-platform findings:

- **Android**: Flutter + thin Kotlin bridge; foreground VPN lifecycle is delegated to external `io.nebula.vpn_service.VpnServiceImpl`; Quick Settings tile and connect/disconnect/reconnect automation are implemented natively.
- **Android TV / Google TV**: same Android product/core path, but manifest includes Leanback launcher/TV resources and shared settings include runtime TV detection / TV mode.
- **iOS/iPadOS**: Flutter host; `karingService/PacketTunnelProvider.swift` delegates to `LibVpnCore.ExtensionProvider`; App Group + Network Extension entitlements provide host/extension boundary.
- **tvOS**: separate native SwiftUI product shell, not the normal Flutter mobile UI. It implements QR/LAN provisioning, local HTTP sync, profile download, Always-On and extension install/start/stop state flow.
- **macOS**: Flutter/Cocoa desktop shell with persistent-after-window-close behavior; VPN runs through packet-tunnel System Extension; App Group/iCloud/keychain/sandbox entitlements are explicit.
- **Windows**: Win32/C++ Flutter host; release CMake expects separate `bind/windows/core/`; installer uses Inno Setup and separates installed files from user data.
- **Linux**: GTK Flutter host; DEB/RPM packaging; release CMake expects separate `bind/linux/core/karingService` helper.

Critical provenance caveat: the public Karing application tree references `vpn_service`, `LibVpnCore`, `bind/windows/core/`, and `bind/linux/core/karingService`, but those complete implementations/artifacts are not all present in the inspected public tree. Do not claim the Karing app repo alone is a fully reproducible source distribution.

See `app/KARING_PLATFORM_IMPLEMENTATION_ANALYSIS.md` for exact source paths, permissions, entitlements, lifecycle and packaging evidence.

### Direct source reuse

Current best candidate discovered at the application/glue layer:

- `https://github.com/imanheidary/v2box`
- independent project; **not official V2Box source**;
- MIT-licensed plugin code;
- Flutter/platform bridge for Xray/sing-box;
- candidate only for a bounded audit/spike, especially if Flutter is ever tested.

Its top-level MIT license does **not** relicense Xray, sing-box, generated libraries or bundled artifacts. Audit each dependency/artifact separately.

Current stronger engine-level fit for the existing codebase:

- `XTLS/Xray-core` under MPL-2.0, already behind a PVNetwork-owned adapter/runtime boundary.

### Reference-only / GPL sources

Use for architecture/UX/behavior learning but do not copy into an independently licensed PVNetwork application without an explicit license decision:

- Karing — GPL v3-or-later + naming/association condition.
- v2rayNG — GPL-3.0.
- v2rayN — GPL-3.0.
- AmneziaVPN client — GPL-3.0.
- FlClash — GPL-3.0.
- Clash Verge Rev — GPL-3.0.
- historical/archived NekoBox — GPL family; exact pinned license still required if ever considered.

### Hiddify

Treat as reference-only under the current independent/commercial product direction. The repository currently publishes an extended GPL license with additional conditions including non-commercial use without prior consent, fork/source/release/attribution requirements and name/UI restrictions.

Do not import Hiddify source unless a later written permission/license plan explicitly permits the intended distribution.

### No verified canonical app source

At this snapshot no authoritative reusable application source tree was verified for:

- official V2Box end-user application;
- Happ application (public Happ repos inspected are release/readme oriented rather than full app source);
- NPV Tunnel / NapsternetV.

Do not substitute same-name repositories, binary mirrors, decompilation or configuration-decryption repositories as source provenance.

## Primary product lessons to implement independently

From Karing:

- simple connect-first UX;
- subscriptions separate from raw nodes;
- beginner vs advanced modes;
- adaptive layouts;
- backup/import/export;
- rich GeoIP/GeoSite/ACL/app/process routing;
- routing and diagnostics integrated without forcing core internals on users;
- OS VPN lifecycle separated from UI lifecycle;
- Quick Settings / automation entry points on Android;
- TV-first interaction and QR-assisted provisioning;
- desktop window lifecycle separated from network-service lifecycle;
- platform service/core artifacts packaged behind a narrow boundary.

From Hiddify:

- feature-oriented organization;
- explicit core/native bridge separation;
- remote profile UX.

From FlClash:

- one product interface can use different core integration methods per platform (FFI/library on one platform, external process/socket on another).

From v2rayNG:

- Android VPN lifecycle and profile/import edge cases.

From Amnezia:

- privileged service/platform integration and packaging robustness.

From v2rayN / Clash Verge Rev:

- desktop core supervision, system proxy/TUN, logs, tray/service/update flows.

## Non-negotiable architecture rules

- UI never calls a third-party core directly.
- raw subscription formats normalize into a PVNetwork-owned canonical model.
- generated runtime config is transient output, not product source-of-truth.
- credentials remain behind secret-store references.
- routing/DNS are product subsystems compiled to engine/platform capabilities.
- engines are replaceable capability providers.
- platform-specific TUN/VPN/privilege behavior stays behind platform adapters.
- connect/disconnect state must come from the platform/core state machine, not a UI button assumption.
- TV targets need a TV-first interaction contract; do not blindly stretch the phone/desktop UI.
- desktop management-window lifetime must be independent from tunnel/service lifetime.
- do not implement protocol cryptography from scratch.
- do not infer production support from parser tests.

## Exact next engineering decision

Before new cross-platform implementation work, perform a short **design gate** against the existing implementation:

1. confirm KMP/Compose Multiplatform remains the default product path;
2. define exact target order (recommended: desktop preservation -> Android -> iOS -> Android TV -> Apple TV where feasible);
3. formalize the missing platform adapter contracts;
4. formalize a shared tunnel state machine and platform-service install/version model;
5. decide which engine capabilities are required for the first mobile vertical slice;
6. select a first real mobile E2E test protocol already supported in repository adapters.

## First recommended implementation spike after design approval

**Android KMP/Compose vertical slice**, not a competitor fork:

- add Android target/app shell without moving canonical models out of `core:foundation`;
- implement the minimum Android `VpnService`/platform boundary needed for one existing engine path;
- reuse current adapter contracts;
- prove connect/data path/disconnect/cleanup on a real or controlled Android target;
- keep service state independent from Activity/UI lifetime;
- only then add Quick Settings/automation and expand subscription/routing scope;
- only after the phone flow is stable, add TV-specific focus/QR provisioning behavior.

A separate Flutter experiment may inspect `imanheidary/v2box` MIT glue if useful, but it must be isolated and compared against KMP rather than silently replacing the architecture.

## Completion boundary for this handoff

Completed in this research slice:

- client/source inventory;
- source provenance classification;
- license screening;
- direct-reuse vs clean-room reference classification;
- V2Box naming/provenance disambiguation;
- deep Karing core/ruleset/protocol/routing research;
- deep Karing OS-by-OS implementation research for Android/Android TV, iOS, tvOS, macOS, Windows and Linux;
- Karing public-build-chain/core/service completeness analysis;
- architecture recommendation aligned to the actual PVNetwork repository;
- durable future-agent handoff.

Not completed/claimed:

- adoption of a third-party dependency;
- mobile implementation;
- device tests;
- Store validation;
- production readiness;
- final legal sign-off.