# 013 — SoftEther VPN Protocol — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research gate only. This document does **not** claim PVNetwork implementation, runtime certification, Store approval, production readiness, or that a specific SoftEther release is safe for deployment.

Primary upstream: `SoftEtherVPN/SoftEtherVPN`.

Reviewed architecture/source pin: `b1f7ef00040786d00bfa06c27fa463d106851e0c`.

Current upstream head observed during this reconciliation: `28564dd1886c5c5b6264ba07557498783311b3ca` (2026-08-14). The current head is recorded for freshness; the older immutable architecture pin remains the source-analysis baseline so existing file/path evidence stays reproducible.

Current Developer Edition GitHub release observed: `5.2.5188` (published 2025-07-18). The release assets identify Windows binaries as `5.02.5187`, so release tag and artifact build identity must remain separate fields.

Official Stable sibling line reviewed: `SoftEtherVPN/SoftEtherVPN_Stable`, latest observed commit `ed17437af9719ac66acab30faa29e375d613c35f` (`v4.44-9807-rtm`, 2025-04-16).

Primary evidence already in this repository:

- `research/upstreams/softether-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/softether-family/SOURCE_CLIENT_ETHERIP_IPSEC_EVIDENCE.md`
- `research/upstreams/softether-family/CLIENT_CONFIG_LICENSE_MODEL.md`
- `research/upstreams/softether-family/CLIENT_SERVER_CONFIG_UI.md`
- `research/upstreams/softether-family/DEPENDENCIES_TESTS_SECURITY.md`
- `research/upstreams/softether-family/PROTOCOL_CAPABILITIES.md`
- `research/upstreams/softether-family/RELEASE_SECURITY_ISSUE_REVIEW.md`
- `research/upstreams/softether-family/SUPPORT_REUSE_DECISIONS.md`
- `research/upstreams/classic-tunnels-family/softether-protocol-reference-v2/` (incidental later-layer evidence reused only where it closes a v1 research question)

Canonical upstream references used in this reconciliation:

- recursive tree at the architecture pin: `https://api.github.com/repos/SoftEtherVPN/SoftEtherVPN/git/trees/b1f7ef00040786d00bfa06c27fa463d106851e0c?recursive=1`
- root license: `https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/LICENSE`
- third-party notices: `https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/src/THIRD_PARTY.TXT`
- submodules: `https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/.gitmodules`
- client source: `https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/src/Cedar/Client.c`
- client header: `https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/src/Cedar/Client.h`
- official manual index: `https://www.softether.org/4-docs/1-manual`
- official Client manual: `https://www.softether.org/4-docs/1-manual/4._SoftEther_VPN_Client_Manual`
- official Client Manager description: `https://www.softether.org/4-docs/1-manual/4._SoftEther_VPN_Client_Manual/4.2_Using_the_VPN_Client`
- official client-management command reference: `https://www.softether.org/4-docs/1-manual/6/6.5`
- Developer release: `https://github.com/SoftEtherVPN/SoftEtherVPN/releases/tag/5.2.5188`
- current high-severity advisory already reviewed: `https://github.com/SoftEtherVPN/SoftEtherVPN/security/advisories/GHSA-q5g3-qhc6-pr3h`

## Gate-by-gate reconciliation

### 1. Top clients / implementations identified and justified — PASS

The canonical upstream itself contains the native VPN Client, VPN Server, VPN Bridge, Client Manager, Server Manager and `vpncmd`; these are the primary implementation/reference set for the native SoftEther protocol. Compatibility modes such as SSTP/L2TP/OpenVPN are explicitly excluded from entry 013 identity. See `SOURCE_ARCHITECTURE.md`, `CLIENT_SERVER_CONFIG_UI.md`, and the v2 client/server implementation maps.

### 2. Canonical sources pinned — PASS

The architecture analysis is reproducibly pinned to `b1f7ef00040786d00bfa06c27fa463d106851e0c`, with a complete recursive-tree URL. Freshness is separately recorded through current upstream head `28564dd...`, release `5.2.5188`, and Stable sibling `ed17437...`. A future implementation source-freeze may select a different pin without invalidating this research gate.

### 3. License / legal reuse reviewed — PASS

The canonical repository root is Apache-2.0 at the reviewed pin. `src/THIRD_PARTY.TXT` and `.gitmodules` are explicitly part of redistribution due diligence. Existing research correctly classifies source reuse as conditional on exact-build dependency/NOTICE generation; that is an implementation/distribution gate, not missing research evidence. Branding/trademark is not assumed reusable.

### 4. Complete source-tree reference / manifest captured — PASS

The recursive tree for `b1f7ef...` is preserved. Major executable, Cedar, Mayaqua, UI/manager, build, dependency and test areas are mapped in the shared source architecture dossier.

### 5. Languages / build systems mapped — PASS

The reviewed codebase is a native C/C++-style systems project centered on CMake plus platform-specific build/packaging resources. Shared Cedar/Mayaqua layers and product executables are separated. Exact selected-build dependency graphs remain a future build/SBOM concern and are explicitly documented.

### 6. Architecture mapped — PASS

Client, server, bridge, command/manager UI, Cedar protocol/session layer, Mayaqua runtime/platform layer, Virtual Hub model, persistent service ownership and management boundaries are documented with source paths.

### 7. Core / engine integration mapped — PASS

Entry 013 is not modeled as a generic executable invocation. The selected direction is a typed PVNetwork SoftEther adapter over the native client management/RPC/service boundary, while PVNetwork owns canonical profile, UI, localization, secure secret references and product state. Native compatibility server modes remain separate capabilities.

### 8. UI / menu map completed at v1 research depth — PASS

The native Client Manager, Server Manager and `vpncmd` surfaces are separated and their user/admin roles are mapped. Incidental v2 work adds detailed client/server menu matrices. Official SoftEther manual chapters independently identify VPN Client Manager, VPN Server Manager, `vpncmd`, Virtual Hub administration, client service, virtual adapter and client management commands. Exact pixel/screenshots for one eventual shipped release are release-certification work, not a hidden v1 gate.

### 9. Configuration / import / export mapped — PASS

`CLIENT_CONFIG_FILE_NAME = "$vpn_client.config"`, periodic saver ownership, account schema, RPC account lifecycle and enterprise configuration-distribution concepts are documented. A generic PVNetwork interchange format is deliberately **not** equated to the native config file. Cross-platform secret-bearing export is treated as unsupported/unverified unless a selected path proves safe; this is an explicit semantics result rather than an omitted research area.

### 10. Persistence / secure storage mapped — PASS

Pinned `Client.c` provides direct evidence for the native account secret representations:

- `CLIENT_AUTHTYPE_PASSWORD` persists `HashedPassword`;
- `CLIENT_AUTHTYPE_PLAIN_PASSWORD` is written through `EncryptPassword(...)` into `EncryptedPassword` and recovered through `DecryptPassword(...)`;
- certificate authentication can serialize `ClientCert` and `ClientKey` into the native config;
- secure-device authentication stores device/object names rather than treating all secrets as one password field;
- the client manager/service password is represented separately from account authentication.

Research consequence: native config storage is **not** adopted as PVNetwork's product secret store. PVNetwork must use platform secure storage/reference semantics and must treat native config/backup export as sensitive. The strength/portability of the upstream `EncryptPassword` mechanism is not promoted as a PVNetwork security primitive and remains an implementation-time security review item.

### 11. Platform integration mapped — PASS

Windows native Client/Manager/service/virtual-adapter architecture is primary. Linux/source-build capability is separated from polished packaged support. Native iOS/Android/TV support is not claimed merely because SoftEther Server exposes compatibility protocols. The v2 client install matrix records these boundaries explicitly. Unknown or unselected mobile paths are documented as uncertainty, not fabricated support.

### 12. Logs / diagnostics mapped — PASS

Shared research distinguishes native session status, Virtual Hub/session state, listener/service health, client/service logs, TLS/auth state, virtual-adapter status, traffic/statistics, reconnect and error classes. The official manual includes logging/management sections and `vpncmd` status/management commands. PVNetwork-specific diagnostics must redact credentials/private keys and separate transport/auth/adapter failures.

### 13. Images / UI assets / visual references mapped — PASS

The pinned source tree contains UI/resource material. For example `src/vpncmgr/` includes `Server.ico`, `Server_Offline.ico`, `VPN.ico`, `resource.h`, `vpncmgr.rc`, and Client Manager source. These are recorded as visual/source references only. No third-party icon/visual asset is approved for PVNetwork copying merely because it is visible in the source tree; root/third-party notices and branding restrictions remain applicable.

### 14. Meaningful fork / sibling ecosystem reviewed — PASS

The official `SoftEtherVPN/SoftEtherVPN_Stable` sibling line is reviewed separately from Developer Edition; latest observed Stable commit is `ed17437...` (`v4.44-9807-rtm`). A top-forks API review also surfaced long-lived forks including `norbusan/SoftEtherVPN`, `dnobori/SoftEtherVPN`, and the Debian/Ubuntu-oriented `dajhorn/SoftEtherVPN`. These are not promoted over canonical upstream because the primary project and official Stable sibling remain the authoritative choices; fork popularity alone is not a security/maintenance approval.

### 15. Issues / PRs / releases / advisories reviewed — PASS

`RELEASE_SECURITY_ISSUE_REVIEW.md` covers current Developer release identity, `CVE-2026-39312` / `GHSA-q5g3-qhc6-pr3h`, `CVE-2025-32787`, representative crash/compatibility/listener issues and release policy. Current upstream head was refreshed during this run. The unresolved high-severity advisory means **do not bless a production release**; it does not mean the security research topic is unresearched.

### 16. Relevant official docs / community lessons reviewed — PASS

The official SoftEther manual was checked for the client, Client Manager, server, Virtual Hub and `vpncmd` management model. GitHub issues/advisories provide the current community/regression evidence used for engineering lessons. No unsupported forum anecdote is promoted to a completion claim.

### 17. Tests / CI reviewed — PASS

The shared dependency/test/security dossier maps upstream test/build automation and distinguishes it from future PVNetwork tests. Current upstream history also shows active CI maintenance; current head followed CI changes on 2026-08-14. Required future PVNetwork tests are explicitly enumerated for profile/auth/cert/service/reconnect/network change/routes/DNS/error redaction/upgrade and related server capabilities. Runtime execution is intentionally a separate evidence state.

### 18. Store / privacy / security implications reviewed — PASS

Desktop native capability is not projected onto Android/iOS Stores. Privileged service/virtual-adapter operations, remote client management, secret export, TLS/certificate policy, advisory state, dependency supply chain, logs/redaction and minimization of unused server compatibility listeners are documented. Store policies must be rechecked at implementation/release time.

### 19. PVNetwork reuse decision documented — PASS

Decision: `UNIQUE NATIVE VPN TARGET / SOFTETHER-UPSTREAM PRIMARY CANDIDATE`, using a product-owned adapter and normalized profile rather than reskinning the full manager. Server administration and compatibility protocols remain distinct modules/capabilities. No implementation claim is made.

### 20. Uncertainties explicitly listed — PASS

Open uncertainties that remain **after research completion**:

- no production-safe Developer Edition release is blessed while the reviewed high-severity advisory remains unresolved;
- final implementation source/tag and exact build graph are not selected;
- per-build third-party SBOM/NOTICE bundle is not generated yet;
- exact native mobile feasibility is not claimed;
- exact selected-release UI screenshot/pixel inventory is not frozen;
- runtime/device/interoperability/Store/performance certification has not been executed;
- upstream `EncryptPassword` strength/OS binding must not be treated as equivalent to a PVNetwork secure-storage design.

These uncertainties are implementation/release decisions or explicitly bounded unknowns. They do not leave an applicable section of the original v1 research template unexamined.

## Formal v1 result

All 20 applicable original-v1 research gates are now evidence-backed or explicitly bounded with evidence-backed uncertainty.

**Entry 013 may be promoted to `COMPLETE-RESEARCH-v1`.**

This promotion means research completion only. It does not convert any implementation/build/device/Store/production state to PASS.
