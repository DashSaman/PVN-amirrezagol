# IKE / IPsec Family — Protocol Capability Model

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

## 004 — IKEv2/IPsec

Classification: **modern IKE control/authentication protocol + negotiated IPsec data SAs**.

Research direction:

`HIGH-PRIORITY STANDARD VPN TARGET / NATIVE-OS-FIRST WHERE CAPABLE / STRONGSWAN ADVANCED+LINUX CANDIDATE`

PVNetwork canonical model must keep separate:

- IKE version = 2;
- endpoint / local+remote identities;
- authentication method and secure credential references;
- IKE proposal constraints;
- CHILD_SA / ESP proposal constraints;
- traffic selectors/routes;
- mobility/liveness/rekey settings where needed;
- vendor/server compatibility metadata;
- platform backend/capability version.

Do not expose raw proposal strings to Simple Mode unless necessary.

## 005 — IKEv1/IPsec

Classification: **legacy IKE control/authentication family + IPsec data SAs**.

Research direction:

`LEGACY / VENDOR-COMPATIBILITY TARGET / NOT DEFAULT`

Why separate:

- protocol state machine differs from IKEv2;
- authentication/mode semantics differ;
- NAT/interoperability/vendor extensions differ;
- native modern OS APIs may not expose generic IKEv1 remote-access capability;
- security/algorithm expectations are often older.

PVNetwork should support IKEv1 only when a concrete server/vendor compatibility requirement justifies it and the selected engine/platform can meet a safe policy.

Never silently downgrade IKEv2 to IKEv1.

## 006 — IPsec ESP

Classification: **IPsec data-plane protocol**, not a user-facing remote-access connection method by itself.

ESP normally carries protected payload traffic according to negotiated/manual Security Associations. Depending on transform/mode, it can provide confidentiality plus integrity/authentication.

PVNetwork model should treat ESP parameters as part of the negotiated IPsec/CHILD_SA capability, not as a standalone “connect with ESP” profile for normal users.

Key product concepts:

- tunnel vs transport mode where applicable;
- encryption/integrity/AEAD transform;
- SA lifetime/rekey;
- traffic selectors;
- replay protection;
- NAT traversal relationship;
- kernel/user-space data-plane backend.

Do not implement ESP cryptography from scratch.

## 007 — IPsec AH

Classification: **IPsec Authentication Header data-plane protocol**.

AH provides integrity/authentication protection but does not encrypt the protected payload.

Research direction:

`ADVANCED / LOW REMOTE-ACCESS PRIORITY / EXPLICIT SECURITY WARNING`

PVNetwork should not market AH as an encrypted VPN.

AH also has deployment/NAT constraints that make it unsuitable for many consumer remote-access scenarios. Retain it for complete IPsec reference/advanced compatibility, not as a primary onboarding option.

Do not enable AH automatically as a fallback from ESP.

## 008 — L2TP/IPsec

Classification: **L2TP tunnel/session carried through IPsec protection**.

Research direction:

`LEGACY COMPATIBILITY TARGET / COMPOSED STACK`

PVNetwork must model two layers:

1. IKE/IPsec setup/security;
2. L2TP/PPP-style tunnel/session/auth/address configuration.

A platform/backend that can do IKEv2 does not automatically support L2TP/IPsec. Many L2TP/IPsec deployments use older IKE/IPsec semantics and separate OS components.

Do not flatten L2TP user/password and IPsec PSK/certificate into one ambiguous credential field.

## Shared cryptographic-policy model

PVNetwork should store **requirements/policy**, not a blindly copied native proposal string.

Categories:

### IKE SA

- key exchange group;
- encryption/AEAD;
- integrity/PRF as required by protocol/backend;
- lifetime/rekey policy.

### CHILD / ESP SA

- encryption/AEAD;
- integrity where separate;
- PFS/key-exchange requirement where applicable;
- mode;
- lifetime/rekey;
- traffic selectors.

### Authentication

- certificate/public key;
- PSK;
- EAP credentials/methods;
- platform key/identity;
- secure element/token where supported.

Do not store reusable secret material in the policy object itself.

## Algorithm policy rule

The fact that an engine/plugin supports an old algorithm does not mean PVNetwork should expose/allow it by default.

Maintain:

- secure default policy;
- explicit legacy compatibility policy;
- server-required override only when user/admin understands the risk;
- product warning and exact certification state.

Never silently weaken algorithms merely to make a connection succeed.

## NAT-T / mobility classification

NAT traversal, MOBIKE/mobility, fragmentation and liveness are **capabilities/extensions**, not separate VPN protocols.

PVNetwork should expose them as backend/profile capabilities and test them by exact OS/engine/server version.

## Backend capability matrix concept

For each backend record at least:

| Backend | IKEv2 | IKEv1 | ESP | AH | L2TP/IPsec | EAP | Cert | PSK | MOBIKE | Store suitability |
|---|---|---|---|---|---|---|---|---|---|---|
| strongSwan/Linux | research candidate | research candidate | kernel/backend dependent | backend dependent | composition requires L2TP layer | plugin dependent | plugin/credential dependent | supported in relevant configs | plugin/version dependent | desktop/Linux service path |
| Android native IKEv2 | modern API candidate | no generic claim | OS-managed | no generic claim | separate capability | API/profile dependent | API/profile dependent | API/profile dependent | OS-managed capability dependent | strong platform fit when profile fits |
| Apple NEVPN IKEv2 | native candidate | separate/legacy APIs not equivalent | OS-managed | no generic claim | separate | API dependent | platform identity | supported where API allows | OS behavior/version dependent | preferred IKEv2 path |
| Windows native IKEv2 | native candidate | do not infer generic IKEv1 | OS-managed | do not infer | separate native type | Windows feature dependent | cert store | supported/profile dependent | OS/profile dependent | preferred standard IKEv2 path |

Every cell must eventually be replaced with exact versioned evidence before production claims.

## Error taxonomy

Normalize low-level engine/native errors into product categories:

- no compatible IKE proposal;
- authentication failed;
- certificate invalid/untrusted/name mismatch;
- identity mismatch;
- EAP/user interaction required;
- no compatible CHILD/ESP proposal;
- traffic-selector/policy failure;
- kernel/platform SA install failure;
- NAT/network unreachable;
- server timeout;
- rekey/liveness failure;
- platform permission/profile provisioning failure;
- unsupported legacy mode.

Do not show a generic “IPsec failed” when the backend can provide a useful category.

## Import/migration rule

When importing native strongSwan/Windows/Apple/Android/vendor profiles:

- preserve source/backend metadata;
- normalize only semantics PVNetwork understands;
- mark unsupported algorithms/plugins/extensions;
- keep unknown fields for future round trip where practical;
- never silently remove a traffic selector, auth requirement or algorithm constraint;
- never silently switch IKE version.

## Later v2 expansion

The later `COMPLETE-REFERENCE-v2` phase must document:

- full IKEv1/IKEv2 cryptography/handshake sequence;
- ESP/AH packet/data path;
- UDP 500/4500/NAT-T and other port/protocol behavior;
- server implementations/installers/panels;
- server/client OS install matrices;
- complete menu/config fields;
- site-to-site and remote-access topologies;
- L2TP/IPsec layered data path.
