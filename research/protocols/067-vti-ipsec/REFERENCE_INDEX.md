# VTI/IPsec — Reference Index

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

- Linux kernel VTI: `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `net/ipv4/ip_vti.c`, SPDX GPL-2.0-or-later.
- iproute2: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, VTI/VTI6 tunnel support.
- strongSwan current route-based VPN documentation: https://docs.strongswan.org/docs/latest/features/routeBasedVpn.html
- IKE/IPsec/ESP shared evidence: entries 004–007 and `research/upstreams/strongswan-family/reference-v2/`.

## Key boundaries

- VTI is a Linux route-based local interface abstraction around existing IPsec policies/SAs, not a new cryptographic/wire protocol.
- VTI marks are policy selectors, not crypto keys.
- VTI requires endpoint addresses and has limitations versus newer XFRM interfaces.
- Interface-up does not prove an IPsec SA is installed/operational.
- Non-Linux consumer IPsec capability does not equal VTI support.

## Exact next action after completion

Continue entry **068 — XFRM/IPsec**, documenting XFRM interface IDs, no-endpoint local abstraction, IPv4/IPv6/mode flexibility, route/SAs/policy linkage and differences from VTI.
