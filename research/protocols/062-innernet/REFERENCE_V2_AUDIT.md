# Entry 062 — innernet — COMPLETE-REFERENCE-v2 audit

Reviewed 2026-08-15 against `FULL_PROTOCOL_REFERENCE_CONTRACT.md`.

## Canonical pins
- Upstream: `tonarino/innernet` (MIT).
- Stable release: `v2.0.0`, published 2026-07-02.
- Reviewed main: `1ba6154b6ebacd68dfe79c3a4f6273fd3e8dea35` (2026-07-28).
- Upstream describes innernet as a private-network system using WireGuard underneath; it is not an official WireGuard project.

## Exact 16-gate reconciliation
1. **Server ecosystem — PASS.** Canonical `innernet-server` is the coordination server; it manages peers/CIDRs and endpoint information while peers connect directly where possible.
2. **Installers/deployment — PASS.** Canonical paths are Cargo/source, Arch package, community Debian/Ubuntu builds, Homebrew for macOS client, and systemd service on Linux. No canonical Kubernetes/Helm or web-panel path is claimed.
3. **Server install matrix — PASS.** Linux is the meaningful server target; upstream officially tests Linux/macOS overall and lists experimental OpenBSD. Server build requires Rust/Cargo, libclang and libsqlite3. Container use is development-test evidence, not promoted as a production installer.
4. **Server UI/menu map — PASS / evidence-backed N/A.** Canonical administration is CLI (`innernet-server new`, `add-cidr`, `add-peer`, `serve`, `uninstall`); no canonical web GUI exists in reviewed upstream.
5. **Client install matrix — PASS.** Linux and macOS are officially tested; OpenBSD experimental. Arch, Homebrew, Cargo and community Debian/Ubuntu packaging are documented. No Android/iOS/Windows official client is claimed.
6. **Client UI/menu map — PASS / evidence-backed N/A.** Canonical client is CLI; commands include install, list/tree, add-cidr, add-association, list/delete associations, peer enable/disable, endpoint override and listen-port control. No canonical graphical client is claimed.
7. **Cryptography — PASS.** Data-plane cryptography is WireGuard's, not a new innernet cipher suite. Invitation bootstrap is one-time: client connects through WireGuard, generates a fresh key pair, registers it, and the invitation private key can no longer be used.
8. **Data path/wire flow — PASS.** Coordination server distributes peer/endpoint/CIDR state; WireGuard carries peer data. CIDR associations are policy primitives. Server is management/coordination, not automatically a mandatory relay for peer payloads.
9. **Ports/transports/handshake — PASS.** WireGuard UDP listen port is configured per interface; server port forwarding may be required behind NAT. Endpoint discovery/override and WireGuard handshakes are distinct from innernet's coordination API.
10. **Deployment topologies — PASS.** Private mesh with a coordination server, infra CIDR, hierarchical CIDRs and explicit CIDR associations; admin peers can manage network state remotely.
11. **Source/license/activity pins — PASS.** MIT, v2.0.0, main commit above; upstream pushed 2026-07-28 and remains active.
12. **Supply-chain/security — PASS.** Upstream explicitly says no independent security audit and recommends strict reverse-path filtering, interface binding where possible, and application-layer auth rather than trusting source IP alone. Community packages remain separate trust boundaries.
13. **Upgrade/uninstall/rollback — PASS.** v2.0.0 states server/client 2.x remain compatible with 1.x; Cargo installs require manual/cargo-update management; network removal is `innernet-server uninstall <interface>`. Rollback is package/source-manager dependent; no invented transactional rollback is claimed.
14. **Differences/uncertainties — PASS.** innernet is a WireGuard-based coordination/policy system, not a WireGuard replacement. Mobile/Windows GUI, canonical web panel, Helm/Kubernetes and independent audit are not claimed.
15. **REFERENCE_INDEX — PASS.** See `REFERENCE_INDEX.md`.
16. **Handoff continuation — PASS when tracker/state is promoted.** Next numbered entry is 063 GRE.

## Decision
All applicable V2 research gates are evidence-backed. Entry 062 is eligible for `COMPLETE-REFERENCE-v2`. This is a research/reference result, not implementation or production certification.

## Canonical evidence
- https://github.com/tonarino/innernet
- https://github.com/tonarino/innernet/releases/tag/v2.0.0
- https://github.com/tonarino/innernet/commit/1ba6154b6ebacd68dfe79c3a4f6273fd3e8dea35
- upstream README at tag `v2.0.0`
