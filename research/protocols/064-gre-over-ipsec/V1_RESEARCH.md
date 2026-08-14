# 064 — GRE over IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: GRE encapsulation protected by IPsec/IKE.

Decision: **`ADVANCED COMPOSITION / REUSE IPSEC SECURITY MODEL`**.

Keep GRE interface/tunnel parameters separate from IKE/IPsec authentication/security settings. Prefer OS/kernel GRE plus approved IPsec backend.

Later v2 adds exact implementations, crypto/wire flow, ports, install matrices and topologies.
