# 030 — WatchGuard Mobile VPN with IKEv2 — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original 20-gate research contract only. This is not implementation, WatchGuard certification, or production support.

Primary entry audit: `WATCHGUARD_IKEV2_CURRENT_AUDIT.md`.

Shared reusable standards evidence: completed IKEv2/IPsec entry 004 and `research/upstreams/strongswan-family/`.

## 1. Top clients / implementations — PASS

Current official WatchGuard guidance identifies the meaningful client set:

1. native Windows IKEv2 client;
2. native macOS/iOS IKEv2 client;
3. Android strongSwan client;
4. optional WatchGuard IPSec Mobile VPN Client for Windows with IKEv2 profile import on Fireware 12.11.1+;
5. Firebox Mobile VPN with IKEv2 as authoritative gateway/server behavior.

This supports a native-OS-first architecture rather than a new WatchGuard-specific VPN engine.

## 2. Canonical sources pinned — PASS (`WATCHGUARD-PROPRIETARY-N/A`)

WatchGuard Fireware/client source is proprietary and source SHA is not applicable.

Public standards candidate remains pinned strongSwan 6.0.7 at exact commit `5973ff8e41deef4e015e1138a2de688acedf6f75`.

Native OS clients are platform capabilities rather than third-party source reuse. Exact OS/Fireware compatibility remains versioned certification evidence.

## 3. License / legal reuse — PASS

WatchGuard code/UI/assets are proprietary/reference-only. Native OS IKEv2 paths use platform-provided APIs/clients under platform terms. strongSwan licensing/distribution obligations are already mapped in the shared dossier.

No WatchGuard proprietary protocol clone or branding reuse is planned.

## 4. Complete source-tree reference / manifest — PASS (`PUBLIC-ENGINE`; `VENDOR-N/A`)

strongSwan's exact public source evidence is already captured. WatchGuard private Fireware/client source is evidence-backed N/A and is not fabricated.

For native clients, public platform behavior/documentation is the relevant integration evidence, not a cloned client source tree.

## 5. Languages / build systems — PASS

strongSwan native source/build architecture is mapped. WatchGuard private implementation build details are N/A.

The primary Windows/macOS/iOS paths require no WatchGuard tunnel application build at all; deployment is scripts/profiles/certificates into native OS IKEv2. Android strongSwan and optional WatchGuard Windows client remain separate packaging paths.

## 6. Architecture — PASS

The connection architecture is mapped:

`WatchGuard-generated client profile / manual settings`

`-> native OS IKEv2 or strongSwan/optional WatchGuard client`

`-> server certificate identity verification`

`-> IKEv2 negotiation`

`-> EAP/MS-CHAPv2 user authentication`

`-> CHILD_SA / ESP/NAT-T`

`-> virtual IP + routes/DNS`

`-> Firebox user/group policy`

`-> application traffic`.

Full/split tunnel, certificate support and MFA remain typed capabilities.

## 7. Core / engine integration — PASS

Preferred PVNetwork architecture:

- native Windows/macOS/iOS IKEv2 adapter first;
- strongSwan adapter for Android/advanced portability where required;
- product-owned canonical profile/capability model above either backend.

No home-grown IKEv2/IPsec cryptography. WatchGuard-generated profiles are normalized/validated before runtime configuration.

## 8. UI / menu map — PASS

Server UI is mapped under `VPN > Mobile VPN > IKEv2`, Setup Wizard, Configure, and Client Profile.

Client UI is intentionally platform-specific:

- Windows native VPN settings;
- macOS/iOS native VPN/profile UI;
- Android strongSwan profile UI;
- optional WatchGuard Windows Mobile VPN Monitor -> Configuration -> Profiles -> Add/Import.

No invented universal WatchGuard consumer UI is required.

## 9. Configuration / import / export — PASS

Firebox generates a `.TGZ` package with platform-specific instructions/scripts/certificates/profiles.

Current platform artifacts include:

- Windows automatic configuration script/manual settings;
- macOS/iOS `.mobileconfig`;
- Android strongSwan instructions/profile material;
- WatchGuard Windows client `.INI` + `.PEM` profile path in Fireware 12.11.1+.

These remain import/deployment sources, not PVNetwork canonical storage.

## 10. Persistence / secure storage — PASS

State is separated into:

- canonical profile metadata;
- original generated/imported source;
- server certificate/trust data;
- reusable user credentials;
- platform certificate/private-key references;
- transient EAP/IKE/IPsec session state;
- assigned IP/routes/DNS;
- diagnostic state.

PVNetwork uses platform secure storage and certificate stores; secrets are not embedded in ordinary profile metadata.

## 11. Platform integrations — PASS

Current official guidance covers:

- Windows native IKEv2;
- macOS native IKEv2;
- iOS native IKEv2;
- Android strongSwan;
- optional WatchGuard IPSec Mobile VPN Client Windows v15.19+ with supported Fireware.

Exact current OS compatibility is tied to Fireware release notes and remains a versioned support matrix.

## 12. Logs / diagnostics — PASS

The research defines separate diagnostic stages for:

- profile/import;
- certificate trust/SAN/EKU/expiry;
- IKE proposal;
- EAP/MS-CHAPv2/user auth;
- AuthPoint/RADIUS;
- CHILD_SA/ESP/NAT-T;
- virtual IP;
- routes/DNS;
- Firebox policy;
- data-path/reconnect.

Native client strategy requires platform-specific logging/diagnostics rather than one vendor log format.

## 13. Images / assets / visual references — PASS

WatchGuard documentation contains setup wizard/client-profile/native-client/optional-client screenshots used as navigation reference only.

WatchGuard branding/assets are proprietary/do-not-copy. Native OS visuals are platform behavior, not PVNetwork assets. PVNetwork retains independent owner-supplied branding.

## 14. Meaningful forks / ecosystem — PASS

The meaningful ecosystem is standards IKEv2 rather than vendor forks:

- native OS IKEv2 clients;
- strongSwan as maintained public engine/client;
- optional WatchGuard proprietary client.

No vendor-specific open-source fork is necessary or selected. Generic public IKEv2 implementations are not blanket WatchGuard certification.

## 15. Important issues / releases / compatibility lessons — PASS

Current official compatibility lessons converted to tests include:

- Android strongSwan full-tunnel can interfere with AuthPoint push notification reachability;
- EC certificate support differs by OS;
- WatchGuard Windows client IKEv2 import does not support AES-GCM-192;
- certificate SAN/EKU/expiry are strict requirements;
- Fireware/OS compatibility is version-specific;
- profile/certificate rotation must be tested.

Shared strongSwan release/security evidence provides the public engine advisory baseline.

## 16. Relevant official docs / community lessons — PASS

Primary vendor claims are grounded in WatchGuard Help Center documentation for IKEv2 setup/edit, user auth, certificates, client profile generation and each platform.

Public engine behavior is grounded in strongSwan evidence. No community anecdote overrides vendor compatibility claims.

## 17. Tests / CI — PASS

WatchGuard internal CI/source tests are proprietary/N-A. strongSwan public source/security/test evidence is already captured.

Future PVNetwork tests are explicit:

- profile normalization;
- native/strongSwan adapter capability tests;
- cert/EAP/MS-CHAPv2;
- exact Firebox lab;
- AuthPoint/RADIUS;
- proposals/ESP/NAT-T;
- routes/DNS/full/split;
- profile/cert rotation;
- network change/reconnect;
- optional client import;
- security/leak/performance.

## 18. Store / privacy / security implications — PASS

Research covers:

- platform-native deployment versus app installation;
- certificate trust/SAN/EKU/expiry;
- credential/certificate/private-key separation;
- EAP/MS-CHAPv2 compatibility and MFA limitations;
- AuthPoint push reachability under full tunnel;
- route/DNS policy;
- generated-profile controlled distribution;
- strongSwan license/SBOM/security review;
- mobile app/platform Store concerns only for the actual app-backed paths.

## 19. PVNetwork reuse decision — PASS

Decision:

`STANDARD IKEV2 INTEROPERABILITY TARGET / NATIVE-OS-FIRST / STRONGSWAN FOR ANDROID+ADVANCED PORTABILITY / WATCHGUARD PROFILE+AUTH CERTIFICATION REQUIRED`

Do not create a WatchGuard-specific cryptographic engine. Normalize WatchGuard configuration into the shared IKEv2 model and use the best platform backend.

## 20. Uncertainties explicitly listed — PASS

Bounded later-stage uncertainties:

- exact current Fireware/model/OS support matrix;
- complete current Phase 1/2 defaults/options;
- exact EAP/MS-CHAPv2/RADIUS/AuthPoint combinations;
- generated `.TGZ` contents across Fireware versions;
- native platform API implementation details;
- Android strongSwan exact app/version/config;
- optional WatchGuard Windows client exact version/license/package if retained;
- RSA/EC certificate matrix;
- routes/DNS/full/split/policy;
- real packet/interoperability/reconnect/profile-rotation tests;
- post-review advisories/releases.

These are v2/implementation/certification concerns, not missing original-v1 categories.

# Formal result

All 20 original-v1 gates are evidence-backed, evidence-backed proprietary N/A, or explicitly bounded with traceable later-stage uncertainty.

**Entry 030 may be promoted to `COMPLETE-RESEARCH-v1`.**

Research completion only; no implementation/vendor/production claim.