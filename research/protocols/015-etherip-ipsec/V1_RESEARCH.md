# 015 — EtherIP/IPsec — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Classification: protected site-to-site composition of EtherIP encapsulation with IPsec/IKE security.

Canonical/reference layers:

- EtherIP: RFC 3378 + completed entry 014 research;
- ESP: RFC 4303;
- reviewed SoftEther IKEv1-style implementation: RFC 2409 semantics plus pinned `Proto_IKE.c` evidence;
- broader IPsec backends: existing PVNetwork strongSwan/platform-native research.

Primary reviewed SoftEther source areas:

- `src/Cedar/Proto_EtherIP.c`
- `src/Cedar/Proto_IPsec.c`
- `src/Cedar/Proto_IKE.c`

at `SoftEtherVPN/SoftEtherVPN@b1f7ef00040786d00bfa06c27fa463d106851e0c`.

Formal gate reconciliation:

- `research/protocols/015-etherip-ipsec/V1_GATE_RECONCILIATION.md`

Research decision:

**`ADVANCED PROTECTED SITE-TO-SITE TARGET / ETHERIP + TYPED-IPSEC COMPOSITION`**

PVNetwork should model this as linked concepts rather than an unrelated monolithic protocol:

- EtherIP / Layer-2 encapsulation;
- IPsec/IKE authentication, SA management and ESP protection.

Reuse common typed IPsec security/authentication models where semantics match, while preserving EtherIP-specific bridge/encapsulation configuration. Never silently downgrade a modern IPsec policy to legacy IKEv1 solely for compatibility.

Likely product placement is Advanced/server/site-to-site functionality rather than normal consumer onboarding.

Research-complete uncertainties retained for implementation/certification:

- exact selected backend/version and algorithm policy;
- live cross-vendor interoperability;
- topology-specific NAT/firewall/MTU/rekey behavior;
- production-safe SoftEther release selection;
- runtime/performance/failover evidence;
- no consumer mobile support is claimed.

These are not hidden v1 research gates; all 20 original research categories are reconciled in `V1_GATE_RECONCILIATION.md`.
