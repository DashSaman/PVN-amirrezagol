# XFRM/IPsec — Server / Peer Implementations

Reviewed: 2026-08-15

XFRM interfaces are a Linux route-based interface abstraction for IPsec, not a separate cryptographic protocol/server.

- Linux kernel XFRM virtual interface implementation: `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `net/xfrm/xfrm_interface_core.c`, SPDX GPL-2.0.
- iproute2 provides `type xfrm` interface management; current strongSwan docs state XFRM interfaces require Linux kernel 4.19+ and iproute2 5.1.0+.
- strongSwan supports XFRM interfaces since 5.8.0 and documents interface IDs linking policies/SAs to interfaces.

Compared with VTI, XFRM interfaces require no tunnel endpoint addresses, may carry IPv4 and IPv6 SAs on the same interface, support IPsec modes beyond tunnel mode, and use explicit interface IDs instead of VTI mark/key mechanics.

PVNetwork decision: preferred Linux route-based IPsec abstraction for new designs where platform version supports it; reuse native XFRM and reviewed IKE/IPsec stack.
