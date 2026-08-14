# PVNetwork Agent Handoff — FortiGate SSL VPN V2 Complete

Date: 2026-08-14 UTC

Authoritative phase: `COMPLETE-REFERENCE-v2`; V1 remains 93/93.

## Completed in this checkpoint

- 019 — Fortinet FortiGate SSL VPN: `COMPLETE-REFERENCE-v2`

A full dedicated V2 dossier was built under `research/protocols/019-fortinet-ssl-vpn/reference-v2/` and reconciled against all exact 16 gates.

Key boundaries:

- FortiGate/FortiOS and FortiClient are proprietary; no public source hash/open-source license is fabricated.
- FortiOS 7.4.12 is selected as the maintained legacy tunnel-mode vendor reference at review time.
- Starting in FortiOS 7.6.3, SSL VPN tunnel mode is removed from GUI/CLI and related legacy settings do not upgrade automatically; Fortinet requires migration to IPsec VPN. Entry 019 is therefore version-bounded legacy compatibility, not a new-FortiOS default.
- FortiOS 7.6.3+ Agentless VPN is the renamed browser/web mode, not the same tunnel protocol.
- FortiClient 7.4.7 provides the current proprietary client documentation baseline used for platform/UI/install/lifecycle evidence; the free VPN-only agent has a separate 7.4.3 stream noted by Fortinet.
- OpenConnect v9.21 remains pinned at `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1. Fortinet mode is experimental/partial and PPP-based; the newer non-PPP wire protocol is not supported and reconnect/auth behavior is version-dependent.
- FortiGuard PSIRT/current supported upgrade path is an explicit security requirement; vendor image/package trust is kept separate from OpenConnect's public-source supply chain.
- no runtime/appliance/device/Store/interoperability receipt is fabricated or used as a hidden V2 gate.

## Exact continuation state

V2: **19/93**.

Next unfinished V2 entry: **020 — Pulse Secure**.

Exact next action: reconcile all 16 V2 gates for Pulse Secure. Preserve historic Pulse Secure versus current Ivanti naming/product evolution, avoid conflating entry 020 with entry 021 Ivanti Connect Secure, map current proprietary headend/client/admin/lifecycle evidence, reuse OpenConnect pulse mode only as a separately pinned compatible client implementation, then continue entry 021.
