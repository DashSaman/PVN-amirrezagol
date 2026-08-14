# 011 — SSTP / MS-SSTP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research gate only. This document does not claim PVNetwork implementation, runtime interoperability, Store readiness or production certification.

## Canonical/reference set

Protocol authority:

- Microsoft Open Specifications `[MS-SSTP]`: `https://learn.microsoft.com/en-us/openspecs/windows_protocols/ms-sstp/c50ed240-56f3-4309-8e0c-1644898f0ea8`
- current published protocol revision observed: 21.0 (2024-04-23).

Current Windows server/platform authority:

- Microsoft Remote Access/RRAS documentation: `https://learn.microsoft.com/en-us/windows-server/remote/remote-access/get-started-install-ras-as-vpn`
- current guidance includes Windows Server 2025/2022/2019/2016 and retains SSTP while new Server 2025 configurations stop accepting PPTP/L2TP by default.

Open-source/Linux client:

- corrected canonical project: `https://gitlab.com/sstp-project/sstp-client`
- research tag pin: `1.0.20`
- canonical GitLab tag short commit: `dd243124`
- correction evidence: `research/upstreams/classic-tunnels-family/SSTP_CLIENT_SOURCE_PIN_2026-08-14.md`
- license family recorded by canonical packaging: GPLv2+.

Open-source server/interoperability reference:

- SoftEther source pin reused from existing research: `SoftEtherVPN/SoftEtherVPN@49eb2f08641709d1af57a0d04971973ff94461db` in the SSTP v2 dossier.

Deep existing evidence:

- `research/upstreams/classic-tunnels-family/SSTP_CLIENT.md`
- `research/upstreams/classic-tunnels-family/DEPENDENCIES_SECURITY_TESTS.md`
- `research/upstreams/classic-tunnels-family/SUPPORT_REUSE_DECISIONS.md`
- `research/upstreams/classic-tunnels-family/sstp-reference-v2/`
- especially `ENTRY_011_V2_GATE_RECONCILIATION.md`, which already maps server/client install/UI, cryptography, data path, ports/handshake and deployment topology.

## Gate-by-gate reconciliation

### 1. Top clients / implementations identified and justified — PASS

Primary directions are Windows built-in SSTP/RAS client/server, Linux `sstp-project/sstp-client` with PPP/NetworkManager integration, and SoftEther as a major open-source server/interoperability reference. Mobile/macOS consumer support is not claimed without a selected maintained engine.

### 2. Canonical sources pinned — PASS

Microsoft protocol/platform behavior is tied to current official Open Specifications/Learn documentation. Linux client research is now pinned to canonical GitLab tag `1.0.20`; the former stale GitHub-style repository label is explicitly corrected. SoftEther has an immutable reviewed source pin. Full archive digest for future packaging remains a build/source-freeze task.

### 3. License / legal reuse reviewed — PASS

Linux sstp-client is GPLv2+ / GPLv2-family, so closed-product embedding is not assumed permissible; distribution/process/package architecture requires deliberate legal design. Microsoft native APIs are platform functionality, not source reuse. SoftEther license/dependency obligations are separately recorded. No cross-component license is inferred.

### 4. Complete source-tree reference / manifest captured — PASS

The serious open-source components are source-located and pinned/tagged; SoftEther has a pinned source tree, and the Linux client project/tag is canonicalized. Microsoft is proprietary/native and is referenced through protocol/platform documentation rather than pretending source is available.

### 5. Languages / build systems mapped — PASS

Linux sstp-client is a native Unix client integrating TLS/libevent/PPP/pppd and autotools-style packaging/build metadata. SoftEther is a C/CMake systems project. Windows path is OS-native RAS/VPN functionality. Exact package dependency versions remain package/build-specific and are documented as later source-freeze concerns.

### 6. Architecture mapped — PASS

Layering is explicit:

`application/profile`
→ `SSTP client / Windows RAS or sstp-client`
→ `TLS/HTTPS SSTP transport`
→ `PPP negotiation/authentication`
→ `address/DNS/routes/interface`

Server path separates TLS/SSTP termination, PPP/auth policy, address/routing and server management. Windows RRAS, SoftEther and Linux sstp-client roles are not conflated.

### 7. Core / engine integration mapped — PASS

Windows: native OS first. Linux: `sstp-client`/NetworkManager or separately packaged adapter candidate. Other platforms remain unproven. PVNetwork must own canonical profile, typed status/error model and secure secret references rather than reimplement SSTP/TLS/PPP cryptography/protocol state.

### 8. UI / menu map completed — PASS

Existing v2 evidence supplies a detailed Windows-native profile/RRAS map and Linux typed frontend/NetworkManager concepts. V1 requires research mapping, not pixel-perfect frozen-release screenshots. Layered states/errors include TCP/reachability, TLS/certificate, SSTP negotiation, PPP/authentication, addressing/DNS/routes and cleanup. Unsupported mobile platforms are explicit rather than invented.

### 9. Configuration / import / export mapped — PASS

Windows native profile ownership and Linux client/PPP configuration are separately mapped. Endpoint, hostname/certificate trust, PPP identity/auth, proxy options where backend-supported and route/DNS results are not collapsed into one generic config blob. No universal SSTP QR/subscription schema is invented; import/export remains backend-specific and secret-bearing exports require explicit handling.

### 10. Persistence / secure storage mapped — PASS

Reusable credentials/certificates belong in OS/platform secure stores or protected product vault references; PPP passwords/private keys are not ordinary JSON/log material. Windows credential/certificate stores and Linux keyring/Secret Service/product-vault direction are documented. Client temp/process exposure is an explicit Linux security review point.

### 11. Platform integration mapped — PASS

Windows native SSTP is primary. Linux has the tagged sstp-client path plus NetworkManager integration direction. Android/iOS/iPadOS/TV/macOS are not marked supported merely because SSTP exists or a Unix client source exists; a maintained platform engine and Store-compatible architecture would need separate implementation evidence.

### 12. Logs / diagnostics mapped — PASS

Research requires distinct TLS/certificate, SSTP control, PPP negotiation/auth, proxy, IP/DNS/routes, reconnect and cleanup observability. Sensitive credentials/private keys are redacted. Windows native and Linux process/service diagnostics are platform-owned and must be normalized rather than replaced by one generic error.

### 13. Images / UI assets / visual references mapped — PASS

Microsoft official platform/management documentation and the selected open-source project/NetworkManager surfaces are the reference set. Third-party screenshots/icons are references only; no asset copying is approved by this research state. No protocol-specific branded asset is required for completion.

### 14. Meaningful forks / ecosystem reviewed — PASS

The serious ecosystem is represented by three distinct roles: Microsoft native client/server authority, canonical GitLab sstp-client Linux client, and SoftEther open-source server/interoperability. NetworkManager SSTP is treated as a separate Linux frontend/integration component rather than misclassified as the SSTP engine. This provides meaningful architectural alternatives without promoting abandoned forks.

### 15. Issues / PRs / releases / advisories reviewed — PASS

Existing classic-tunnels/SSTP research records current project/release, certificate/proxy/PPP/regression risks and SoftEther shared security/release issues. The canonical Linux tag was refreshed to 1.0.20. Microsoft current Windows Server guidance was refreshed. These lessons feed future regression tests; runtime proof is not fabricated.

### 16. Relevant official docs / community lessons reviewed — PASS

Microsoft Open Specifications and Windows Server Learn are protocol/platform authorities. Canonical GitLab project/tag/wiki/packaging evidence covers Linux client behavior and dependencies. SoftEther source/release research covers the alternative server. No unsupported community claim is needed to close this gate.

### 17. Tests / CI reviewed — PASS

Existing research enumerates required SSTP regressions: server-name/certificate validation, proxy/no-proxy, PPP auth success/failure, reconnect/network loss/sleep, DNS/address/routes, cleanup, native Windows profile lifecycle, Linux service/NetworkManager lifecycle and secret redaction. Upstream Linux package history also records prior authentication/proxy/PPP compatibility regressions. These are test requirements, not claimed runtime PASS.

### 18. Store / privacy / security implications reviewed — PASS

SSTP security depends on TLS server validation plus SSTP/PPP security boundaries; unsafe certificate bypass is not a default. GPL Linux-client distribution implications are explicit. Windows native path avoids bundling an extra client. Mobile/Apple/TV Store support remains unclaimed. Proxy credentials, PPP credentials, server private keys and certificate trust remain separate secret/security domains.

### 19. PVNetwork reuse decision documented — PASS

Decision remains:

`COMPATIBILITY REMOTE-ACCESS TARGET / WINDOWS-NATIVE-FIRST / LINUX SSTP-CLIENT CANDIDATE`

Windows uses OS-native SSTP where suitable. Linux evaluates tagged sstp-client/NetworkManager behind a product adapter and compatible legal/distribution model. SoftEther is server/interoperability reference. Other platforms defer until a sound engine exists.

### 20. Uncertainties explicitly listed — PASS

Remaining bounded uncertainties after research completion:

- full long Git object SHA/archive digest for sstp-client tag 1.0.20 belongs in the future build lockfile;
- exact selected Linux distro/package/dependency versions are not frozen;
- live Windows/Linux/RRAS/SoftEther interoperability is not certified;
- proxy, crypto-binding, PPP/EAP method combinations and certificate rotation require runtime acceptance testing;
- exact mobile/macOS third-party engine remains unselected and support is not claimed;
- performance/MTU/TCP-over-TCP behavior is topology-dependent;
- Store/release policies must be rechecked at implementation time.

These are implementation/source-freeze/certification choices or explicit unknowns, not unexamined original-v1 research categories.

## Formal v1 result

All 20 applicable original-v1 research gates are evidence-backed or explicitly bounded with traceable uncertainty.

**Entry 011 may be promoted to `COMPLETE-RESEARCH-v1`.**

This means research completion only, not implementation or production support.
