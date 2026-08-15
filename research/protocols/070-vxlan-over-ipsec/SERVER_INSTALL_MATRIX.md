# VXLAN over IPsec — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Boundary |
|---|---|---|
| Modern Linux | Supported composition | Kernel VXLAN + iproute2 plus XFRM/IPsec and reviewed IKE stack; privileged networking |
| Linux + Open vSwitch | Supported architecture where components are available | OVS VXLAN datapath plus host/network IPsec; lifecycle/license remain component-specific |
| Containers/Kubernetes | Conditional | Depends on host VTEP/IPsec capabilities and privileges; no protocol-owned canonical container/Helm deployment |
| Consumer mobile/desktop | NOT PROMOTED | Standalone IPsec capability does not establish generic VXLAN VTEP + IPsec support to an app |

VXLAN and IKE/IPsec detailed platform matrices are reused from entry 069 and the strongSwan-family V2 dossier.