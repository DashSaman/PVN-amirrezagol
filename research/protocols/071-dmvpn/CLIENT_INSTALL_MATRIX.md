# DMVPN — Client / Peer Install Matrix

Reviewed: 2026-08-15

DMVPN roles are hub/spoke/router peers rather than consumer VPN clients.

Cisco IOS XE routers are the canonical proprietary role implementation. The public Linux reference uses kernel mGRE/GRE + FRR NHRP/routing + strongSwan. Generic mobile/desktop consumer platforms are NOT-APPLICABLE and must not be inferred from standalone IPsec support.

Spoke lifecycle includes NHRP registration/resolution and routing plus security-layer state; it is not equivalent to importing a portable consumer VPN profile.