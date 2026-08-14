# PVNetwork Agent Handoff — Pulse Secure V2 Complete

Date: 2026-08-14 UTC

Authoritative phase: `COMPLETE-REFERENCE-v2`; V1 remains 93/93.

## Completed

- 020 — Pulse Secure: `COMPLETE-REFERENCE-v2`

Dedicated V2 evidence is under `research/protocols/020-pulse-secure/reference-v2/` and reconciles all exact 16 gates.

Key boundaries:

- Pulse Connect Secure / Pulse Secure Client are historical product names; current maintained vendor lineage is Ivanti Connect Secure / Ivanti Secure Access Client.
- Entry 020 preserves the historic Pulse compatibility identity; entry 021 remains the current Ivanti Connect Secure entry and must be separately reconciled rather than merged.
- vendor gateway/client are proprietary; no public source hash/license was invented.
- current vendor anchors include ICS 22.8R2.3 build 18655 and ISAC 22.8R7 build 48847 / current mobile 22.8.7 matrices.
- OpenConnect Pulse mode is a separate LGPL-2.1 compatible implementation, pinned via the shared enterprise baseline to v9.21 / `8b702bf2dbaf11302ed98629214b1df5d50a12aa`; it is experimental, lacks some Pulse authentication modes and does not implement Pulse Host Checker/TNCC.
- Pulse and older Juniper Network Connect are different protocols even where gateways support both.
- no appliance/device/Store/interoperability receipt is fabricated or used as a hidden V2 gate.

## Exact continuation state

V2: **20/93**.

Next unfinished V2 entry: **021 — Ivanti Connect Secure**.

Exact next action: reconcile all 16 V2 gates for current Ivanti Connect Secure. Reuse the current Ivanti vendor evidence from entry 020 only where it directly applies, but make entry 021 a current-product dossier: current ICS server releases/platforms/admin UI/security/upgrade lifecycle, current ISAC desktop/mobile clients, and the separately pinned OpenConnect Pulse/NC compatibility boundaries. Then continue entry 022 Juniper Network Connect.