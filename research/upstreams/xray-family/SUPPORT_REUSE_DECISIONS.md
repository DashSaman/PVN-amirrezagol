# Xray Family — PVNetwork Support / Reuse Decision Record

Decision date: 2026-08-14

State: **research decision only**. Nothing here means PVNetwork currently implements, certifies or advertises these entries.

## Shared engine decision — Xray-core

Pinned research baseline:

`XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`

Root license at pin: **MPL-2.0**.

Research classification:

**`STRONG-CORE-CANDIDATE / SBOM+PLATFORM+MPL-OBLIGATION REVIEW REQUIRED`**

Why:

- mature and active multi-protocol core;
- modular source layout separates protocol, transport, routing/DNS and runtime services;
- strong fit for VLESS/REALITY/Vision/XHTTP-centric coverage;
- cross-platform core build evidence;
- upstream tests across desktop OSes;
- narrower reuse path than forking GPL GUI applications.

Before implementation approval:

- choose/pin an actual release candidate, not merely current `main`;
- full dependency/license/security SBOM;
- decide subprocess vs wrapper/FFI per platform;
- define runtime control and log-redaction boundary;
- build product-owned canonical profile + version-aware capability validator;
- test every advertised protocol/security/flow/transport combination;
- verify platform Store/lifecycle feasibility.

## Shared wrapper decision — libXray

Pinned:

`XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a`

Wrapper license: MIT.

Research classification:

**`STRONG-WRAPPER-CANDIDATE / Xray-MPL+DEPENDENCY+LIFECYCLE REVIEW REQUIRED`**

Why:

- narrow wrapper instead of full GUI fork;
- versioned native boundary;
- Android, Apple, Linux and Windows artifact paths;
- share/config/lifecycle helpers;
- active source in 2026.

Caveat: process-wide Xray state/concurrency and platform DNS/socket behavior require strict owner/lifetime rules.

## 037 — VLESS

Research classification:

**`HIGH-PRIORITY XRAY-NATIVE TARGET / EXACT COMBINATION CERTIFICATION REQUIRED`**

Rationale:

- first-class Xray protocol implementation and source/config tests;
- central to current Xray ecosystem;
- commonly paired with security/flow/transport capabilities that are independently versioned.

PVNetwork must not certify “VLESS” as one monolithic feature. Record at least:

- core version;
- authentication/identity fields;
- flow setting;
- security layer;
- transport;
- IPv4/IPv6 behavior;
- UDP behavior where applicable;
- routing/DNS interaction;
- tested server/client combination.

Product claim allowed today: **none**.

## 038 — VMess

Research classification:

**`COMPATIBILITY TARGET / MATURE ECOSYSTEM / LOWER STRATEGIC PRIORITY THAN VLESS`**

Why:

- established Xray/V2Ray family support and broad installed-base relevance;
- useful for compatibility/import migration;
- should not dominate new PVNetwork architecture when newer Xray-native combinations are the main innovation path.

Requirements:

- exact cipher/security/profile semantics preserved;
- import/share-link compatibility tests;
- server version/core-family compatibility matrix;
- no automatic conversion to VLESS unless user explicitly chooses a semantically understood migration.

Product claim today: **none**.

## 039 — Trojan

Research classification:

**`MULTI-CORE COMPATIBILITY TARGET / CORE-SELECTION BENCHMARK REQUIRED`**

Trojan is supported by multiple modern cores. Xray is a candidate, not automatically the best engine for every platform.

Before selecting Xray as default Trojan engine, compare against other approved candidate cores for:

- protocol feature coverage;
- TLS/transport combinations;
- platform integration;
- performance/resource use;
- dependency/license burden;
- maintenance/regression history.

PVNetwork canonical Trojan profile should remain core-neutral where semantics permit.

Product claim today: **none**.

## 040 — Shadowsocks

Research classification:

**`MULTI-CORE / DEDICATED-IMPLEMENTATION COMPARISON REQUIRED`**

Xray supports Shadowsocks, but dedicated and alternative engines exist. Do not select Xray solely because it already exists in the process.

Compare at least:

- current cipher/method coverage;
- modern Shadowsocks variants;
- UDP behavior;
- plugin/transport compatibility;
- performance;
- platform packaging;
- security/maintenance;
- license/dependency impact.

A dedicated Shadowsocks implementation may be preferable if it materially reduces complexity or improves compatibility.

Product claim today: **none**.

## 074 — REALITY

Classification:

**`SECURITY-LAYER CAPABILITY / XRAY PRIMARY REFERENCE`**

REALITY is not a standalone VPN protocol. It is a security-layer capability used with supported protocol/transport combinations.

Pinned Xray source treats REALITY separately from transport and validates compatible transport families.

PVNetwork UI/matrix must expose it as a security/capability dimension, not inflate protocol counts by calling it an independent VPN protocol.

Xray is the primary reference/engine candidate for this capability.

Product claim today: **none**.

## 075 — XTLS

Classification:

**`LEGACY TERMINOLOGY / DO NOT ADVERTISE OLD XTLS SECURITY MODE AS CURRENT FEATURE`**

Pinned Xray configuration source explicitly treats legacy `xtls` security configuration as removed and points toward current Vision flow usage with TLS/REALITY.

PVNetwork requirements:

- keep historical XTLS metadata for imported legacy profiles/documentation;
- do not present a current generic “XTLS security” toggle if the selected core no longer supports that semantic;
- guide migration through explicit, version-aware validation rather than silent rewrite.

Product claim today: **none**.

## 076 — XTLS Vision

Classification:

**`CURRENT FLOW/MODE CAPABILITY / COMBINATION CERTIFICATION REQUIRED`**

Vision is a flow/mode, not a standalone transport or VPN protocol.

It must be certified in combination with:

- application protocol (especially VLESS contexts);
- TLS or REALITY security as applicable;
- selected transport;
- client/server core versions;
- platform/runtime behavior.

PVNetwork UI should expose flow only when the chosen protocol/core/version supports it.

Product claim today: **none**.

## 084 — WebSocket

Classification:

**`COMPATIBILITY TRANSPORT / DEPRECATION-AWARE`**

Pinned Xray config still recognizes WebSocket but emits a deprecation guidance toward newer XHTTP H2/H3-style usage for some scenarios.

PVNetwork requirements:

- continue importing/storing WebSocket for installed-base compatibility while selected Xray release supports it;
- do not silently migrate to XHTTP because semantics/infrastructure can differ;
- mark future deprecation state by core version;
- keep transport choice independent from protocol.

Product claim today: **none**.

## 086 — HTTP/2 transport classification

Classification:

**`MIGRATION/SEMANTIC CLASSIFICATION REQUIRED`**

Pinned source marks the old generic HTTP transport path as removed in favor of XHTTP stream modes for modern H2/H3-style use.

PVNetwork must distinguish:

- historical Xray “HTTP/H2 transport” configuration;
- current XHTTP modes that can use HTTP/2 behavior;
- generic HTTP/2 as an underlying protocol technology.

Do not store all three as one ambiguous `http2=true` field.

Product claim today: **none**.

## 088 — gRPC

Classification:

**`SUPPORTED COMPATIBILITY TRANSPORT / DEPRECATION-GUIDANCE-AWARE`**

Pinned source recognizes gRPC transport while emitting guidance toward newer XHTTP stream-up H2 usage.

PVNetwork should:

- preserve/import gRPC profiles while core support remains;
- surface deprecation/migration information without silently rewriting;
- test exact gRPC interoperability and server deployment assumptions;
- keep it a transport capability, not a standalone VPN protocol.

Product claim today: **none**.

## 089 — mKCP

Classification:

**`LEGACY/COMPATIBILITY TRANSPORT TARGET`**

Pinned source still recognizes mKCP/KCP configuration.

PVNetwork should support it only if real user demand and selected core support justify the maintenance/test burden. It is not a reason to expand normal-user UI complexity.

Requirements:

- import compatibility;
- effective-parameter validation;
- resource/performance testing;
- server/client core-version interoperability.

Product claim today: **none**.

## 091 — XHTTP

Classification:

**`HIGH-PRIORITY CURRENT XRAY TRANSPORT TARGET`**

Pinned source has dedicated XHTTP/split-HTTP handling and current migration guidance often points toward XHTTP-based modes.

This makes XHTTP strategically important for Xray-family support, but its configuration surface is evolving and defaults/version changes have already produced operational issue discussions.

PVNetwork requirements:

- pin selected core release;
- model explicit values vs unspecified/defaulted values;
- validate protocol/security/flow combinations;
- preserve version metadata;
- add default-drift regression tests;
- benchmark/resource-test actual modes used by PVNetwork;
- avoid exposing raw expert fields in Simple Mode.

Product claim today: **none**.

## 092 — RAW / TCP

Classification:

**`FOUNDATIONAL TRANSPORT TARGET`**

Pinned source maps RAW/TCP to the TCP transport implementation and retains raw/tcp settings aliases.

PVNetwork requirements:

- canonical model should choose one unambiguous internal transport identity and preserve imported alias/source metadata;
- test header/settings semantics;
- keep outer TCP transport separate from application protocol and security layer.

Product claim today: **none**.

## Shared client-reference decision

### Reference-only GUI applications by default

- v2rayN — GPLv3
- v2rayNG — GPLv3
- Hiddify — Extended GPLv3 with additional commercial/interface/fork conditions
- Karing — GPLv3-or-later plus naming/association restriction
- NekoBox — GPL-family
- Throne — GPL-3.0
- Happ public desktop repo — incomplete/unclear application-source licensing

Use these for UX/source/bug/storage/platform lessons unless the owner deliberately chooses a compatible source-distribution model or obtains separate rights.

## Current implementation architecture recommendation

For Xray-family research, the preferred comparison is:

### Option A — libXray wrapper

Good cross-platform narrow boundary; especially attractive for Android/Apple/native-app integration if lifecycle/Store/legal tests pass.

### Option B — managed Xray subprocess

Potentially attractive on desktop/server-like platforms for crash isolation, replaceability and simpler MPL-covered component separation.

### Option C — other narrow wrapper

Only if it provides a materially stronger API/lifecycle/platform model than libXray.

### Avoid as default

Forking a full GPL GUI client just to obtain Xray connectivity for a closed commercial PVNetwork product.

## Family v1 closure blockers still open

- deeper libXray lifecycle/API/issue review;
- Xray runtime commander/API/stats exposure map;
- v2rayNG Android architecture/storage/VpnService/menu split dossier;
- Xray security/dependency-advisory review;
- numbered entry synchronization;
- exact stable-release-vs-main decision;
- final family index/status update.

Until these are addressed, Xray family remains `IN-RESEARCH`.
