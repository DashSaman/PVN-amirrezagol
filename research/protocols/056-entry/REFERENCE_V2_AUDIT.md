# 056 — Tailscale — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Entry: **056 — Tailscale**

Decision: **`COMPLETE-REFERENCE-v2 / WIREGUARD-BASED MESH ECOSYSTEM / OPEN CLIENT+DATA-PLANE CORE WITH HOSTED-CONTROL BOUNDARY / NOT IMPLEMENTED / NOT CERTIFIED`**

This audit applies the exact 16 second-layer gates in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`. It reuses the evidence-backed V1 dossier and refreshes current first-party installation, update, custom-control-server, Kubernetes, exit-node, subnet-router and local-web-UI documentation. Missing runtime/device/Store/interoperability receipts are intentionally not treated as hidden research gates.

## Canonical evidence baseline

Primary source repository:

- `tailscale/tailscale`
- reviewed V1 main commit: `0953fd9a97e9697fb496c0e1d3a0e2a45bc264ea`
- reviewed tree: `a1703106fb225b27026d8c45964b4c8ac0a260b8`
- current stable release re-verified on 2026-08-15: `v1.102.2`, published 2026-08-04
- root license: BSD-3-Clause
- official release API: https://api.github.com/repos/tailscale/tailscale/releases/latest

Android / Android TV source baseline from V1:

- `tailscale/tailscale-android`
- reviewed commit: `0867f01687a3955f7c0b5c6c62b236b997d68601`
- reviewed tree: `8eaa1daf0f632e71d058dcb09300efb3b1ccb079`
- BSD-3-Clause-style root license

Current first-party documentation reviewed:

- install/update/uninstall index: https://tailscale.com/docs/install
- custom control server: https://tailscale.com/docs/how-to/set-up-custom-control-server
- Kubernetes deployment: https://tailscale.com/docs/kubernetes
- Kubernetes exit-node/subnet-router Connector: https://tailscale.com/docs/kubernetes-operator/connector/deploy-subnet-router
- subnet routers: https://tailscale.com/docs/features/subnet-routers
- exit nodes: https://tailscale.com/kb/1103/exit-nodes/
- local device web interface: https://tailscale.com/kb/1325/device-web-interface
- update behavior: https://tailscale.com/kb/1067/update
- daemon configuration: https://tailscale.com/kb/1654/tailscaled-config-file
- server provisioning: https://tailscale.com/kb/1245/set-up-servers

## Architectural boundary used by all gates

Tailscale must be modeled as a mesh/network ecosystem, not as raw WireGuard plus a GUI:

`platform UI / CLI / local web UI`

`-> tailscaled / local backend / node identity`

`-> coordination/control service + network map + policy/account/device state`

`-> route/DNS/exit-node/subnet-router decisions`

`-> WireGuard-based peer data plane`

`-> direct NAT-traversed peer path when possible OR DERP relay path when needed`

The open client repository explicitly contains the majority, not the entirety, of Tailscale's open-source code. The hosted coordination/control service and some platform GUI wrappers are not proven open-source by the BSD-licensed client repository. That boundary is preserved throughout this dossier.

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence-backed conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | There is no conventional single VPN server. Serious server-side roles are nodes running `tailscaled`, subnet routers, exit nodes, DERP relays, and the coordination/control service. Official clients can also target a custom control server URL. Headscale may be used as a separately governed self-managed control-plane ecosystem, but it is not source-equivalent to Tailscale's hosted control service. |
| 2 | Official and major community installer/deployment projects reviewed | PASS | First-party install docs cover direct installers/packages/app stores, Linux packages, Windows installers/MSI, macOS variants, iOS, Android, Apple TV and other platforms. First-party container/Kubernetes paths are documented. Community one-click scripts are not promoted to canonical status merely because they exist; blind `curl | sh` trust is not inferred. |
| 3 | Server OS/container/orchestration install matrix completed | PASS | Relevant server/node roles are supported through the normal Tailscale Linux/Windows/macOS client paths; Linux is the primary documented subnet-router/server path. Docker image and Kubernetes Operator/sidecar/proxy/subnet-router paths are first-party documented. x86_64/ARM64 package/build concerns remain release/platform-specific rather than inferred globally. |
| 4 | Server panel/UI/menu maps completed | PASS | Hosted admin-console behavior is a management/control-plane reference, not an open server-panel source tree. Evidence-backed management surfaces include Machines/devices, routes/exit-node approval, access policy, keys/tags and automatic-update control. For local machine management, the first-party web interface is available on desktop platforms; current docs state v1.56.0+ and local `100.100.100.100`, with optional tailnet exposure on port 5252. No fictitious self-hosted clone of the hosted admin console is claimed. |
| 5 | Client install matrix completed across relevant OS targets | PASS | First-party install index covers Windows, macOS, Linux, Android, iOS and Apple TV/tvOS, plus Chromebook/Amazon Fire and managed deployment paths. App-store and direct-package update channels are explicitly separated. Windows MSI documentation currently requires Windows 10+ or Windows Server 2016+. Android/Android TV source/build evidence is separately pinned in V1. |
| 6 | Major client UI/menu maps completed separately | PASS | Open Android/TV UI/service source, open CLI/local API/web UI, and documented desktop/mobile first-party behavior together cover sign-in/account, device/tailnet state, peer list, exit-node selection, local-LAN-with-exit-node option, route/DNS state, updates, diagnostics/status and settings. Closed platform wrappers remain behavior references only; their source is not fabricated. |
| 7 | Cryptographic design documented | PASS | Tailscale data-plane confidentiality/authentication is WireGuard-based; entry 002 remains the canonical WireGuard cryptography reference. Tailscale adds node/account/control-plane identity, network-map/policy distribution, NAT traversal and DERP rather than defining a new replacement cipher suite. Direct and DERP-carried peer payloads remain WireGuard-protected; coordination metadata is a separate privacy surface. No claim is made that the hosted control service is cryptographically invisible to all metadata. |
| 8 | Data path/wire flow documented | PASS | Application traffic enters OS routing/TUN/platform networking, is assigned to a Tailscale peer/route/exit/subnet role, and traverses the WireGuard-based peer engine. Connectivity attempts direct peer paths using NAT traversal; DERP relays provide fallback when direct paths are unavailable. Exit nodes forward broader internet traffic; subnet routers expose selected private prefixes. Coordination/control exchanges distribute peer/network state separately from payload forwarding. |
| 9 | Ports/transports/handshake documented | PASS | Tailscale does not have one fixed `server:port` data endpoint. Peer transport is WireGuard-based UDP when direct connectivity succeeds, with DERP relay fallback over the relay ecosystem. Local web management can bind `100.100.100.100` by default and optional tailnet exposure uses port 5252 per current first-party docs. Hosted/custom control-server URLs are separate control-plane endpoints. Exact production firewall requirements remain deployment-specific and must follow current first-party networking docs rather than a fabricated universal port list. |
| 10 | Deployment topologies documented | PASS | Supported architectural roles include peer mesh, remote access, subnet router, exit node, server node, Kubernetes sidecar/operator/connector, and hybrid direct/DERP connectivity. Management plane, control/coordination plane and encrypted data plane remain distinct. Current Kubernetes docs cover ingress/egress/tailnet access and operator/sidecar/proxy/subnet-router patterns. |
| 11 | Source/license/activity pins recorded for server and client projects | PASS | Main open client/data-plane repository is pinned and BSD-3-Clause; Android/TV source is separately pinned. Stable release `v1.102.2` was re-verified through the canonical GitHub release API on 2026-08-15. Hosted control service, closed platform wrappers, branding and third-party integrations are not assumed to inherit the client repository license. |
| 12 | Security/supply-chain risks of installer projects recorded | PASS | Prefer first-party signed/package/store channels and first-party container/operator artifacts. Auth keys, OAuth/device credentials, node private keys and local backend state are secrets; ephemeral keys can reduce container/server lifecycle residue. Exposing local management interfaces or web UI beyond their defaults enlarges the attack surface. Hosted-service dependency, control metadata and third-party self-hosted-control compatibility are separate trust decisions. |
| 13 | Upgrade/uninstall/rollback behavior researched | PASS | First-party docs define manual updates, CLI update, app-store updates, packages/release tracks, admin-console/client auto-update controls and uninstall guidance. Platform stores own rollback/update semantics on store-managed clients; production rollback must pin an exact supported release rather than assuming indefinite downgrade compatibility. Container/operator deployments should pin image/operator versions for reproducibility. |
| 14 | Protocol/server/client differences and uncertainties explicitly listed | PASS | Raw WireGuard != Tailscale tailnet membership; subnet router != exit node; DERP != ordinary data-plane server; local web UI != hosted admin console; custom control server != proof of hosted-control source availability; Headscale compatibility != Tailscale-hosted-control equivalence. Closed desktop/iOS GUI source, hosted-control implementation details, exact release SBOM and production Store/account policies remain explicit later implementation/certification concerns. |
| 15 | `REFERENCE_INDEX.md` links the complete dossier | PASS | `research/protocols/056-entry/REFERENCE_INDEX.md` created with source pins, official evidence, V1/V2 files, completion state and exact continuation. |
| 16 | Latest AGENTS handoff contains exact continuation state | PASS | A new Tailscale V2 handoff is created in the same work unit and advances the campaign to Entry 057 ZeroTier. |

## Server implementation and deployment ecosystem

Tailscale does not map cleanly to a classic VPN-server inventory. The meaningful roles are:

1. **Tailscale node / `tailscaled`** — open client/service code; can act as ordinary peer, server node, subnet router or exit node depending on configuration.
2. **DERP relay** — relay role used when direct peer connectivity cannot be established; relay is not the account/control plane and is not a replacement for peer encryption.
3. **Hosted Tailscale coordination/control service** — account/device/network-map/policy coordination; proprietary/hosted-service boundary remains explicit.
4. **Custom control server** — official clients support a custom control server URL. Current first-party docs explicitly describe this for self-managed Headscale deployments. Compatibility must be verified against the selected versions rather than inferred forever.
5. **Kubernetes Operator / Connector / sidecar patterns** — first-party deployment mechanisms for cluster ingress/egress, subnet-router and exit-node roles.

No community installer is elevated to a security recommendation merely because it is popular.

## Install/deployment matrix

| Environment | Research state | Canonical path / notes |
|---|---|---|
| Linux server | SUPPORTED REFERENCE | First-party packages/install docs; common server, subnet-router and exit-node role. Requires routing/IP-forwarding capabilities where that role demands them. |
| Windows / Windows Server | SUPPORTED REFERENCE | First-party installer/MSI. Current MSI docs: Windows 10+ / Windows Server 2016+. Server role is a Tailscale node; routing features depend on documented platform support. |
| macOS | SUPPORTED REFERENCE | App Store and standalone variants documented; desktop client plus CLI paths. |
| Docker | SUPPORTED REFERENCE | First-party Docker image documented; auth/state/capability choices must be explicit. |
| Kubernetes | SUPPORTED REFERENCE | Operator, sidecar, proxy and subnet-router patterns documented; Connector CRD can deploy exit-node/subnet-router roles. |
| Android / Android TV | SUPPORTED CLIENT REFERENCE | First-party Android source pin plus app-store paths. Android TV build/output is explicit in source evidence. |
| iOS / iPadOS / tvOS | SUPPORTED CLIENT REFERENCE | First-party install docs and App Store/TestFlight channels. Closed wrapper source is not inferred. |
| BSD/Nix/other packages | SOURCE/PACKAGE-SPECIFIC | Main repository contains broader platform/build/package material, but exact support must be tied to selected release/package documentation. |

## UI / menu reference boundaries

### Local/desktop machine surfaces

Evidence-backed functions include:

- account/sign-in and multi-account/custom-control-server flows where supported;
- connect/backend status and device identity;
- peer/device visibility;
- exit-node selection;
- local-network-access while using an exit node;
- route/DNS/MagicDNS-related state;
- update/version controls where exposed;
- local web interface on desktop platforms;
- diagnostics/status/log-oriented surfaces.

### Hosted admin console

Behavioral/admin reference includes:

- Machines/devices;
- route and exit-node approval;
- users/accounts/tailnet context;
- access policy / ACL-grant policy;
- tags and auth keys;
- DNS/tailnet settings;
- device update controls and administration.

This is not represented as an open-source server-panel menu tree because the hosted control service/source boundary does not justify that claim.

## Cryptography and trust model

Tailscale's data plane reuses WireGuard. Therefore:

- do not invent a Tailscale-specific replacement cipher suite;
- keep peer traffic encryption/authentication under the WireGuard model documented in Entry 002;
- keep node/account enrollment, control-plane metadata and device authorization as separate trust layers;
- DERP relays may observe connection/relay metadata but do not require decrypting peer payloads;
- hosted coordination privacy is not equivalent to data-plane payload confidentiality.

Device keys, auth keys, OAuth/device credentials and related enrollment material belong in protected platform/backend storage.

## Wire/data-flow reference

```text
Application / OS traffic
        |
        v
Tailscale route / peer / exit / subnet decision
        |
        v
Local tailscaled / wgengine
        |
        +---- direct NAT-traversed peer path ----> remote peer
        |
        +---- DERP relay fallback --------------> remote peer

Separate control path:
account/device auth -> coordination/control -> network map/policy/routes/DNS metadata
```

Exit-node path extends the remote peer path into general internet forwarding. Subnet-router path extends it into selected non-Tailscale private prefixes. Neither role is a distinct cryptographic protocol.

## Installer/update/security review

- Prefer first-party packages, signed installers, app stores, package repositories, first-party Docker images and the official Kubernetes Operator.
- Do not convert an unreviewed community `curl | sh` into a recommended production installer.
- Containers/Kubernetes should use pinned image/operator versions and explicit auth-key or OAuth/device enrollment handling.
- Current Kubernetes docs recommend ephemeral auth keys for ephemeral workloads because nodes are automatically cleaned up after shutdown.
- Local management interfaces should remain local/default-restricted unless remote exposure is explicitly justified and access-controlled.
- Auto-update improves patch velocity but changes reproducibility; production support must record exact engine version and update policy.

## Upgrade / uninstall / rollback

Current first-party documentation supports:

- manual installer/package updates;
- `tailscale update` where CLI support exists;
- app-store update channels;
- stable / release-candidate / unstable package tracks;
- client/admin-controlled auto-update mechanisms;
- documented uninstall paths.

Rollback is not treated as an undocumented guaranteed cross-version feature. Reproducible deployments must pin a selected release/package/container image and preserve a tested recovery procedure during implementation.

## Explicit uncertainties / non-claims

The following are deliberately **not** inferred from V2 research completion:

- that PVNetwork has implemented Tailscale;
- that every target device/Store combination is certified;
- that Tailscale's hosted control service is open source;
- that all desktop/iOS GUI wrapper source is available;
- that Headscale is identical to or supported for every hosted Tailscale feature;
- that all traffic always uses direct P2P paths;
- that client BSD licensing grants Tailscale branding/trademark rights;
- that one release's SBOM/advisory status remains current forever;
- that runtime kill-switch/leak/NAT/roaming behavior has been certified on PVNetwork devices.

## PVNetwork reuse decision

**`OPTIONAL DEDICATED MESH-PROVIDER ADAPTER / REUSE OFFICIAL CLIENT CORE WHERE LEGALLY AND TECHNICALLY SELECTED / DO NOT DUPLICATE ENTRY 002 RAW WIREGUARD`**

If implemented, keep at least these boundaries explicit:

- account/provider identity;
- control-server/provider URL;
- tailnet/network identity;
- device/node keys and enrollment secrets;
- peer/network-map state;
- exit-node and subnet-router roles;
- DNS/MagicDNS state;
- direct-vs-DERP path status;
- engine/release metadata;
- hosted-service privacy and support boundaries.

## Final V2 decision

All exact 16 `COMPLETE-REFERENCE-v2` research gates are evidence-backed by the pinned V1 source analysis plus refreshed canonical first-party documentation and release evidence. Entry **056 — Tailscale** may be promoted to **`COMPLETE-REFERENCE-v2`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
