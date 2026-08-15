# 058 — NetBird — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / WIREGUARD MESH + DISTINCT MANAGEMENT/SIGNAL/RELAY PLANE / SPLIT BSD-AGPL LICENSE / NOT IMPLEMENTED / NOT CERTIFIED`**

This audit applies all exact 16 second-layer gates in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`. It reuses the completed V1 dossier and refreshes current first-party install, self-hosting, access-control, routing, exit-node, DNS, scaling and release evidence. Runtime/device/Store/interoperability receipts remain later implementation/certification work.

## Canonical release and license baseline

Repository: `netbirdio/netbird`

Current stable release re-verified 2026-08-15:

- tag: `v0.77.0`
- published: 2026-08-13
- V1 pinned tag object: `a30bb8d40108f4ff96e5731f8508d4fc28e6d1ca`
- target commit: `c5503fdc7f93ae6844a39caecf2970b43618c9b2`
- release tree: `9da63d4d8e9d22918c56cc6112f97e3a3cd496a2`
- canonical release: https://github.com/netbirdio/netbird/releases/tag/v0.77.0

License split remains mandatory:

- repository paths generally BSD-3-Clause;
- `management/`, `signal/`, `relay/`, `combined/`: AGPL-3.0;
- Enterprise self-hosting may use a separate commercial license;
- Cloud service terms are separate from client/source licenses.

Current first-party docs reviewed:

- client install matrix: https://docs.netbird.io/get-started/install
- architecture: https://docs.netbird.io/about-netbird/how-netbird-works
- self-host advanced guide / ports: https://docs.netbird.io/selfhosted/selfhosted-guide
- self-host scaling/split services: https://docs.netbird.io/selfhosted/maintenance/scaling/scaling-your-self-hosted-deployment
- self-host environment variables: https://docs.netbird.io/selfhosted/environment-variables
- access control/groups/policies: https://docs.netbird.io/manage/access-control
- routing/legacy Routes: https://docs.netbird.io/manage/network-routes
- current Networks/routing-peer model: https://docs.netbird.io/manage/networks/how-routing-peers-work
- exit nodes: https://docs.netbird.io/use-cases/remote-access/exit-nodes
- DNS management: https://docs.netbird.io/manage/dns/dns-settings
- dashboard Control Center: https://docs.netbird.io/manage/control-center

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence-backed conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | First-party server roles are Management, Signal, Relay/STUN and Dashboard, with current combined `netbird-server` self-host deployment and optional split-service scaling. NetBird Cloud and Enterprise self-host are separate service/license modes. |
| 2 | Official and major community installer/deployment projects reviewed | PASS | First-party self-host quickstart/combined architecture, advanced multi-container guide and release-provided installer/self-host scripts are canonical. Release assets expose SHA-256 digests. Community installers are not promoted without separate audit. |
| 3 | Server OS/container/orchestration install matrix completed | PASS | Current self-host documentation centers on Linux/server Docker/container deployment; combined server, reverse proxy, dashboard and split services are documented. Client install docs cover Linux, macOS, Windows, Android, iOS plus Synology/TrueNAS/pfSense. Kubernetes/routed-resource behavior is documented where applicable, but no universal orchestration support is invented. |
| 4 | Server panel/UI/menu maps completed | PASS | Dashboard/admin surfaces are evidenced for Peers, Groups, Policies, posture/access control, DNS, Networks/routes/exit-node-related routing, identity/users and Control Center. Control Center provides topology views linking peers, groups, networks/resources and policies. Cloud/self-host UI behavior is referenced without assuming identical licensing/source for every hosted feature. |
| 5 | Client install matrix completed across relevant OS targets | PASS | First-party install index covers Linux, macOS, Windows, Android, iOS and additional NAS/firewall platforms. Current release assets and release CI provide cross-platform package evidence. Exact Store/minimum-OS values remain platform-release specific. |
| 6 | Major client UI/menu maps completed separately | PASS | Desktop/mobile/client behavior includes login/enrollment, connect/status, peers, DNS, routing/exit-node state, updates and diagnostics. Browser/Web client is a separate architecture. Dashboard/admin UI remains distinct from endpoint client UI; no generic merged menu is invented. |
| 7 | Cryptographic design documented | PASS | Peer traffic is WireGuard-encrypted; the peer private key is generated and retained on the endpoint. Management distributes public-key/network-map/policy/DNS/routing state; Signal brokers encrypted negotiation; Relay forwards already WireGuard-encrypted packets when direct connectivity fails. Entry 002 remains the underlying WireGuard crypto reference. |
| 8 | Data path/wire flow documented | PASS | Client gets account/network map from Management, exchanges connection candidates through Signal/STUN, prefers direct peer WireGuard paths, and falls back to Relay when direct P2P is unavailable. Routing peers/Networks/exit nodes forward selected resource or default-route traffic. Control/signaling/relay/data planes remain distinct. |
| 9 | Ports/transports/handshake documented | PASS | Current self-host reverse-proxy deployment uses 80/TCP and 443/TCP plus 3478/UDP STUN; direct exposure may use Management gRPC 33073/TCP, Signal 10000/TCP and Relay 33080/TCP in addition to 80/443/3478. These are deployment-version facts, not immutable protocol ports. Connection negotiation uses Management + Signal/STUN/ICE-style candidate exchange, then direct WireGuard or relay fallback. |
| 10 | Deployment topologies documented | PASS | Cloud managed control plane, open-source self-host, commercial Enterprise self-host, combined single-server, split Management/Signal/Relay, peer mesh, routing peer, site-to-site/remote access, Networks resources and exit-node/default-route patterns are mapped. |
| 11 | Source/license/activity pins recorded for server and client projects | PASS | v0.77.0 release, exact V1 tag/commit/tree and release date are pinned. Client/general BSD paths and AGPL server directories are explicitly separated; Enterprise commercial license and Cloud terms remain separate. |
| 12 | Security/supply-chain risks of installer projects recorded | PASS | Release scripts/assets have GitHub SHA-256 digests; blind remote-script trust is not recommended. Sensitive setup keys, IdP tokens, Management API credentials, relay auth secret, TLS keys and datastore encryption keys are separately identified. Self-host reverse proxy, IdP and server components expand the supply-chain surface. |
| 13 | Upgrade/uninstall/rollback behavior researched | PASS | Client/platform package channels and current release pipeline provide update paths. Self-host combined-vs-legacy multi-container migration is explicitly documented; environment/config changes require coordinated service/config updates. Production rollback must pin exact release/images/config/database state instead of assuming schema downgrade compatibility. |
| 14 | Protocol/server/client differences and uncertainties explicitly listed | PASS | NetBird != raw WireGuard; Management != Signal != Relay; direct != relayed path; Cloud != OSS self-host != Enterprise; Networks != deprecated Routes; dashboard/admin != endpoint client UI; BSD client paths != AGPL server paths. Exact Store/runtime certification, HA mode, selected IdP and production SBOM remain explicit later decisions. |
| 15 | `REFERENCE_INDEX.md` links the complete dossier | PASS | `research/protocols/058-entry/REFERENCE_INDEX.md` created in this work unit with sources, pins and continuation. |
| 16 | Latest AGENTS handoff contains exact continuation state | PASS | A new mesh-family V2 handoff advances the campaign to Entry 059 Netmaker after this entry is promoted. |

## Server/deployment model

Current default self-host architecture is a single-server deployment with reverse proxy + Dashboard + a combined `netbird-server` container containing Management, Signal and Relay/STUN functions. Current scaling docs allow splitting Relay, Signal and database/service roles as deployments grow. The advanced guide preserves the older multi-container model for migrations/advanced cases.

Current documented reverse-proxy ports:

- 80/TCP — certificate validation / HTTP redirect;
- 443/TCP — Dashboard, Management API/gRPC, Signal gRPC and Relay WebSocket multiplexing;
- 3478/UDP — STUN.

Direct exposure may separate Management gRPC (`33073/TCP`), Signal (`10000/TCP`) and Relay (`33080/TCP`). Older pre-v0.29 clients may require legacy compatibility ports. These values belong to the reviewed deployment generation, not to a timeless protocol specification.

## Admin/client UI reference

### Dashboard/admin

Evidence-backed domains include:

- Peers/devices;
- Access Control > Groups;
- Policies and posture checks;
- Networks/resources/routing peers;
- legacy Routes for compatibility and exit-node cases;
- DNS configuration and managed/unmanaged peer DNS behavior;
- users/identity/enrollment;
- Control Center topology and policy graph;
- self-host/cloud administration/health.

Current docs explicitly say the old `Routes` model is deprecated for most new routed-resource use cases and `Networks` is the preferred abstraction; exit nodes remain a special default-route use case.

### Endpoint client

Reference surfaces include:

- enrollment/login/setup key;
- connection/backend status;
- peer/network path state;
- DNS enable/disable state;
- route/Network/exit-node application state;
- direct-vs-relayed connectivity and diagnostics;
- update/version/logging surfaces.

## Wire/data path

```text
Endpoint app traffic
      |
      v
NetBird client / local WireGuard interface
      |
      +---- Management: identity, peer public keys, policies, DNS, Networks/routes
      |
      +---- Signal/STUN: candidate exchange / connection negotiation
      |
      +---- direct peer WireGuard path (preferred)
      |
      `---- Relay fallback carrying WireGuard-encrypted packets
                 |
                 v
       remote peer / routing peer / exit-node destination
```

Signal does not carry ordinary peer payload after path setup. Relay cannot decrypt WireGuard peer payload. Management/control metadata remains a separate privacy surface.

## Routing/DNS/security specifics

- Current `Networks` model is the recommended routed-resource abstraction for new deployments.
- Legacy `Routes` continue for compatibility and exit-node/default-route scenarios.
- Exit nodes use `0.0.0.0/0`; with IPv6 overlay enabled, `::/0` may be paired. Current docs describe blocking IPv6 on peers without overlay IPv6 support to avoid leak outside the exit-node path.
- DNS can be managed per peer/group; unmanaged mode leaves system DNS unchanged.
- Exit-node deployments should pair routing with an appropriate DNS design to avoid location/DNS leakage.

## Security / supply-chain / license boundaries

- Peer WireGuard private keys remain endpoint-local.
- Treat setup keys, IdP/OIDC credentials, API/service credentials, relay authentication secrets, TLS private keys and datastore encryption keys as protected secrets.
- Do not call the repository wholly BSD: `management/`, `signal/`, `relay/`, `combined/` are AGPL-3.0.
- Using/modifying self-host AGPL services has different obligations from embedding BSD client paths.
- Enterprise self-host and Cloud are separate commercial/service arrangements.
- Release script digests help provenance but do not replace source review, signature verification or production SBOM/advisory scanning.

## Explicit uncertainties / non-claims

V2 completion does **not** claim:

- PVNetwork implementation or real-device certification;
- Store approval on every platform;
- that every NetBird feature/UI is BSD licensed;
- that open-source self-host includes every Enterprise HA capability;
- that Cloud and self-host feature parity is complete;
- that direct P2P is always possible;
- that runtime DNS/IPv6/kill-switch behavior has been certified on PVNetwork;
- that current release dependency/SBOM risk remains valid indefinitely.

## PVNetwork reuse decision

**`OPTIONAL DEDICATED NETBIRD PROVIDER ADAPTER / BSD CLIENT PATHS MAY BE REUSE-CANDIDATES / AGPL SERVER PATHS REQUIRE DELIBERATE SELF-HOST LICENSE COMPLIANCE OR COMMERCIAL ALTERNATIVE`**

Do not flatten NetBird to raw WireGuard. Preserve provider/account, enrollment, Management/Signal/Relay endpoints, Networks/policies/DNS/exit-node state, direct-vs-relay path health and exact engine/server versions.

## Final V2 decision

All exact 16 `COMPLETE-REFERENCE-v2` gates are evidence-backed by the V1 source/license/provenance dossier plus refreshed first-party architecture, self-host, install, routing, access-control, DNS and scaling documentation. Entry **058 — NetBird** may be promoted to **`COMPLETE-REFERENCE-v2`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
