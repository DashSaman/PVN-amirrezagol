# 066 — IPIP over IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: IPIP encapsulation protected by IPsec/IKE.

Decision: **`ADVANCED COMPOSITION / REUSE IPSEC SECURITY MODEL`**.

Use OS/kernel IPIP plus the approved typed IPsec backend. Keep tunnel parameters separate from authentication/cryptographic policy.

Later v2 adds implementations, install matrices, cryptography/wire flow and topologies.
