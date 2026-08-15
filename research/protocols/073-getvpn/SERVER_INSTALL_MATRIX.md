# Cisco GETVPN — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Boundary |
|---|---|---|
| Supported Cisco IOS/IOS XE routing platforms | Canonical implementation | Exact GETVPN/GDOI/G-IKEv2 feature and release/platform support is controlled by current Cisco docs/Feature Navigator |
| Cisco key server (KS) | Supported role | Creates/maintains control plane, policy, group keys, rekey state; current docs can support GKM and GDOI as documented |
| Cisco group member (GM) | Supported role | Registers to KS, receives policy/keying material, protects native unicast/multicast data plane |
| Generic Linux/strongSwan | Reference only | Generic IKE/IPsec does not establish GETVPN/G-IKEv2/GDOI interoperability by itself |
| Containers/Kubernetes | No canonical GETVPN role deployment | Not a protocol-defined deployment target |
| Consumer Windows/macOS/iOS/Android | NOT-APPLICABLE | GETVPN is enterprise router/group-member infrastructure, not a consumer VPN client |

Exact IOS XE version interoperability and GKM/GDOI migration behavior remain capability/version-specific.