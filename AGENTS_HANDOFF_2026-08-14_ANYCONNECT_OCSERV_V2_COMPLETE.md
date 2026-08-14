# PVNetwork Agent Handoff — AnyConnect / OpenConnect-ocserv V2 Complete

Date: 2026-08-14 UTC

## Completed

- 016 — Cisco AnyConnect: `COMPLETE-REFERENCE-v2`
- 017 — OpenConnect / ocserv-compatible: `COMPLETE-REFERENCE-v2`

V1 remains 93/93. V2 becomes **17/93**.

## Key evidence / boundaries

- Cisco Secure Client / AnyConnect remains proprietary; no Cisco source/build/test internals are invented.
- Current reviewed Cisco desktop/VPN-core baseline is 5.1.18.314; current official docs cover Windows/macOS/Linux and separate mobile streams.
- Cisco ASA 9.24 is the selected current authoritative headend configuration baseline; FTD remains a distinct Cisco headend family confirmed by current deployment documentation.
- Cisco webdeploy/predeploy, headend policy, client UI/diagnostics and TLS/DTLS/IKEv2 separation are mapped.
- OpenConnect v9.21 is pinned at `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1.
- ocserv 1.5.0 is pinned at signed canonical tag commit `49f9956eeeffd613e4bcac3f6450c682ec21e75a`, GPLv2+. Its release fixes security defects in worker cookie parsing and DTLS MTU negotiation; older builds are not silently blessed.
- ocserv is a public compatible server, not Cisco ASA/FTD source or proof of complete vendor equivalence.
- Runtime/server-version/SSO/posture/device/Store/interoperability tests remain later certification evidence, not hidden V2 gates.

## Exact continuation

Next unfinished V2 entry: **018 — Palo Alto GlobalProtect**.

Exact next action: map current PAN-OS GlobalProtect portal/gateway deployment/server UI and platform client matrices, preserve proprietary source/license boundaries, reuse OpenConnect GP mode only where directly traceable, close all exact 16 V2 gates, then continue 019 Fortinet FortiGate SSL VPN.
