# VXLAN — Server UI and Menus

Reviewed: 2026-08-15

VXLAN defines no accounts, authentication server or protocol-owned web UI. Linux management is via iproute2/bridge/netlink and exposes VNI, local/remote/group endpoint behavior, destination UDP port, underlying device, learning/FDB, ageing, bridge membership, link/route/MTU and statistics.

A future PVNetwork infrastructure UI may expose those fields only where supported by the selected platform. VNI is an overlay identifier, not a cryptographic credential. Account/MFA/cert/cipher/subscription/quota UI is NOT-APPLICABLE to bare VXLAN.