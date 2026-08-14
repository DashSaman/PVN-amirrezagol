# 030 — WatchGuard IKEv2 VPN — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Decision: **`STANDARD IKEV2 INTEROPERABILITY TARGET / NATIVE-OS-FIRST`**.

Reuse the shared IKEv2/IPsec backend model and certify exact WatchGuard Firebox/profile versions. Generic IKEv2 support is not a complete vendor-certification claim.

Shared evidence: `research/upstreams/vendor-enterprise-family/` and `research/upstreams/strongswan-family/`.

Later v2 adds exact profile/auth/algorithm versions, server/client installs, menus and wire/crypto flow.