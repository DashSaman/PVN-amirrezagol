# IKE / IPsec Family — PVNetwork Support / Reuse Decision Record

Decision date: 2026-08-14

State: research architecture decision only. No PVNetwork IPsec implementation/certification exists.

## Shared backend strategy

Research decision:

**`NATIVE-IKEV2-FIRST WHERE CAPABLE / STRONGSWAN FOR LINUX+ADVANCED COMPATIBILITY`**

Do not force strongSwan onto every OS and do not assume native OS APIs cover every advanced/vendor/legacy profile.

Use one product-level IPsec Adapter with platform/backend capability discovery.

---

## 004 — IKEv2/IPsec

Research classification:

**`HIGH-PRIORITY STANDARD VPN TARGET`**

Preferred backend direction:

- Apple: native NetworkExtension IKEv2 first;
- Windows: native Windows IKEv2/VPN platform first;
- modern Android: native IKEv2 profile/VpnManager first when API/profile capabilities fit;
- Linux: strongSwan + kernel IPsec primary candidate;
- fallback/advanced: strongSwan or another approved engine only where a required feature/interoperability gap justifies it.

Canonical profile must preserve authentication, identities, IKE/CHILD policy, traffic selectors and secure credential references independent from backend-specific syntax.

Product claim today: none.

---

## 005 — IKEv1/IPsec

Research classification:

**`LEGACY / VENDOR-COMPATIBILITY TARGET / NOT DEFAULT`**

Preferred engine direction:

- strongSwan is the primary open-source reference/candidate because of mature IKEv1 implementation/plugin architecture;
- native OS generic IKEv1 support must not be assumed from IKEv2 APIs;
- vendor-specific clients/engines may still be required for proprietary modes/extensions.

Rules:

- never silently downgrade IKEv2 to IKEv1;
- legacy algorithms/modes require explicit security/interoperability policy;
- only expose when the selected backend/platform/server combination is certified.

Product claim today: none.

---

## 006 — IPsec ESP

Research classification:

**`FOUNDATIONAL IPSEC DATA-PLANE CAPABILITY / NOT A NORMAL STANDALONE PROFILE`**

PVNetwork should normally obtain ESP through:

- native OS IPsec implementation; or
- strongSwan-controlled kernel/user-space backend where appropriate.

Do not implement ESP cryptographic transforms from scratch.

Model negotiated data SA/policy/effective transform separately from IKE SA state.

Product claim today: none.

---

## 007 — IPsec AH

Research classification:

**`ADVANCED / LOW REMOTE-ACCESS PRIORITY / NON-ENCRYPTING INTEGRITY MODE`**

AH is retained for complete technical/IPsec compatibility research but should not be a primary consumer onboarding option.

Rules:

- never describe AH as encrypted payload protection;
- never use it as automatic fallback from ESP;
- support only where the selected OS/kernel/backend/server configuration has real evidence;
- later v2 must document NAT/deployment constraints and wire semantics in detail.

Product claim today: none.

---

## 008 — L2TP/IPsec

Research classification:

**`LEGACY COMPOSED COMPATIBILITY TARGET`**

Architecture:

`IPsec protection`

`+ L2TP tunnel/session`

`+ PPP/user authentication/addressing where applicable`

Preferred backend direction:

- native OS stack where supported/acceptable;
- strongSwan can provide the IPsec layer on Linux/advanced deployments, but an L2TP implementation is still required;
- do not treat a strongSwan IKEv2 adapter as a complete L2TP/IPsec implementation.

Store IPsec PSK/cert credentials separately from L2TP/PPP credentials.

Product claim today: none.

---

# strongSwan reuse decision

Upstream: `strongswan/strongswan`.

Root license reviewed: GPLv2 family.

Research classification:

**`STRONG LINUX/ADVANCED ENGINE CANDIDATE / GPL+PLUGIN+DAEMON-BOUNDARY REVIEW REQUIRED`**

Preferred use if selected:

- separately managed daemon/service;
- VICI or another narrow typed local control boundary;
- OS kernel IPsec backend on Linux where applicable;
- minimal plugin set matching required features;
- product UI/storage independent from strongSwan native config files.

Avoid embedding/copying the entire GPL front end into a closed product by default.

## VICI direction

VICI is attractive as a local daemon-control boundary for Linux/desktop integrations because it separates `swanctl`/management clients from daemon internals.

PVNetwork must wrap it in a typed adapter and restrict local permissions. Do not expose arbitrary VICI operations to untrusted UI/plugins/LAN callers.

# Native backend decision

Native OS APIs are preferred when they satisfy exact profile requirements because they reduce custom privileged data-plane code and better align with Store/platform lifecycle.

However, native backend selection must be capability-driven, not merely OS-driven.

If a profile requires unsupported EAP/vendor/algorithm/legacy behavior:

- report unsupported capability;
- try only an approved alternate backend;
- never silently weaken or change IKE version/security semantics.

# Product data model

Recommended canonical layers:

`PVProfile.IPsec`

- IKE version;
- endpoint/identities;
- authentication type;
- secure credential references;
- security policy requirements;
- traffic selectors/routes;
- mobility/rekey requirements;
- optional composition (`L2TP`, site-to-site, etc.);
- source/vendor/version metadata.

Runtime/backend state:

- IKE SA;
- child/data SA(s);
- effective algorithms;
- route/policy install state;
- liveness/rekey state;
- backend/platform version.

Do not serialize runtime SA keys/material into normal application storage/logs.

# Engine/plugin minimization

For a strongSwan-based build, compile/load only required plugins/capabilities where feasible.

Benefits:

- smaller attack surface;
- smaller binary/dependency graph;
- clearer license/SBOM;
- fewer untested algorithms/EAP methods;
- easier diagnostics/certification.

Do not strip plugins before verifying required vendor/profile interoperability.

# Family v1 closure position

Current research covers:

- strongSwan source architecture;
- IKE_SA vs CHILD_SA/data-plane separation;
- plugin/kernel/VICI architecture;
- native OS backend comparison;
- protocol capability model for 004–008;
- dependency/security/test gates;
- per-entry backend/reuse decisions.

This is enough for a reasonable original-v1 handoff while exact build/security/implementation evidence remains explicit.

## Residual gaps

- exact current full strongSwan release/tag/commit;
- complete plugin/dependency/license/advisory table;
- source-level Android front-end/menu/storage audit;
- exact native OS capability/version matrix;
- current issue/regression sampling;
- actual interoperability/device/performance tests;
- server installers/panels, full cryptography, IKE/ESP/AH wire flow, ports/NAT-T and deployment topologies belong to mandatory v2.
