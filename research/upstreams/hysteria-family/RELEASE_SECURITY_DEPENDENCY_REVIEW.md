# Hysteria release, security and dependency review

Official upstream: `apernet/hysteria`.

This note supports PVNetwork entries **042 Hysteria** and **043 Hysteria2** and is separate from strict implementation/certification.

## 1. Release generation split

Official tags preserve the legacy Hysteria v1 line through `v1.3.5` at commit `57c5164854d6cfe00bead730cce731da2babe406`.

Current GitHub Releases use the Hysteria2 application tag namespace. The newest non-prerelease release observed in this review is:

- `app/v2.12.1`
- published 2026-08-09
- https://github.com/apernet/hysteria/releases/tag/app/v2.12.1

This reinforces that entry 042 (legacy Hysteria) and entry 043 (Hysteria2) require independent version pins and support policy.

## 2. Artifact integrity and platform evidence

The `app/v2.12.1` release publishes a `hashes.txt` asset and platform/architecture binaries including Android and Darwin variants. GitHub release metadata also exposes SHA-256 digests for individual assets.

### PVNetwork rule

Never download an unqualified `latest` binary at runtime. A production bundle must pin:

- exact release tag;
- exact asset filename;
- exact SHA-256 digest;
- core/app/extras and QUIC dependency provenance;
- rollback version.

## 3. Hysteria2 security advisories and minimum patch floor

### GHSA-vgrc-hq28-p3xp — UDP ACL bypass / internal UDP SSRF

Official advisory:
https://github.com/apernet/hysteria/security/advisories/GHSA-vgrc-hq28-p3xp

Reviewed facts:

- severity: High, CVSS 3.1 7.4;
- package: `github.com/apernet/hysteria/core/v2`;
- vulnerable: `>= 2.0.0, <= 2.9.1`;
- patched: `2.9.2`.

The issue is especially relevant to PVNetwork server policy: UDP destination is packet-scoped, while the vulnerable implementation cached ACL/outbound authorization at session scope. An authenticated client could establish a UDP session with an allowed destination and then reuse it for blocked localhost/private destinations.

### GHSA-qh5x-rfwf-rvfv — QUIC DATAGRAM fragmentation server crash

Official advisory:
https://github.com/apernet/hysteria/security/advisories/GHSA-qh5x-rfwf-rvfv

Reviewed facts:

- severity: High, CVSS 3.1 7.5;
- vulnerable: `< 2.9.2`;
- patched: `2.9.2`;
- impact: server crash when a client advertises a very small QUIC `max_datagram_frame_size` and triggers the UDP response fragmentation path.

### Security floor decision

For Hysteria2, **2.9.2 is the minimum security floor established by these two reviewed advisories**. The currently observed `app/v2.12.1` release is numerically above that floor, but PVNetwork must still query all current advisories and inspect its exact dependency graph before declaring a selected build safe.

Do not interpret `>=2.9.2` as a permanent blanket approval.

## 4. Dependency and SBOM ownership

At the reviewed current source pin `14e9fff1d972ab0187ac7fcf75b9514dc8664065`, `app/go.mod` is `github.com/apernet/hysteria/app/v2`, requires Go 1.25/toolchain 1.25.1, and replaces the Hysteria `core/v2` and `extras/v2` modules with local repository directories.

Important dependency families include:

- `github.com/apernet/quic-go` — a Hysteria/ApertNet QUIC fork, not merely a generic system QUIC library;
- `github.com/apernet/sing-tun` for TUN integration;
- certmagic and multiple DNS providers;
- uTLS;
- DTLS/STUN/NAT libraries;
- Viper/Cobra, zap and system/network libraries.

### PVNetwork consequence

The product SBOM must identify exact Hysteria app/core/extras source revision and the exact `apernet/quic-go` revision. A release label alone does not capture the networking/security dependency surface.

Official source:
https://github.com/apernet/hysteria/blob/14e9fff1d972ab0187ac7fcf75b9514dc8664065/app/go.mod

## 5. Security architecture lessons for server controls

The reviewed advisories yield concrete product acceptance tests:

1. **UDP ACL re-evaluation test** — a UDP session must not be able to pivot from an allowed destination to a blocked localhost/RFC1918 destination.
2. **QUIC tiny-datagram robustness test** — malformed/small peer DATAGRAM limits must fail closed without process panic.
3. **Server process health isolation** — a protocol-session fault must not silently leave the product UI claiming the server is healthy.
4. **Version-policy test** — builds below the reviewed security floor must be rejected by default for Hysteria2.

These tests belong to later implementation/certification, but the requirements are now part of the research contract.

## 6. Legacy Hysteria v1 maintenance policy

Legacy entry 042 ends at the older `v1.x` tag family in the current official tag history, while active releases are under `app/v2.x`.

### PVNetwork decision

Treat legacy Hysteria v1 as **compatibility-only by default**, not as the preferred new-profile protocol. Before enabling it for new deployments, a separate legacy advisory/dependency review is required. Importing an existing v1 profile is a different product decision from recommending v1 for newly created profiles.

## 7. Current per-entry support/reuse decision

| Entry | Security/release decision | Research status |
|---|---|---|
| 042 Hysteria | legacy `v1.3.5` compatibility line; no automatic recommendation for new profiles | `IN-RESEARCH` |
| 043 Hysteria2 | primary current target; require reviewed pin >= known 2.9.2 advisory floor, exact artifact digest and full current advisory/SBOM pass | `IN-RESEARCH` |

No `COMPLETE-RESEARCH-v1` claim is made here.

## 8. Remaining closure work

- review current issue/regression samples around TUN, Android, QUIC/port hopping and config migration;
- audit top client references/import formats and licenses;
- synchronize numbered entries 042/043 and family README;
- final gate-by-gate check against the v1 research template;
- keep legacy v1 and Hysteria2 support policy explicit.
