# PVNetwork Research Campaign Status — 2026-08-14 — Xray v1 Work Unit 1

Repository phase: research / requirements / architecture.

Priority: continue original `COMPLETE-RESEARCH-v1`; do not start mass `COMPLETE-REFERENCE-v2` yet.

## Why Xray family was selected

OpenConnect/Enterprise reached `V1-HANDOFF-READY / NOT IMPLEMENTED` at the shared-family research level. Xray/modern-proxy research existed only as a small shared index plus scattered client references, while it affects many numbered entries and is a major candidate engine family.

## Current Xray source baseline

Repository:

`XTLS/Xray-core`

Pinned current main head:

`7d214f8b094f75322fa3990f8aadad1c912f24f5`

Pinned tree:

`46ee908a9a67513d3c85bbf998be5d553a078109`

Commit date: 2026-08-12.

Current latest stable release observed:

`v26.3.27` (2026-03-27).

Root license at pin: MPL-2.0.

Important consequence: current main is newer than latest stable and contains fixes not present in the stable tag; production candidate selection needs a release/main comparison.

## Files created in this work unit

### `SOURCE_ARCHITECTURE.md`

Commit:

`e200ad6c01c190ff267ea7ef3182fecda60e11bc`

Maps:

- `core/` runtime instance/lifecycle;
- `app/` dispatcher/router/DNS/proxyman/policy/stats/log/commander/observatory/etc.;
- `proxy/` protocol modules;
- `transport/` separation;
- `infra/conf/` human config -> runtime structures;
- `common/` utilities/session/mux/log;
- scenario/unit test presence;
- product-owned adapter/canonical-profile boundary.

### `DEPENDENCIES_TESTS_RELEASES.md`

Commit:

`1083c4e9ebec66b8b2734da2ba7af00bd041f3b2`

Records:

- Go 1.26 pinned module baseline;
- major dependency categories including QUIC, uTLS, REALITY, DNS, gRPC/protobuf, WireGuard/Wintun, Shadowsocks and platform networking;
- root MPL is not the complete SBOM/license answer;
- upstream test workflow runs formatting/protobuf checks and Go tests across Windows/Ubuntu/macOS;
- stable-release vs current-main drift;
- product upgrade gate.

### `CONFIG_CAPABILITY_MODEL.md`

Commit:

`ad5135feed8c9cf5aeab3eb2e24ee84451850443`

Establishes that protocol, transport, security, flow and routing/DNS are separate product axes. Current pinned source shows active/deprecated/removed transport/security behavior, proving PVNetwork must use adapter-version-aware capability validation and must never silently rewrite unsupported combinations.

### `CLIENT_ECOSYSTEM.md`

Commit:

`4c08b98287a95b3e8a42b7c9138a7575129564b1`

Maps primary references:

- v2rayN;
- v2rayNG;
- Hiddify;
- Karing;
- NekoBox;
- Throne;
- Happ;
- selected adjacent multi-core clients.

Important license position remains:

- Xray-core MPL-2.0;
- v2rayN/v2rayNG GPLv3;
- Hiddify current Extended GPLv3 with additional commercial/interface/fork conditions;
- Karing GPLv3-or-later plus naming/association condition;
- NekoBox/Throne GPL families;
- Happ reviewed public desktop repo is not confirmed full licensed source.

Client license must never be inferred from engine license.

### `ISSUE_RELEASE_LESSONS.md`

Commit:

`23e8f43c3a9b16900c83d7eaea33c6e04a6bb5da`

Captures evidence/failure classes including:

- Xray core stable-vs-main drift;
- current head fix for WireGuard outbound behavior / issue #6559;
- default-setting/version-drift risks in XHTTP-style configurations;
- protocol/security/flow/transport combination validation;
- contested public security/detectability claims must not be accepted without corroboration;
- current v2rayN TUN self-address/DNS regression and restored tests;
- current v2rayNG distinction between share-link export and full generated-config export.

### `LIBXRAY_WRAPPER.md`

Commit:

`9cd861ce44e659a912f1a40d8a9b737046fb927e`

Pinned wrapper:

`XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a`

Root wrapper license: MIT.

Current upstream describes libXray as a narrow Go wrapper around Xray-core with a versioned structured API and build outputs for Android, Apple, Linux and Windows.

Important: libXray MIT does not change Xray-core MPL or dependency licenses.

Provisional classification:

`STRONG-WRAPPER-CANDIDATE / LEGAL+PLATFORM+LIFECYCLE REVIEW REQUIRED`

It is a stronger architectural reuse candidate than copying a GPL client GUI merely to obtain Xray integration.

## Current high-value regression lesson

Pinned v2rayN head `e01717d8326a4f5060b335523590c5fda943fe03` fixes a TUN routing rule that could block the resolver address and cause system DNS failure even while the proxy path remained healthy.

PVNetwork must independently test:

- engine connection state;
- TUN routing;
- system DNS;
- resolver addresses;
- route-loop prevention;
- cleanup after reconnect/failure.

## Current Xray-family numbered scope relationship

At minimum related entries include:

- 037 VLESS
- 038 VMess
- 039 Trojan
- 040 Shadowsocks
- 074 REALITY
- 075 XTLS
- 076 XTLS Vision
- 084 WebSocket
- 086 HTTP/2-related transport classification
- 088 gRPC
- 089 mKCP
- 091 XHTTP
- 092 RAW

These are not all equivalent protocol types. Maintain protocol/security/flow/transport classifications.

## Current state

Xray shared family is still `IN-RESEARCH`; not `V1-HANDOFF-READY` yet.

## Next exact action

1. audit `libXray` API/lifecycle/dependencies and current issues in more detail;
2. build Xray per-protocol/core capability and support/reuse decision matrix;
3. expand v2rayNG source/storage/VpnService evidence;
4. expand security/dependency advisory evidence;
5. map Xray API/commander/stats/control ownership;
6. update shared `INDEX.md` and then decide whether family is broad enough for v1 handoff;
7. persist every meaningful work unit in a newer AGENTS handoff.
