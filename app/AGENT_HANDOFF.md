# Agent Handoff — Cross-Platform Client / Source Reuse Research

Date: **2026-08-29**

Repository: `DashSaman/PVN-amirrezagol`

Status: **CLIENT-SOURCE-RESEARCH COMPLETE FOR CURRENT SCOPE; IMPLEMENTATION NOT STARTED BY THIS SLICE.**

## Read first

1. `app/README.md`
2. `app/CLIENT_SOURCE_REUSE_MATRIX.md`
3. `app/CROSS_PLATFORM_ARCHITECTURE_RECOMMENDATION.md`
4. `docs/ARCHITECTURE.md`
5. `docs/PROJECT_STATE.md`
6. existing `core/foundation`, `apps/desktop`, and `engines/*-adapter` source/tests

Do not reopen the completed 93-entry protocol research campaign merely because this app/client research exists.

## Owner intent captured

The eventual goal is a new PVNetwork-owned cross-platform VPN/proxy client informed by mature applications such as Karing, V2Box, v2rayNG, Happ, NPV/NV Tunnel and other strong upstream clients.

The goal is **not** to cosmetically rebrand a competitor fork. We need reusable source only when provenance/license allow it and clean-room learning elsewhere.

## Research conclusions

### Architecture

- Current PVNetwork architecture should be preserved.
- Repository already uses Kotlin/Gradle.
- `core:foundation` uses Kotlin Multiplatform and stores common product domains under `commonMain`.
- `apps:desktop` is a Compose Desktop shell.
- engine modules already exist for WireGuard, OpenVPN, Xray, Mihomo and OpenConnect.
- Therefore the recommended default is **Kotlin Multiplatform + Compose Multiplatform**, not a wholesale Flutter rewrite.
- Flutter remains a possible UI experiment only if a platform/UX spike proves a concrete advantage; it must sit above PVNetwork-owned contracts rather than become the network architecture.

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
- routing and diagnostics integrated without forcing core internals on users.

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
- do not implement protocol cryptography from scratch.
- do not infer production support from parser tests.

## Exact next engineering decision

Before new cross-platform implementation work, perform a short **design gate** against the existing implementation:

1. confirm KMP/Compose Multiplatform remains the default product path;
2. define exact target order (recommended: desktop preservation -> Android -> iOS -> Android TV -> Apple TV where feasible);
3. formalize the missing platform adapter contracts;
4. decide which engine capabilities are required for the first mobile vertical slice;
5. select a first real mobile E2E test protocol already supported in repository adapters.

## First recommended implementation spike after design approval

**Android KMP/Compose vertical slice**, not a competitor fork:

- add Android target/app shell without moving canonical models out of `core:foundation`;
- implement the minimum Android `VpnService`/platform boundary needed for one existing engine path;
- reuse current adapter contracts;
- prove connect/data path/disconnect/cleanup on a real or controlled Android target;
- only then expand UI/subscription/routing scope.

A separate Flutter experiment may inspect `imanheidary/v2box` MIT glue if useful, but it must be isolated and compared against KMP rather than silently replacing the architecture.

## Completion boundary for this handoff

Completed in this research slice:

- client/source inventory;
- source provenance classification;
- license screening;
- direct-reuse vs clean-room reference classification;
- V2Box naming/provenance disambiguation;
- architecture recommendation aligned to the actual PVNetwork repository;
- durable future-agent handoff.

Not completed/claimed:

- adoption of a third-party dependency;
- mobile implementation;
- device tests;
- Store validation;
- production readiness;
- final legal sign-off.
