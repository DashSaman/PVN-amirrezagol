# 010 — L2TPv3/IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: layered site-to-site composition: L2TPv3 pseudowire plus IPsec/IKE protection.

Research decision:

**`ADVANCED PROTECTED SITE-TO-SITE COMPOSITION / REUSE IPSEC SECURITY MODEL`**

PVNetwork should keep Ether/L2 pseudowire/session fields separate from the reusable typed IPsec authentication/security model. Do not duplicate PSK/certificate/IKE proposal fields in an opaque L2TPv3/IPsec profile.

Likely Linux direction: kernel L2TPv3 + approved strongSwan/kernel IPsec backend, subject to exact distribution/interoperability evidence.

Shared evidence:

- `research/upstreams/classic-tunnels-family/`
- `research/upstreams/strongswan-family/`

Later v2 must add full cryptography/wire flow, ports/session setup, server/router implementations, install matrices, panels/menus and deployment topologies.
