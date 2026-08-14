# 015 — EtherIP/IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research gate only. Entry 015 is a **composition**: EtherIP provides Layer-2 encapsulation; IPsec/IKE provides security association establishment and ESP protection. This document does not claim runtime interoperability or production certification.

## Canonical/reference set

EtherIP:

- RFC 3378: `https://www.rfc-editor.org/rfc/rfc3378.html`
- completed PVNetwork entry 014 research: `research/protocols/014-etherip/V1_GATE_RECONCILIATION.md`

IPsec/IKE references:

- RFC 4303, IP Encapsulating Security Payload (ESP): `https://www.rfc-editor.org/rfc/rfc4303.html`
- RFC 2409, Internet Key Exchange (historic IKEv1 reference): `https://www.rfc-editor.org/rfc/rfc2409.html`
- existing PVNetwork IKE/IPsec research under `research/upstreams/strongswan-family/` and numbered entries 004–007.

Primary reviewed composed implementation:

- `SoftEtherVPN/SoftEtherVPN@b1f7ef00040786d00bfa06c27fa463d106851e0c`
- `src/Cedar/Proto_EtherIP.c`
- `src/Cedar/Proto_IPsec.c`
- `src/Cedar/Proto_IKE.c`

Independent platform/reference topology:

- OpenBSD `etherip(4)`: `https://man.openbsd.org/etherip.4` — native EtherIP interface documentation includes IPsec-protected EtherIP examples and treats raw EtherIP permission separately when IPsec is not protecting traffic.

## Implementation-specific SoftEther security boundary

Pinned `Proto_IPsec.c` directly establishes that SoftEther considers IPsec active when `EtherIP_IPsec || L2TP_IPsec` is enabled, owns IKE/ESP packet dispatch, handles ISAKMP, UDP-encapsulated ESP and raw ESP paths, and coordinates OS/kernel IPsec processing to avoid conflicting ownership.

Pinned `Proto_IKE.c` identifies itself as the IKE (ISAKMP) and ESP stack and dispatches **Main Mode**, **Aggressive Mode**, **Quick Mode** and Informational exchanges. Those exchange names are evidence that this reviewed SoftEther implementation path is IKEv1-style, not a generic claim that every EtherIP/IPsec deployment must use IKEv1. The code also distinguishes transport/tunnel SA handling and builds ESP packets with SPI, sequence number, IV, encrypted payload, padding and HMAC according to the selected transform structures.

Therefore PVNetwork must model:

`EtherIP peer/bridge mapping`

plus

`selected IKE/IPsec backend + auth/algorithms/SA policy`

rather than one opaque “secure EtherIP” checkbox.

## Gate-by-gate reconciliation

### 1. Top clients / implementations identified and justified — PASS

Entry 015 is infrastructure gateway/peer functionality, not a consumer VPN app. The primary composed source candidate is SoftEther's EtherIP + IPsec/IKE server path. OpenBSD `etherip(4)` + IPsec is an independent native peer/reference implementation. The broader IKE/IPsec implementation ecosystem is already researched separately through strongSwan and platform-native IPsec entries. No fake mobile client is required.

### 2. Canonical sources pinned — PASS

EtherIP wire semantics are anchored to RFC 3378; ESP security semantics to RFC 4303; legacy IKE semantics relevant to the reviewed SoftEther Main/Aggressive/Quick mode path to RFC 2409. SoftEther source is immutably pinned to `b1f7ef...`. Final deployment versions remain later source-freeze/certification choices.

### 3. License / legal reuse reviewed — PASS

SoftEther root source is Apache-2.0 with third-party/submodule obligations already mapped in the family dossier. RFCs are specification references. OpenBSD is used as a platform/interoperability reference, not copied into PVNetwork by this decision. strongSwan/platform-native IPsec licensing/reuse is separately tracked where those backends are considered. No license is inferred across unrelated components.

### 4. Complete source-tree reference / manifest captured — PASS

The recursive SoftEther tree at the reviewed pin is preserved, and exact EtherIP/IPsec/IKE implementation files are named. Existing strongSwan research has its own source architecture/release/security evidence. The selected implementation boundary is therefore traceable without copying whole third-party trees.

### 5. Languages / build systems mapped — PASS

SoftEther composed path is part of the native C/CMake Cedar/Mayaqua systems codebase. Other IPsec backends are independently modeled behind platform/backend adapters. This entry does not assume one universal implementation language across peers.

### 6. Architecture mapped — PASS

The architecture is explicitly layered:

`Ethernet frame / bridge`
→ `EtherIP encapsulation / SoftEther IPC Layer 2`
→ `IPsec SA protection (ESP)`
→ `outer IP / optional UDP encapsulation depending peer/backend`

IKE/control-plane negotiation, ESP data-plane protection, EtherIP mapping and Virtual-Hub/bridge attachment remain separate ownership domains. SoftEther source additionally owns OS IPsec conflict handling for its runtime.

### 7. Core / engine integration mapped — PASS

PVNetwork integration must compose a typed EtherIP capability with a typed IPsec capability. For SoftEther-backed deployments, the SoftEther server runtime owns both components and exposes service/mapping configuration. For OS-native/other backends, PVNetwork should drive supported platform interfaces rather than reimplement cryptography. No cryptographic code is to be invented by PVNetwork.

### 8. UI / menu map — PASS (`N/A-CONSUMER / INFRA-MAPPED`)

Applicable management fields are evidence-backed infrastructure concepts:

- EtherIP peer/client ID and bridge/Virtual-Hub mapping;
- IPsec service enabled state;
- IKE/authentication credential reference (for SoftEther reviewed path, shared secret/service identity as applicable);
- ESP/IKE policy surfaced only through supported backend choices;
- peer/outer addresses;
- tunnel/transport-mode semantics only when backend supports them;
- status split into IKE/SA, ESP, EtherIP and bridge/Hub health;
- explicit raw-vs-protected distinction.

A consumer subscription/profile screen is N/A. SoftEther Server Manager/`vpncmd`, OpenBSD native network/IPsec management and the existing IPsec-family UI research establish ownership.

### 9. Configuration / import / export mapped — PASS

There is no universal EtherIP/IPsec QR/subscription profile. PVNetwork must store a canonical infrastructure composition referencing:

- EtherIP peer/mapping configuration;
- selected IPsec backend/profile;
- authentication/secret/certificate references owned by the IPsec layer;
- implementation-specific extensions where needed.

SoftEther service settings and EtherIP ID mappings are not declared a portable cross-vendor schema. Export must not silently include secrets.

### 10. Persistence / secure storage mapped — PASS

EtherIP mapping data and IPsec credentials are different secret/state domains. In the reviewed SoftEther path, service settings contain IPsec secret state and EtherIP mappings feed Hub/User/Password into internal IPC. These values belong in protected server configuration/secret management. strongSwan/native backend credentials retain their own platform storage rules. PVNetwork must persist secret references, not duplicate raw PSKs/private keys into ordinary profile JSON/logs.

### 11. Platform integration mapped — PASS

SoftEther server/runtime is the primary composed source implementation. OpenBSD demonstrates a native EtherIP + IPsec gateway path. Other IKE/IPsec platform support is covered in the IPsec-family research, but entry 015 is not forced into Android/iOS consumer UX. Platform support is capability/backend-specific and must be certified separately.

### 12. Logs / diagnostics mapped — PASS

Diagnostics must be layered: IKE negotiation/auth state, SA/ESP state and counters, NAT/UDP/raw-ESP path where applicable, EtherIP mapping/IPC state, bridge/Virtual-Hub state, reconnect/rekey and cleanup. Secrets, keys and raw authentication material are redacted. A successful IKE SA does not by itself prove EtherIP forwarding, and EtherIP mapping success does not prove ESP protection.

### 13. Images / UI assets / visual references — PASS (`N/A-PROTOCOL-ASSETS`)

RFC diagrams and canonical platform/admin documentation are sufficient architecture/flow references. SoftEther manager visual resources are product-level references already inventoried; no EtherIP/IPsec logo or third-party GUI asset is required or approved for copying.

### 14. Meaningful forks / implementation ecosystem reviewed — PASS

The ecosystem view deliberately combines independent layers instead of searching for a branded monolith: SoftEther is the primary combined source implementation; OpenBSD provides an OS-native combined topology; strongSwan/platform-native research represents the broader IPsec backend ecosystem. Official SoftEther Stable vs Developer lines are tracked separately. This is sufficient to evaluate reuse/interop roles without promoting abandoned forks.

### 15. Issues / PRs / releases / advisories reviewed — PASS

SoftEther release/security/issue evidence applies to the shared runtime and is already reviewed, including current advisory concerns and listener/service health lessons. StrongSwan has separate pinned release/security/dependency evidence. RFC 2409 is explicitly historic/obsoleted by IKEv2 standards work, so a legacy IKEv1-style SoftEther path must not be presented as a modern default. These are acceptance/security-policy inputs, not unexamined research gaps.

### 16. Relevant official docs / community lessons reviewed — PASS

RFC Editor specifications, canonical SoftEther source/releases/issues, OpenBSD platform documentation and existing strongSwan official/source research cover the relevant protocol, implementation and operational lessons. No unverifiable forum claim is needed for completion.

### 17. Tests / CI reviewed — PASS

Existing family dossiers map SoftEther and strongSwan CI/test surfaces. Required future entry-015 acceptance tests are defined around IKE auth success/failure, SA establishment/rekey, ESP protected path, raw EtherIP rejection when protection is required, EtherIP mapping/bridge forwarding, NAT/firewall/MTU behavior, service conflicts, cleanup/restart and negative security policy cases. No runtime PASS is fabricated.

### 18. Store / privacy / security implications reviewed — PASS (`INFRASTRUCTURE-NOT-CONSUMER-STORE`)

This is an advanced site-to-site/server capability. Raw EtherIP alone is not secure transport; protection comes from the selected IPsec policy. Legacy IKEv1 algorithms/modes must not be silently enabled as a generic default. Secret handling, backend privileges, OS service ownership, firewall exposure, logs/redaction and dependency/advisory state are documented. Consumer Store distribution is N/A unless a future platform role is intentionally designed.

### 19. PVNetwork reuse decision documented — PASS

Decision:

`ADVANCED PROTECTED SITE-TO-SITE TARGET / ETHERIP + TYPED-IPSEC COMPOSITION`

When using SoftEther server, reuse its reviewed composed path behind an infrastructure adapter. Otherwise compose an EtherIP peer implementation with an approved IPsec backend only where exact semantics/interoperability are proven. Never infer protection from EtherIP alone and never silently downgrade a modern IPsec policy to legacy IKEv1 for compatibility.

### 20. Uncertainties explicitly listed — PASS

Remaining bounded uncertainties after research completion:

- exact production backend/version pair is not selected;
- live SoftEther↔OpenBSD/other-vendor interoperability is not certified;
- exact deployed IKE/ESP algorithm policy depends on peer/backend and security baseline;
- SoftEther's reviewed source path is IKEv1-style, but other EtherIP/IPsec compositions may use different supported IPsec control planes;
- NAT/firewall/MTU/rekey behavior remains topology/backend-specific;
- production-safe SoftEther release selection remains separate;
- runtime, performance and failover receipts are not available;
- consumer mobile support is intentionally not claimed.

These are implementation/certification choices or explicit unknowns, not missing original-v1 research categories.

## Formal v1 result

All 20 applicable original-v1 research gates are evidence-backed, evidence-backed `N/A`, or explicitly bounded with traceable uncertainty.

**Entry 015 may be promoted to `COMPLETE-RESEARCH-v1`.**

This means research completion only. It does not mean implementation, live interoperability, Store readiness, or production certification.
