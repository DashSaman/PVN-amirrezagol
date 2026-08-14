# 058 — NetBird — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Decision: **`COMPLETE-RESEARCH-v1 / WIREGUARD MESH + DISTINCT CONTROL PLANE / SPLIT BSD-AGPL LICENSE / NOT IMPLEMENTED / NOT CERTIFIED`**

NetBird is a WireGuard-based private overlay ecosystem with its own client, identity/account management, access policy, DNS, routing, signaling and relay services. It is not merely “WireGuard with a GUI”, and its server-side components do not share the client/general repository license.

## Exact current release/source pin

Canonical repository: `netbirdio/netbird`.

Current stable release reviewed on 2026-08-14:

- tag: **`v0.77.0`**
- published: 2026-08-13
- annotated tag object: `a30bb8d40108f4ff96e5731f8508d4fc28e6d1ca`
- tag target commit: `c5503fdc7f93ae6844a39caecf2970b43618c9b2`
- release tree: `9da63d4d8e9d22918c56cc6112f97e3a3cd496a2`
- tag is unsigned;
- target commit has a valid GitHub-verified signature.

The release includes cross-platform client artifacts/install scripts and self-hosting assets with GitHub-provided SHA-256 digests. Release provenance must record the exact artifact digest/signature path per platform; an unsigned tag is not described as signed.

The release commit itself hardens release provenance by changing the pipeline so a release is not marked GitHub “Latest” until signed Windows/macOS artifacts are uploaded, and ensures release branches run Go tests, frontend UI, install-script, mobile/WASM, infrastructure and license checks.

## Critical path-level license model

Exact root `LICENSE` at v0.77.0 states:

- repository parts are **BSD-3-Clause** except `management/`, `signal/`, `relay/` and `combined/`;
- those four directories are **AGPL-3.0** and carry their own LICENSE files.

Exact `management/LICENSE` is GNU Affero GPL v3.0.

Therefore PVNetwork must not label the whole repository “BSD”. In particular:

- client/agent/general reusable paths can be evaluated under BSD-3-Clause with notices;
- Management, Signal, Relay and Combined self-hosted server code are AGPLv3 and trigger copyleft/network-use obligations when modified/served under the license;
- NetBird Enterprise self-hosted products can instead be available under a separate **commercial license**; current official docs distinguish that licensed enterprise control plane from open-source self-hosting and NetBird Cloud subscription plans.

Commercial/cloud terms are separate from the BSD client-source license.

## Current architecture

Official 2026 architecture documentation identifies four major components:

1. **Client/agent** — generates/owns WireGuard private key, configures interface/routes/DNS, applies management-delivered state and forms peer links.
2. **Management** — stores peer public keys/metadata, private overlay addresses, users/accounts, network map, access policy, DNS and routing configuration; maintains a control channel to peers.
3. **Signal** — brokers encrypted candidate/handshake signaling so peers can negotiate direct connectivity; it stores no network payload and normal peer traffic does not flow through it.
4. **Relay** — forwards already WireGuard-encrypted peer traffic when direct P2P connectivity cannot be established; current NetBird relay replaced/augmented the older Coturn TURN path starting in v0.29.

The peer WireGuard private key never leaves the device according to current official architecture docs.

Control-plane metadata and data-plane payload confidentiality are therefore separate claims: Management can know accounts, peer public keys, overlay IPs, routes, policies and DNS configuration even though peer traffic remains end-to-end WireGuard encrypted.

## Cloud vs self-hosted control plane

Current official docs support:

- NetBird Cloud managed control plane;
- open-source self-hosted deployment;
- commercial self-hosted Enterprise deployment with additional HA/enterprise capabilities and separate license/support.

Current self-hosted architecture uses a combined `netbird-server` container by default, with Management, Signal and Relay/STUN components plus a dashboard/reverse proxy; the older multi-container model remains documented for advanced/legacy deployments.

Management, Signal and Relay can be split for scale. Current docs note that open-source standalone Signal is stateful/in-memory and not generally replicated active-active; higher-availability capabilities may be enterprise/commercial features. PVNetwork must not infer HA from merely self-hosting the AGPL services.

## Connection/path semantics

- direct P2P is preferred;
- ICE/STUN/candidate exchange is used for NAT traversal;
- relay is fallback when direct connectivity fails;
- relay sees encrypted WireGuard packets but cannot decrypt peer payload;
- relayed paths add latency/share relay bandwidth but do not intentionally weaken WireGuard confidentiality.

Diagnostics must expose direct vs relayed path rather than mark a relayed-but-working peer as generically “connected” without path detail.

## Access control, posture and routing

Current official docs provide:

- group-based access policies controlling source/destination protocol/port;
- posture checks for context-aware access;
- default account policy initially allows full-mesh connectivity until administrators tighten policy;
- Networks are the current preferred routed-resource abstraction for new deployments;
- legacy Routes are deprecated for most new use cases except exit-node/default-route scenarios;
- exit-node configuration uses `0.0.0.0/0` and, with current IPv6 overlay support, can create `::/0`; clients without supported IPv6 overlay can block IPv6 to prevent exit-node leaks;
- DNS configuration is management-delivered and must be tested together with routed roles.

PVNetwork must not encode old “Routes everywhere” as the canonical current UI/data model when NetBird has moved new routed-resource use cases to Networks.

## Current ports / server deployment evidence

Current self-hosted docs describe a consolidated architecture. With a reverse proxy, typical current exposure includes:

- 80/TCP for certificate validation/redirect;
- 443/TCP for dashboard, Management API/gRPC, Signal and Relay WebSocket multiplexing;
- 3478/UDP for STUN.

Direct/legacy deployments may expose separate Management, Signal and Relay ports. Older clients can require legacy compatibility ports. These are deployment-version facts, not immutable NetBird protocol port assignments.

## Secrets, persistence and privacy

Sensitive classes include:

- peer WireGuard private keys — client-owned only;
- setup keys / enrollment credentials;
- IdP/OIDC credentials/tokens;
- Management API/service credentials;
- relay authentication secrets;
- TLS private keys;
- self-hosted datastore encryption key;
- database/IdP credentials.

Current environment docs explicitly expose `NETBIRD_RELAY_AUTH_SECRET`, TLS key paths and `NETBIRD_DATASTORE_ENC_KEY`; these must never be ordinary profile/log/support-bundle fields.

Management database state includes accounts, peers, policies, routes/Networks and DNS data. Signal connection state and relay session/bookkeeping are transient service state.

## UI/client/platform evidence

Current repository/release pipeline includes desktop/frontend UI, mobile/WASM validation and platform installers. Official client surface spans major desktop/mobile/Linux/container/router/server use cases, while browser client has a separately documented WebSocket-to-gRPC/relay architecture.

First-party dashboard/manage UI is evidence for:

- peers/devices;
- setup/enrollment;
- groups/access policies/posture;
- DNS;
- Networks/routes/exit nodes;
- users/identity;
- self-hosted/cloud server status.

Exact desktop/mobile/browser menu parity remains V2/platform certification work; no closed/cloud UI source is assumed reusable merely because client code is BSD.

## Logs/observability

Current official self-hosted docs expose metrics/health for Management, Signal and Relay. Relay metrics include peer connections, activity and throughput; Signal metrics include connection/message and gRPC instrumentation.

PVNetwork diagnostics should keep separate:

- identity/login/enrollment;
- Management control channel;
- Signal negotiation;
- STUN/candidate discovery;
- direct vs relay path;
- WireGuard peer/session state;
- DNS/routing/Networks/exit node state;
- local interface/firewall;
- server metrics/health.

Debug bundles/HAR files can contain sensitive account/network metadata; credentials/private keys must remain redacted.

## Tests / CI / releases

The exact v0.77.0 release-target commit records CI on release branches for:

- Go tests across supported platforms;
- frontend UI;
- install script;
- mobile/WASM validation;
- infrastructure files;
- license checks.

Current release assets expose SHA-256 digests. This is upstream quality/provenance evidence, not PVNetwork device certification.

## 20-gate reconciliation

| # | V1 gate | Result | NetBird conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations identified | PASS | Official NetBird client/agent is data-plane authority; Management, Signal, Relay/Combined are first-party control/relay services; official dashboard/mobile/desktop/browser clients are UX references. |
| 2 | Canonical sources pinned | PASS | Exact v0.77.0 tag object, signed target commit, tree and release date are pinned. |
| 3 | Licenses reviewed | PASS | Root BSD-3-Clause except `management/`, `signal/`, `relay/`, `combined/` which are AGPLv3; enterprise commercial license and Cloud terms are separate. |
| 4 | Complete source-tree reference/manifest | PASS | Exact release tree pinned with client, management/signal/relay/combined, UI, mobile submodules, infrastructure and release/CI surfaces. Exact shipping SBOM remains production freeze work. |
| 5 | Languages/build systems mapped | PASS | Primarily Go server/client with frontend/mobile/infrastructure build systems, GoReleaser, containers and platform installers; release CI validates multiple surfaces. |
| 6 | Architecture mapped | PASS | WireGuard client/data plane, Management control plane, Signal negotiation, STUN/ICE and Relay fallback are clearly separated. |
| 7 | Core/engine integration mapped | PASS | PVNetwork should integrate a dedicated NetBird client/provider adapter. Plain WireGuard support does not recreate Management/Signal/relay/identity/policy semantics. |
| 8 | UI/menu map completed | PASS for V1 | First-party dashboard/client/browser management domains are source/documented; peers, groups/policy, DNS, Networks/routes, exit-node and identity states are mapped. Exhaustive menus/screens remain V2. |
| 9 | Config/import/export mapped | PASS | Management endpoint/cloud-vs-self-hosted, enrollment/setup key, peer identity, DNS, Networks/routes, exit node, policy and relay/control settings are typed. This is not a single share URI. |
| 10 | Persistence/secrets mapped | PASS | Peer private key, setup/IdP/API/relay/TLS/datastore secrets and management database state are separated from ordinary profile settings and transient signal/relay state. |
| 11 | Platform integrations mapped | PASS for research | Current release/pipeline covers desktop/mobile/Linux/container/browser paths; exact VPN service/TUN/Store lifecycle remains later certification. |
| 12 | Logs/diagnostics mapped | PASS | Management, Signal, relay/direct path, WireGuard, DNS/routing/interface and server health are separately observable; debug-bundle redaction is explicit. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Official dashboard/client resources are reference targets; branding/cloud UI rights are separate from BSD client source. |
| 14 | Meaningful alternatives/forks reviewed | PASS | Cloud vs open-source self-host vs enterprise commercial self-host, combined vs split services, new relay vs legacy Coturn and Networks vs legacy Routes are current meaningful branches. |
| 15 | Issues/PRs/releases/advisories reviewed | PASS | Current v0.77.0 release and release-signing/CI changes are pinned; release branch/provenance model and current deployment migration issues are represented. Exact production advisory scan remains freeze-time work. |
| 16 | Relevant forums/docs reviewed | PASS | Official architecture, self-hosting, routing/Networks, access-control, relay/signal observability, exit-node and enterprise-license docs are primary evidence. |
| 17 | Tests/CI reviewed | PASS | Release-branch Go/platform, UI, installer, mobile/WASM, infra and license CI plus current upstream testing are documented; independent interop/lifecycle remains later acceptance. |
| 18 | Store/privacy/security implications reviewed | PASS | Client private-key ownership, control-plane metadata, enrollment/IdP/API secrets, relay metadata, exit-node DNS/IPv6 leak handling and split AGPL/commercial licensing are explicit. |
| 19 | PVNetwork reuse decision documented | PASS | Optional dedicated mesh provider. BSD client paths can be evaluated for integration; AGPL control services require deliberate compliance/self-host architecture or commercial license. Do not include merely to duplicate WireGuard. |
| 20 | Uncertainties explicitly listed | PASS | Exact production artifact/signature/SBOM, cloud API terms, enterprise license, mobile/UI parity, IdP variants, HA, relay performance, device lifecycle and V2 wire/server/topology evidence remain later work. |

## Product/security rules that survive handoff

1. Never collapse NetBird to raw WireGuard: identity, policy, DNS, routes and control/relay lifecycle are first-class provider state.
2. Do not call the repository wholly BSD; Management/Signal/Relay/Combined are AGPLv3.
3. Keep Cloud, open-source self-host and enterprise-commercial self-host licensing/support modes separate.
4. Peer private keys remain local; enrollment/API/IdP/relay/datastore secrets use secure storage and redaction.
5. Expose direct versus relayed path in health/performance diagnostics.
6. Use current `Networks` model for new routed resources; preserve legacy Routes only as compatibility where current docs require it.
7. Exit-node mode must test IPv4/IPv6 and DNS leak/fail-close behavior.
8. Do not infer open-source HA capabilities from enterprise-only documentation.
9. Do not implement WireGuard cryptography or NetBird signaling/control protocol from scratch.

## Later acceptance work — not V1 blockers

Before a support claim: freeze exact v0.77.x platform artifacts/signatures/SBOM/licenses; choose Cloud vs open-source self-host vs commercial Enterprise; test enrollment/login/key rotation, direct/relay/NAT traversal, Management/Signal outages, access policy/posture, Networks/routes/exit nodes, DNS/IPv4/IPv6 leak behavior, TUN/interface coexistence, suspend/resume/network changes, upgrades/migration and cleanup; then complete V2 exact server installers/admin menus, cryptography/data flow, ports/handshake and deployment topologies.

## Final V1 decision

All 20 original V1 research gates are evidence-backed with current v0.77.0 provenance, WireGuard/control/relay boundaries and exact BSD-vs-AGPL license paths explicit. Entry 058 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **not implemented/certified**.
