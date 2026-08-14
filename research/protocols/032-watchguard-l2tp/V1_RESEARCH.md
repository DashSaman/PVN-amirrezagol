# 032 — WatchGuard L2TP VPN — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Decision: **`LEGACY L2TP/IPSEC INTEROPERABILITY TARGET / NATIVE COMPOSED STACK`**.

Reuse the layered L2TP/IPsec model from entry 008. Keep IPsec PSK/certificate credentials separate from L2TP/PPP user credentials and mark legacy status consistently.

Certify exact WatchGuard Firebox/profile versions before any support claim.

Shared evidence: `research/upstreams/vendor-enterprise-family/` and `research/upstreams/strongswan-family/`.

Later v2 adds exact server/client installs, menu/profile fields, cryptography/wire flow and interoperability.