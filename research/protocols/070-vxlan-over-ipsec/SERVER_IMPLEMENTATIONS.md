# VXLAN over IPsec — Server / Peer Implementations

Reviewed: 2026-08-15

VXLAN-over-IPsec is a composition rather than a standalone daemon: VXLAN VTEP evidence is in entry 069; IKE/IPsec/ESP implementations are in completed entries 004–007 and `research/upstreams/strongswan-family/reference-v2/`.

Principal open-source Linux reference: native VXLAN (`drivers/net/vxlan/vxlan_core.c`) plus Linux XFRM/IPsec and an established IKE implementation such as strongSwan. VXLAN provides L2 overlay/VNI framing; IPsec provides cryptographic security.

PVNetwork should reuse native VTEP/XFRM/IKE implementations behind platform adapters. Do not implement VXLAN framing or IPsec cryptography from scratch and do not attribute IPsec security properties to bare VXLAN.