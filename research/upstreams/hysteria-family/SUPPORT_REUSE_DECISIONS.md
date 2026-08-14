# Hysteria Family — PVNetwork Support / Reuse Decision Record

Decision date: 2026-08-14

State: research architecture decision only. No PVNetwork implementation/certification exists.

## Shared upstream

Repository: `apernet/hysteria`

Root license reviewed: MIT.

Current upstream source is Hysteria2-focused. Hysteria1 legacy compatibility must be independently pinned/tested.

## 042 — Hysteria (legacy v1)

Research classification:

**`LEGACY COMPATIBILITY TARGET / DO NOT INFER FROM HYSTERIA2`**

### Product direction

Keep entry 042 because installed deployments/configurations may still exist, but do not prioritize it over current Hysteria2 unless user/server demand justifies the maintenance burden.

Before PVNetwork can claim support:

- pin an actual v1-compatible source/client/server version;
- define legacy URI/config schema;
- audit security/dependencies separately;
- test exact client/server interoperability;
- mark legacy status in UI;
- define safe migration guidance without silent conversion.

### Engine decision

Do not assume current Hysteria2 engine accepts v1 profiles or speaks v1 protocol.

A separate legacy component/version may be required.

### Product claim today

None.

---

## 043 — Hysteria2

Research classification:

**`HIGH-PRIORITY MODERN QUIC PROXY TARGET / UPSTREAM ENGINE CANDIDATE`**

### Why

- current actively maintained upstream;
- MIT root license;
- designed around QUIC/TLS with UDP/TCP forwarding use cases;
- useful modern transport behavior for difficult/high-loss networks;
- server and client source available in one project;
- broad client ecosystem support through modern multi-protocol clients.

### Integration direction

Evaluate the official upstream client/core behind a PVNetwork Hysteria Adapter.

Product-owned layers remain:

- canonical profile;
- secure auth-secret storage;
- TLS/trust policy;
- TUN/DNS/routing;
- platform service/extension lifecycle;
- UI/localization;
- diagnostics;
- Store/package/update.

### Canonical model

Keep separate:

- endpoint;
- authentication;
- TLS/server-name/certificate policy;
- QUIC/session/transport settings;
- bandwidth/congestion settings;
- UDP/TCP behavior;
- engine/core version;
- original imported source.

Do not store the entire profile as one opaque URI string.

### Simple vs advanced UI

Simple mode:

- server/profile;
- connect/disconnect;
- auth prompt/status;
- basic latency/traffic.

Advanced mode:

- TLS/certificate options;
- QUIC/session parameters;
- bandwidth/congestion tuning;
- protocol-specific settings only when the selected core version supports them.

Do not ask ordinary users to guess low-level QUIC tuning values.

### Reuse classification

Current upstream:

**`STRONG REUSE-CANDIDATE / EXACT-RELEASE+SBOM+PLATFORM REVIEW REQUIRED`**

Prefer the official engine/core rather than copying a third-party GUI client.

### Product claim today

None.

---

# Multi-client ecosystem rule

Hiddify, Karing, NekoBox, sing-box/Mihomo-based clients and other modern GUIs may support Hysteria2 and are valuable UX/import/reference sources.

Their licenses and engines differ. Do not infer Hysteria upstream's MIT license applies to those applications.

PVNetwork should use third-party clients mainly to learn:

- import/subscription UX;
- config naming;
- mobile/desktop lifecycle;
- routing/DNS issues;
- error states;
- regression history.

## Engine duplication rule

If another approved core already implements Hysteria2 fully and reliably, compare it with direct upstream integration before shipping two engines.

Decision criteria:

- protocol feature parity;
- release lag;
- performance;
- QUIC behavior;
- platform support;
- dependency/license burden;
- security patch latency;
- binary size;
- adapter complexity.

Do not add a standalone Hysteria core merely because it exists if an existing approved engine offers equivalent certified behavior.

## Family v1 closure position

Current evidence covers:

- current upstream/source/license role;
- v1 vs v2 separation;
- QUIC/TLS/auth/product architecture;
- dependency/security/test rules;
- per-entry support/reuse decisions.

Remaining source-release/API/client issue details can be explicit residual gaps without blocking the full 93-entry campaign.

## Residual gaps

- exact current Hysteria2 release/commit and resolved SBOM;
- exact library/API vs subprocess integration boundary;
- legacy Hysteria1 exact source/version;
- current issue/release regressions;
- full GUI/client menu references;
- real-device/performance/Store evidence;
- later server installers/menus/cryptography/wire-flow v2 work.
