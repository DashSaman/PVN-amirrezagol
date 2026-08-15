# GRE — Reference Index

State target: `COMPLETE-REFERENCE-v2` after audit promotion.
Reviewed: 2026-08-15.

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

## Canonical specifications

- RFC 2784 — Generic Routing Encapsulation: https://www.rfc-editor.org/rfc/rfc2784.html
- RFC 2890 — Key and Sequence Number Extensions: https://www.rfc-editor.org/rfc/rfc2890.html

## Pinned open-source implementation references

- Linux kernel: `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`; GRE implementation `net/ipv4/ip_gre.c`; SPDX GPL-2.0-or-later.
- iproute2: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`; `man/man8/ip-tunnel.8`; Linux tunnel configuration reference. iproute2 is GPL-2.0-or-later with repository-specific license notices; code reuse is not required for PVNetwork's platform-adapter approach.

## Vendor interoperability references

- Cisco IOS XE 17.x tunnel configuration guide.
- Juniper Junos GRE/tunnel-services documentation.

## Key boundaries

- GRE has no intrinsic encryption/authentication.
- GRE key != cryptographic key.
- GRE checksum != cryptographic MAC.
- No TCP/UDP port; GRE over IPv4 is IP protocol 47.
- Generic bare GRE is not GRE-over-IPsec (064), DMVPN (071), or PPTP.
- Consumer UI/server-panel concepts are N/A unless supplied by a particular platform/product.

## Next action after promotion

Continue entry 064 — GRE over IPsec and add the IPsec/IKE security/data-path layers without retroactively attributing them to bare GRE.
