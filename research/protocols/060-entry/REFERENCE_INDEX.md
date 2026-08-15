# 060 — Nebula — Reference Index

Status after review: **COMPLETE-RESEARCH-v1 + COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED / NOT CERTIFIED**

## Repository evidence

- `research/protocols/060-entry/V1_GATE_RECONCILIATION.md`
- `research/protocols/060-entry/REFERENCE_V2_AUDIT.md`

## Canonical pins

### Nebula core
- repository: `slackhq/nebula`
- stable release: `v1.11.0`
- release date: 2026-07-23
- signed tag object: `c0d5dc004527f0883f35376919e5c135349b555f`
- exact release commit: `16178970439d01b9e83e3073421fda718878b5b9`
- license: MIT
- release: https://github.com/slackhq/nebula/releases/tag/v1.11.0

### Mobile wrapper reference
- repository: `DefinedNet/mobile_nebula`
- V1-reviewed commit: `c9bef19e519a35d35d37f5d4cef867fdebb7e2e9`
- V1-reviewed tree: `d1fad4ebfb7047be52220e1521745e6c20dcadbe`
- source license status: **unresolved in reviewed root; REFERENCE-ONLY / DO-NOT-COPY until explicit license is verified**

Managed Nebula from Defined Networking is a separate commercial/provider service and must not inherit the MIT core license by assumption.

## First-party reference set

- core repository: https://github.com/slackhq/nebula
- release: https://github.com/slackhq/nebula/releases/tag/v1.11.0
- docs: https://nebula.defined.net/docs/
- quick start: https://nebula.defined.net/docs/guides/quick-start/
- complete config: https://nebula.defined.net/docs/config/
- lighthouse: https://nebula.defined.net/docs/config/lighthouse/
- firewall: https://nebula.defined.net/docs/config/firewall/
- guides: https://nebula.defined.net/docs/guides/

## Architecture summary

`Nebula CA -> per-host certificate/key -> lighthouse/static discovery -> direct UDP/NAT-punched peer path -> mutually authenticated Noise tunnel -> encrypted overlay traffic`

Relay is an encrypted-frame forwarding fallback. A lighthouse is discovery infrastructure, not a conventional centralized VPN server that decrypts all payload.

## Platform / management boundaries

- Core/server/peer binary is first-party for Linux, macOS, Windows and FreeBSD, with broad architecture portability and OCI/container use.
- iOS/Android use the separate `mobile_nebula` wrapper/application path.
- Nebula OSS has no canonical web server panel. Management is YAML + `nebula`/`nebula-cert` + service/log/stats/debug surfaces.
- Evidence-backed configuration domains: PKI, static host map, lighthouse, listen UDP port, punchy/NAT, cipher, relay, TUN/unsafe routes, tunnels, SSH debug, logging, firewall, stats and handshake settings.

## Crypto / PKI boundary

- Noise-based mutually authenticated peer overlay.
- Default Curve25519 path; P256 is optional for specific compliance-oriented deployments.
- AES-256-GCM is the default data cipher described by upstream.
- `ca.key` is highest-sensitivity provisioning material and must not be copied to ordinary nodes.
- host private key is device-local identity secret; host and CA certs are trust/identity state.

## Current release lifecycle notes

Nebula v1.11.0 includes breaking/operational changes around Go `slog` logging, firewall reject/drop action direction, Windows WFP/network category handling, and embedder APIs, plus fixes for replay handling, malformed handshakes, IPv6 parsing, relay state and network-change recovery. Production integration must pin releases and carry migration notes instead of assuming timeless behavior.

## Security / supply-chain notes

Prefer pinned/signed/hash-verifiable upstream releases over unreviewed community packages. Protect CA/host private keys, overlay topology, firewall/group policy, unsafe-route configuration and any Managed Nebula provider credentials. Optional lighthouse DNS, SSH debug and stats listeners are extra exposed surfaces and should remain restricted unless deliberately enabled.

## PVNetwork reuse decision

**OPTIONAL DEDICATED NEBULA OVERLAY ADAPTER.** MIT core is a reuse candidate. Preserve CA/host cert/group/firewall/lighthouse/relay/route state explicitly and use upstream crypto/certificate machinery. Mobile wrapper remains reference-only until its source license is verified. Managed Nebula remains a separate provider/API/terms boundary.

## Exact continuation

After promotion of Entry 060, continue `COMPLETE-REFERENCE-v2` at **061 — Tinc**. Apply all exact 16 gates using the completed V1 dossier plus current canonical tinc source/release/documentation. Keep peer-daemon topology, meta/data connection behavior, routing/switching modes, key/host-file configuration, platform packaging and source/license boundaries explicit. If 061 passes, continue **062 — innernet**.
