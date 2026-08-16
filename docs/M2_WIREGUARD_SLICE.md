# M2 WireGuard Adapter — First Implementation Slice

Status: **IN PROGRESS — source committed; CI result pending**

## Research reused before implementation

This slice reuses the completed WireGuard dossier rather than reopening protocol research:

- `research/upstreams/wireguard-family/SUPPORT_REUSE_DECISIONS.md`
- `research/upstreams/wireguard-family/SOURCE_REVISIONS.md`
- `research/upstreams/wireguard-family/CORE_ARCHITECTURE.md`
- `research/upstreams/wireguard-family/DEPENDENCIES_SBOM.md`

Relevant reviewed source pin for the portable upstream family remains `WireGuard/wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0` (MIT), but **no WireGuard engine source or binary is imported by this slice**. The dossier recommends official/native platform implementations and forbids reimplementing protocol cryptography.

## Real source added

`engines/wireguard-adapter` is a product-owned KMP module containing:

- typed non-secret WireGuard interface/peer configuration;
- wg-quick style `[Interface]` / `[Peer]` import for the first supported field set;
- immediate transfer of `PrivateKey` and `PresharedKey` material into the existing `SecretStore` boundary;
- canonical `PVProfile` containing only opaque secret references;
- explicit warnings for unsupported fields rather than silently claiming support;
- CIDR/endpoint/basic numeric validation;
- a `WireGuardRuntimeFactory` boundary for later native/official engine integration;
- a `WireGuardAdapter` that advertises the WireGuard capability **only when a concrete runtime says it is available**.

This is intentionally not a cryptographic implementation and does not create a fake tunnel runtime.

## Test gate

`.github/workflows/m2-wireguard-adapter-ci.yml` executes:

```bash
gradle --no-daemon :engines:wireguard-adapter:jvmTest --stacktrace
```

Tests cover secret separation, IPv4/IPv6 configuration parsing, unsupported-field warnings, malformed CIDR/missing endpoint rejection, and the rule that research/unavailable runtimes must not appear as implemented capabilities.

## Current evidence boundary

Before the new CI run passes:

- WireGuard research: RESEARCHED.
- product-owned WireGuard config/import/adapter-boundary source: IMPLEMENTED in source, build/test gate pending.
- official/native WireGuard engine runtime: NOT YET INTEGRATED.
- real connection test: NOT YET PERFORMED.
- interoperability/device/Store/production status: NOT CLAIMED.

M2 cannot close until real connection tests exist, as required by `docs/ROADMAP.md`.
