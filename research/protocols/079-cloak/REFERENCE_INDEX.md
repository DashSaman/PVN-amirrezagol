# Cloak — COMPLETE-REFERENCE-v2 index

Research date: 2026-08-15

Classification: **GPL-3.0 pluggable transport / camouflage wrapper around an underlying TCP/UDP proxy**, not a standalone VPN/proxy security protocol.

## Canonical pins

- repository: `cbeuw/Cloak`
- current reviewed stable release: `v2.12.0`
- tag commit: `c3d5470ef76bba68d7812f5d06e4181dc1b1a5d6`
- release published: 2025-07-23
- license: GPL-3.0
- primary language: Go
- repository is public/non-archived; GitHub metadata showed source pushes through 2026-05-29 while latest release remained v2.12.0.

## Existing evidence reused

- `research/protocols/079-entry/V1_GATE_RECONCILIATION.md`
- canonical tree inventory at `bb1eda880af94d7a3f09a78f724cd13aaec29a55`
- upstream examples `example_config/ckclient.json` and `ckserver.json`
- canonical client/server source under `cmd/ck-client`, `cmd/ck-server`, `internal/client`, `internal/server`, `internal/common`.

## Primary references

- `https://github.com/cbeuw/Cloak`
- `https://github.com/cbeuw/Cloak/releases/tag/v2.12.0`
- `https://github.com/cbeuw/Cloak/tree/c3d5470ef76bba68d7812f5d06e4181dc1b1a5d6`

Boundary: Cloak must be modeled as an optional pluggable transport attached to an underlying supported proxy such as OpenVPN/Shadowsocks/Tor. Cloak's own camouflage encryption is not a substitute for authenticated transport security; upstream anti-detection claims are design claims, not PVNetwork certification. GPL obligations materially constrain bundling/reuse.
