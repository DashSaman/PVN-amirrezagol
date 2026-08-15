# XFRM/IPsec — Client / Peer Install Matrix

Reviewed: 2026-08-15

XFRM interfaces are Linux-local infrastructure interfaces, not a portable consumer protocol/client.

| Target | State | Boundary |
|---|---|---|
| Modern Linux | Supported infrastructure peer | Kernel 4.19+, iproute2 5.1.0+ (or strongSwan `xfrmi` fallback), XFRM/IPsec + selected IKE stack; privileged networking |
| Linux namespaces/containers | Supported design with host policy | Interface may move into another namespace while SAs/keys remain outside; exact privilege model must be controlled |
| Android / Android TV | NOT-APPLICABLE to Linux XFRM interface abstraction | Android IPsec/VpnService support does not equal Linux `type xfrm` interface access |
| iOS/iPadOS/macOS/Windows | NOT-APPLICABLE to Linux XFRM abstraction | These platforms may implement IPsec but not this Linux interface model |

Cross-platform IKE/IPsec packaging is already covered by the strongSwan-family reference layer; this entry records only XFRM-interface applicability.
