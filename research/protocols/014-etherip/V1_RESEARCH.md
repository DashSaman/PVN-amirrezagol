# 014 — EtherIP — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Classification: Layer-2-over-IP encapsulation protocol; **not encrypted by itself**.

Canonical specification: RFC 3378.

Primary reviewed implementation evidence:

- SoftEther `src/Cedar/Proto_EtherIP.c` at `SoftEtherVPN/SoftEtherVPN@b1f7ef00040786d00bfa06c27fa463d106851e0c`;
- OpenBSD `etherip(4)` native peer/bridge reference;
- FreeBSD `gif(4)` + bridge EtherIP reference.

Formal gate reconciliation:

- `research/protocols/014-etherip/V1_GATE_RECONCILIATION.md`

Research decision:

**`ADVANCED L2 ENCAPSULATION / SERVER-CAPABILITY / LOW CONSUMER PRIORITY / NOT ENCRYPTED BY ITSELF`**

PVNetwork should retain EtherIP in the technical reference and advanced/site-to-site capability model, but it should not be presented as an ordinary encrypted consumer VPN.

Likely product relevance:

- site-to-site;
- Layer-2 bridge/router interoperability;
- SoftEther server/bridge administration;
- specialist network deployments.

Security UI must clearly distinguish raw EtherIP from EtherIP protected by IPsec.

Shared evidence: `research/upstreams/softether-family/`.

Research-complete uncertainties retained for implementation/certification:

- exact selected peer/runtime implementation;
- live cross-implementation interoperability;
- deployment-specific MTU/firewall/bridge behavior;
- production-safe SoftEther release selection;
- performance/resource evidence;
- no mobile consumer-client support is claimed;
- IPsec protection remains a separate layer/entry.

These are not hidden v1 research gates. The exact 20-item template reconciliation is in `V1_GATE_RECONCILIATION.md`.
