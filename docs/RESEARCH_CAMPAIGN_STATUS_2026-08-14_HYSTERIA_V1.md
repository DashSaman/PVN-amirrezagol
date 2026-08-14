# PVNetwork Research Campaign Status — 2026-08-14 — Hysteria v1 Closure

Shared Hysteria family entries 042–043: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

## Evidence created

Shared:

- `research/upstreams/hysteria-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/hysteria-family/DEPENDENCIES_SECURITY_TESTS.md`
- `research/upstreams/hysteria-family/SUPPORT_REUSE_DECISIONS.md`

Numbered:

- `research/protocols/042-entry/V1_RESEARCH.md`
- `research/protocols/043-entry/V1_RESEARCH.md`

## Decisions

### 042 Hysteria v1

`LEGACY COMPATIBILITY TARGET / DO NOT INFER FROM HYSTERIA2`

Current Hysteria2 source is not accepted as proof of v1 compatibility. Exact legacy source/version must be pinned if legacy support is implemented.

### 043 Hysteria2

`HIGH-PRIORITY MODERN QUIC PROXY TARGET / UPSTREAM ENGINE CANDIDATE`

Primary upstream: `apernet/hysteria`, root license reviewed MIT.

PVNetwork retains ownership of canonical profile, secure auth storage, TLS trust, TUN/routing/DNS, platform service lifecycle, UI and packaging.

## Residual gaps

- exact selected current Hysteria2 release/commit and resolved SBOM;
- exact public library/API vs subprocess boundary;
- current issue/release matrix;
- legacy v1 exact source;
- full client GUI menus;
- device/performance/Store proof;
- server installers/menus/cryptography/wire flow deferred to mandatory v2.

## Next exact action

Continue original v1 immediately. Selected next high-value family: **IKE/IPsec** (entries 004–008 and related vendor compatibility) because it covers several core VPN entries and needs a strongSwan/native-OS decision. Inspect actual existing strongSwan/IPsec research first; do not restart from scratch.
