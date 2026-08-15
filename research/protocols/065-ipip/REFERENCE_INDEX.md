# IPIP — Reference Index

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

## Canonical specification

- RFC 2003 — IP Encapsulation within IP: https://www.rfc-editor.org/rfc/rfc2003.html
- RFC 2003 is updated by RFC 3168 and RFC 6864 for broader IP header behavior; this dossier does not invent new IPIP handshake/security semantics from those updates.

## Pinned implementation references

- Linux kernel `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `net/ipv4/ipip.c`, SPDX GPL-2.0-or-later.
- iproute2 `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, `man/man8/ip-tunnel.8`, with `mode ipip` and endpoint/PMTU configuration.

## Key boundaries

- IPv4-in-IPv4 uses IP protocol 4; there is no TCP/UDP port.
- Bare IPIP has no encryption/authentication/handshake.
- No canonical consumer UI, daemon or installer exists.
- Generic mobile/Windows/macOS product support is not inferred.
- IPIP-over-IPsec is entry 066 and is a separate security composition.

## Exact next action after completion

Continue entry 066 — IPIP over IPsec, reusing this bare-IPIP evidence plus the already-completed IPsec/IKE reference layer while keeping crypto attribution separate.
