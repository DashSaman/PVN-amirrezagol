# 025 — Check Point VPN — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original 20-gate research contract only. This is not implementation or Check Point interoperability certification.

Primary proprietary authority: current official Check Point Remote Access VPN / SSL Network Extender documentation and release notes.

Primary public implementation reference: `ancwrd1/snx-rs` v6.2.4, exact commit `a263c47cecdbbc019bc77c482bb77525a02e20a1`, AGPL-3.0.

Detailed source evidence: `SNX_RS_SOURCE_AUDIT.md`.

## Gate reconciliation

### 1. Top clients / implementations identified — PASS

- official Check Point Remote Access VPN clients and SNX are authoritative vendor references;
- `snx-rs` is the strongest current public source-level interoperability reference found;
- `qsnx` is an older Qt GUI over the proprietary SNX executable, not a separate protocol core.

### 2. Canonical sources pinned — PASS

`snx-rs` release v6.2.4 is pinned to exact commit `a263c47cecdbbc019bc77c482bb77525a02e20a1`; annotated tag/signature evidence and release artifacts are recorded. Official Check Point client code is proprietary, so source pin is N/A; current official release-note/admin documentation is the behavioral authority.

### 3. License / legal reuse reviewed — PASS

`snx-rs` is AGPL-3.0. Direct embedding/reuse in a closed PVNetwork client is not approved without an intentional AGPL-compatible legal/product model. Official Check Point code/branding is proprietary/reference-only. `qsnx` is not selected as a core.

### 4. Complete source-tree reference / manifest — PASS

The recursive Git tree for the exact snx-rs commit was captured through GitHub and returned `truncated=false`, covering apps, crates, docs, tests, packaging and workflows. Proprietary Check Point source is N/A and not fabricated.

### 5. Languages / build systems mapped — PASS

snx-rs is a Rust/Cargo workspace with native/platform dependencies. Source-build requirements, minimum Rust 1.88, optional GTK4/WebKit6, OpenSSL/SQLite/fontconfig, vendored/static modes and platform package tooling are recorded.

### 6. Architecture mapped — PASS

The source separates controller/session/gateway/auth, IPsec and SSL tunnel paths, Linux XFRM vs userspace TUN/ESP, platform networking/routing/DNS/keychain/stats, CLI/service/control, GUI and packaging. Official Check Point gateway/client policy remains a separate proprietary authority.

### 7. Core / engine integration mapped — PASS

Future PVNetwork integration must use a product-owned adapter boundary and cannot simply merge AGPL snxcore into a closed product. Auth/SSO/MFA/cert, tunnel transport, routing/DNS, secret ownership and connection health remain separate capabilities. No home-grown cryptography.

### 8. UI / menu map completed — PASS

Current snx-rs GUI source/i18n maps status, live statistics, Connect/Disconnect, Settings, profile/server/auth/tunnel, General/Advanced tabs, DNS/Routing/Certificates/Misc/UI expanders and tray Connect/Disconnect/Status/Settings/About/Exit. Official Check Point UI remains reference-only; v1 does not copy assets/trade dress.

### 9. Configuration / import / export mapped — PASS

snx-rs configuration options cover server/login/auth/certificate, DNS/routing, tunnel/transport, TLS policy, IKE lifecycle, keychain, MTU/IPv6/forwarding and profiles. PVNetwork canonical profile must remain product-owned and report unsupported/lossy vendor fields rather than adopting snx-rs config as universal product storage.

### 10. Persistence / secure storage mapped — PASS

Multiple profile storage and profile lifecycle are source-mapped. Password storage is optional via OS keychain. MFA code is not written back on save. Certificate/HSM references, persisted IKE state, profile metadata and runtime auth state are distinct. Trace logging can expose sensitive information and requires protected/redacted diagnostics.

### 11. Platform integrations mapped — PASS

Current snx-rs supports Linux, Windows and macOS with platform-specific routing/DNS/network/keychain code and package paths. Linux package repositories/DEB/RPM, Windows MSI/WiX and macOS package/LaunchDaemon behavior are recorded. macOS artifacts are documented as ad-hoc signed/not notarized; no iOS/Android app is inferred.

Official Check Point Windows/macOS release lines and SNX Linux/macOS behavior are separately referenced.

### 12. Logs / diagnostics mapped — PASS

snx-rs exposes structured log levels, troubleshooting guidance, stats and explicit warnings that trace logs include sensitive request/response data. PVNetwork must implement typed states/errors, redaction, protected diagnostic export and route/DNS/data-path health rather than equating process/tunnel state with connectivity.

### 13. Images / assets / visual references mapped — PASS

Exact snx-rs GUI Slint assets, tray/status icons, Windows resources and packaging icons are present in the pinned tree. They are reference/AGPL-governed project assets, not PVNetwork branding. PVNetwork uses its own owner-supplied assets. Official Check Point visual assets are proprietary/do-not-copy.

### 14. Meaningful forks / ecosystem reviewed — PASS

Repository search found the canonical snx-rs repo, ordinary forks and qsnx. No maintained divergent fork was selected over upstream. qsnx is a wrapper for the proprietary SNX client rather than an independent reusable tunnel engine.

### 15. Important issues / PRs / releases / advisories reviewed — PASS

Current v6.2.4 release/changelog and active issues were sampled. High-value regression cases include Office Mode route mismatch (#217), broken-looking-success reconnect with persisted IKE/XFRM (#221), machine-certificate/multifactor behavior (#186) and Linux NetworkManager integration concerns (#58). These are converted into future acceptance tests.

Historical SNX/client security history is treated as a security-review input, not evidence that the current open-source implementation shares every proprietary vulnerability.

### 16. Relevant official docs / community lessons reviewed — PASS

Current Check Point E89.x Windows/macOS Remote Access VPN release notes and current SSL Network Extender administration documentation were reviewed alongside canonical snx-rs docs/issues. Official vendor documentation wins for proprietary support/policy claims; open-source issues provide interoperability/regression lessons.

### 17. Tests / CI reviewed — PASS

Pinned snx-rs CI runs formatting, Clippy and workspace tests on Linux, Windows and macOS, including mobile-access feature coverage. The source has test-server/handshake fixtures and module/unit tests. CI/source tests do not replace real Check Point gateway interoperability.

### 18. Store / privacy / security implications reviewed — PASS

AGPL obligations are explicit. macOS Developer-ID/notarization absence is explicit. No mobile Store claim exists. Sensitive classes include password/MFA/SSO/session cookies, certificate private material/PINs, persisted IKE state and trace logs. Certificate validation, route/DNS/IPv6 cleanup and reconnect/data-path health are security requirements.

### 19. PVNetwork reuse decision documented — PASS

Decision:

`VALUABLE OPEN-SOURCE INTEROPERABILITY REFERENCE / AGPL DIRECT-EMBED CAUTION / OFFICIAL-VENDOR CERTIFICATION REQUIRED`

Prefer standards/native IPsec where the exact gateway policy is demonstrably compatible. Treat snx-rs as source/behavior/test reference unless a deliberate legal architecture accepts AGPL obligations. Keep proprietary/unsupported posture/provisioning combinations official-client-only until proven.

### 20. Uncertainties explicitly listed — PASS

Remaining uncertainties are bounded and moved to later v2/implementation/certification:

- exhaustive current Check Point gateway/version/client matrix;
- exact gateway cryptography/wire/deployment details;
- all SSO/MFA/cert/SCV/posture combinations;
- real interoperability and packet traces;
- official-client complete menus/update lifecycle per platform;
- exact production SBOM/license model;
- PVNetwork mobile/Store/notarization architecture;
- real route/DNS/reconnect/failover regression proof.

These are not missing original research categories.

## Formal v1 result

All 20 original-v1 gates are evidence-backed, evidence-backed proprietary N/A, or explicitly bounded with traceable later-stage uncertainty.

**Entry 025 may be promoted to `COMPLETE-RESEARCH-v1`.**

This is research completion only. It is not PVNetwork implementation, vendor certification, Store readiness or production support.