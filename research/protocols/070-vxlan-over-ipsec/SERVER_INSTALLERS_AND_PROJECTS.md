# VXLAN over IPsec — Server Installers and Deployment Projects

Reviewed: 2026-08-15

No canonical standalone VXLAN-over-IPsec installer exists. Linux deployment composes kernel/iproute2 VXLAN with reviewed XFRM/IKE/IPsec packages. Open vSwitch may supply the VXLAN datapath while the host/network IPsec layer supplies protection; that remains a separate implementation boundary.

Deployment is privileged and changes VTEP/bridge/FDB/routing plus IPsec policies/SAs/credentials. Prefer distribution/native packages and reviewed IKE software; avoid unreviewed one-click scripts.

Upgrade/uninstall/rollback are the component lifecycles plus removal/reversion of overlay and IPsec configuration; there is no separate composition package.