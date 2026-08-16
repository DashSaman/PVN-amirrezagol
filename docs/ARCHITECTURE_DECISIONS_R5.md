# PVNetwork R5 Architecture Decisions

Status: **PASS — architecture decision gate closed for implementation start**
Date: 2026-08-16
Scope boundary: this document approves product architecture. It does not claim any engine is implemented, built, tested, interoperable, device verified, Store verified, or production ready.

## Decision 1 — shared application stack

PVNetwork will use **Kotlin Multiplatform (KMP)** for product-owned shared domain/application code. The UI shell will use **Compose Multiplatform** where the target is supported and the UX remains appropriate. Platform networking and privileged integration stay behind native adapters rather than being forced into shared UI code.

Initial implementation pins for the foundation are:

- Kotlin/KMP: `2.4.10`
- Compose Multiplatform: `1.11.1` when UI work begins in M1
- Gradle: a version within Kotlin 2.4.10's documented supported range; wrapper pin is created with the first Gradle-enabled build slice rather than inventing an unverified wrapper binary.

The pinned versions above are implementation-tool pins, not third-party protocol-engine pins.

## Decision 2 — module boundaries

Product-owned code is split into stable layers:

```text
apps/*
  -> app/application
  -> core/profile + core/import + core/subscription
  -> core/connection + core/routing + core/dns
  -> core/adapter-api + core/security-api + core/diagnostics
  -> engines/* and platform/*
```

Rules:

- `apps/*` never call upstream engine APIs directly.
- `engines/*` translate the product contracts to one approved engine/runtime.
- `platform/*` owns OS VPN/TUN/service/Network Extension/privilege/secure-storage integration.
- protocol engines and platform adapters may be replaced without changing `PVProfile` or presentation contracts.

## Decision 3 — canonical profile model

PVNetwork owns a versioned `PVProfile` model. Imported source payloads, canonical profiles, protected secrets, generated runtime configs, and transient engine/session state are separate objects.

A profile stores **secret references**, never reusable plaintext secret material. Unknown/vendor-specific fields are preserved as explicitly namespaced extension values where lossless preservation is possible. Import/export must surface lossy conversion instead of silently dropping data.

## Decision 4 — core adapter contract

Every engine integration implements a product-owned adapter contract covering:

- stable adapter ID and version;
- capability discovery;
- profile validation;
- runtime configuration generation;
- lifecycle start/stop/restart;
- canonical connection-state reporting;
- health state;
- statistics;
- sanitized diagnostics;
- upstream engine version reporting.

`RESEARCHED` is never mapped to `IMPLEMENTED`. Capability advertisement is runtime/adapter evidence based.

## Decision 5 — connection state

The canonical lifecycle is:

`DISCONNECTED -> PREPARING -> REQUESTING_PERMISSION -> CONNECTING -> AUTHENTICATING -> ESTABLISHING_TUNNEL -> CONNECTED`

with explicit `RECONNECTING`, `DISCONNECTING`, and `ERROR` states. UI success may only follow adapter/platform state evidence; no fake connected state is permitted.

## Decision 6 — secrets and persistence

Security boundaries are mandatory from the first source slice:

- reusable credentials/private keys/tokens are represented by opaque `SecretRef` identifiers;
- `SecretStore` is a platform boundary;
- non-secret structured profile metadata may be persisted separately;
- runtime configuration generation resolves only required secrets for the shortest practical lifetime;
- logging/diagnostics accept sanitized values only and must support redaction before persistence/export;
- Android uses Keystore-backed protected storage, Apple uses Keychain, Windows uses an OS protected-secret facility, and Linux uses a desktop/system secret-service path where available, each behind the same contract.

No file-based plaintext fallback is approved for production secrets.

## Decision 7 — routing and DNS

Routing and DNS are shared product policy subsystems with platform execution adapters. Engine-specific fields can be exposed as extensions, but global/direct/rule/split and DNS policy are not encoded directly into UI-specific engine configs.

## Decision 8 — import/subscriptions

Import is a pipeline:

`Input -> Detect -> Parse -> Validate -> Normalize -> Capability Match -> Save`

Parsers return structured warnings and preservation/loss information. Subscription refresh produces a candidate normalized set before changing persisted user profiles, allowing duplicate detection and preservation of user-owned metadata.

## Decision 9 — localization and RTL

English and Persian are first-class from the first UI source. User-visible strings are resource-backed. Technical tokens such as addresses, URLs, hashes, paths, ports, and protocol IDs keep LTR semantics inside RTL layouts. Android TV remains a separate D-pad/focus UX target.

## Decision 10 — diagnostics/logging

Diagnostics are structured product events with severity, subsystem, stable event code, timestamp, and sanitized metadata. Secrets, auth headers, private keys, subscription credentials, cookies, and raw sensitive configurations are forbidden in normal logs/support bundles.

## Decision 11 — platform delivery boundary

Shared code does not claim identical networking behavior across operating systems.

- Android: `VpnService` platform adapter.
- iOS/iPadOS and relevant macOS builds: Network Extension adapter.
- Windows: service/helper plus selected native networking boundary when required by an engine.
- Linux: TUN/routing/privilege integration behind a platform adapter.
- Android TV reuses Android networking but not phone-only presentation assumptions.

## Decision 12 — dependency/license boundary

Third-party engines remain isolated integration units. A protocol dossier's reuse decision, exact source/release/tag/commit pin, and license strategy must be read before its adapter is added. GPL/AGPL/reference-only code is not copied into a closed product unless an explicit compatible distribution strategy is approved.

## Implementation entry criteria

R5 is satisfied because the implementation can now begin with stable product-owned contracts without selecting engine internals prematurely. R6 remains the gate for the initial engine set and per-engine reuse strategy. M0 may begin only after R6 records those approvals; source added before that is limited to engine-independent product foundation contracts.
