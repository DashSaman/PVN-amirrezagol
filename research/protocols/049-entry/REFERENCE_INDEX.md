# 049 SOCKS4 — Reference index

Review: 2026-08-15

State: **COMPLETE-REFERENCE-v2 candidate pending tracker promotion**.

## Files

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
- `V1_GATE_RECONCILIATION.md`
- shared source evidence: `research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`

## Pins

- curl/curl `d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`, tree `39bb285e8839dc38e3406812ecabe29723fe5063` — permissive curl license.
- openssh/openssh-portable `528055671c26962093a871bff8241a48d42dd9a0`, tree `377ab7f76a7ce3751aae83e48daaad172c46d9ec` — BSD-family/component licensing.
- 3proxy/3proxy `4fb5c957046c6011b5a0b45f48c1b854daf70bca`, tree `b12b0c1a80ae44158d78c44810e387f1092f676a` — current 3proxy license boundary documented in shared evidence.

## Key boundaries

SOCKS4 is legacy, TCP CONNECT/IPv4-oriented, locally resolved in ordinary curl SOCKS4 mode, and unencrypted. SOCKS4a and SOCKS5 are separate entries. Server/client UI and orchestration concepts that do not exist in the protocol are explicitly N/A rather than fabricated.