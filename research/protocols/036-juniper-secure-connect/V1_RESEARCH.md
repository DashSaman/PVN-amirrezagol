# 036 — Juniper Secure Connect — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Decision: **`VENDOR REMOTE-ACCESS TARGET / STANDARD IPSEC REUSE ONLY WHERE EXACT MODE IS PROVEN`**.

Juniper Secure Connect must remain distinct from older Juniper Network Connect/OpenConnect compatibility. Use native/strongSwan IPsec only when an exact deployment is documented and tested as standard IKE/IPsec-compatible; otherwise keep official-client compatibility status.

Shared evidence: `research/upstreams/vendor-enterprise-family/`.

Later v2 adds exact Juniper gateway/client versions, provisioning/authentication, installs, full menus and security/wire-flow evidence.