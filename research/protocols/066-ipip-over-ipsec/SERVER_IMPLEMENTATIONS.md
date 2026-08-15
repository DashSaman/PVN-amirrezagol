# IPIP over IPsec — Server / Peer Implementations

Reviewed: 2026-08-15

IPIP-over-IPsec is a composition, not a standalone standardized daemon: bare IPIP endpoint behavior is documented in entry 065; IKE/IPsec/ESP implementations and licensing are already mapped in entries 004–007 and `research/upstreams/strongswan-family/reference-v2/`.

Principal open-source reference: Linux kernel IPIP + Linux XFRM/IPsec with an IKE implementation such as strongSwan. Linux IPIP is pinned at `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6` (`net/ipv4/ipip.c`, GPL-2.0-or-later); configuration uses iproute2 `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`.

PVNetwork should reuse OS-native tunneling/XFRM plus a reviewed IKE implementation behind platform adapters. IPIP supplies encapsulation; IPsec supplies cryptographic protection. Do not implement either cryptographic layer from scratch.
