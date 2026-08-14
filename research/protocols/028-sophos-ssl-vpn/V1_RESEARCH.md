# 028 — Sophos SSL VPN — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Decision: **`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST WHEN PROFILE IS STANDARD-COMPATIBLE`**.

For Sophos deployments that provide standard OpenVPN-compatible profiles, route through PVNetwork's OpenVPN Adapter rather than add a dedicated Sophos SSL engine.

Exact Sophos Firewall/profile/version compatibility still requires testing; do not infer every Sophos SSL generation is identical.

Shared evidence: `research/upstreams/vendor-enterprise-family/` and `research/upstreams/openvpn-family/`.

Later v2 adds gateway versions, profile/install/menu details, auth/crypto/wire flow and interoperability.