# 059 — Netmaker — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / WIREGUARD NETWORK ORCHESTRATION + GATEWAY PLATFORM / APACHE COMMUNITY PATHS + SEPARATELY LICENSED PRO / NOT IMPLEMENTED / NOT CERTIFIED`**

This audit applies all exact 16 second-layer gates in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`. It reuses the completed V1 source/license/architecture dossier and refreshes first-party release, deployment, gateway, UI, DNS, access-control, remote-access and lifecycle evidence. Runtime/device/Store/interoperability receipts remain later implementation/certification work and are not hidden V2 research gates.

## Canonical source / release / license baseline

Server / control plane:

- canonical repository: `gravitl/netmaker`
- stable release: `v1.6.0`
- published: 2026-06-12
- tag commit: `5f20416e13c307696605551459b116428b4053d3`
- release tree: `13e70d47ebbc9fa32de217dfe04274356c9ed1b5`
- release page: https://github.com/gravitl/netmaker/releases/tag/v1.6.0
- release commit is GitHub-verified.

Endpoint agent:

- canonical repository: `gravitl/netclient`
- stable release family: `v1.6.0`
- first-party release assets cover Darwin, Linux and Windows across multiple architectures and publish SHA-256 digests.

License boundary from exact `gravitl/netmaker@v1.6.0` source:

- normal repository paths: Apache-2.0;
- `pro/`: governed by `pro/PRO_LICENSE` and not an open-source production grant;
- third-party components retain their own licenses;
- hosted/SaaS and Enterprise commercial terms are separate from source-code licenses.

Netclient's root `LICENSE.txt` at v1.6.0 is Apache-2.0.

Primary first-party evidence reviewed:

- v1.6.0 README and quick-start: https://github.com/gravitl/netmaker/blob/v1.6.0/README.md
- release: https://github.com/gravitl/netmaker/releases/tag/v1.6.0
- quick install: https://docs.netmaker.io/docs/server-installation/quick-install
- current UI reference: https://docs.netmaker.io/docs/references/user-interface
- current Gateways model: https://docs.netmaker.io/docs/features/gateways
- remote-access gateway/client model: https://docs.netmaker.io/docs/features/remote-access-gateways-and-clients
- DNS: https://docs.netmaker.io/docs/features/dns
- traffic-flow/networking model: https://docs.netmaker.io/docs/netmaker-network-setup-concepts/3-configure-traffic-flow
- nmctl/API CLI: https://docs.netmaker.io/docs/references/nmctl
- release-pinned installer source: https://github.com/gravitl/netmaker/blob/v1.6.0/scripts/nm-quick.sh

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence-backed conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | First-party Netmaker server/API/Admin UI/nmctl is the canonical management/control plane, with Netclient as the managed endpoint agent and ordinary WireGuard as the peer data plane. Hosted SaaS, self-host Community and licensed Pro/Enterprise are separate operating/license modes. Gateway nodes provide remote-access, relay, egress/site-routing or internet-gateway roles depending on version/edition. Community projects listed by upstream are reference-only unless separately audited. |
| 2 | Official and major community installer/deployment projects reviewed | PASS | Canonical paths are the first-party Ubuntu quick installer plus repository Dockerfile/Compose/Kubernetes assets and direct release binaries. The release-pinned `nm-quick.sh` requires root, downloads auxiliary tooling and Netclient, creates config/compose state and installs/registers Netclient; therefore it is a privileged supply-chain subject, not a blind `curl|sh` recommendation. Upstream-listed Traefik/OpenWRT/Podman/Terraform/K3s projects are community references, not promoted to trusted defaults without separate source review. |
| 3 | Server OS/container/orchestration install matrix completed | PASS | Current upstream quick-start is explicitly Ubuntu 24.04 on a static-public-IP VM; repository evidence includes Docker, Compose and Kubernetes assets. The server path is Linux-centric. Netclient/release automation spans Linux, macOS and Windows; Docker/Kubernetes agent examples exist. FreeBSD/OpenWRT/community paths are integration/community cases, not asserted first-party server support. x86_64/ARM64 are covered in first-party release/build evidence. |
| 4 | Server panel/UI/menu maps completed | PASS | First-party UI reference maps Authentication/Signup/Login/Dashboard, Networks, All Devices/Nodes, node details, Egress, Remote Access/Gateways, DNS, ACLs, Users, user groups/invites/pending users and graph surfaces. Current docs add Gateways, Internet Gateway, DNS/System Configuration and edition-specific Pro features. Community/Pro menus are not flattened together: Pro-only metrics, audit logs, internet-gateway/user-management and newer policy features remain edition-scoped. |
| 5 | Client install matrix completed across relevant OS targets | PASS | Managed Netclient has first-party Linux/macOS/Windows release artifacts and container/Kubernetes deployment examples. Remote Access Clients can instead use generated WireGuard configurations on unmanaged WireGuard-capable devices, including phones/laptops/desktops/routers; QR download flows are documented. Current docs mention a Netmaker Desktop/mobile client path, but source/license/minimum-OS and Store certification are not inferred where not pinned. Android/iOS/TV are therefore remote-access/WireGuard capability references, not blanket certified Netclient targets. |
| 6 | Major client UI/menu maps completed separately | PASS | Endpoint roles are intentionally separated: managed Netclient is primarily daemon/CLI registration/network state; remote-access clients use Dashboard-generated config/QR plus their WireGuard/client UI; nmctl is an administrator CLI for API contexts and network/host/ACL/DNS/enrollment/ext-client/log operations. The Netmaker Admin UI is not treated as endpoint-client UI. Current docs evidence create/disable/delete external clients and gateway configuration; unsupported Store/UI details remain explicit unknowns rather than invented screens. |
| 7 | Cryptographic design documented | PASS | Netmaker does not define a replacement transport cipher: peer payload uses WireGuard cryptography and key model from Entry 002. Netmaker adds management/auth/enrollment/control metadata, DNS/ACL/routing policy and gateway configuration. Generated remote-access profiles contain WireGuard private/public key material and therefore are secret-bearing. OAuth/JWT/master/enrollment/access tokens belong to control-plane auth, not to the WireGuard data-plane cipher. |
| 8 | Data path/wire flow documented | PASS | Managed path: Admin/API/nmctl -> Netmaker management/auth/DB/policy/DNS -> Netclient enrollment/config distribution -> direct WireGuard peer interfaces. Gateway path: unmanaged WireGuard/remote-access client -> selected public Netmaker Gateway -> routed destination/mesh/egress or Internet Gateway. Relay/Gateway mode is used where direct peer reachability fails. Control-plane state is distinct from encrypted peer payload. |
| 9 | Ports/transports/handshake documented | PASS | v1.6.0 README quick-start requires inbound 443 and 51821 TCP/UDP at the VM/firewall and wildcard DNS, while endpoint WireGuard/gateway endpoints remain configurable. Current gateway examples show WireGuard endpoints and PersistentKeepalive. Registration/enrollment/auth occurs through Netmaker API/control mechanisms before WireGuard peer configuration becomes usable. These are deployment-version defaults, not immutable protocol ports. |
| 10 | Deployment topologies documented | PASS | Self-host Community, hosted SaaS, licensed Pro/Enterprise; peer mesh; remote-access gateway; relay/gateway; site-to-site via egress/routing; hub/gateway-assisted access; Internet Gateway/full tunnel; Docker/Kubernetes infrastructure; multi-gateway segmentation and DNS/ACL-controlled access are all first-party documented. Management plane, gateway forwarding roles and WireGuard data plane remain separate. |
| 11 | Source/license/activity pins recorded for server and client projects | PASS | Netmaker v1.6.0 commit/tree and publication date are pinned; Netclient v1.6.0 is separately versioned. Apache-2.0 community and Netclient paths, `pro/PRO_LICENSE`, third-party licenses and hosted/commercial terms are explicitly separated. Current `develop` is not a production pin. |
| 12 | Security/supply-chain risks of installer projects recorded | PASS | `nm-quick.sh` runs as root, downloads yq/Netclient/nmctl and creates compose/config/service state; production must pin/review exact script and downloaded artifacts rather than execute moving `master`. Sensitive state includes master key, enrollment/access tokens, OAuth/OIDC client secrets, MQ credentials, SMTP secrets, metrics secrets and WireGuard private keys/configs. Public dashboard/API/firewall exposure and wildcard DNS expand attack surface. |
| 13 | Upgrade/uninstall/rollback behavior researched | PASS | First-party quick installer documents `-u` for Pro upgrade and `-d` for Community downgrade; script preserves/reuses configuration values across install transitions. Netclient has explicit install/uninstall lifecycle in source. For production rollback, exact image/binary/config/database state must be pinned and backed up; no unsupported claim of arbitrary schema downgrade is made. Full destructive server uninstall is not promoted as a one-command guarantee where upstream does not document it. |
| 14 | Protocol/server/client differences and uncertainties explicitly listed | PASS | Netmaker != raw WireGuard; server/API/Admin UI != Netclient; Netclient-managed hosts != unmanaged Remote Access Clients; direct mesh != Gateway/Relay; egress/site routing != Internet Gateway; Community != Pro/Enterprise != SaaS; Apache paths != `pro/`; management tokens != WireGuard keys. Exact current mobile app source/license, Store state, full HA/SaaS parity and production rollback guarantees remain explicit uncertainties. |
| 15 | `REFERENCE_INDEX.md` links the complete dossier | PASS | `research/protocols/059-entry/REFERENCE_INDEX.md` is created with V1/V2 evidence, canonical pins, first-party references and next state. |
| 16 | Latest AGENTS handoff contains exact continuation state | PASS | The post-batch handoff advances the authoritative V2 campaign to Entry 060 Nebula after promotion and records the exact continuation contract. |

## Server / installer / deployment reference

### Canonical self-host quick install

The release README requires an Ubuntu 24.04 VM with static public IP, inbound 443 and 51821 TCP/UDP, and recommends wildcard DNS. It then downloads a root-run `nm-quick.sh` from the repository. The pinned v1.6.0 script is materially privileged:

- exits unless UID 0;
- uses Docker/Compose-oriented install state;
- stores files under a Netmaker install directory;
- downloads `yq` if missing;
- downloads/install-registers Netclient;
- downloads nmctl and configures an API context using a master key;
- persists server/domain/image/auth/DB/MQ/OIDC/SMTP/metrics variables;
- supports Community, Pro, monitoring, upgrade and downgrade branches.

PVNetwork must never convert the README's moving-branch convenience command into an unpinned production bootstrap.

### Server environment matrix

| Environment | V2 conclusion |
|---|---|
| Ubuntu 24.04 VM | Official quick-start server target. |
| Other Linux distributions | Source/container deployment may be feasible; not promoted to official quick-start support without exact docs. |
| Docker / Compose | First-party repository/deployment path. |
| Kubernetes | First-party repository assets and integrations exist; exact production chart/operator support is release-specific. |
| Windows Server / macOS as server | NOT-APPLICABLE / not asserted as canonical Netmaker-server targets. |
| x86_64 / ARM64 | First-party build/release evidence exists for relevant binaries/agents. |
| FreeBSD/OpenWRT | Community/client/integration evidence only; not generalized to official server support. |

## Admin UI / CLI map

### Admin UI

Evidence-backed current domains include:

- authentication, signup/login and dashboard;
- Networks;
- All Devices / Nodes and node detail;
- Gateway creation and configuration;
- Remote Access/Conf Files/client generation;
- Egress/site-routing and Internet Gateway where edition/version permits;
- DNS records and system DNS configuration;
- Access Controls/policies;
- users, groups, invitations and pending-user approval;
- graph/visibility/status surfaces;
- settings/system configuration;
- edition-specific metrics, audit, tagging, posture/policy/Enterprise functionality.

### nmctl

Current first-party CLI reference exposes API-oriented commands including ACL, context, DNS, enrollment keys, external clients, hosts, logs and network operations. nmctl is an administrator/API automation surface; it is not an endpoint consumer VPN UI.

### Remote Access client flow

Admin selects/creates a Gateway, creates a client/config, optionally sets DNS/address/public key and then downloads a WireGuard config or displays a QR code. Clients can be disabled/re-enabled or deleted from the UI. Current Gateways docs also describe desktop/mobile client paths; those are kept separate from managed Netclient.

## Cryptography and data path

```text
Admin UI / nmctl / API
        |
        v
Netmaker server: auth + DB + networks + policy + DNS + enrollment
        |
        +---- managed host -> Netclient -> WireGuard peer/interface
        |                              \
        |                               `--> direct encrypted peer path
        |
        `---- Gateway policy/config
                  ^
                  |
 unmanaged Remote Access Client / WireGuard config
                  |
                  `--> Gateway -> mesh peer / egress resource / Internet Gateway
```

Netmaker's management/control plane can see network membership, device/user metadata, routes, ACL/DNS and gateway state. It does not replace WireGuard's peer payload encryption. Remote-access configuration files contain private key material and must be handled as secrets.

## Gateway / routing / DNS boundaries

- A Gateway can provide remote access and, where needed, relay connectivity for nodes behind restrictive NAT/firewalls.
- Internet Gateway routes default/public traffic and is conceptually a traditional full-tunnel VPN gateway; current docs restrict the gateway role itself to Linux while Windows/macOS/Linux and remote clients may connect.
- Egress routes reach resources outside the overlay (office/VPC/LAN/IoT CIDRs).
- Multiple gateways can segment access by network or ACL policy.
- DNS is managed through Netmaker and current docs place DNS records under Networks -> DNS; the DNS base domain is set in Settings -> System Configuration.
- Remote-access Gateway DNS can be set to avoid DNS leakage or reach private DNS, but runtime leak prevention remains a later certification task.

## Security / supply-chain / privacy

Protected secrets include:

- WireGuard private keys and generated remote-access configs;
- `MASTER_KEY` / administrator API credentials;
- enrollment/preauth/access tokens;
- OAuth/OIDC client secret and JWT/session material;
- MQ credentials;
- SMTP credentials;
- metrics/service secrets;
- Pro license/tenant credentials where applicable.

Do not put these in ordinary support bundles. Public IP, wildcard DNS, dashboard/API exposure, Gateway forwarding, host networking/capabilities and privileged installers all require production hardening outside the research-completion claim.

## Explicit uncertainties / non-claims

V2 completion does **not** claim:

- PVNetwork implementation or real-device interoperability;
- Store approval for any Netmaker-specific mobile/desktop app;
- that every SaaS/Enterprise feature is present in Community;
- that `pro/` is Apache-2.0 or commercially reusable without Enterprise rights;
- that moving `develop`/`master` is a safe production source;
- that every Linux distribution is an officially supported server;
- that arbitrary database/schema rollback is supported;
- that every community installer/project is trustworthy;
- that current ports/features remain unchanged in future releases.

## PVNetwork reuse decision

**`OPTIONAL DEDICATED NETMAKER ORCHESTRATION PROVIDER / WIREGUARD DATA-PLANE REUSE / APACHE COMMUNITY CLIENT-SERVER PATHS MAY BE REUSE-CANDIDATES / PRO REQUIRES SEPARATE RIGHTS`**

Use a provider adapter that preserves account/server/network/host/enrollment/gateway/DNS/ACL state instead of flattening Netmaker into a raw WireGuard profile. Prefer stable APIs/release artifacts and audited Apache paths. Keep Pro/hosted features behind explicit licensing and terms review.

## Final V2 decision

All exact 16 `COMPLETE-REFERENCE-v2` gates are evidence-backed by the V1 pin/license/source dossier plus refreshed first-party release, installer, UI, gateway, remote-access, DNS, routing and CLI evidence. Entry **059 — Netmaker** may be promoted to **`COMPLETE-REFERENCE-v2`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
