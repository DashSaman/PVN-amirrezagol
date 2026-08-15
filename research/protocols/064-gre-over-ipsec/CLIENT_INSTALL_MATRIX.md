# GRE over IPsec — Client / Peer Install Matrix

Reviewed: 2026-08-15

GRE-over-IPsec is infrastructure peer composition rather than a canonical consumer-client protocol.

| Target | State | Boundary |
|---|---|---|
| Linux | Supported composition | GRE via kernel/iproute2 plus IPsec/XFRM/IKE stack such as strongSwan; privileged network admin required |
| Cisco IOS XE devices | Supported on documented releases/platforms | Native GRE + IKEv2/IPsec configuration |
| Android / Android TV | NOT PROMOTED | Native IPsec/VPN APIs do not prove generic GRE-over-IPsec composition availability to an app |
| iOS/iPadOS | NOT PROMOTED | Generic GRE endpoint + IPsec composition not established from authoritative platform evidence here |
| macOS / Windows consumer endpoints | UNKNOWN for product support | Do not infer composition support from individual GRE/PPTP or IKE/IPsec features |

The detailed IKE/IPsec client packaging matrix remains in `research/upstreams/strongswan-family/reference-v2/CLIENT_INSTALL_MATRIX.md`; entry 063 contains the bare-GRE endpoint matrix. This file records only the composition-specific conclusion.
