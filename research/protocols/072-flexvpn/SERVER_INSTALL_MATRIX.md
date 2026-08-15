# Cisco FlexVPN — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Boundary |
|---|---|---|
| Supported Cisco IOS XE router platforms | Canonical FlexVPN implementation | Exact feature/release/platform matrix is controlled by current Cisco documentation |
| Linux + strongSwan/native IPsec | Generic IKEv2/IPsec interoperability/reference | Not proof of full Cisco FlexVPN extension compatibility |
| Containers/Kubernetes | No canonical FlexVPN server deployment | Generic IPsec may be deployed separately; Cisco FlexVPN itself is IOS XE framework |
| Consumer mobile/desktop | Server N/A; client interoperability is IKEv2/profile-dependent | Do not infer full FlexVPN support from generic IKEv2 connection capability |

Platform/version-specific certification remains later implementation/interoperability work, not a hidden research gate.