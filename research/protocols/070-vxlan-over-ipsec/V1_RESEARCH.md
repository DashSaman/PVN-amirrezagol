# 070 — VXLAN over IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: VXLAN overlay protected by IPsec/IKE.

Decision: **`ADVANCED COMPOSITION / REUSE VXLAN+IPSEC BACKENDS`**.

Keep VXLAN/VNI/overlay fields separate from IKE/IPsec authentication/security policy. Prefer mature OS/kernel VXLAN and approved IPsec backend rather than a custom combined engine.

Later v2 adds exact implementations, cryptography/wire flow, UDP/IPsec behavior, install matrices and topologies.
