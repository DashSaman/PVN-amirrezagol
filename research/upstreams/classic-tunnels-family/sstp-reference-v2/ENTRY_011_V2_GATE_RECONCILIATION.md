# Entry 011 — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14

Entry: **011 — SSTP / MS-SSTP**

Purpose: reconcile the SSTP dossier against every research/reference category in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`, while separating Microsoft-native protocol authority from open-source interoperability projects and preserving one explicit Linux-client source-freeze residual.

## Status vocabulary

- `REFERENCE-PASS`: category has traceable source/reference evidence.
- `REFERENCE-PASS-WITH-PIN-RESIDUAL`: architecture/license/project role is evidenced, but the selected immutable source freeze still needs an exact upstream release/commit materialized before implementation/certification.
- `BLOCKED_EXTERNAL`: strict proof requires live Windows/Linux/server/proxy/interoperability/device/runtime evidence.

## 1. Server implementation/project ecosystem mapped

`REFERENCE-PASS`

Evidence:

- `SERVER_IMPLEMENTATIONS.md`
- Microsoft Windows Server RRAS as the authoritative native/proprietary server path;
- SoftEther VPN Server as the major reviewed open-source multiprotocol SSTP interoperability/server reference;
- small/community Unix server category kept unapproved until exact source/TLS/PPP/security evidence exists.

## 2. Official and major server installer/deployment projects reviewed

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`

Covers Windows Remote Access/RRAS role installation, certificate/NPS/RADIUS/addressing ownership, SoftEther package/source deployment, reverse-proxy/load-balancer cautions, cloud images, containers/Kubernetes, certificate automation and anti-blind-script rules.

## 3. Server OS/container/orchestration install matrix completed

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALL_MATRIX.md`

Covers Windows Server 2025, selected older Windows Server releases, SoftEther on Windows/Linux and product-specific platforms, cloud VMs, OCI/Kubernetes and TLS pass-through/offload distinctions.

## 4. Server panel/UI/control-plane menus completed

`REFERENCE-PASS`

Evidence:

- `SERVER_UI_AND_MENUS.md`

Maps Windows Server Manager/RRAS/Ports/certificate/NPS/RADIUS/addressing/firewall/status domains and SoftEther listener/Virtual Hub/users/network/logging domains, plus a normalized PVNetwork server UI/RBAC/secret model.

## 5. Client install matrix completed across relevant OS targets

`REFERENCE-PASS-WITH-PIN-RESIDUAL`

Evidence:

- `CLIENT_INSTALL_MATRIX.md`

Covers Windows native SSTP as the primary client, Linux `sstp-client` + pppd as the serious open-source client path, optional NetworkManager frontend, and explicit no-native claims for macOS/iOS/Android until a separate third-party engine is selected.

Residual:

- canonical `sstp-client/sstp-client` project is identified, but the current repository/connector evidence has not materialized a trustworthy immutable selected release/commit SHA into PVNetwork evidence. Do not invent one. Final source freeze must add it before implementation/certification.

## 6. Major client UI/menu maps completed separately

`REFERENCE-PASS`

Evidence:

- `CLIENT_UI_AND_MENUS.md`

Maps Windows native profile/system-owned state, Linux typed frontend concepts, NetworkManager plugin as a separate component, non-native mobile/macOS policy, layered connection states, certificate/proxy/diagnostic UX and Persian/RTL handling.

## 7. Cryptographic design/security boundary documented

`REFERENCE-PASS`

Evidence:

- `CRYPTOGRAPHY.md`

Separates TLS server authentication/confidentiality, SSTP protocol crypto/channel binding, PPP/EAP user authentication, proxy credentials and server/private-key/RADIUS secret storage. Generic TLS termination/offload is not assumed safe.

## 8. Data path/wire flow documented

`REFERENCE-PASS`

Evidence:

- `DATA_PATH_AND_WIRE_FLOW.md`

Covers Windows native route -> PPP -> SSTP -> TLS -> TCP443 path, Linux sstp-client/pppd composition, SoftEther server path, proxy handling, TCP-over-TCP behavior, reconnect, addressing/routes/DNS, MTU and cleanup.

## 9. Ports/transports/handshake documented

`REFERENCE-PASS`

Evidence:

- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`

Documents TCP443/TLS, SSTP duplex HTTP/tunnel boundary, call control, PPP LCP/auth/NCP, crypto-binding stage, proxy traversal, teardown, TCP implications and failure ownership.

## 10. Deployment topologies documented

`REFERENCE-PASS`

Evidence:

- `DEPLOYMENT_TOPOLOGIES.md`

Covers Windows-native -> RRAS, Windows -> SoftEther, Linux -> RRAS/SoftEther, restrictive firewall, explicit HTTP proxy, L4 pass-through, unsafe/unproven generic TLS termination, HA, cloud, container, IPv6, lossy TCP-over-TCP and migration.

## 11. Source/license/activity pins recorded for server and client projects

`REFERENCE-PASS-WITH-PIN-RESIDUAL`

Evidence:

- Microsoft protocol/server/client is referenced through current Microsoft Open Specifications/Learn rather than source reuse;
- SoftEther source pin reused from existing PVNetwork research: `49eb2f08641709d1af57a0d04971973ff94461db`;
- canonical Linux client project identified: `sstp-client/sstp-client`.

Residual:

- exact immutable selected `sstp-client` release/commit + root/component license must be materialized into the repo before source freeze. The absence of that immutable pin blocks a claim of fully frozen open-source client source, but it does not invalidate the rest of the SSTP reference architecture.

## 12. Security/supply-chain risks of installer projects recorded

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `CRYPTOGRAPHY.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`

Includes certificate/private-key ownership, TLS policy, PPP/RADIUS secrets, proxy interception, multiprotocol SoftEther attack surface, unreviewed scripts/images, TLS-offload risks, container privilege/persistence and client temp/process secret risks.

## 13. Upgrade/uninstall/rollback behavior researched

`REFERENCE-PASS at reference layer / BLOCKED_EXTERNAL for receipts`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `CLIENT_INSTALL_MATRIX.md`

Defines Windows/SoftEther update/rollback, certificate rotation/rebinding, listener/session cleanup, native profile removal, secret/certificate retention and container/image lifecycle.

## 14. Protocol/server/client differences and uncertainties explicitly listed

`REFERENCE-PASS`

Evidence throughout dossier:

- TLS != SSTP control != PPP/EAP auth;
- TCP443 reachability != SSTP compatibility;
- RRAS != SoftEther;
- Windows native != Linux open-source interoperability;
- proxy CONNECT != TLS interception;
- TLS pass-through != TLS termination;
- consumer mobile/macOS native SSTP support is not assumed;
- exact Linux client pin remains explicit.

## 15. REFERENCE_INDEX links the complete dossier

`SOURCE-PASS after synchronization`

Update `REFERENCE_INDEX.md` after this reconciliation with all mandatory files, this gate file, the immutable Linux-client pin residual and strict runtime blockers.

## 16. Latest AGENTS handoff contains exact continuation state

`SOURCE-PASS after checkpoint`

Create a new SSTP v2 handoff/checkpoint, update Run State and AGENTS pointer, then continue the next independent v2 entry.

---

# Formal source/reference result

All 16 research/reference categories have traceable evidence for entry 011, with one explicit **source-freeze residual** for the immutable `sstp-client` release/commit/license pin.

Recommended internal state:

`REFERENCE-V2-EVIDENCE-COMPLETE / EXACT-LINUX-CLIENT-PIN-RESIDUAL / WINDOWS+INTEROP-EXECUTION-BLOCKED / NOT IMPLEMENTED`

This state is intentionally one step more conservative than `REFERENCE-V2-SOURCE-COMPLETE` because the open-source Linux client source freeze is not yet immutable in PVNetwork evidence.

# Strict external/runtime blockers

Do not mark strict `COMPLETE-REFERENCE-v2` until representative proof exists for:

1. Windows Server 2025 RRAS install/config/certificate/listener lifecycle;
2. Windows 11 native client -> RRAS;
3. Windows native client -> selected SoftEther release;
4. exact immutable `sstp-client` release/commit/license materialization;
5. Linux sstp-client -> RRAS;
6. Linux sstp-client -> SoftEther;
7. invalid/untrusted/name-mismatch/expired/revoked certificate cases;
8. certificate rotation/rebinding;
9. SSTP crypto-binding success and negative/tamper case;
10. PPP/EAP/MS-CHAPv2/selected authentication profiles;
11. HTTP proxy CONNECT/auth and TLS-interception negative case;
12. L4 TCP load balancer idle-timeout/failover;
13. IPv6;
14. TCP-over-TCP loss/latency performance;
15. MTU/MSS;
16. split/full routes and DNS;
17. update/rollback/uninstall/native-profile cleanup;
18. any macOS/iOS/Android third-party engine, because none is selected/certified yet.

# Promotion decision

Keep entry 011 `PENDING` in the strict v2 tracker.

Checkpoint the evidence-complete state with the immutable Linux-client pin residual, then continue entry 012 PPTP from actual repository state. If a later agent can materialize the exact maintained `sstp-client` release/commit/license, update this dossier and remove only that residual; do not redo the already completed SSTP reference categories.
