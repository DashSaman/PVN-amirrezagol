# 060 — Nebula — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / DISTINCT NOISE-BASED PEER OVERLAY / MIT CORE / LIGHTHOUSE DISCOVERY NOT CENTRAL PAYLOAD SERVER / MOBILE WRAPPER REFERENCE-ONLY PENDING LICENSE / NOT IMPLEMENTED / NOT CERTIFIED`**

This audit applies all exact 16 second-layer gates in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`. It reuses the completed V1 source/license/architecture dossier and refreshes current first-party release, configuration, firewall, lighthouse, certificate, relay, DNS, platform and lifecycle evidence. Runtime/device/Store/interoperability receipts remain later implementation/certification work.

## Canonical release / source / license baseline

Core:

- canonical repository: `slackhq/nebula`
- latest reviewed stable release: `v1.11.0`
- release date: 2026-07-23
- signed tag object: `c0d5dc004527f0883f35376919e5c135349b555f`
- exact release commit: `16178970439d01b9e83e3073421fda718878b5b9`
- upstream license: MIT
- implementation: Go
- release page: https://github.com/slackhq/nebula/releases/tag/v1.11.0

Current upstream docs reviewed:

- introduction / compatibility: https://nebula.defined.net/docs/
- quick start: https://nebula.defined.net/docs/guides/quick-start/
- complete config reference: https://nebula.defined.net/docs/config/
- lighthouse: https://nebula.defined.net/docs/config/lighthouse/
- firewall: https://nebula.defined.net/docs/config/firewall/
- guides / CA rotation / DNS / routing / debugging / logs: https://nebula.defined.net/docs/guides/
- core repository/README: https://github.com/slackhq/nebula
- release notes: https://github.com/slackhq/nebula/releases/tag/v1.11.0

Mobile reference:

- repository: `DefinedNet/mobile_nebula`
- V1 reviewed main commit: `c9bef19e519a35d35d37f5d4cef867fdebb7e2e9`
- V1 reviewed tree: `d1fad4ebfb7047be52220e1521745e6c20dcadbe`
- Flutter/Dart + Android/iOS + gomobile/Go bridge
- no explicit root source-code license was found in the V1-reviewed tree; therefore mobile source remains **REFERENCE-ONLY / DO-NOT-COPY UNTIL LICENSE VERIFIED**.

Managed Nebula from Defined Networking is a separate commercial/provider control-plane/PKI/lighthouse service and is not inferred MIT merely because the Nebula core is MIT.

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence-backed conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | Nebula has no conventional decrypting VPN server. The canonical implementation is the same Nebula peer binary on every node; one or more nodes can be configured as **lighthouses** for discovery, and relay functionality can forward already encrypted Nebula traffic when direct connectivity is difficult. `nebula-cert` is the canonical PKI tool. Managed Nebula is a separate provider service. |
| 2 | Official and major community installer/deployment projects reviewed | PASS | Canonical distribution is upstream release archives/binaries plus documented distro/package channels and container images. Quick-start is manual binary/config/certificate deployment, not a privileged one-click server script. Community packages may ease installation but are not promoted above signed upstream releases. |
| 3 | Server OS/container/orchestration install matrix completed | PASS | Lighthouse/peer roles use the same Nebula binary. First-party compatibility covers Linux, macOS, Windows, FreeBSD and mobile packaging, with x86/ARM/MIPS/PPC portability; upstream release assets cover major desktop/server OS/architectures and OCI/Docker images exist. Kubernetes is a deployment environment rather than a distinct server implementation; no Kubernetes control plane is invented. |
| 4 | Server panel/UI/menu maps completed | PASS via evidence-backed N/A | Nebula OSS has no canonical web admin/server panel. Lighthouse is configured by YAML plus `nebula`/`nebula-cert`; its relevant management surfaces are PKI, lighthouse/static host map, listen, punchy/NAT, relay, TUN, tunnels, SSH debug, logging, firewall, stats and handshake configuration. Managed Nebula's hosted UI is a separate commercial product and is not flattened into OSS Nebula. |
| 5 | Client install matrix completed across relevant OS targets | PASS | Core docs state Linux, macOS, Windows, iOS, Android and FreeBSD, with portability across x86/ARM/MIPS/PPC. Upstream release assets cover desktop/server platforms; mobile uses the separate DefinedNet wrapper. Exact Store/minimum-OS certification remains outside research completion. |
| 6 | Major client UI/menu maps completed separately | PASS | Desktop/server core is CLI/config/service-oriented, so a GUI menu map is N/A there; operational surfaces are config, service lifecycle, logs, stats and Nebula SSH diagnostics. The separate Android/iOS mobile wrapper provides the consumer UI reference, but its source license remains unresolved and therefore it is reference-only. Hosted Managed Nebula UI is a different provider boundary. |
| 7 | Cryptographic design documented | PASS | Nebula uses the Noise Protocol Framework with mutually authenticated host certificates rooted in a Nebula CA. Default curve is Curve25519; P256/ECDSA+ECDH is optional for compliance-oriented deployments. Current upstream describes AES-256-GCM default traffic encryption and certificate/group-based authorization. Replay protection and authenticated tunnel handshakes are implemented by upstream; v1.11.0 includes replay-window and malformed-handshake hardening. Do not reimplement crypto. |
| 8 | Data path/wire flow documented | PASS | App packet enters TUN -> host firewall evaluates certificate identity/groups/IPs -> peer discovery uses lighthouse/static map -> NAT hole punching/direct UDP is attempted -> authenticated Noise tunnel established -> encrypted packets travel directly peer-to-peer; relay is a fallback forwarding path for encrypted Nebula frames. Lighthouse discovery does not decrypt ordinary peer payload. Unsafe routes may forward traffic beyond overlay hosts. |
| 9 | Ports/transports/handshake documented | PASS | Nebula uses UDP underlay; default documented port is UDP/4242 and is configurable. Lighthouses must be reachable on the chosen UDP listen port. NAT punch/keepalive behavior is configured by `punchy`; handshake manager and certificate validation establish authenticated tunnels. Optional lighthouse DNS uses UDP/53 by default when explicitly enabled and firewall-permitted; that DNS port is not the Nebula data-plane port. |
| 10 | Deployment topologies documented | PASS | Direct peer mesh with one/multiple lighthouses, hub-like discovery without payload centralization, relay-assisted connectivity, cloud/datacenter/endpoint mesh, unsafe-route gateway to non-Nebula networks, mobile endpoints and Managed Nebula provider topology are mapped. Lighthouses should have stable routable underlay addresses; multiple lighthouses improve discovery resilience. |
| 11 | Source/license/activity pins recorded for server and client projects | PASS | Core v1.11.0 signed tag and exact commit are pinned; core is MIT. Mobile wrapper exact V1 commit/tree is pinned but license is unresolved and therefore not reusable. Managed Nebula is separate commercial service/terms. Current July/August 2026 activity demonstrates maintained core/mobile lines. |
| 12 | Security/supply-chain risks of installer projects recorded | PASS | Prefer signed/hash-verifiable upstream release artifacts over unreviewed community packages. CA private key must never be copied to normal nodes/lighthouses; host private keys and configs are protected. Release 1.10.3 fixed P256 blocklist/signature-representation security behavior, and 1.11.0 includes multiple handshake/replay/firewall/parser fixes, demonstrating the need for current supported versions and advisory review. |
| 13 | Upgrade/uninstall/rollback behavior researched | PASS | Nebula is largely a versioned binary/service plus YAML/PKI state; package-manager or release-archive replacement is the upgrade model. v1.11.0 documents breaking logging, firewall-action, Windows WFP/network-category and embedder API changes, so upgrades require release-note/config/log-parser review. Rollback requires preserving exact binary/package, service definition, config and PKI state; arbitrary downgrade compatibility is not assumed. Removing the service/binary/TUN integration must not delete CA/host credentials unless explicitly intended. |
| 14 | Protocol/server/client differences and uncertainties explicitly listed | PASS | Nebula != WireGuard; lighthouse != decrypting VPN server; relay != lighthouse; CA private key != host key; host firewall policy != host OS firewall; direct path != relayed path; OSS MIT core != mobile wrapper license != Managed Nebula terms. Exact mobile source license, Store state, managed-provider API/terms, runtime IPv6/route behavior and selected compliance crypto mode remain explicit uncertainties. |
| 15 | `REFERENCE_INDEX.md` links the complete dossier | PASS | `research/protocols/060-entry/REFERENCE_INDEX.md` is created with V1/V2 evidence, exact release pins, docs and continuation. |
| 16 | Latest AGENTS handoff contains exact continuation state | PASS | The mesh/overlay V2 handoff advances the campaign to Entry 061 Tinc after promotion and records the exact next action. |

## Deployment / install matrix

| Environment | V2 conclusion |
|---|---|
| Linux | Canonical core target; release packages/binaries, system service and TUN/CAP_NET_ADMIN patterns are documented. |
| Windows | First-party release target; v1.11.0 adds signed Windows binaries and changes WFP/network-category defaults. |
| macOS | First-party release target; v1.11.0 improves listener rebind/lighthouse refresh on underlay network change. |
| FreeBSD/OpenBSD-family | FreeBSD is documented/released; v1.11.0 aligns Darwin/OpenBSD TUN behavior and FreeBSD nonblocking TUN handling. |
| iOS / Android | Separate mobile wrapper/application path; reference-only for source reuse until license is verified. |
| Docker / OCI | Upstream release/container path; v1.11.0 adds version labels to Docker/OCI images. |
| Kubernetes | Nebula can run in infrastructure/container contexts, but no separate central Kubernetes controller is claimed. |
| x86 / ARM / MIPS / PPC | Upstream compatibility documentation identifies broad 32/64-bit portability. |

## Server / lighthouse / UI boundary

Nebula OSS does not have a central server panel. A lighthouse is simply a Nebula host with `am_lighthouse: true`, a stable reachable underlay address and normal host certificate/key/config. It maintains discovery mappings and assists NAT traversal; it is not a TLS/VPN gateway that terminates and decrypts all peer traffic.

Evidence-backed core configuration domains:

- `pki`: CA bundle, host cert/key, fingerprint blocklist;
- `static_host_map` / hostname resolution;
- `lighthouse` discovery and optional DNS;
- `listen` UDP underlay port;
- `punchy` NAT keepalive/hole punching;
- `cipher`;
- preferred ranges / underlay selection;
- relay;
- TUN and unsafe routes;
- tunnels;
- built-in debugging SSH;
- logging;
- firewall;
- stats;
- handshake manager.

This CLI/config inventory satisfies the UI/menu gate by explicit N/A for a nonexistent OSS panel rather than inventing one.

## PKI / cryptography / lifecycle

A Nebula network's root of trust is its CA. `nebula-cert ca` creates the CA certificate and CA private key; host certs bind Nebula IP(s), names, groups and lifetime. Upstream explicitly warns **DO NOT COPY `ca.key` TO INDIVIDUAL NODES**. Host private keys belong only to their device. CA and host certificate expiry/rotation are first-class lifecycle concerns, and upstream documents CA rotation without downtime.

Current v1.11.0 release changes matter operationally:

- logging moved to Go `slog`, breaking assumptions in log parsers/embedders;
- firewall reject/drop action direction bug was corrected;
- Windows WFP permit filters and adapter network category behavior changed defaults;
- malformed handshake, relay replay window, IPv6 parser and tunnel/relay-state issues were fixed;
- macOS can rebind listener/re-query lighthouses on underlay network changes;
- subsystems can be reloaded/stopped more cleanly.

PVNetwork must pin a release and carry release-specific migration notes instead of treating Nebula config/runtime behavior as timeless.

## Data path

```text
Application packet
      |
      v
Nebula TUN interface
      |
      v
Certificate/group/IP firewall decision
      |
      +---- lighthouse/static host map: discover current underlay endpoint
      |
      +---- punchy/NAT traversal: attempt direct UDP reachability
      |
      v
Mutually authenticated Noise handshake
      |
      v
Encrypted direct UDP peer tunnel
      |
      `---- relay fallback for hard-to-reach peers (encrypted frame forwarding)
      |
      v
Remote Nebula TUN -> application / optional unsafe-route destination
```

Lighthouse control/discovery metadata and underlay endpoints are visible to discovery infrastructure; ordinary overlay payload remains peer-encrypted. Optional lighthouse DNS is a separate service and must be firewalled to intended Nebula hosts.

## Firewall / DNS / routing

- Nebula's host firewall is default-deny for inbound/outbound unless rules permit traffic.
- Rules may select ports/protocols plus certificate CA/name/group and CIDR properties.
- `unsafe_routes` extend access beyond native overlay hosts through a designated overlay gateway; these are explicit route/security surfaces.
- Lighthouse DNS is optional/experimental and must be enabled only on lighthouse nodes; binding to the Nebula IP limits reachability compared with wildcard/public binding.
- Runtime route/DNS leak behavior remains an implementation/certification test, not a hidden research gate.

## Security / supply-chain / privacy

Protected state:

- CA private key — highest sensitivity, admin/offline provisioning material;
- host private key — device identity secret;
- host/CA certificates — non-secret but trust/identity-sensitive;
- YAML config, static maps, routes and firewall groups — topology/security-policy sensitive;
- Managed Nebula account/API state — separate provider secret surface if used.

Release artifacts should be pinned by version/hash/signature where available. Community packages/containers add maintainer and registry trust boundaries. Debug SSH/stats/DNS listeners should remain disabled or restricted unless explicitly needed.

## Explicit uncertainties / non-claims

V2 completion does **not** claim:

- PVNetwork implementation or real-device interoperability;
- mobile wrapper source is licensed for copying/reuse;
- Store approval/current minimum OS for mobile clients;
- Managed Nebula source is MIT or feature-identical to OSS;
- a lighthouse is a central payload VPN server;
- every community package/container is trusted;
- P256/BoringCrypto builds are automatically compliance-certified;
- runtime route/IPv6/firewall behavior has been certified in PVNetwork;
- future releases retain v1.11.0 defaults.

## PVNetwork reuse decision

**`OPTIONAL DEDICATED NEBULA OVERLAY ADAPTER / MIT CORE REUSE-CANDIDATE / MOBILE WRAPPER REFERENCE-ONLY UNTIL LICENSE VERIFIED / MANAGED PROVIDER SEPARATE`**

Reuse the official Nebula core and PKI/certificate/Noise implementation. Preserve CA/host cert/group/firewall/lighthouse/relay/route state in a dedicated provider model. Never import the CA private key into ordinary endpoint profiles and never reimplement Nebula cryptography.

## Final V2 decision

All exact 16 `COMPLETE-REFERENCE-v2` gates are evidence-backed by the completed V1 dossier plus refreshed signed-release, official Nebula docs/config, lighthouse, firewall, PKI, relay, DNS and lifecycle evidence. Entry **060 — Nebula** may be promoted to **`COMPLETE-REFERENCE-v2`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
