# AGENTS Handoff — 2026-08-14 — Xray v1 Work Unit 1

Mandatory continuation checkpoint for `DashSaman/PVN-amirrezagol`.

## Priority

Continue original `COMPLETE-RESEARCH-v1`. Do not begin mass `COMPLETE-REFERENCE-v2` server/client/crypto/wire-flow expansion yet.

OpenConnect/Enterprise shared-family research reached `V1-HANDOFF-READY / NOT IMPLEMENTED` in the previous handoff. Xray/modern-proxy is now the active original-research family.

## Read first for this work unit

- `research/upstreams/xray-family/INDEX.md`
- `research/upstreams/xray-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/xray-family/DEPENDENCIES_TESTS_RELEASES.md`
- `research/upstreams/xray-family/CONFIG_CAPABILITY_MODEL.md`
- `research/upstreams/xray-family/CLIENT_ECOSYSTEM.md`
- `research/upstreams/xray-family/ISSUE_RELEASE_LESSONS.md`
- `research/upstreams/xray-family/LIBXRAY_WRAPPER.md`
- `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_XRAY_V1_1.md`
- relevant `research/upstreams/client-references/` files.

## Current Xray-core pin

`XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`

Tree:

`46ee908a9a67513d3c85bbf998be5d553a078109`

Current latest stable observed:

`v26.3.27` (2026-03-27).

Root license: MPL-2.0.

Current main is newer than stable and includes behavior fixes; never assume latest main equals approved production release.

## New files/commits

- `SOURCE_ARCHITECTURE.md` — `e200ad6c01c190ff267ea7ef3182fecda60e11bc`
- `DEPENDENCIES_TESTS_RELEASES.md` — `1083c4e9ebec66b8b2734da2ba7af00bd041f3b2`
- `CONFIG_CAPABILITY_MODEL.md` — `ad5135feed8c9cf5aeab3eb2e24ee84451850443`
- `CLIENT_ECOSYSTEM.md` — `4c08b98287a95b3e8a42b7c9138a7575129564b1`
- `ISSUE_RELEASE_LESSONS.md` — `23e8f43c3a9b16900c83d7eaea33c6e04a6bb5da`
- `LIBXRAY_WRAPPER.md` — `9cd861ce44e659a912f1a40d8a9b737046fb927e`
- dated status — `e411cf66d9a083d104a0f264064cfb7db69a0135`

## Key architectural findings

### Xray core is a networking runtime, not one protocol

Pinned source separates:

- `core/` lifecycle;
- `app/` routing/DNS/dispatcher/proxyman/policy/stats/log/observability/API-like services;
- `proxy/` protocol implementations;
- `transport/` outer transport;
- `infra/conf/` human config conversion;
- `common/` shared runtime utilities.

PVNetwork must keep protocol, transport, security/flow, routing/DNS and platform lifecycle as separate product concepts.

### Canonical product profile must not be raw Xray config

Use:

`import source -> canonical PVProfile -> validation/capability -> generated Xray runtime config -> transient engine state`

Preserve unknown/lossy fields instead of silently dropping them.

### Current source shows configuration drift

Pinned transport configuration contains active, deprecated and removed transport/security names. This is direct evidence that PVNetwork needs core-version-aware capability validation and migration handling.

### Dependency/SBOM surface is substantial

Pinned `go.mod` includes QUIC, uTLS, REALITY, DNS, gRPC/protobuf, WireGuard/Wintun, Shadowsocks, platform networking and other dependencies. Root MPL alone is not the complete license/security answer.

### Current test workflow

Pinned CI runs format/protobuf checks plus `go test ./...` on Windows, Ubuntu and macOS. Product integration/Android/Apple/TV behavior still requires PVNetwork-specific tests.

## Client ecosystem findings

Primary architecture/UX references include v2rayN, v2rayNG, Hiddify, Karing, NekoBox, Throne and Happ, plus adjacent multi-core clients.

Important license separation:

- Xray-core: MPL-2.0
- v2rayN/v2rayNG: GPLv3
- Hiddify: Extended GPLv3 with additional conditions including non-commercial restriction without consent
- Karing: GPLv3-or-later + naming/association condition
- NekoBox/Throne: GPL-family reference-only by default
- Happ public desktop repo: source/license incomplete/unclear; product reference only

Never copy a GUI merely because its underlying engine is usable under a different license.

## libXray finding

Pinned:

`XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a`

Root wrapper license: MIT.

Current upstream documents:

- narrow versioned structured API;
- Xray lifecycle/config/share helpers;
- Android AAR/gomobile integration;
- Apple XCFramework for iOS/macOS/tvOS variants;
- Linux shared object;
- Windows DLL/C ABI;
- explicit lifetime/concurrency warnings around process-wide Xray state.

Provisional classification:

`STRONG-WRAPPER-CANDIDATE / LEGAL+PLATFORM+LIFECYCLE REVIEW REQUIRED`

The wrapper MIT license does not change Xray-core MPL or dependency obligations.

## Issue/regression lessons

- Xray stable-vs-main drift matters.
- Current Xray head fixes WireGuard outbound behavior tied to issue #6559.
- XHTTP/default-setting changes demonstrate that omitted fields can change semantics across core versions.
- Public detectability/security claims in issues must not be accepted as facts without corroborating evidence.
- Pinned v2rayN head fixes a TUN rule that could break system DNS while proxy connectivity remained healthy.
- Current v2rayNG localization explicitly distinguishes share-link export from full generated-config export.

## Current numbered relationship

At least entries 037–040, 074–076, 084, 086, 088, 089, 091 and 092 have direct Xray-family relevance, but they are a mix of protocol/security/flow/transport concepts.

Do not call all of them separate VPN protocols.

## Exact next action

1. Deep-audit libXray API/lifecycle/build/dependency surface and current issue history.
2. Create per-entry/core capability and `SUPPORT_REUSE_DECISIONS.md` for Xray-related numbered entries.
3. Expand v2rayNG source/storage/VpnService/import/menu evidence into split safe files.
4. Add Xray security/dependency-advisory review.
5. Map Xray commander/API/stats/runtime-control exposure.
6. Update Xray `INDEX.md` when the above are committed.
7. Decide whether Xray family is `V1-HANDOFF-READY`; preserve gaps explicitly.
8. Then select the next unfinished original family; keep WireGuard/AmneziaWG residual v1 closure queued.
9. After every meaningful unit, create a newer handoff and point `AGENTS.md` to it.

## Later mandatory second layer

After original v1 campaign gates, execute `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` for every applicable Xray-related entry, including official/community server installers/panels, server/client install matrices, complete menu maps, cryptography, wire/data flow, ports/transports/handshake and deployment topologies.
