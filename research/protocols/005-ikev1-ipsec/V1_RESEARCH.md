# 005 — IKEv1/IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: legacy IKE control/authentication family plus IPsec data SAs.

Research decision:

**`LEGACY / VENDOR-COMPATIBILITY TARGET / NOT DEFAULT`**

Primary open-source engine reference/candidate: strongSwan.

PVNetwork must never silently downgrade IKEv2 to IKEv1. Legacy algorithms/modes require explicit compatibility/security policy and exact server/backend evidence.

Do not infer generic IKEv1 support from native IKEv2 APIs on Apple/Android/Windows.

Shared evidence: `research/upstreams/strongswan-family/`.

Later v2 must document IKEv1 modes/handshake/crypto/NAT behavior, server/client implementations/installers and exhaustive menus.
