# 059 — Netmaker — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **059 — Netmaker**

Decision: **`COMPLETE-RESEARCH-v1 / WIREGUARD NETWORK ORCHESTRATION PLATFORM / COMMUNITY+PRO PATH-LICENSE-SENSITIVE / NOT IMPLEMENTED / NOT CERTIFIED`**

Netmaker is a management/orchestration platform around WireGuard networks, remote-access gateways, site-to-site connectivity, DNS, policy/access controls and host/client lifecycle. It is not a replacement identity for entry 002 WireGuard and should be modeled as a distinct network/provider ecosystem.

## 1. Current source / release baseline

Primary repository:

- `gravitl/netmaker`
- default branch: `develop`
- reviewed commit: `f81fe29d4d6aaf59a3ef8db4933a60665f30028f`
- reviewed tree: `b9e3fe9eb6e6145dbe7560a9db7b16e3fab4fa0e`
- reviewed head date: 2026-08-10
- latest reviewed release: **v1.6.0**, published 2026-06-12
- implementation: primarily Go, with server/API/CLI/network logic, scripts, containers, Kubernetes assets, tests/workflows and `pro/` extensions in one large repository.

Current release artifacts include:

- Linux Netmaker server binary;
- `nmctl` binaries for Linux/macOS/Windows architectures;
- checksums;
- release/container workflows.

The current README explicitly shows version 1.6.0 and self-host/community/PRO boundaries.

## 2. Exact license boundary

The reviewed `LICENSE.md` is path-sensitive:

- `pro/` content, if present, uses `pro/PRO_LICENSE`;
- third-party components keep original licenses;
- content outside those restrictions is **Apache License 2.0**.

Therefore:

- community/open paths may be **REUSE-CANDIDATE** under Apache-2.0 obligations;
- `pro/` must never be treated as Apache/open merely because it is visible in the same repository;
- third-party and NOTICE obligations require path/dependency review;
- trademark/product naming remains separately restricted by Apache's trademark clause.

Current develop head contains substantial MSP/multitenancy/license-validation/tenant-scoping work. This reinforces that moving `develop` is not a stable production pin. PVNetwork must select a release/tag and understand Community vs Pro capability boundaries.

## 3. Current architecture

Current README defines Netmaker as WireGuard automation for:

- WireGuard Networks;
- Remote Access Gateways;
- Mesh VPNs;
- Site-to-Site;
- Admin UI;
- OAuth;
- Private DNS;
- Access Control Lists;
- Linux/Docker/macOS/Windows automation.

Current repository source/tree includes:

- API/controllers/logic/database/models/schema;
- auth and host/session state;
- CLI/nmctl commands and contexts;
- DNS/network/ACL/egress/ingress/extclient/host logic;
- MQTT/EMQX-style event/control plumbing in the wider architecture;
- gRPC/proto flows;
- Docker/Compose/Kubernetes deployment material;
- `scripts/` installation/upgrade/netclient/OpenWRT helpers;
- `k8s/client/` and `compose/docker-compose.netclient.yml` client/agent deployment examples;
- `pro/` commercial extensions and integrations;
- CI/release/test workflows.

Conceptual separation:

`Admin UI / API / nmctl`

`-> Netmaker management server / DB / auth / policy / DNS`

`-> host/netclient registration + enrollment/preauth state`

`-> WireGuard interfaces/peer config`

`-> direct WireGuard data plane between participating hosts, plus gateway/egress/ingress roles where configured`

The server/control plane configures the network; actual peer payload is handled by WireGuard. A raw WireGuard profile does not carry Netmaker users/tenants/networks/ACL/DNS/remote-access lifecycle.

## 4. Server install/deployment evidence

Current README self-host quick start requires:

- Ubuntu 24.04 VM with static public IP;
- inbound TCP/UDP exposure documented for 443 and 51821;
- recommended wildcard DNS;
- root-level quick-install script downloaded from the repository.

This is deployment evidence, **not a recommendation for PVNetwork to execute a remote shell script without source review**.

Current tree includes:

- `Dockerfile`, `Dockerfile-quick`;
- Compose assets;
- Kubernetes assets;
- install/upgrade scripts;
- Docker publish/release workflows;
- OpenWRT daemon scripts;
- client daemonsets/manifests.

V2 must audit exact privilege, firewall, DNS, service, container host-network/capability, update/rollback/uninstall and secret-default effects before Netmaker installers are ever recommended or automated.

## 5. Client/agent / platform evidence

Current repository search/tree contains Netclient deployment material directly, including:

- `scripts/netclient.sh`;
- FreeBSD rc support;
- multi-arch and userspace Dockerfiles;
- Kubernetes netclient YAML/daemonset;
- Netclient Compose;
- release/build references.

Netmaker README currently advertises Linux, Docker, macOS and Windows automation. Community integrations also include OpenWRT and other infrastructure ecosystems.

Netmaker is primarily an infrastructure/network-orchestration platform rather than a consumer mobile/TV VPN app. Android/iOS/TV should therefore be `N/A` unless a current official supported client path is separately verified; PVNetwork must not invent a mobile client because its overall product targets include mobile.

## 6. UI / menu / configuration evidence

Current product/server UX includes an Admin UI and current server/API/CLI surfaces around:

- networks;
- hosts/clients;
- remote access;
- ingress/egress;
- DNS;
- ACL/access control;
- users/auth/OAuth;
- enrollment/preauth tokens;
- server/settings;
- tenants/orgs/roles in newer current development;
- integrations/posture/flows/audit/security functionality where edition/version permits.

`nmctl` current release artifacts and source provide CLI context/access-token/network/admin automation. UI fields/menus in Community vs Pro must not be mixed without exact edition evidence.

## 7. Persistence / credentials / privacy

Distinct state includes:

- server DB/network/host/user/role/tenant state;
- OAuth/SSO/session tokens;
- preauth/enrollment/access tokens;
- WireGuard peer/private-key material;
- DNS/ACL/routes/egress/ingress configuration;
- client/host registration and context state;
- license/feature state for Pro/enterprise paths;
- audit/flow/posture/telemetry data.

PVNetwork rules:

- enrollment/access/OAuth tokens and private keys -> protected storage;
- generated WireGuard configs -> secret-bearing and redacted;
- network/host/user/tenant metadata -> privacy-sensitive structured state;
- logout/unregister/delete-host/delete-network are different lifecycle operations;
- admin/API credentials never belong in ordinary client profiles/support bundles.

## 8. Tests / CI / maintenance evidence

Current tree contains workflows for:

- branch testing;
- unit/integration/test jobs;
- Docker builds;
- release/pre-release;
- docs;
- dependency monitoring through Dependabot;
- server/client packaging and build scripts.

The reviewed current develop commit includes extensive multitenancy/auth/license/cache/API context changes with test-fix follow-ups, demonstrating active 2026 development but also making it inappropriate to treat current develop as a stable production contract.

Release v1.6.0 includes checksummed binaries; PVNetwork production evidence must pin release source/binary origins and not use moving develop.

## 9. PVNetwork reuse decision

Classification:

**`OPTIONAL INFRASTRUCTURE/SELF-HOSTED NETWORK PLATFORM / APACHE COMMUNITY PATHS REUSE-CANDIDATE / PRO PATHS NEED SEPARATE LICENSE`**

Recommended strategy if Netmaker support is selected:

1. use a dedicated `MeshOrchestrationProviderAdapter`, not the ordinary consumer single-server VPN profile model;
2. prefer stable Netmaker API/CLI or audited Apache community components over copying server internals;
3. keep `pro/` and hosted/SaaS features behind their own licensing/API/terms review;
4. do not embed a full Netmaker server in a consumer client merely to claim another protocol;
5. reuse entry 002 WireGuard for the data-plane cryptography model while retaining Netmaker's control/user/network/DNS/gateway lifecycle independently;
6. treat current quick-install scripts as V2 auditable deployment projects, not blindly runnable recipes.

## 10. 20-gate V1 reconciliation

| # | V1 gate | Result | Evidence / Netmaker conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | Official Netmaker server/API/Admin UI/nmctl/netclient ecosystem is primary; community integrations are supplementary and role-separated. |
| 2 | Canonical sources pinned | PASS | Exact current develop commit/tree and stable v1.6.0 release are pinned. |
| 3 | Licenses reviewed | PASS | Apache-2.0 community paths, `pro/PRO_LICENSE` exception and third-party original licenses are explicit. |
| 4 | Complete source-tree reference | PASS | Exact recursive tree records server/API/CLI/auth/logic/models/scripts/Docker/K8s/netclient/pro/workflows/test paths. |
| 5 | Languages/build systems | PASS | Go primary code, Go modules/build/release, Docker/Compose/K8s, shell installers and platform packaging are mapped. |
| 6 | Architecture | PASS | Admin/API -> management/auth/DB/DNS/ACL -> host/netclient enrollment -> WireGuard data-plane separation is explicit. |
| 7 | Core/engine integration | PASS | Dedicated provider/API/CLI or audited community component path; raw WireGuard does not replace Netmaker orchestration semantics. |
| 8 | UI/menu map | PASS for V1 | Admin UI/nmctl current management surfaces and edition boundaries are mapped; consumer mobile UI is N/A unless officially supported. Exhaustive menus remain V2. |
| 9 | Config/import/export | PASS | Network/host/client/tokens/routes/DNS/ACL/ingress/egress/server settings and API contexts are mapped; this is not a single share URI. |
| 10 | Persistence/secrets | PASS | WireGuard keys, preauth/access/OAuth/admin secrets and structured server/network/user/client state have separate ownership/redaction. |
| 11 | Platform integrations | PASS for research | Linux/Docker/macOS/Windows plus client/container/K8s/OpenWRT infrastructure paths are source-backed; unsupported consumer mobile roles are not fabricated. |
| 12 | Logs/diagnostics | PASS | Server/API/client/flow/audit/status/CLI surfaces are mapped; sensitive host/user/token/key/network metadata requires redaction. |
| 13 | Assets/screenshots | PASS for V1 | README/admin imagery and product assets are references only; Netmaker brand/trademark rights are separate from Apache source rights. |
| 14 | Meaningful alternatives/forks | PASS | SaaS, self-host Community, Pro, community plugins and other mesh/orchestration platforms are explicitly distinguished. |
| 15 | Issues/PRs/releases/advisories | PASS | Current develop 2026 multitenancy/licensing/auth changes, v1.6.0 release and dependency/build processes are recorded. |
| 16 | Relevant forums/docs | PASS | Current README, official install/getting-started docs, Discord/Reddit/blog resources and source/API docs are identified as evidence classes. |
| 17 | Tests/CI | PASS | Current test/release/Docker/docs workflows and test-fix activity are source-backed; product-specific integration remains later. |
| 18 | Store/privacy/security | PASS | Infrastructure privileges, quick-install risk, admin/OAuth/tokens/keys, host/user metadata, Community/Pro license and consumer Store N/A boundaries are explicit. |
| 19 | PVNetwork reuse decision | PASS | Optional orchestration provider using official stable API/CLI/community paths; no full server embed just for protocol count; Pro requires separate license. |
| 20 | Uncertainties | PASS | Exact production API/release/client compatibility, Pro/SaaS API terms, installer side effects, mobile scope, performance and full V2 server/admin/install/topology evidence remain later. |

## 11. Later acceptance work — not V1 blockers

- freeze exact stable server/client/API release, source, binaries, hashes, dependencies/SBOM/advisories;
- source-audit `nm-quick.sh`, upgrade scripts, Docker/Compose/K8s and Netclient installers;
- certify Community vs Pro feature/API matrix;
- test enrollment/access-token/OAuth/logout/revocation lifecycle;
- test WireGuard config updates, DNS, ACL, ingress/egress/remote access and route cleanup;
- test self-host upgrades/rollback/uninstall/backup/restore;
- test Linux/macOS/Windows supported client lifecycle;
- privacy-safe audit/flow/support exports;
- complete V2 exhaustive Admin UI/CLI/client menus, crypto/data path, ports and deployment topologies.

## Final V1 decision

All 20 original V1 research gates have traceable evidence or edition/platform N/A boundaries. Entry 059 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
