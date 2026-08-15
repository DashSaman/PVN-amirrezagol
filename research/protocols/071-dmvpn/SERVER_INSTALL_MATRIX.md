# DMVPN — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Boundary |
|---|---|---|
| Cisco IOS XE documented router platforms | Canonical proprietary DMVPN deployment | Router-native mGRE/NHRP/routing/IPsec; exact release/platform guide controls support |
| Linux + FRR + strongSwan | Public reference/implementation composition | Kernel GRE/netlink + FRR `nhrpd`/routing + IKE/IPsec; Linux-oriented integration |
| Containers/Kubernetes | Not canonical | Possible only with extensive host networking/privileges and component design; no protocol-owned deployment contract |
| Windows/macOS/iOS/Android consumer clients | NOT-APPLICABLE | DMVPN is site-to-site/router infrastructure; do not advertise consumer DMVPN client support |

FRR generally supports Linux/BSD but the DMVPN/NHRP integration documented in the V1 evidence is Linux-oriented.