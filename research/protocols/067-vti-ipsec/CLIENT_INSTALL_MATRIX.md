# VTI/IPsec — Client / Peer Install Matrix

Reviewed: 2026-08-15

VTI is a Linux-local infrastructure interface abstraction, not a portable consumer VPN client protocol.

| Target | State | Boundary |
|---|---|---|
| Linux | Supported infrastructure peer | Kernel VTI/XFRM capability + iproute2 + selected IKE/IPsec implementation; privileged network administration |
| Linux network namespaces/containers | Conditional | Depends on host kernel, policies/SAs and privileges/namespace design |
| Android / Android TV | NOT-APPLICABLE as Linux VTI consumer path | Android IPsec/VpnService capability does not imply VTI device semantics available to an app |
| iOS/iPadOS/macOS/Windows | NOT-APPLICABLE to VTI abstraction | These platforms may have IPsec implementations but not Linux VTI semantics; do not market them as VTI support |

Detailed cross-platform IKE/IPsec client packaging remains in the strongSwan-family V2 dossier; this entry concerns the Linux VTI interface model only.
