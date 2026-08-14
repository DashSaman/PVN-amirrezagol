# Entry 013 — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14

Entry: **013 — SoftEther VPN Protocol**

Purpose: reconcile the native SoftEther VPN Protocol dossier against every research/reference category in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`, without conflating the native protocol with SoftEther Server compatibility modes such as SSTP, L2TP/IPsec, OpenVPN-compatible mode or EtherIP.

## Status vocabulary

- `REFERENCE-PASS`: category has traceable source/reference evidence.
- `BLOCKED_EXTERNAL`: strict proof requires exact selected-release client/server/platform/runtime evidence.

## 1. Server implementation/project ecosystem mapped

`REFERENCE-PASS`

Evidence:

- `SERVER_IMPLEMENTATIONS.md`
- canonical implementation pinned at `SoftEtherVPN/SoftEtherVPN@49eb2f08641709d1af57a0d04971973ff94461db`;
- native Server/Protocol/Client/Cedar session/Virtual Hub architecture separated from compatibility protocols;
- VPN Bridge treated as an infrastructure/native role.

## 2. Official and major installer/deployment projects reviewed

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`

Covers canonical Windows/Linux server deployment, VPN Bridge, Unix-like portability caveats, OCI/Kubernetes constraints, management exposure, multiprotocol minimization, upgrade/rollback/uninstall and supply-chain rules.

## 3. Server OS/container/orchestration install matrix completed

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALL_MATRIX.md`

Covers Windows, Debian/Ubuntu, Fedora/RHEL, rolling Linux, selected Unix-like platforms, VPN Bridge, cloud VM, OCI and Kubernetes with exact-release/runtime gates.

## 4. Server panel/UI/control-plane menus completed

`REFERENCE-PASS`

Evidence:

- `SERVER_UI_AND_MENUS.md`

Maps server/listeners, Virtual Hubs, users/auth, native vs compatibility modes, local bridge, SecureNAT, cascade, sessions, vpncmd/API boundaries, RBAC and secret handling.

## 5. Client install matrix completed across relevant targets

`REFERENCE-PASS`

Evidence:

- `CLIENT_INSTALL_MATRIX.md`

Maps Windows native SoftEther VPN Client as the primary client, Linux/source-build evidence conservatively, VPN Bridge as infrastructure peer, and explicitly rejects iOS/Android compatibility-mode clients as native entry-013 evidence.

## 6. Major client UI/menu maps completed separately

`REFERENCE-PASS`

Evidence:

- `CLIENT_UI_AND_MENUS.md`

Maps Windows Client Manager/native profile concepts, virtual adapter lifecycle, authentication/trust, native session/connection state, diagnostics, Linux/source-build caveats, secret handling and protocol identity.

## 7. Cryptographic/security design documented

`REFERENCE-PASS`

Evidence:

- `CRYPTOGRAPHY.md`

Separates TLS transport security, server certificate trust, native session/user authentication, Virtual Hub authorization, management credentials, compatibility-protocol crypto boundaries, secure storage and algorithm-policy refresh requirements.

## 8. Data path/wire flow documented

`REFERENCE-PASS`

Evidence:

- `DATA_PATH_AND_WIRE_FLOW.md`

Covers virtual adapter -> native session -> TLS/TCP connection(s) -> server -> Virtual Hub -> bridge/SecureNAT/cascade path, parallel-connection handling, routes/DNS, observability and cleanup.

## 9. Ports/transports/handshake documented

`REFERENCE-PASS`

Evidence:

- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`

Documents configurable TCP/TLS listeners, commonly used 443/992/5555 product defaults as non-invariants, native session/auth/hub establishment, multiple connection capability, management-plane separation, firewall, reconnect and MTU.

## 10. Deployment topologies documented

`REFERENCE-PASS`

Evidence:

- `DEPLOYMENT_TOPOLOGIES.md`

Covers remote access, SecureNAT, local bridge, VPN Bridge/site-to-site, cascade, multiple listeners, multiprotocol gateway, cloud, load balancer/HA, clustering boundary, containers, dual stack and migration.

## 11. Source/license/activity pins recorded

`REFERENCE-PASS at reviewed baseline / refresh required before implementation`

Evidence:

- `REFERENCE_INDEX.md`
- exact reviewed source baseline: `49eb2f08641709d1af57a0d04971973ff94461db`;
- root Apache-2.0 license recorded;
- relevant Cedar source areas identified.

Before implementation/source freeze, refresh to the exact selected current release/tag and preserve third-party dependency/license inventory. This is a normal release-freeze requirement, not a missing current dossier source identity.

## 12. Security/supply-chain risks recorded

`REFERENCE-PASS`

Evidence:

- `CRYPTOGRAPHY.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- UI/install matrices.

Includes TLS/private-key/user/admin/external-AAA separation, unused multiprotocol listener exposure, management plane, bridge/network privilege, container image provenance, backup secrets and no blind-script rule.

## 13. Upgrade/uninstall/rollback behavior researched

`REFERENCE-PASS at reference layer / BLOCKED_EXTERNAL for receipts`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `CLIENT_INSTALL_MATRIX.md`

Defines binary/config/certificate backup, disabled-listener verification, virtual adapter/bridge cleanup, image/package lifecycle and credential retention/revocation decisions.

## 14. Protocol/server/client differences and uncertainties explicitly listed

`REFERENCE-PASS`

Dossier explicitly separates:

- native SoftEther protocol vs SSTP/L2TP/OpenVPN/EtherIP compatibility modes;
- native client listener/session vs management channel;
- TLS server trust vs user authentication vs Virtual Hub authorization;
- Virtual Hub vs local bridge/SecureNAT/cascade;
- configurable listener defaults vs protocol invariants;
- source portability vs certified client/server OS support;
- native Windows client vs non-native mobile compatibility paths.

## 15. REFERENCE_INDEX links complete dossier

`SOURCE-PASS after synchronization`

Update the index after this file with all mandatory file states and final blockers.

## 16. Latest AGENTS handoff contains exact continuation state

`SOURCE-PASS after checkpoint`

Create the SoftEther entry-013 handoff/checkpoint, update Run State and AGENTS pointer, then immediately continue entry 014 EtherIP.

---

# Formal source/reference result

All 16 research/reference categories have traceable evidence for the native SoftEther VPN Protocol.

Recommended internal state:

`REFERENCE-V2-SOURCE-COMPLETE / NATIVE-SOFTETHER-RUNTIME-EXECUTION-BLOCKED / NOT IMPLEMENTED`

# Strict external blockers

Do not mark strict `COMPLETE-REFERENCE-v2` until representative proof exists for:

1. exact selected current SoftEther release/tag/source/dependency freeze;
2. native Windows client installation, virtual adapter and profile lifecycle;
3. native client -> server TLS/auth/Virtual Hub session;
4. Linux native client path only if retained/supported in selected release;
5. server Windows/Linux install/update/rollback/uninstall;
6. certificate invalid/name/expiry/rotation cases;
7. selected user authentication methods/external AAA;
8. single vs configured parallel TCP connection behavior;
9. Virtual Hub native packet forwarding;
10. SecureNAT and local bridge separately;
11. VPN Bridge/cascade/site-to-site behavior;
12. disabled compatibility-listener proof;
13. management-plane restriction/RBAC;
14. IPv4/IPv6, MTU, NAT/firewall/reconnect;
15. cloud/load-balancer/container topology only if retained;
16. crash/restart/resource/performance/security-advisory tests.

# Promotion decision

Keep entry 013 `PENDING` in the strict v2 tracker.

Checkpoint the source/reference closure and continue entry **014 EtherIP**. Reuse SoftEther server source only where EtherIP is explicitly evidenced, but do not count the native SoftEther protocol work as EtherIP completion.
