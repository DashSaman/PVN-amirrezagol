# 006 — IPsec ESP — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: IPsec data-plane protocol, normally negotiated/installed as part of an IPsec connection rather than a standalone normal-user profile.

Research decision:

**`FOUNDATIONAL IPSEC DATA-PLANE CAPABILITY / NOT A NORMAL STANDALONE PROFILE`**

PVNetwork should obtain ESP through mature native OS IPsec or strongSwan-controlled backend/kernel integration. Do not implement ESP cryptographic transforms from scratch.

Model effective data-SA transform, mode, traffic selectors, lifetime/rekey and backend state separately from IKE authentication/control state.

Shared evidence: `research/upstreams/strongswan-family/`.

Later v2 must document ESP packet/wire structure, modes, crypto, NAT-T relationship, server implementations, install matrices and full technical flow.
