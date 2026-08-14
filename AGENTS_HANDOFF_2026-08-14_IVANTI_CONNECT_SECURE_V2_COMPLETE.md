# PVNetwork Agent Handoff — Ivanti Connect Secure V2 Complete

Date: 2026-08-14 UTC

Authoritative phase: `COMPLETE-REFERENCE-v2`; V1 remains 93/93.

## Completed

- 021 — Ivanti Connect Secure: `COMPLETE-REFERENCE-v2`

A dedicated current-product V2 dossier lives under `research/protocols/021-ivanti-connect-secure/reference-v2/` and reconciles all exact 16 gates.

Key boundaries:

- ICS is proprietary; current server activity anchor is **25.1.2.1 build 15773**. No public source/license is invented.
- ISAC Desktop current baseline is **22.8R7 build 48847** with current Windows/macOS/Linux platform evidence; reviewed mobile baseline is ISAC Mobile 22.8.7 for iOS/Android/ChromeOS.
- the published ISAC 22.8R7 server-compatibility table qualifies ICS 25.x through **25.1.1.1**. The newer **25.1.2.1 × 22.8R7** pair is deliberately **not** marked vendor-qualified without evidence.
- current 25.1.2.1 lifecycle evidence includes an ISA6500 tested upgrade path and documented configuration migrations from 25.1.0.1, 25.1.1.0, 22.8R2.3 and 22.7R2.12.
- Pulse-compatible IF-T/TLS/EAP/ESP remains distinct from older Juniper Network Connect/oNCP and from ICS's separate IKEv2 capability.
- OpenConnect v9.21 remains pinned at `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1; Pulse mode is capability-gated and does not implement full proprietary Host Checker/TNCC/auth behavior.
- no runtime/appliance/device/Store/interoperability receipt is fabricated or used as a hidden V2 gate.

## Exact continuation

V2: **21/93**.

Next unfinished V2 entry: **022 — Juniper Network Connect**.

Exact next action: reconcile all exact 16 V2 gates for legacy Juniper Network Connect/oNCP. Preserve its retired/proprietary Juniper client lineage and distinction from Pulse; reuse OpenConnect `--protocol=nc` only as a separately pinned compatible implementation; map maintained current ICS legacy compatibility only where official evidence exists, use evidence-backed N/A for retired standalone client/server packaging where applicable, then continue entry 023 F5 BIG-IP SSL VPN.
