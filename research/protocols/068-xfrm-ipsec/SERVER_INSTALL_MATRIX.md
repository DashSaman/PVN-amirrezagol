# XFRM/IPsec — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Evidence-backed boundary |
|---|---|---|
| Linux kernel 4.19+ | Supported | XFRM interface support; current strongSwan docs identify this minimum |
| iproute2 5.1.0+ | Supported native management | `type xfrm` interface creation/inspection; strongSwan docs identify this minimum |
| Older iproute2 with suitable kernel | Conditional | strongSwan `xfrmi` utility may create/manage XFRM interfaces |
| IPv4 + IPv6 SAs | Supported on same XFRM interface | No endpoint addresses are configured on the interface; interface ID links policy/SAs |
| Network namespaces | Supported design | strongSwan documents moving XFRM interfaces to namespaces while SAs/keys remain in original namespace |
| VRF | Supported with documented caveats | XFRM interfaces can be attached to L3 VRF master devices; kernel-version limitations must be respected |
| Non-Linux consumer platforms | NOT-APPLICABLE to Linux XFRM interface abstraction | Native IPsec elsewhere does not imply Linux XFRM-interface support |

Detailed IKE/IPsec server/client package matrices are reused from the strongSwan-family V2 dossier.
