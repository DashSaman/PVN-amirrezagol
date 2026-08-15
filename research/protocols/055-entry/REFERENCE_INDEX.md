# 055 — Tor SOCKS — Reference Index

Current state: **COMPLETE-REFERENCE-v2** (research/reference only)

## Dossier

- `V1_GATE_RECONCILIATION.md` — exact 20-gate V1 audit
- `REFERENCE_V2_AUDIT.md` — exact 16-gate V2 audit

## Canonical specifications / projects

- Tor SOCKS extensions: https://spec.torproject.org/socks-extensions.html
- Arti source: https://gitlab.torproject.org/tpo/core/arti.git
- C Tor source: https://gitlab.torproject.org/tpo/core/tor

## Current reviewed pins

- Arti `2.5.0` (2026-06-30)
- `arti-client 0.44.0`, MIT OR Apache-2.0
- C Tor stable line `0.4.9.x`; V2 current reviewed release **0.4.9.11** from Tor Project release/archive evidence, superseding the older V1 point-in-time 0.4.9.8 note
- C Tor core: 3-clause BSD-style license boundary; optional feature/dependency licenses remain separately owned

## Critical boundaries

- Tor SOCKS is not ordinary SOCKS5.
- BIND and UDP ASSOCIATE are unsupported; GSSAPI is unsupported.
- Tor-specific `RESOLVE`/`RESOLVE_PTR` and isolation metadata must be preserved.
- SOCKS username/password extension fields can encode isolation/RPC metadata rather than ordinary login credentials.
- local DNS for Tor-routed hostnames can leak destinations; remote resolution is the safe product model.
- Tor Browser privacy hardening is not implied for arbitrary applications routed through Tor.
- SOCKS framing and Tor circuit/onion cryptography are separate layers.

## Continuation

Next V2 entry: **056 — Tailscale**.
