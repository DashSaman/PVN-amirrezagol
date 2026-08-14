# PVNetwork Research Campaign Status — 2026-08-14 — Xray v1 Work Unit 2

Repository phase: research / requirements / architecture.

Original `COMPLETE-RESEARCH-v1` remains the active campaign. Xray/modern-proxy shared-family research is now **`V1-HANDOFF-READY / NOT IMPLEMENTED`** with explicit residual gaps.

## New evidence since Xray v1 Work Unit 1

### libXray issue/lifecycle lessons

`research/upstreams/xray-family/LIBXRAY_ISSUE_LESSONS.md`

Commit: `156ab7e1dcebb6d26c5d3c3b13877aa507980ac7`

Captured:

- closed metrics/process-global panic class (#127);
- open possible long-running iOS memory-growth report (#104), explicitly unverified as universal bug;
- open iOS config-test/input-contract ambiguity (#118);
- wrapper/core release-cadence coupling (#123);
- regression requirements for process ownership, soak tests, native API typing and upgrade pinning.

### Xray shared index synchronized

Initial expanded index commit: `e354911126e313d05cfb72bb3d54743b9f9871cf`

Final v1 handoff state commit: `cabb346ac60636fe4110d6ca22e4917fac1b4bb0`

The index now records current source/core/wrapper/client/security evidence and explicit residual gaps.

### Transport numbered entries synchronized

- 084 WebSocket — `5cbb7873072156d1ebff4e9788d983cded65215e`
- 086 HTTP/2 semantics — `54492d79b0cded0529f9cb1738ed8ea8ee2b668e`
- 088 gRPC — `2c621c30a03bdee437bb15628444ef0f3870dc1b`
- 089 mKCP — `3541669fa8378d95458a280ccd620057bb17554f`
- 091 XHTTP — `f84c9328686eaeb465f98763de6096abeab46f11`
- 092 RAW/TCP — `462e18d6df70ba4f96f3f3dd9f1e6b2205dc7cd4`

These remain research-only and correctly classified as transport/building-block concepts rather than standalone VPN protocols.

### v2rayNG current build/CI/native supply-chain research

`research/upstreams/client-references/V2RAYNG_BUILD_CI.md`

Initial commit: `eae4a5f98a9e6544ba393fcbef7f6775848bfa2a`

Tag/supply-chain update: `cf9b016817ef85077d43b1065c3ebee3cd1507de`

Key evidence:

- current repo really has GitHub Actions despite internal guide text claiming no CI;
- build installs Android 37 / NDK 29 and builds native tun2socks;
- native Xray wrapper is a pinned submodule;
- pinned `AndroidLibXrayLite` commit `b21389865ed69ba01e81c1521965c27832a33cf9` maps exactly to tag `v26.7.31`;
- wrapper root license = LGPL-3.0;
- wrapper `go.mod` depends on a 2026-07-28 Xray-core pseudo-version;
- CI downloads prebuilt `libv2ray.aar` from the matching wrapper release instead of rebuilding the wrapper inside the main app workflow;
- this creates a supply-chain boundary that PVNetwork should strengthen through controlled builds/hashes/SBOM/provenance.

### Xray security advisory finding

`research/upstreams/xray-family/SECURITY_AND_DEPENDENCY_ADVISORIES.md`

Commit: `8c1acb20fa822795c6f0ca27d575a469f7f5c7ba`

Repository-published advisory:

`GHSA-5wf9-h793-w73c`

Published 2026-07-10; upstream vulnerable range `>= v26.1.13`; patched range `>= v26.7.11`; low severity; CWE-297 certificate-host mismatch class affecting certain certificate-pinning Hy2/gRPC cases.

Critical consequence:

GitHub's non-prerelease `releases/latest` result during research was `v26.3.27`, which is inside the advisory's vulnerable range. Newer prerelease releases and current main are beyond the patch threshold, but no production Xray pin is approved by research alone.

### Per-entry support/reuse decisions

`research/upstreams/xray-family/SUPPORT_REUSE_DECISIONS.md`

Commit: `6135fa78222e3a35b7383846e4aec8bce240140b`

Covers decisions for 037/038/039/040/074/075/076/084/086/088/089/091/092 and separates protocol/security/flow/transport semantics.

### Xray runtime control/API

`research/upstreams/xray-family/XRAY_API_CONTROL.md`

Commit: `b724496decadf7d47ea1ad3dd643875f63998ab0`

Documents Commander/proxyman/router/stats mutability/privacy risk and product rule that management endpoints remain private behind a PVNetwork Core Adapter.

### libXray API/lifecycle deep audit

`research/upstreams/xray-family/LIBXRAY_API_LIFECYCLE.md`

Commit: `fea75dbbfa187908403adc6ca3281f72dc9ee82e`

Documents API version 2, 16 MiB envelope limit, one managed in-process Xray instance, C ABI ownership, Android socket/DNS/process callbacks, cross-platform build artifacts and process-global-state caveats.

### v2rayNG Android client deep research

Committed files:

- `V2RAYNG_ANDROID_ARCHITECTURE.md` — `93f964d2fe5647c60db6d79b223efd7fd21963d8`
- `V2RAYNG_STORAGE_IMPORT.md` — `1fd792ebc2cf1c50f8e9383e9c85ed694dac05a4`
- `V2RAYNG_CLIENT_UI_AND_MENUS_V1.md` — `22d348cdc075cbe12e36f41faa163eb268f0c471`

Key findings include dedicated VPN/core daemon process, VpnService-owned lifecycle, network handover/reload, MMKV multi-process stores, versioned profile schema, separate raw imported data, subscription scheduling, QR/clipboard/file/manual import, distinct share-link vs full generated config, drawer/settings map and Android TV/Leanback indicators.

Security improvement requirement recorded: reusable passwords/keys/PSKs should be separated into platform secure storage in PVNetwork; explicit MMKV cryptKey was not observed in reviewed `MmkvManager` initialization paths.

### Numbered protocol/security entries synchronized

- 037 VLESS — `cb7d8fd30b4467934065dd6490946c64405d758e`
- 038 VMess — `2326c7baa1b9b8278949d0da2c7e7b8b178102b9`
- 039 Trojan — `eaaea80cd0e2082f3b4bcf9a74ea01798b882638`
- 040 Shadowsocks — `77981fa64af076c841528e5607e9d9d3f98ccf32`
- 074 REALITY — `0aaa80101215e7a33dbd03f5e5004a754a727b20`
- 075 XTLS legacy semantics — `a8b05c8ff8618680b290b3e91a6cabaa21ab04db`
- 076 XTLS Vision flow — `a5c9c3d3e4d519368b3ebf3cbb719098d05c5515`

## Why the shared family can move on

Original-v1 evidence is now broad across source, architecture, config semantics, dependencies/tests/releases, security, runtime control, wrapper API/lifecycle/issues, major Android client architecture/storage/menus/CI, client ecosystem licenses, representative regressions and per-entry decisions.

Holding the whole 93-entry campaign on implementation-only/real-device/server evidence would violate the priority structure; those remain explicit gaps and/or belong to mandatory v2.

## Residual gaps preserved

- exact patched production Xray-core release pin not approved;
- complete resolved SBOM/vulnerability/license scan for eventual shipped pin;
- stronger stable/prerelease/main comparative regression evidence;
- exact libXray wrapper/core historical release map;
- more current v2rayNG Android issue sampling;
- long-tail client menus/issues;
- real device/platform soak, background, handover, memory and Store evidence;
- actual performance benchmarks;
- server implementations/installers/panels/crypto/data-path/handshake/deployment are mandatory later in `COMPLETE-REFERENCE-v2`;
- no PVNetwork implementation/E2E certification exists.

## Next exact action

Return to remaining WireGuard/AmneziaWG original-v1 closure gaps already recorded in repository handoffs/state. After that, select the next unfinished original-v1 family from actual repository evidence and continue without waiting for owner input.
