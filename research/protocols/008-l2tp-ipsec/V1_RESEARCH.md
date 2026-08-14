# 008 — L2TP/IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: layered legacy compatibility stack: IPsec protection + L2TP tunnel/session + PPP/user authentication/addressing where applicable.

Research decision:

**`LEGACY COMPOSED COMPATIBILITY TARGET`**

PVNetwork must keep credentials and failures separate by layer:

- IPsec PSK/certificate/IKE failure;
- L2TP tunnel/session failure;
- PPP/user authentication failure;
- address/DNS/route assignment failure.

A strongSwan IKE adapter alone is not a complete L2TP/IPsec implementation; an L2TP layer/backend is additionally required.

Prefer native OS stack where appropriate and still supported safely; Linux/advanced solutions may use strongSwan for the IPsec layer plus a separate L2TP component.

Shared evidence: `research/upstreams/strongswan-family/`.

Later v2 must add full L2TP/IPsec crypto/wire/data flow, ports/handshake, server/client implementations/installers, OS install matrices and complete menus.
