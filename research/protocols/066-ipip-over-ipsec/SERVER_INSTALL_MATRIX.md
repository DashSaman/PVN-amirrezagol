# IPIP over IPsec — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Evidence-backed path |
|---|---|---|
| Linux | Supported composition | IPIP-capable kernel + iproute2 plus Linux XFRM/IPsec and reviewed IKE implementation such as strongSwan; privileged network administration required |
| Containers | Conditional, not canonical | Depends on host/kernel IPIP/XFRM capabilities, network namespaces and privileges; no standalone canonical composition image |
| Kubernetes | No protocol-owned install path | Networking components may build similar overlays, but this entry has no canonical Helm/operator contract |
| Windows/macOS/iOS/Android consumer targets | Not promoted | Standalone IPsec availability does not prove generic IPIP interface + IPsec composition support to PVNetwork |

Detailed IPIP endpoint evidence is in entry 065; detailed IKE/IPsec platform/install evidence is in the strongSwan-family V2 dossier.
