# AGENTS Handoff — 2026-08-14 — Xray v1 Closure

Mandatory continuation checkpoint for `DashSaman/PVN-amirrezagol`.

## State transition

Shared Xray/modern-proxy family research is now:

**`V1-HANDOFF-READY / NOT IMPLEMENTED`**

This is a research handoff state only. It is not product support, implementation, E2E certification, Store approval or security certification.

Original `COMPLETE-RESEARCH-v1` campaign remains active across the full 93-entry scope.

## Current source/core pins

### Xray-core

- repo: `XTLS/Xray-core`
- research main pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- root license: MPL-2.0

### libXray

- repo: `XTLS/libXray`
- pin: `d0ab60ae4dd91cf119c878152d12103e6f84b78a`
- root wrapper license: MIT
- underlying Xray-core/dependencies retain their own licenses.

### v2rayNG

- app pin: `2dust/v2rayNG@e8a82d9810ca1cf97a3cc8a9b9525a9f21955807`
- app license: GPLv3
- native wrapper submodule pin: `2dust/AndroidLibXrayLite@b21389865ed69ba01e81c1521965c27832a33cf9`
- exact submodule tag: `v26.7.31`
- wrapper root license: LGPL-3.0

## Critical security/release finding

Xray repository advisory:

`GHSA-5wf9-h793-w73c`

Upstream records vulnerable range `>= v26.1.13` and patched versions `>= v26.7.11`.

GitHub's non-prerelease `releases/latest` result during research was `v26.3.27`, which is inside the vulnerable range. Newer prerelease builds and current main are past the patch threshold, but no production pin is approved by research alone.

Do not select `v26.3.27` simply because it is marked latest non-prerelease.

## New evidence committed in Xray work unit 2

### libXray issue lessons

`research/upstreams/xray-family/LIBXRAY_ISSUE_LESSONS.md`

Commit: `156ab7e1dcebb6d26c5d3c3b13877aa507980ac7`

### Shared index expansion + final v1 handoff state

- expansion: `e354911126e313d05cfb72bb3d54743b9f9871cf`
- final v1 state: `cabb346ac60636fe4110d6ca22e4917fac1b4bb0`

### Numbered transport entries

- 084 WebSocket — `5cbb7873072156d1ebff4e9788d983cded65215e`
- 086 HTTP/2 — `54492d79b0cded0529f9cb1738ed8ea8ee2b668e`
- 088 gRPC — `2c621c30a03bdee437bb15628444ef0f3870dc1b`
- 089 mKCP — `3541669fa8378d95458a280ccd620057bb17554f`
- 091 XHTTP — `f84c9328686eaeb465f98763de6096abeab46f11`
- 092 RAW/TCP — `462e18d6df70ba4f96f3f3dd9f1e6b2205dc7cd4`

### v2rayNG build/native supply chain

`research/upstreams/client-references/V2RAYNG_BUILD_CI.md`

- initial: `eae4a5f98a9e6544ba393fcbef7f6775848bfa2a`
- exact wrapper tag update: `cf9b016817ef85077d43b1065c3ebee3cd1507de`

### Xray security dossier

`research/upstreams/xray-family/SECURITY_AND_DEPENDENCY_ADVISORIES.md`

Commit: `8c1acb20fa822795c6f0ca27d575a469f7f5c7ba`

### Status snapshot

`docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_XRAY_V1_2.md`

Commit: `ba5c18ad5a616cd47f54b7bdc513a0fdfa863d2b`

## Full Xray shared evidence to read

Under `research/upstreams/xray-family/`:

- `INDEX.md`
- `SOURCE_ARCHITECTURE.md`
- `DEPENDENCIES_TESTS_RELEASES.md`
- `CONFIG_CAPABILITY_MODEL.md`
- `SUPPORT_REUSE_DECISIONS.md`
- `ISSUE_RELEASE_LESSONS.md`
- `SECURITY_AND_DEPENDENCY_ADVISORIES.md`
- `XRAY_API_CONTROL.md`
- `LIBXRAY_WRAPPER.md`
- `LIBXRAY_API_LIFECYCLE.md`
- `LIBXRAY_ISSUE_LESSONS.md`
- `CLIENT_ECOSYSTEM.md`

Detailed client evidence under `research/upstreams/client-references/` includes:

- `V2RAYNG_ANDROID_ARCHITECTURE.md`
- `V2RAYNG_STORAGE_IMPORT.md`
- `V2RAYNG_CLIENT_UI_AND_MENUS_V1.md`
- `V2RAYNG_BUILD_CI.md`
- `V2RAYN_INDEX.md`
- Karing/Throne/Happ/Clash/FlClash/Amnezia references.

## Key product architecture conclusions

- Xray is a modular runtime, not one protocol.
- protocol, transport, security and flow are different axes.
- raw Xray JSON must not be PVNetwork's authoritative database.
- use canonical PVProfile + adapter-version-aware validation + generated runtime config.
- management Commander/proxyman/router/stats APIs are privileged/private, not general LAN/public product APIs.
- libXray is a strong narrow wrapper candidate but process-global state, lifecycle, Store feasibility and SBOM must be validated.
- v2rayNG proves Android product networking lifecycle remains outside the Xray core: VpnService, TUN, DNS, routes, per-app, handover, process state, notifications and Always-On are product/platform responsibilities.
- profile/application secrets need stronger product secure-storage design than ordinary profile JSON/MMKV storage.

## Numbered entries covered at research-decision level

- 037 VLESS
- 038 VMess
- 039 Trojan
- 040 Shadowsocks
- 074 REALITY
- 075 XTLS legacy semantics
- 076 XTLS Vision
- 084 WebSocket
- 086 HTTP/2 semantics
- 088 gRPC
- 089 mKCP
- 091 XHTTP
- 092 RAW/TCP

Do not treat these all as standalone VPN protocols.

## Residual Xray gaps — preserve, do not hold overall campaign hostage

- exact patched production Xray pin not selected;
- complete final SBOM/license/vulnerability scan for future shipped pin;
- stable/prerelease/main regression comparison can be deepened;
- longer libXray release/core mapping;
- more current Android issue sampling and real-device tests;
- long-tail client menu/issue research;
- performance/soak/Store evidence;
- server-side implementations/installers/crypto/wire-flow remain mandatory later in `COMPLETE-REFERENCE-v2`;
- no product implementation or certification exists.

## Exact next action

1. Update machine Run State/checkpoint/Project State/AGENTS pointer to leave Xray and activate remaining WireGuard/AmneziaWG original-v1 closure.
2. Read the actual current `research/upstreams/wireguard-family/` tree and previous WireGuard handoff/status evidence.
3. Close high-value remaining original v1 gaps: Windows architecture/storage, AmneziaWG platform/source/version evidence, dependency/SBOM/license distinctions, client/source references, issue/regression gaps, per-entry 002/003 decisions, shared family index/state.
4. If WireGuard family reaches reasonable `V1-HANDOFF-READY`, checkpoint it and immediately choose next unfinished original-v1 family from actual repository state.
5. Do not start mass `COMPLETE-REFERENCE-v2` until original v1 campaign gates reach intended state.
