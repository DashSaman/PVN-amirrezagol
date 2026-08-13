# PVNetwork Architecture — Research Baseline

Status: **Research baseline; no production implementation exists yet.**

## 1. Architectural goal

PVNetwork should expose one consistent branded user experience while delegating protocol handling to carefully selected mature upstream engines or official platform networking APIs.

High-level structure:

```text
PVNetwork Presentation Layer
        -> Unified Application Layer
        -> Core Adapter Layer
        -> Approved Upstream Engines / Platform APIs
```

The Unified Application Layer is expected to eventually cover profile management, subscriptions, import/detection, connection state, routing policy, DNS policy, credentials, diagnostics, localization, and platform services.

## 2. Core independence

The UI must not depend directly on one specific protocol engine.

A stable adapter boundary should make it possible to add, replace, or remove an engine without redesigning the product UX.

Candidate adapter responsibilities include capability discovery, profile validation, configuration translation, lifecycle control, state reporting, statistics, diagnostics, and version reporting.

## 3. Internal profile model

PVNetwork should eventually use a versioned internal profile model instead of making application storage depend directly on any one upstream configuration format.

Conceptual categories:

- identity
- endpoint
- authentication
- transport
- security
- routing
- DNS
- metadata
- subscription source
- engine-specific extension data

The model should preserve unknown/vendor-specific information where feasible and warn when conversion is lossy.

## 4. Universal import direction

Research should cover importing common configuration files, structured JSON/YAML, QR codes, clipboard content, subscription URLs, and supported protocol URI formats.

The conceptual flow is:

```text
Input -> Detect -> Parse -> Identify protocol -> Validate -> Select compatible engine -> Normalize -> Save
```

## 5. Unified connection state

Different engines should map into one consistent product state model, for example:

- Disconnected
- Preparing
- Requesting permission
- Connecting
- Authenticating
- Establishing connection
- Connected
- Reconnecting
- Disconnecting
- Error

The product UI must reflect real verified state rather than simulated success.

## 6. Platform separation

Shared product logic should not hide real OS differences.

Research targets:

- Android phones/tablets/foldables
- Android TV / Google TV
- Windows
- macOS
- iPhone/iPad
- Linux

Each platform may require different official APIs, packaging, permissions, background behavior, and distribution strategy.

Android TV must have a TV-specific UX with remote/D-pad focus behavior rather than a stretched phone interface.

## 7. Current engine candidates

These are **research candidates only**, not approved implementation commitments:

1. OpenVPN 3
2. Official WireGuard implementations
3. AmneziaWG
4. Xray-core
5. Mihomo
6. OpenConnect
7. strongSwan and/or official native IPsec APIs
8. SoftEther
9. Hysteria2 official implementation where independent integration provides real value

Additional libraries may be considered only when they provide unique required coverage.

## 8. Engine minimization rule

Do not add a new engine merely to increase a marketing number.

For each candidate evaluate:

- unique coverage
- maturity
- maintenance activity
- security history
- license
- commercial redistribution implications
- platform support
- store compatibility
- size and dependency burden
- expected maintenance cost

The goal is maximum useful coverage with the minimum reliable set of engines.

## 9. Networking policy subsystems

Routing and DNS should be treated as first-class shared product subsystems rather than scattered engine-specific UI options.

Long-term research includes:

- global/direct/rule-based/smart routing concepts
- split behavior where supported
- application-based policy where supported by the OS
- custom and encrypted DNS options
- split DNS and leak prevention
- IPv4/IPv6 behavior

Exact implementation details remain open until platform research is complete.

## 10. Security architecture

Do not implement cryptography from scratch when mature reviewed implementations exist.

Secrets must not be stored in plaintext or ordinary logs.

Future implementations should use appropriate OS secure-storage facilities and redact sensitive diagnostic data.

## 11. Localization

Persian and English are mandatory first-class languages.

Persian support must include correct RTL behavior and correct handling of mixed content such as protocol names, addresses, ports, URLs, hashes, paths, and diagnostic text.

All visible strings should be localizable from the beginning of implementation.

## 12. Branding

Product identity is **PVNetwork**.

The exact official logo supplied by the owner must be used. Platform-specific icon/banner variants should be derived from that approved asset rather than replaced with a new AI-generated identity.

## 13. Store-aware architecture

Architecture decisions must account for public distribution constraints from the start.

Before release-affecting decisions, re-check current official rules for Google Play, Android TV/Google TV, Apple distribution, Microsoft Store, and selected Linux channels.

## 14. Current open architecture decisions

Research is still required for:

- cross-platform UI framework
- language/runtime split
- process vs library integration per engine
- platform-specific engine availability
- iOS-compatible feature subset
- per-platform update model
- local data storage
- diagnostics/crash reporting policy
- exact minimum OS versions
- final core set

These choices must be documented with evidence before major implementation begins.
