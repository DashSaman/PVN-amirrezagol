# AGENTS Handoff — 2026-08-14 — PPTP v2 slice 1

Work unit: `PPTP-COMPLETE-REFERENCE-V2`

Entry: 012 PPTP

## State

`REFERENCE-V2-EVIDENCE-COMPLETE / HISTORICAL-LINUX-SOURCE-PIN-RESIDUAL / OBSOLETE-RUNTIME-EXECUTION-BLOCKED / NOT IMPLEMENTED`

Strict tracker remains PENDING. PPTP is legacy compatibility only and must not be recommended for new deployment.

## Dossier

Folder:

`research/upstreams/classic-tunnels-family/pptp-reference-v2/`

All 11 mandatory files plus:

`ENTRY_012_V2_GATE_RECONCILIATION.md`

are committed and the `REFERENCE_INDEX.md` is synchronized.

## Core rules

- TCP1723 = PPTP control.
- GRE IP protocol47 = user data; there is no TCP/UDP port47.
- PPP auth/MPPE are separate from PPTP transport.
- MPPE is legacy RC4-era PPP encryption and does not make PPTP a modern secure VPN.
- no silent fallback/downgrade to PPTP.
- NAT/PPTP ALG/helper is interoperability, not security.
- Windows/RouterOS support is exact legacy compatibility only.
- Apple native PPTP is removed; Android is legacy/device-specific.
- historical Linux pptpd/pptp-client pins are not fabricated and only become source-freeze requirements if a future isolated lab actually selects them.
- every retained profile/server needs migration/retirement state.

## Strict blockers

- exact Windows/RouterOS runtime combinations;
- exact historical Linux source pins only if selected;
- TCP1723/GRE captures and Call-ID mapping;
- PPP auth/MPPE negatives;
- GRE-blocked/control-only case;
- NAT/ALG multi-client/CGNAT/cloud path if business-critical;
- route/DNS/MTU/lifecycle;
- migration cutover and final listener/firewall/helper removal.

## Next task

`SOFTETHER-PROTOCOL-COMPLETE-REFERENCE-V2`

Entry 013 SoftEther VPN Protocol.

Important: study the native SoftEther VPN Protocol separately from SoftEther VPN Server's compatibility features (SSTP, L2TP/IPsec, OpenVPN, EtherIP, etc.). Reuse existing SoftEther source pin, source architecture and UI evidence, then create all 11 mandatory v2 files, document native protocol crypto/data path/ports/handshake/server/client install+UI, reconcile gates, checkpoint and continue.
