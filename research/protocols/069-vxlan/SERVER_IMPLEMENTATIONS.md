# VXLAN — Server / Peer Implementations

Reviewed: 2026-08-15

VXLAN is a Layer-2 overlay over a Layer-3 UDP underlay. The endpoint role is VTEP, not a client/server daemon.

Canonical specification: RFC 7348. Principal open-source implementation: Linux kernel VXLAN, pinned at `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `drivers/net/vxlan/vxlan_core.c`, SPDX GPL-2.0-only. Current Linux kernel documentation explicitly describes both the kernel tunnel device and a separate Open vSwitch implementation.

Linux VTEPs support dynamic endpoint learning similar to a bridge and static forwarding entries. iproute2 is the management layer. Bare VXLAN is infrastructure encapsulation, not a secure VPN and not a consumer login service.

PVNetwork decision: reuse native Linux/network-platform VXLAN; do not implement VXLAN framing from scratch. Keep VXLAN-over-IPsec as entry 070.