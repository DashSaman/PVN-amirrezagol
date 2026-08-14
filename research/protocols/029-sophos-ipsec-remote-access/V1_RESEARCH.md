# 029 — Sophos IPsec Remote Access — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Decision: **`IPSEC INTEROPERABILITY TARGET / NATIVE-OS OR STRONGSWAN BACKEND`**.

Reuse the shared IKE/IPsec profile and backend model. Certify exact Sophos Firewall/profile versions; do not create a separate IPsec cryptographic stack.

Shared evidence: `research/upstreams/vendor-enterprise-family/` and `research/upstreams/strongswan-family/`.

Later v2 adds exact auth/algorithm/profile behavior, gateway/client versions, installs, menus and wire flow.