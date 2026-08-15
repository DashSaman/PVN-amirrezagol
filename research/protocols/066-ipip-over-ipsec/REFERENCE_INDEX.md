# IPIP over IPsec — Reference Index

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

## Reused evidence

- Bare IPIP: `research/protocols/065-ipip/` — RFC 2003, Linux `ipip.c`, iproute2 pins.
- IKE/IPsec/ESP: entries 004–007 and `research/upstreams/strongswan-family/reference-v2/` — server/client/install/UI/crypto/wire/ports/topology/source/license/supply-chain/lifecycle.
- IKEv2: RFC 7296; ESP: RFC 4303.

## Key boundaries

- IPIP protocol 4 is encapsulation, not a port or security layer.
- IPsec/IKE supplies all cryptographic protection and authentication.
- Interface state and IPsec SA state are separate.
- Native ESP protocol 50 and NAT-T UDP 4500 are IPsec behavior, not IPIP ports.
- Generic consumer-platform support is not inferred.
- VTI/IPsec (067) and XFRM/IPsec (068) have different interface/policy abstractions.

## Exact next action after completion

Continue entry **067 — VTI/IPsec**. Reuse the existing strongSwan/Linux XFRM evidence, but document VTI's route-based interface/mark semantics independently from IPIP-over-IPsec and XFRM interfaces.
