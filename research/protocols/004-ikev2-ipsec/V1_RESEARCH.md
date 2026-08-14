# 004 — IKEv2/IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: modern IKE control/authentication plus negotiated IPsec data SAs.

Research decision:

**`HIGH-PRIORITY STANDARD VPN TARGET / NATIVE-OS-FIRST WHERE CAPABLE / STRONGSWAN ADVANCED+LINUX`**

Shared evidence: `research/upstreams/strongswan-family/`.

PVNetwork canonical model must keep endpoint/identity/authentication/secure credentials, IKE policy, CHILD/ESP policy, traffic selectors and backend metadata separate from native strongSwan/Windows/Apple/Android config syntax.

Preferred direction:

- Apple native IKEv2/NetworkExtension first where capability fits;
- Windows native IKEv2 first for standard profiles;
- modern Android native IKEv2 APIs first when API/profile requirements fit;
- Linux strongSwan + kernel IPsec primary candidate;
- alternate strongSwan/other backend only for real capability/interoperability gaps.

No support claim exists until backend/platform/server combinations are implemented and tested.

Later mandatory v2 adds complete crypto/handshake, ESP data path, UDP 500/4500/NAT-T, server installers/panels, OS install matrices and full client/server menus.
