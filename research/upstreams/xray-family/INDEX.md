# Xray Family Research Index

Research state: **`IN-RESEARCH / NOT IMPLEMENTED`**.

This is the shared source/core/client/architecture/security evidence index for Xray-related numbered entries. It is not a product support claim.

## Primary reviewed core

Repository:

`XTLS/Xray-core`

Current-main research pin:

`7d214f8b094f75322fa3990f8aadad1c912f24f5`

Pinned tree:

`46ee908a9a67513d3c85bbf998be5d553a078109`

Root license: **MPL-2.0**.

Current main is newer than the latest non-prerelease release returned by GitHub and must not automatically be treated as the production candidate.

## Critical release/security finding

Repository advisory:

`GHSA-5wf9-h793-w73c`

Published: 2026-07-10.

Upstream records:

- vulnerable range: `>= v26.1.13`
- patched versions: `>= v26.7.11`
- severity: low
- CWE-297 certificate-hostname validation class in certain certificate-pinning/Hy2/gRPC scenarios.

GitHub's current non-prerelease `releases/latest` result was **v26.3.27**, which lies inside the advisory's vulnerable range.

The release list also contains newer prerelease versions such as **v26.7.28**, and current main is newer again.

PVNetwork rule:

**never select an engine release merely because an API labels it latest/stable; candidate selection must be advisory-aware and regression-tested.**

See:

`SECURITY_AND_DEPENDENCY_ADVISORIES.md`

## Shared files

### Core / architecture

- `SOURCE_ARCHITECTURE.md`
- `CONFIG_CAPABILITY_MODEL.md`
- `DEPENDENCIES_TESTS_RELEASES.md`
- `XRAY_API_CONTROL.md`

### Security / issues / release lessons

- `SECURITY_AND_DEPENDENCY_ADVISORIES.md`
- `ISSUE_RELEASE_LESSONS.md`

### Reuse / support decisions

- `SUPPORT_REUSE_DECISIONS.md`

### Wrapper integration

- `LIBXRAY_WRAPPER.md`
- `LIBXRAY_API_LIFECYCLE.md`
- `LIBXRAY_ISSUE_LESSONS.md`

### Client ecosystem

- `CLIENT_ECOSYSTEM.md`
- related detailed client files under `research/upstreams/client-references/`, including current v2rayNG Android architecture/storage/menu research and v2rayN/Karing/Throne/etc references.

## libXray wrapper candidate

Reviewed wrapper:

`XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a`

Root wrapper license: MIT.

The wrapper does **not** change Xray-core's MPL obligations or third-party dependency licenses.

Current classification:

`STRONG-WRAPPER-CANDIDATE / LEGAL+PLATFORM+LIFECYCLE REVIEW REQUIRED`

Current evidence covers:

- versioned invocation API;
- one managed in-process Xray instance;
- C ABI memory ownership;
- Android socket/process/DNS integration;
- Android/Apple/Linux/Windows build artifacts;
- process-global lifecycle/concurrency warnings;
- issue-derived metrics/memory/input/version-coupling regression classes.

## Current numbered-entry relationship

Direct Xray-family relevance currently includes at least:

- 037 VLESS — application protocol
- 038 VMess — application protocol
- 039 Trojan — application protocol, multi-core candidate
- 040 Shadowsocks — proxy protocol, multi-core candidate
- 074 REALITY — security layer
- 075 XTLS — legacy security terminology/configuration
- 076 XTLS Vision — flow/mode
- 084 WebSocket — transport
- 086 HTTP/2-related historical/current transport classification
- 088 gRPC — transport
- 089 mKCP — transport
- 091 XHTTP — transport
- 092 RAW/TCP — transport

Do not market all of these as separate VPN protocols.

Numbered folders 037–040 and 074–076 have been upgraded from placeholders/missing generic entries to Xray-specific conclusions. Remaining related transport entries must be synchronized as part of current work.

## Current core architecture conclusion

Xray-core is a modular networking runtime, not one protocol implementation.

Pinned source separates:

- `core/` lifecycle/instance assembly;
- `app/` routing/DNS/dispatcher/proxyman/policy/stats/log/commander/observability;
- `proxy/` protocol implementations;
- `transport/` outer transports;
- `infra/conf/` human configuration conversion;
- common/session/mux/runtime utilities.

PVNetwork should place Xray behind a product-owned Core Adapter.

## Canonical profile rule

Do not use raw Xray JSON as PVNetwork's authoritative user database.

Use:

`original import -> canonical PVProfile -> version-aware capability validation -> generated Xray runtime config -> transient runtime state`

Preserve unsupported/unknown imported data where practical and mark lossy conversions explicitly.

## Current client reference status

Primary source/UX references include:

- v2rayN — GPLv3, desktop multi-core/source reference;
- v2rayNG — GPLv3, Android architecture/VpnService/storage/menu reference;
- Hiddify — current Extended GPLv3 with additional commercial/interface/fork conditions;
- Karing — GPLv3-or-later plus naming/association condition;
- NekoBox — GPL-family reference;
- Throne — GPL-3.0 reference;
- Happ — product/UX reference; reviewed public desktop source/license remains incomplete/unclear.

Application licenses must be separated from core/wrapper licenses.

## v2rayNG evidence now available

Under `research/upstreams/client-references/`:

- `V2RAYNG_ANDROID_ARCHITECTURE.md`
- `V2RAYNG_STORAGE_IMPORT.md`
- `V2RAYNG_CLIENT_UI_AND_MENUS_V1.md`

Current findings include:

- dedicated Android VPN/core daemon process;
- VpnService-owned route/DNS/MTU/per-app lifecycle;
- network handover/reload behavior;
- MMKV multi-process logical stores;
- profile schema with sensitive fields;
- no explicit MMKV cryptKey observed in reviewed manager initialization;
- separate raw profile/import storage and normalized profiles;
- QR/clipboard/file/manual import;
- share-link vs generated-full-config export distinction;
- subscriptions/routing/per-app/assets/settings/logs/backup/update/about navigation;
- Leanback/TV launcher indicators but no proof of exhaustive D-pad/10-foot UX.

PVNetwork should improve secret storage through platform secure storage and explicit credential references.

## Runtime-control policy

`XRAY_API_CONTROL.md` documents Commander/proxyman/stats/router capabilities.

Do not expose arbitrary Xray management gRPC to LAN/public interfaces or directly to UI/plugins. Management state is privileged and can mutate handlers/users/routes or expose privacy-sensitive stats.

## Main remaining v1 closure items

Before moving this shared family to `V1-HANDOFF-READY`, finish or explicitly preserve gaps for:

1. synchronize Xray-related transport numbered entries 084/086/088/089/091/092;
2. current v2rayNG workflow/CI/native-core pin audit;
3. stronger exact Xray patched release candidate comparison and SBOM/advisory state;
4. current libXray issues/releases mapping to wrapper/core pins;
5. current v2rayNG Android issue/regression sampling;
6. final family v1 status/handoff and residual-gap list;
7. update relevant v1 tracker/status evidence where connector allows.

## Mandatory later second layer

After original `COMPLETE-RESEARCH-v1` gates across the campaign, execute:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

for applicable Xray-related entries. That later phase adds:

- server implementations/forks/installers/panels;
- server install matrices and server UI/menu inventories;
- client OS install matrices and exhaustive client menus;
- cryptography;
- data path/wire flow;
- ports/transports/handshake;
- deployment topologies;
- full reference indexes.

Do not begin mass second-layer work while original v1 backlog remains unfinished.
