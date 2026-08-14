# SoftEther Family — PVNetwork Support / Reuse Decision Record

Decision date: 2026-08-14

State: research architecture decision only. No PVNetwork implementation/certification exists.

## Shared upstream

Repository: `SoftEtherVPN/SoftEtherVPN`

Reviewed research source line: `b1f7ef0...`

Root license reviewed: **Apache-2.0**.

Research classification:

**`STRONG SOURCE/REUSE CANDIDATE / MINIMAL-CLIENT-BOUNDARY+DEPENDENCY+PLATFORM REVIEW REQUIRED`**

## 013 — SoftEther VPN Protocol

Classification:

**`UNIQUE NATIVE VPN TARGET / SOFTETHER-UPSTREAM PRIMARY CANDIDATE`**

### Why preserve it

- adds native SoftEther coverage that OpenVPN/WireGuard/Xray/OpenConnect do not provide directly;
- official upstream contains both client and server implementations;
- Apache-2.0 root license is comparatively friendly for commercial engineering evaluation;
- source contains the native client/server/session stack and management tooling.

### Integration direction

Do not fork/reskin the entire SoftEther client manager by default.

First determine the smallest reusable native-client/core boundary and place it behind:

`PVNetwork SoftEther Adapter`

with product-owned:

- canonical profile;
- secure credentials/certificates;
- platform network lifecycle;
- connection state;
- routing/DNS;
- UI/localization;
- diagnostics;
- packaging/update.

### Platform caution

Desktop client availability/source does not prove Android/iOS/TV feasibility. Native SoftEther mobile support must be independently proven against Android VpnService / Apple NetworkExtension and Layer-2/virtual-adapter constraints.

### Product claim today

None.

---

## 014 — EtherIP

Classification:

**`ADVANCED L2 ENCAPSULATION / LOW CONSUMER PRIORITY / NOT ENCRYPTED BY ITSELF`**

Primary source reference:

`src/Cedar/EtherIP.c`

### Product direction

Keep in the research/reference matrix, but do not prioritize it for the first normal-user client UI.

Likely use cases:

- site-to-site;
- bridge/router interoperability;
- SoftEther server/bridge administration;
- specialist network deployments.

UI/security must clearly state that raw EtherIP itself is encapsulation, not confidentiality/authentication.

### Product claim today

None.

---

## 015 — EtherIP/IPsec

Classification:

**`ADVANCED PROTECTED SITE-TO-SITE TARGET / IPSEC-COMPOSED`**

Primary source references include:

- `src/Cedar/EtherIP.c`
- `src/Cedar/IPsec.c`

### Product direction

Model this as a composition:

- EtherIP/L2 encapsulation;
- IPsec/IKE protection/security policy.

Do not create a totally unrelated duplicate security model if PVNetwork already has a typed IPsec capability layer.

Likely belongs in Advanced/server/site-to-site functionality rather than normal consumer onboarding.

### Product claim today

None.

---

# Related compatibility modes — reuse rule

SoftEther server contains modules for OpenVPN, SSTP, L2TP/IPsec and other compatibility services.

These are **server/interoperability evidence**, not reasons to replace PVNetwork's chosen client engines:

- OpenVPN client remains OpenVPN3-core-first research direction;
- IKE/IPsec client remains separate native/strongSwan-style family research;
- SSTP client remains a separate decision;
- L2TP/IPsec remains a separate client family.

Do not route all compatible profiles through SoftEther just because its server accepts them.

# Component/source reuse policy

Apache-2.0 makes direct source reuse technically attractive, but final approval requires:

- exact file/component dependency map;
- minimal client-only build feasibility;
- notices;
- current security/release state;
- trademark/branding separation;
- privileged service/network adapter lifecycle;
- mobile Store feasibility;
- tests for only enabled modules.

## GUI/manager reuse

SoftEther Client Manager / Server Manager / vpncmd are strong feature/menu/admin references.

PVNetwork should implement its own UI and localization rather than copy the manager visual identity. Admin functionality, if added later, belongs in a separate advanced/server area.

# Canonical product modeling

## Entry 013

Store a typed native SoftEther profile with:

- endpoint/listener;
- Virtual Hub target where required;
- auth/certificate references;
- adapter/session options;
- version/source metadata;
- original imported data where needed.

## Entries 014/015

Keep site-to-site/encapsulation fields separate from normal remote-access profiles.

For 015, reuse common IPsec security/auth models where semantics match.

# Family v1 closure position

Current evidence now covers:

- source architecture;
- native vs compatibility capability separation;
- client/server/bridge/command/manager roles;
- configuration/storage design boundaries;
- build/dependency/security rules;
- per-entry support/reuse decisions.

This is sufficient for a reasonable shared-family original-v1 handoff while preserving exact implementation/server/menu/crypto gaps.

## Residual gaps

- exact full current source/tag and recursive tree;
- exact minimal client library/API boundary;
- full dependency/SBOM/advisory scan;
- complete client/server manager menu and screenshot catalog;
- exact secret/config serialization;
- Android/iOS native-client feasibility;
- current issue/release/performance matrix;
- server installers/panels, full install matrices, cryptography/wire flow belong to mandatory later v2.
