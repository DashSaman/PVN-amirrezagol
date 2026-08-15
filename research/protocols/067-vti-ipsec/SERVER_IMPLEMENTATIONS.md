# VTI/IPsec — Server / Peer Implementations

Reviewed: 2026-08-15

VTI/IPsec is a Linux route-based IPsec interface model rather than a distinct cryptographic protocol or server daemon.

- Linux kernel VTI is pinned at `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `net/ipv4/ip_vti.c`, SPDX GPL-2.0-or-later. The source describes a virtual tunnel interface integrated with XFRM policy/input handling.
- iproute2 pinned at `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78` exposes `vti`/`vti6` tunnel types.
- strongSwan is the principal IKE/IPsec reference; its current route-based VPN documentation states VTI devices act as wrappers around existing IPsec policies and use marks to bind routed traffic to matching policies/SAs.

VTI is a local interface decision; it adds no GRE-like extra encapsulation. The peer need not use a VTI as long as the negotiated IPsec policies are interoperable.

PVNetwork decision: Linux infrastructure capability; reuse native kernel/XFRM and reviewed IKE stack. Prefer newer XFRM interfaces where their documented advantages fit (entry 068), but keep VTI support distinct for existing environments.
