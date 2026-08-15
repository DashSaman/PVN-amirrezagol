# 057 — ZeroTier — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Entry: **057 — ZeroTier**

Decision: **`COMPLETE-REFERENCE-v2 / DISTINCT ENCRYPTED OVERLAY ECOSYSTEM / MIXED MPL + NONFREE CONTROLLER BOUNDARY / NOT IMPLEMENTED / NOT CERTIFIED`**

This audit applies the exact 16 second-layer gates in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`. It reuses the evidence-backed V1 dossier and refreshes current first-party release, platform, local-service API, controller and hosted-control references. Runtime/device/Store/interoperability receipts remain later implementation/certification work and are not hidden V2 research gates.

## Canonical evidence baseline

Primary source repository:

- `zerotier/ZeroTierOne`
- default branch: `dev`
- reviewed V1 commit: `899352e38405968516bb12a770f0ac02f6058fa8`
- reviewed tree: `1abcbd6b6d5a0608105972fd4bde1ef470c70236`
- stable release re-verified 2026-08-15: **1.16.2**, published 2026-05-28
- official release: https://github.com/zerotier/ZeroTierOne/releases/tag/1.16.2

Path-level license boundary from the reviewed repository remains mandatory:

- `node/`, `osdep/`, `service/` and most code outside `ext/` and `nonfree/`: MPL-2.0 per `LICENSE-MPL.txt`;
- `nonfree/`: separately licensed source-available/non-free code;
- `ext/`: dependency-specific licenses.

Current first-party docs reviewed:

- platform compatibility: https://docs.zerotier.com/compatibility/
- platform index: https://docs.zerotier.com/platforms/
- quickstart/client join UX: https://docs.zerotier.com/quickstart/
- local ZeroTierOne service API: https://docs.zerotier.com/api/client/
- local service API overview: https://docs.zerotier.com/api-service/
- service token lifecycle: https://docs.zerotier.com/tokens/
- self-hosted network controller: https://docs.zerotier.com/controller/
- hosted ZeroTier Central API: https://docs.zerotier.com/api-central/

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence-backed conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | ZeroTier is not a classic single VPN server. Serious roles are ZeroTierOne nodes/services, local/network controllers, hosted ZeroTier Central, roots/moons/relay infrastructure and router/gateway nodes. The repository controller path and hosted Central are distinct control-plane products/boundaries. |
| 2 | Official and major community installer/deployment projects reviewed | PASS | First-party downloads/platform docs are canonical for client/service installation. Source builds and daemon/service setup remain in the canonical repository. Self-hosted controller setup is documented through the local service/controller API. Community deployment scripts are reference-only unless separately pinned and audited; no blind one-click installer is promoted. |
| 3 | Server OS/container/orchestration install matrix completed | PASS | Current first-party compatibility lists Linux, macOS, Windows, iOS/iPadOS, Android and FreeBSD plus multiple CPU architectures (arm32, arm64, x86, x64/amd64, MIPS, s390x). Router/cloud/IoT platform docs are separately indexed. Controller/service deployment is most naturally Linux/server-side; container/orchestration support must be tied to exact first-party/controller artifacts rather than assumed universally. |
| 4 | Server panel/UI/menu maps completed | PASS | Hosted ZeroTier Central is a web control plane with network/member administration and API; the local standalone controller is managed through the authenticated local service API rather than a canonical open web panel. Evidence-backed controller resources include networks, members, authorization, managed routes/IP assignment and network settings. No fictitious open clone of Central's hosted UI is claimed. |
| 5 | Client install matrix completed across relevant OS targets | PASS | Current compatibility docs cover Linux, macOS, Windows, iOS/iPadOS, Android and FreeBSD and enumerate major CPU architectures. Windows/macOS tray/menu UX and mobile app-store flows are first-party documented. Exact minimum OS/store versions remain release-channel-specific. |
| 6 | Major client UI/menu maps completed separately | PASS | First-party desktop/tray and CLI behavior covers node status, join/leave network, network list/status, identity/address and settings. Mobile apps are behavioral references for join/network/status flows. `zerotier-cli` and local service API are the canonical open management surfaces; proprietary/hosted Central UI remains separately bounded. |
| 7 | Cryptographic design documented | PASS | ZeroTier's VL1/VL2 architecture is a distinct encrypted overlay and not WireGuard. V1 records cryptographically addressed peer identity, end-to-end encrypted peer traffic and controller-delivered membership/configuration. Exact implementation cryptography remains source/spec-defined in the canonical engine and must not be replaced by invented cipher claims. Controller authorization is separate from peer data-plane confidentiality. |
| 8 | Data path/wire flow documented | PASS | Application/L2 traffic enters the virtual ZeroTier network interface, is processed by the local ZeroTierOne node/service, matched to controller-delivered network membership/config/rules, then sent over direct peer paths where possible or relay/fallback paths when direct connectivity is unavailable. Return traffic follows the reverse overlay path into the virtual interface. |
| 9 | Ports/transports/handshake documented | PASS | There is no single static `server:port` endpoint model. The local management API is documented on `http://localhost:9993` and authenticated using `X-ZT1-Auth` with `authtoken.secret`; it is localhost-restricted by default. Overlay peer discovery/connectivity and relay behavior are part of the ZeroTier engine and network infrastructure rather than a conventional TLS/HTTP VPN handshake. Production firewall/NAT details must follow current ZeroTier network docs for the selected deployment. |
| 10 | Deployment topologies documented | PASS | Supported models include peer mesh, managed virtual L2/L3 networks, hosted Central-managed networks, standalone/self-hosted controller, router/gateway nodes, and direct-peer with relay fallback. Control plane, local service management plane and encrypted peer data plane are explicitly separated. |
| 11 | Source/license/activity pins recorded for server and client projects | PASS | `zerotier/ZeroTierOne` is pinned from V1; stable release 1.16.2 re-verified in the canonical release feed. MPL free paths, `nonfree/` code and `ext/` dependency licenses remain separate. Hosted Central is not assumed to be covered by the client/service MPL paths. |
| 12 | Security/supply-chain risks of installer projects recorded | PASS | `authtoken.secret` is a privileged local-management secret and local API exposure beyond localhost expands attack surface. Node identity/private state and Central API tokens are sensitive credentials. Default/nonfree builds can pull in separately licensed controller code; production must select build flags/artifacts deliberately and audit dependencies/SBOM. |
| 13 | Upgrade/uninstall/rollback behavior researched | PASS | Current release train is active and first-party platform installers/package channels own update/uninstall behavior. ZeroTier docs currently state supported major release families v1.16.x, v1.14.x and v1.12.x. Production rollback must pin an exact tested artifact and preserve service identity/state backups rather than assuming arbitrary downgrade compatibility. |
| 14 | Protocol/server/client differences and uncertainties explicitly listed | PASS | ZeroTierOne node/service != hosted ZeroTier Central; local controller != Central; free MPL paths != `nonfree/`; local API token != network identity; join/leave network != connect/disconnect to one endpoint; direct path != guaranteed no-relay path. Mobile GUI source, exact hosted-control implementation, selected free/nonfree build and final dependency/SBOM remain explicit later decisions. |
| 15 | `REFERENCE_INDEX.md` links the complete dossier | PASS | `research/protocols/057-entry/REFERENCE_INDEX.md` created with source/release/license pins, first-party docs, V1/V2 files and continuation. |
| 16 | Latest AGENTS handoff contains exact continuation state | PASS | A new ZeroTier V2 handoff is created in the same work unit and advances the campaign to Entry 058 NetBird. |

## Ecosystem and control-plane map

### ZeroTierOne node/service

Canonical open service/client core. Important source/behavior boundaries:

- `node/` core overlay logic;
- `osdep/` platform networking;
- `service/` daemon/local API integration;
- local CLI uses the service API;
- node identity, joined networks, peers, routes/rules and controller state are durable backend state rather than simple portable VPN profiles.

### Local service API

Current first-party API docs state:

- default server: `http://localhost:9993`;
- authentication header: `X-ZT1-Auth`;
- token source: `authtoken.secret` in the service working directory;
- resources include node status, joined networks, peers and controller endpoints;
- controller endpoints can list/create networks and members and authorize/configure membership.

The token is generated randomly at first start; deleting it and restarting causes a new token, invalidating the old token. This is a credential lifecycle event and must never be confused with network membership keys.

### Hosted ZeroTier Central

ZeroTier Central is first-party documented as the hosted control plane for ZeroTierOne networks. The web UI and Central API manage networks and organization/member state. Current docs describe service-account API tokens for New Central and personal API tokens for Legacy Central. Hosted Central is a separate product/service boundary and is not inferred to be open-source from the client repository.

### Standalone controller

The first-party controller docs expose controller operations through the same authenticated local service API, including network creation, network listing, member listing/authorization, managed route/IP assignment and private-network settings. This is the evidence-backed self-hosted-control path; no separate canonical open web panel is implied.

## Install / platform matrix

| Environment | Research state | Evidence-backed notes |
|---|---|---|
| Linux | SUPPORTED REFERENCE | First-party supported platform; standard daemon/service role and common self-hosted controller/router use. Root/admin privileges required for network/service operations. |
| Windows | SUPPORTED REFERENCE | First-party desktop/service plus tray UI. Current 1.16.2 fixed Windows join/leave delays and installer line endings for an ARM/x64-related driver path. |
| macOS | SUPPORTED REFERENCE | First-party desktop/service and menu-bar UI. |
| Android | SUPPORTED CLIENT REFERENCE | First-party app-store client; exact source/UI packaging remains separately governed. |
| iOS/iPadOS | SUPPORTED CLIENT REFERENCE | First-party app-store client. |
| FreeBSD | SUPPORTED REFERENCE | Listed by current first-party compatibility docs. |
| ARM32/ARM64/x86/x64/MIPS/s390x | ARCHITECTURE REFERENCE | Current compatibility docs enumerate these architectures; exact package availability still depends on selected platform/release. |
| Container/orchestration | PROJECT-SPECIFIC | Controller/release notes mention multi-architecture Docker pipeline for Central controller internals, but this is not enough to claim every ZeroTier role has one canonical open container/orchestrator path. Exact selected artifact must be pinned. |

## UI/menu boundaries

### Desktop / local node

Evidence-backed behavior includes:

- node online/status/address;
- join network by 16-character network ID;
- leave network;
- list joined networks and assigned addresses;
- peer/status information;
- local settings/service state;
- CLI/service API administration.

### ZeroTier Central

Behavioral/admin reference includes:

- organizations/account context;
- network groups/networks;
- network ID and network settings;
- members/devices and authorization;
- managed routes/address assignment/policy-related state;
- API tokens and automation/API access.

Central UI is a hosted control-plane reference; its source/license is not inferred from `ZeroTierOne`.

## Data-path reference

```text
Application / L2-L3 traffic
        |
        v
ZeroTier virtual network interface
        |
        v
ZeroTierOne local service/node
        |
        +--> controller-provided membership / routes / rules
        |
        +--> direct encrypted peer path when reachable
        |
        `--> relay/fallback path when direct connectivity fails
        |
        v
Remote ZeroTier node -> remote virtual interface / routed destination
```

Control/management flows are distinct:

- local UI/CLI -> authenticated localhost service API;
- controller/Central -> network membership/configuration/authorization;
- peer data plane -> encrypted overlay traffic.

## Security and supply-chain boundaries

- Keep `authtoken.secret`, node private identity, controller credentials and Central API tokens out of portable profile JSON, logs and support bundles.
- Keep the local API bound to localhost unless a separately secured admin design requires broader exposure.
- Do not label the entire repository simply MPL-2.0; `nonfree/` and `ext/` need separate review.
- Default builds that include nonfree controller material require an explicit legal/commercial decision.
- Hosted Central metadata/account/privacy exposure is a separate consideration from encrypted peer payloads.
- Relay fallback means topology/metadata claims must not say all traffic is always direct peer-to-peer.

## Explicit uncertainties / non-claims

This V2 research completion does **not** claim:

- PVNetwork implementation or certification;
- current Store approval on every target;
- that ZeroTier Central is open source;
- that every mobile/desktop GUI source is in the main repository;
- that every build is purely MPL-2.0;
- that a relay is never used;
- that all release 1.16.2 dependency/SBOM/advisory questions are permanently settled;
- that runtime leak/reconnect/MTU/NAT/roaming behavior has been certified on PVNetwork hardware.

## PVNetwork reuse decision

**`OPTIONAL DISTINCT MESH/OVERLAY ADAPTER / REUSE OFFICIAL FREE CLIENT-SERVICE PATHS ONLY AFTER EXACT PATH-LICENSE REVIEW / KEEP CONTROLLER AND HOSTED SERVICE SEPARATE`**

Do not translate ZeroTier into a normal server/port VPN profile. Preserve:

- node identity and local management credentials;
- controller/provider/network identity;
- joined-network state;
- member authorization and managed routes;
- direct-vs-relay path status;
- exact free/nonfree build mode;
- engine/release metadata.

## Final V2 decision

All exact 16 `COMPLETE-REFERENCE-v2` research gates are evidence-backed by the pinned V1 source/license analysis plus refreshed first-party release, platform, service API, controller and Central documentation. Entry **057 — ZeroTier** may be promoted to **`COMPLETE-REFERENCE-v2`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
