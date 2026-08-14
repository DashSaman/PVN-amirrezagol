# 014 — EtherIP — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: Layer-2-over-IP encapsulation protocol; **not encrypted by itself**.

Primary reviewed SoftEther source includes `src/Cedar/EtherIP.c`.

Research decision:

**`ADVANCED L2 ENCAPSULATION / LOW CONSUMER PRIORITY / NOT ENCRYPTED BY ITSELF`**

PVNetwork should retain EtherIP in the technical reference and advanced/site-to-site capability model, but it should not be presented as an ordinary encrypted consumer VPN.

Likely product relevance:

- site-to-site;
- Layer-2 bridge/router interoperability;
- SoftEther server/bridge administration;
- specialist network deployments.

Security UI must clearly distinguish raw EtherIP from EtherIP protected by IPsec.

Shared evidence: `research/upstreams/softether-family/`.

Residual gaps:

- exact interoperability matrix;
- full OS/router/server implementation landscape;
- complete config/menu evidence;
- cryptography/security classification and packet/wire path belong to mandatory v2;
- no PVNetwork implementation exists.
