# 031 — WatchGuard SSL VPN — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Decision: **`SSL-VPN COMPATIBILITY TARGET / PROFILE-PROTOCOL DETECTION REQUIRED`**.

Where an exact WatchGuard deployment/profile is OpenVPN-compatible, route through the approved OpenVPN Adapter. Do not assume every WatchGuard SSL VPN generation/profile has identical OpenVPN semantics without server/profile evidence.

Shared evidence: `research/upstreams/vendor-enterprise-family/` and `research/upstreams/openvpn-family/`.

Later v2 adds exact Firebox/client/profile versions, authentication, installs, menus and wire/crypto behavior.