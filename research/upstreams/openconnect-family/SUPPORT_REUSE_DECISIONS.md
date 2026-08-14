# OpenConnect Family — PVNetwork Support / Reuse Decision Record

Decision date: 2026-08-14

Decision level: **research architecture decision only**. Nothing here means PVNetwork currently implements or certifies these protocols.

## Shared core decision

### `libopenconnect`

Research classification: **REUSE-CANDIDATE / LGPL-DISTRIBUTION-REVIEW-REQUIRED**.

Why it remains attractive:

- mature C library with a versioned public API;
- consolidates multiple enterprise VPN compatibility families;
- substantial upstream protocol/authentication/test history;
- active canonical project and current stable v9.21 baseline;
- allows PVNetwork to keep vendor-specific transport logic behind one Enterprise Adapter rather than rewriting each protocol.

Required conditions before implementation approval:

- exact per-platform dependency/SBOM review;
- final LGPL distribution/linking design;
- public-API-only adapter boundary;
- no dependence on private/non-exported internals;
- platform-specific route/DNS/service architecture approved;
- browser/SSO and protected-secret services owned by PVNetwork/platform layer;
- vendor/version interoperability lab defined.

Preferred engineering direction for legal/platform review: replaceable shared-library boundary where technically and Store-feasible. Static linking is not the default approved path.

## 016 — Cisco AnyConnect-compatible

Research decision: **HIGH-PRIORITY CERTIFICATION CANDIDATE**.

Rationale:

- AnyConnect is OpenConnect's original and most mature compatibility family;
- strong candidate for early enterprise coverage;
- still requires exact Cisco server/version/auth-mode validation;
- external browser/SSO, MFA continuation and posture/CSD behavior must remain separate capabilities.

PVNetwork product claim allowed today: **none**.

Implementation path if approved: OpenConnect Enterprise Adapter + product Auth Challenge/Browser service + platform network service.

Main blockers before certification:

- actual Cisco interoperability lab;
- browser/SSO coverage;
- posture/CSD policy and safety decision;
- reconnect/network-change/platform matrix.

## 017 — OpenConnect / ocserv-compatible

Research decision: **FIRST CONTROLLED ENTERPRISE INTEGRATION TARGET**.

Rationale:

- open server counterpart makes deterministic integration testing feasible;
- ideal baseline for validating PVNetwork's Enterprise Adapter, auth challenge model, platform route/DNS lifecycle and packaging without requiring proprietary appliances;
- success here is not proof for Cisco or other vendor families.

PVNetwork product claim allowed today: **none**.

Recommended future role: first automated real-server lab target for the OpenConnect adapter.

## 018 — Palo Alto GlobalProtect

Research decision: **REUSE-CANDIDATE / VERSION-AND-CAPABILITY CERTIFICATION REQUIRED**.

Do not certify a generic “GlobalProtect supported” label. Separate at least:

- portal/gateway discovery;
- basic/certificate auth;
- SAML/browser SSO;
- gateway selection;
- HIP/posture behavior;
- reconnect/session renewal;
- exact PAN-OS/server versions.

PVNetwork product claim allowed today: **none**.

## 019 — Fortinet FortiGate SSL VPN

Research decision: **PARTIAL-UPSTREAM-CAPABILITY / CONDITIONAL CERTIFICATION ONLY**.

Reasons:

- OpenConnect documentation describes experimental/partial behavior;
- supported tunnel/auth behavior differs across FortiGate/FortiOS generations;
- SAML/SSO and reconnect behavior need current exact-version tests;
- unsupported/newer proprietary tunnel variants must not be hidden behind a generic success flag.

PVNetwork product claim allowed today: **none**.

## 020 — Pulse Secure

Research decision: **CONDITIONAL COMPATIBILITY TARGET**.

Reasons:

- usable tunnel/auth capability exists, but authentication/posture coverage is not universal;
- Host Checker/TNCC and other appliance policies can make tunnel success insufficient for enterprise access;
- IPv6/transport/auth behavior needs version-specific tests.

PVNetwork product claim allowed today: **none**.

## 021 — Ivanti Connect Secure

Research decision: **APPLIANCE-VERSION MATRIX REQUIRED; DO NOT MODEL AS A SINGLE NEW PROTOCOL**.

Treat Ivanti/Pulse branding/product evolution as server/appliance compatibility metadata above the OpenConnect Pulse/Juniper modes. Certification must record the exact appliance version and selected protocol/auth path.

PVNetwork product claim allowed today: **none**.

## 022 — Juniper Network Connect

Research decision: **LEGACY ENTERPRISE COMPATIBILITY TARGET / LOWER PRIORITY THAN MODERN BASELINES**.

Reasons:

- different protocol/auth behavior from Pulse;
- browser-like custom login pages and posture/TNCC can be significant;
- legacy limitations such as IPv6 behavior must be explicit;
- valuable for existing enterprise deployments but not a default consumer flow.

PVNetwork product claim allowed today: **none**.

## 023 — F5 BIG-IP SSL VPN

Research decision: **EXPERIMENTAL/PARTIAL CERTIFICATION CANDIDATE**.

Reasons:

- upstream support is experimental and authentication pages can be browser/JavaScript heavy;
- common basic/certificate/domain flows may work while other MFA/SAML/proprietary flows do not;
- data-transport behavior can depend on server generation/configuration.

PVNetwork product claim allowed today: **none**.

## 024 — Array Networks SSL VPN

Research decision: **LIMITED/EXPERIMENTAL COMPATIBILITY TARGET**.

Reasons:

- current upstream documentation describes limited authentication coverage;
- historical transport/framing and security-policy compatibility require explicit tests;
- likely lower priority than Cisco/ocserv/GlobalProtect for initial certification.

PVNetwork product claim allowed today: **none**.

## Priority ordering for future implementation/certification

Provisional research ordering, subject to product demand and legal/platform feasibility:

1. 017 OpenConnect/ocserv — controlled integration baseline.
2. 016 Cisco AnyConnect-compatible — highest-value proprietary enterprise target.
3. 018 GlobalProtect — strong enterprise target with SSO/HIP capability matrix.
4. 019 Fortinet — only for explicitly tested FortiOS/protocol modes.
5. 020/021 Pulse/Ivanti — version/auth/posture matrix.
6. 022 Juniper Network Connect — legacy compatibility.
7. 023 F5 — experimental/vendor-specific auth matrix.
8. 024 Array — limited/experimental, demand-driven.

This ordering is not a marketing ranking and can change with user demand or new upstream evidence.

## Architecture decision common to all entries

Use one product-facing Enterprise Adapter contract, but return a structured capability result rather than a Boolean protocol flag.

At minimum the eventual capability/certification record must distinguish:

- server/vendor/version;
- protocol mode;
- authentication modes;
- client certificate support;
- MFA/challenge support;
- external browser/SSO support;
- posture/host-check behavior;
- IPv4/IPv6;
- preferred/fallback transport;
- reconnect/network-change behavior;
- tested PVNetwork platform/build;
- known limitations.

## Reuse prohibitions / cautions

- Do not copy OpenConnect GUI wholesale into a closed PVNetwork product; its application license is GPL-2.0-or-later.
- Do not copy general NetworkManager-openconnect UI/plugin code without path-level license review.
- Do not infer frontend license from `libopenconnect` LGPL or vice versa.
- Do not use project/vendor branding/assets as PVNetwork branding.
- Do not claim vendor support from upstream protocol-mode presence alone.

## `COMPLETE-RESEARCH-v1` closure position for this family

Shared source/core/client/frontend/license/architecture/issues/tests/storage/security/packaging/performance/reuse evidence is now broad enough for a **reasonable family-level v1 handoff**, with explicit remaining evidence gaps rather than hidden assumptions.

Still-open research items:

- authoritative materialized full v9.21 source-archive manifest remains tool-blocked;
- current canonical OpenConnect GUI main recursive source pin/tree needs stronger machine-readable materialization;
- asset/screenshot catalog is reference-level, not a current running-client capture set;
- exact dependency-advisory/SBOM review must be repeated for the build actually selected;
- vendor certification needs real server/version labs and cannot be completed before implementation/testing.

Therefore the family can move from active closure work to **`V1-HANDOFF-READY / NOT IMPLEMENTED`**, while numbered entries remain uncertified until future implementation evidence exists.
