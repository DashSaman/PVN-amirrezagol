# XFRM/IPsec — Reference Index

Reviewed: 2026-08-15

## Entry-specific files

- `SERVER_IMPLEMENTATIONS.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `CRYPTOGRAPHY.md`
- `DATA_PATH_AND_WIRE_FLOW.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `DEPLOYMENT_TOPOLOGIES.md`
- `REFERENCE_V2_AUDIT.md`
- existing `V1_RESEARCH.md`

## Pinned / authoritative evidence

- Linux kernel `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `net/xfrm/xfrm_interface_core.c`, SPDX GPL-2.0.
- iproute2 `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, current XFRM link support.
- strongSwan route-based VPN: https://docs.strongswan.org/docs/latest/features/routeBasedVpn.html
- Linux XFRM-device docs: https://docs.kernel.org/networking/xfrm/xfrm_device.html
- shared IKE/IPsec evidence: entries 004–007 and `research/upstreams/strongswan-family/reference-v2/`.

## Key distinctions

XFRM interfaces use interface IDs and require no tunnel endpoint addresses; support IPv4/IPv6 and multiple IPsec modes; are local to the Linux peer and add no extra wire header. They are different from VTI marks/endpoints and from hardware XFRM offload.

## Exact next action after completion

Continue **069 — VXLAN**, documenting RFC 7348 / Linux VXLAN implementation, UDP encapsulation, VNI, learning/FDB/multicast/unicast control boundaries and bare VXLAN's lack of intrinsic cryptographic security. Keep VXLAN-over-IPsec as entry 070.
