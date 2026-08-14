# PVNetwork Agent Handoff — Juniper Network Connect V2 Complete

Date: 2026-08-14 UTC

## Completed

- 022 — Juniper Network Connect / oNCP: `COMPLETE-REFERENCE-v2`

V1 remains 93/93. V2 becomes **22/93**.

Key boundaries:

- proprietary Network Connect client is retired: Ivanti documents Windows client unsupported from ICS 9.1R2 onward and macOS EOL from 8.3R1 onward.
- current ICS VPN Tunneling is product lineage (“formerly called Network Connect”), not evidence that ICS 25.1.x still exposes the old NC/oNCP wire protocol. No such current support claim is fabricated.
- OpenConnect v9.21 `--protocol=nc` is the selected maintained compatibility engine, pinned at `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1.
- OpenConnect NC uses HTTPS/browser-like authentication, session cookie, HTTPS/oNCP data with optional ESP acceleration, lacks IPv6, and can require TNCC/external browser/helper behavior on customized gateways.
- NC remains distinct from Pulse IF-T/TLS and from ICS IKEv2.
- reuse decision is legacy compatibility/migration only; no new NC server deployment is recommended.

## Exact continuation

Next unfinished V2 entry: **023 — F5 BIG-IP SSL VPN**.

Exact next action: reconcile all exact 16 V2 gates for F5 BIG-IP SSL VPN/APM. Preserve proprietary BIG-IP/APM server and BIG-IP Edge Client boundaries, map current F5OS/TMOS/APM release/deployment/admin/client lifecycle evidence, reuse OpenConnect `--protocol=f5` only as a separately pinned PPP-compatible client implementation, then continue entry 024 Array Networks SSL VPN.
