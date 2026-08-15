# GRE over IPsec — Reference Index

Reviewed: 2026-08-15

## Dossier files

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

## Reused authoritative shared evidence

- Bare GRE: `research/protocols/063-gre/` (RFC 2784/2890; Linux/iproute2 pins).
- IKE/IPsec/ESP: `research/upstreams/strongswan-family/reference-v2/` and completed entries 004–007.
- strongSwan route-based GRE/IPsec guidance: https://docs.strongswan.org/docs/latest/features/routeBasedVpn.html
- strongSwan ESP/IPsec mode guidance: https://docs.strongswan.org/docs/latest/howtos/ipsecProtocol.html
- Cisco GRE-over-IPsec guide (updated 2025-09-15): https://www.cisco.com/c/en/us/td/docs/switches/lan/c9000/lyr3-fwd/gre/gre-configuration-guide/m-gre-over-ipsec.html

## Key distinctions

- GRE supplies encapsulation/routing flexibility; IPsec supplies cryptographic protection.
- A GRE key is not an IKE/IPsec secret.
- GRE protocol 47 is not a TCP/UDP port; ESP/NAT-T may change the outer wire format.
- GRE-over-IPsec is not DMVPN and does not inherently imply NHRP/multipoint behavior.
- Consumer platform support is not inferred from standalone IKE/IPsec capability.

## Exact next action after completion

Continue entry 065 — IP-in-IP / IPIP, keeping bare IPIP separate from IPIP-over-IPsec entry 066.
