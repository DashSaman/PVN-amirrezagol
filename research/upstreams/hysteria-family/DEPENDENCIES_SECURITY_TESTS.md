# Hysteria / Hysteria2 — Dependency / Security / Test Surface

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

Upstream: `apernet/hysteria`.

Root license reviewed: MIT.

## Go dependency surface

The current upstream is a Go networking project with a non-trivial module graph. The reviewed `go.mod` includes QUIC/network/TLS/system/runtime dependencies plus test/support modules.

Final PVNetwork release review must resolve the exact module graph for the chosen tag/commit and record:

- direct/transitive modules;
- license;
- vulnerability/advisory state;
- build tags/features;
- OS/architecture-specific dependencies;
- artifact hashes/toolchain version.

Do not treat MIT at repository root as the full shipped-binary license/SBOM answer.

## QUIC implementation risk

Hysteria2 depends on a QUIC implementation and uses QUIC as a fundamental transport rather than an optional wrapper.

PVNetwork upgrades must therefore review both:

- Hysteria application protocol/runtime changes;
- QUIC-library changes/security/performance regressions.

Regression categories:

- handshake failure;
- path/network migration;
- UDP behavior;
- congestion/bandwidth settings;
- packet loss/high latency;
- IPv4/IPv6 transitions;
- MTU/fragmentation edge cases;
- connection resumption/reconnect;
- CPU/memory use under loss.

## TLS/certificate security

Hysteria2's QUIC sessions rely on TLS security semantics.

PVNetwork must test:

- correct certificate chain validation;
- hostname/server-name verification;
- platform trust-store behavior;
- imported custom CA/pinning semantics where supported;
- expired/not-yet-valid certificate;
- wrong hostname;
- unsafe verification override warnings;
- certificate rotation/reconnect.

Do not let an “insecure” compatibility option become the default or silently persist from a one-time debug action.

## Authentication secret storage

Authentication tokens/passwords/secrets must be stored through platform secure storage references rather than ordinary profile JSON/YAML.

Support bundle/logging rules:

- never dump auth secret;
- redact subscription URLs containing credentials;
- avoid logging full generated config;
- separate server-provided rejection text from trusted UI content.

## Server masquerade/admin surface

Hysteria server can expose HTTP-style/masquerade behavior depending on config. This is server-side behavior and belongs to later server-reference/security analysis.

PVNetwork consumer client should not expose server configuration fields in the normal profile editor.

Later server installer research must inspect listener exposure, TLS/ACME, auth backends, web/masquerade behavior, privilege/firewall requirements and update/rollback.

## Security advisory source rule

The GitHub repository security-advisory endpoint was queried during current research. Regardless of whether the repository endpoint contains published advisories at this moment, PVNetwork must also monitor:

- upstream release notes;
- Go dependency advisories;
- QUIC library advisories;
- TLS/crypto dependency advisories;
- security-relevant commits/issues.

“Zero entries in one advisory endpoint” is never a security certification.

## Test boundaries

### Upstream tests

Useful for validating the engine/protocol implementation.

### PVNetwork tests

Required independently for:

- URI/config import/export;
- canonical profile round trip;
- secure credential references;
- adapter lifecycle;
- local proxy mode;
- full TUN mode;
- DNS/routing/leak behavior;
- network handover;
- sleep/resume/background;
- Android/iOS service/extension lifecycle;
- error mapping;
- performance/resource use;
- Store/package behavior.

## Hysteria v1 compatibility gate

Current Hysteria2 source/security/test evidence must not be reused to certify Hysteria v1.

If PVNetwork chooses to support legacy v1:

- pin a v1 source/tag/client;
- separately audit dependencies/security;
- separately test client/server compatibility;
- mark legacy status in UI;
- provide migration guidance if possible without silently converting profiles.

## Upgrade gate

Before changing a Hysteria2 engine build:

1. pin exact source/tag;
2. diff protocol/config/default changes;
3. resolve SBOM/vulnerability state;
4. review QUIC dependency changes;
5. test TLS/certificate behavior;
6. test auth failures and secure storage;
7. test loss/latency/network migration;
8. test TUN/DNS/routing/full-device integration;
9. test platform process/background lifecycle;
10. retain rollback and canonical-profile compatibility.

## Current v1 conclusion

Hysteria2 is an attractive MIT-licensed reusable engine candidate but still needs exact release/API/platform selection. Legacy Hysteria1 should be kept as a separate compatibility target, not inferred from v2.

## Residual gaps

- exact current stable Hysteria2 tag/commit and full module SBOM;
- exact current published security/release matrix;
- exact public API/library boundary vs subprocess integration;
- mobile embedding/runtime evidence;
- issue-derived current regression matrix;
- legacy Hysteria1 exact source pin;
- independent client GUI references and menus.
