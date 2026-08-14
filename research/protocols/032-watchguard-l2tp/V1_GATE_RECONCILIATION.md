# 032 — WatchGuard Mobile VPN with L2TP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original 20-gate research contract only. This is not implementation or WatchGuard certification.

Primary entry audit: `WATCHGUARD_L2TP_CURRENT_AUDIT.md`.

Shared reusable standards evidence: completed generic L2TP/IPsec entry 008 and related strongSwan/IPsec dossiers.

## 1. Top clients / implementations — PASS

Current meaningful client/server set:

1. WatchGuard Firebox Mobile VPN with L2TP — authoritative server/policy implementation.
2. Windows built-in L2TP/IPsec client — documented WatchGuard client path.
3. macOS built-in L2TP over IPsec — documented WatchGuard client path.
4. iOS native L2TP — documented WatchGuard client path.
5. Android native L2TP — historical/current-limit reference only; WatchGuard states native Android L2TP is unavailable on Android 12+.
6. Existing generic L2TP/IPsec implementations from entry 008 — standards/source references where exact semantics match.

No WatchGuard-specific endpoint app is required for the normal standards path.

## 2. Canonical sources pinned — PASS (`WATCHGUARD-PROPRIETARY-N/A`)

WatchGuard Fireware is proprietary; public source SHA is N/A. Current WatchGuard Help Center documentation is the server/client behavior authority.

Public source references for reusable L2TP/IPsec components are already pinned in entry 008/shared dossiers and are not duplicated as a fictitious WatchGuard source tree.

## 3. License / legal reuse — PASS

WatchGuard Fireware code/assets are proprietary/reference-only.

Reusable L2TP/IPsec components inherit their already-reviewed licenses from entry 008/strongSwan-family evidence. Native OS clients are platform capabilities under platform terms.

No proprietary WatchGuard code or branding is copied.

## 4. Complete source-tree reference / manifest — PASS (`PUBLIC-COMPONENTS`; `WATCHGUARD-N/A`)

The complete reusable standards-component source evidence belongs to entry 008/shared upstream dossiers. WatchGuard private server source is evidence-backed N/A and is not fabricated.

Native OS client behavior is documented at the platform/vendor interface level rather than pretending a WatchGuard client source tree exists.

## 5. Languages / build systems — PASS

Public L2TP/IPsec backend/build evidence is inherited from the completed generic dossier.

WatchGuard private implementation languages/build systems are unavailable. Normal user paths are native OS VPN configuration rather than a WatchGuard app build/install.

## 6. Architecture — PASS

Secure default layering is mapped:

`platform/native L2TP client`

`-> IKE/IPsec tunnel auth (PSK or certificate)`

`-> IPsec protection / NAT-T as required`

`-> L2TPv2`

`-> PPP / MS-CHAPv2 user auth`

`-> virtual IP`

`-> routes/DNS`

`-> Firebox L2TP-Users/access policy`

`-> application traffic`.

Unprotected L2TP is explicitly a separate insecure/non-recommended mode.

## 7. Core / engine integration — PASS

PVNetwork reuses the existing layered L2TP/IPsec architecture from entry 008 and native OS backends where current support exists.

WatchGuard policy/auth/certificate/address-pool behavior remains a server capability/certification layer. No new IKE/IPsec/L2TP/PPP cryptography or framing stack is built.

## 8. UI / menu map — PASS

Server UI is mapped under `VPN > Mobile VPN > L2TP`, including Setup Wizard and manual Networking/Authentication/IPsec settings.

Client UI is intentionally native/platform-specific:

- Windows VPN settings;
- macOS Network/VPN settings;
- iOS VPN settings;
- Android native path marked unavailable on Android 12+.

No fabricated WatchGuard consumer UI is introduced.

## 9. Configuration / import / export — PASS

Entry 032 is primarily manual/native profile configuration rather than a WatchGuard proprietary client profile format.

Canonical fields include server identity, L2TP/IPsec type, PSK/certificate reference, user/account/domain, virtual-address/network policy and platform route behavior.

PVNetwork keeps product-owned canonical profile state and generates platform runtime configuration. Any managed profile/GPO/MDM deployment remains a later platform capability.

## 10. Persistence / secure storage — PASS

Distinct classes:

- non-secret server/profile metadata;
- IPsec PSK;
- certificate/private-key reference;
- PPP username/password;
- MFA state;
- transient IKE/IPsec keys;
- transient L2TP/PPP session state;
- virtual IP/routes/DNS;
- diagnostics.

Platform secure stores/certificate stores must own reusable secrets. Tunnel-auth credentials are not collapsed into user credentials.

## 11. Platform integrations — PASS

Current WatchGuard guidance documents native Windows, macOS and iOS paths.

Android has an explicit current negative capability: built-in L2TP removed/unsupported on Android 12+.

Exact OS compatibility remains tied to current Fireware release notes. No third-party modern Android client is selected without its own source/license/security audit.

## 12. Logs / diagnostics — PASS

Failure ownership is explicitly separated:

- OS/client compatibility;
- IKE/IPsec proposal/PSK/certificate;
- NAT-T/port reachability;
- L2TP control/session;
- PPP/MS-CHAPv2/RADIUS/MFA;
- virtual IP pool;
- routes/DNS;
- L2TP-Users/access policy;
- application traffic.

WatchGuard documentation explicitly warns that client `Connected` can coexist with resource-access/policy failure. PVNetwork must expose actual data-path health.

## 13. Images / assets / visual references — PASS

WatchGuard current admin/client help includes screenshots of Fireware wizard/config and native client setup.

WatchGuard branding/assets are proprietary/do-not-copy. Native OS visuals are platform behavior, not PVNetwork assets. PVNetwork uses independent owner-supplied branding.

## 14. Meaningful forks / ecosystem — PASS

The meaningful public ecosystem is generic standards L2TP/IPsec, already reviewed in entry 008.

No separate WatchGuard-specific open-source protocol fork is needed because WatchGuard advertises RFC 2661-compatible L2TPv2/native-client behavior. Third-party Android replacement remains unselected because modern Android native support is absent.

## 15. Important issues / releases / compatibility lessons — PASS

Current high-value lessons converted to regression tests:

- IPsec can be disabled but becomes insecure/non-recommended;
- Android 12+ removes the native L2TP path;
- tunnel PSK/certificate and PPP user auth are distinct;
- MS-CHAPv2-compatible MFA has exact AuthPoint constraints;
- virtual IP pool exhaustion can block usable sessions;
- authorization/group mismatch can yield misleading connection state;
- split tunnel is manual/unsupported by WatchGuard for L2TP;
- transform/client mismatch can prevent IPsec;
- NAT-T/UDP 500/4500/ESP/1701 are distinct network failure classes.

Exact Fireware/OS release-advisory recheck remains mandatory at implementation freeze.

## 16. Relevant official docs / community lessons — PASS

Primary evidence uses current WatchGuard Help Center pages for L2TP overview, wizard/edit, policies, authentication/MFA, tunnel certificates, Windows/macOS/iOS/Android clients, Internet routing and troubleshooting.

Standards/source claims reuse already-reviewed RFC/component evidence from entry 008 rather than community guesses.

## 17. Tests / CI — PASS

WatchGuard internal CI/source tests are proprietary/N-A.

Reusable component tests/CI belong to the selected entry-008/public backend stack. Future PVNetwork test layers are defined:

- canonical profile/security validation;
- native/backend adapter tests;
- IKE/IPsec PSK/certificate;
- L2TP/PPP/MS-CHAPv2;
- RADIUS/AuthPoint;
- exact Firebox lab;
- address pool/routes/DNS/access policy;
- NAT/multiple clients;
- profile/cert rotation;
- OS network-change/reconnect;
- security/leak/performance tests.

## 18. Store / privacy / security implications — PASS

Research explicitly covers:

- IPsec required as secure default;
- no silent unprotected-L2TP fallback;
- PSK/cert/private-key/user-password/MFA separation;
- certificate trust/SAN/algorithm/expiry;
- MS-CHAPv2 compatibility boundary;
- transform security floor;
- route/DNS/full-tunnel/access-policy state;
- native platform secret stores;
- modern Android native unavailability;
- exact selected public backend license/SBOM obligations.

## 19. PVNetwork reuse decision — PASS

Decision:

`STANDARD L2TP/IPSEC INTEROPERABILITY TARGET / NATIVE-OS-FIRST WHERE CURRENT SUPPORT EXISTS / REUSE ENTRY-008 LAYERED STACK / WATCHGUARD AUTH+POLICY CERTIFICATION REQUIRED / MODERN-ANDROID-NATIVE-UNAVAILABLE`

Do not create a WatchGuard-specific cryptographic engine. Use typed canonical L2TP/IPsec profile data and native/approved existing backends.

## 20. Uncertainties explicitly listed — PASS

Bounded later-stage uncertainties:

- exact current Fireware/model/OS matrix;
- exact Phase 1/2 transform/security floor;
- exact PPP/MS-CHAPv2/RADIUS/AuthPoint combinations;
- native OS API/profile deployment details;
- certificate/PSK lifecycle;
- third-party Android client decision if required;
- full server/admin topology/menu dossier;
- virtual pool/NAT/routes/DNS/full-tunnel/access-policy matrix;
- packet/interoperability/reconnect tests;
- post-review platform deprecations/advisories.

These are v2/implementation/certification concerns, not missing original-v1 research categories.

# Formal result

All 20 original-v1 gates are evidence-backed, evidence-backed proprietary N/A, or explicitly bounded with traceable later-stage uncertainty.

**Entry 032 may be promoted to `COMPLETE-RESEARCH-v1`.**

Research completion only; no implementation/vendor/production claim.