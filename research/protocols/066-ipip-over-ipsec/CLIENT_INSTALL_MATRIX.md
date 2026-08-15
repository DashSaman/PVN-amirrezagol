# IPIP over IPsec — Client / Peer Install Matrix

Reviewed: 2026-08-15

This is an infrastructure peer composition rather than a canonical consumer VPN client.

| Target | State | Boundary |
|---|---|---|
| Linux | Supported composition | Kernel IPIP + iproute2 plus XFRM/IPsec/IKE; privileged network administration |
| Containers | Conditional | Requires host/kernel IPIP/XFRM capability and privileges; no independent client package |
| Android / Android TV | NOT PROMOTED | Native/VpnService or IPsec capability does not establish generic IPIP-over-IPsec composition for an app |
| iOS/iPadOS | NOT PROMOTED | No authoritative generic IPIP interface + IPsec app path established here |
| macOS / Windows | UNKNOWN for PVNetwork | Do not infer composition support from standalone IPsec availability |

Entry 065 contains the bare-IPIP endpoint matrix; the strongSwan-family V2 dossier contains detailed IKE/IPsec client packaging evidence.
