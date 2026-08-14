# 015 — EtherIP/IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: protected site-to-site composition of EtherIP encapsulation with IPsec/IKE security.

Primary reviewed SoftEther source areas include:

- `src/Cedar/EtherIP.c`
- `src/Cedar/IPsec.c`

Research decision:

**`ADVANCED PROTECTED SITE-TO-SITE TARGET / IPSEC-COMPOSED`**

PVNetwork should model this as two linked concepts rather than a totally unrelated monolithic protocol:

- EtherIP / Layer-2 encapsulation;
- IPsec/IKE authentication and cryptographic protection.

Reuse common typed IPsec security/authentication models where semantics match, while preserving EtherIP-specific bridge/encapsulation configuration.

Likely product placement is Advanced/server/site-to-site functionality rather than normal consumer onboarding.

Shared evidence: `research/upstreams/softether-family/`.

Residual gaps:

- exact IKE/IPsec profile/security capability mapping;
- router/server/client interoperability;
- full install/menu/deployment evidence;
- cryptography, handshake, data path and server installers belong to mandatory v2;
- no PVNetwork implementation exists.
