# PVNetwork Research Campaign Status — 2026-08-14 — Modern Proxy / Tunnel v1 Closure

Repository phase: research / requirements / architecture.

Entries 044–055: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

## Shared evidence

Under `research/upstreams/modern-proxy-family/`:

- `SOURCE_REUSE_MATRIX.md`
- `SUPPORT_REUSE_DECISIONS.md`

## Numbered evidence

Each of entries 044–055 now has a separate `V1_RESEARCH.md` decision file.

## Decisions

- 044 TUIC — modern QUIC proxy; direct upstream vs existing approved core benchmark required.
- 045 AnyTLS — modern TLS-based proxy; prefer existing approved core where parity exists.
- 046 ShadowTLS — wrapper/composition model required.
- 047 NaiveProxy — web-proxy target; Chromium/network-stack dependency cost must be justified.
- 048 Snell — proprietary/externally specified compatibility; legally compatible implementation required.
- 049 SOCKS4 — foundational capability, no dedicated engine.
- 050 SOCKS4a — foundational SOCKS variant, no dedicated engine.
- 051 SOCKS5 — core product proxy capability through existing core/library.
- 052 HTTP Proxy — mature HTTP stack, no dedicated VPN engine.
- 053 HTTPS / HTTP CONNECT — mature HTTP+TLS stack.
- 054 SSH Tunnel — use mature OpenSSH/native implementation; never reimplement SSH crypto.
- 055 Tor SOCKS — optional privacy overlay via Arti/Tor daemon; not a normal one-hop VPN.

## Engine minimization rule

049–053 should normally be capabilities inside approved networking cores/libraries, not separate engines. 044–047 must be compared against already approved modern multi-protocol cores before direct engine additions.

## Security/storage rules

Reusable proxy/SSH/Tor secrets belong in platform secure storage references. Chained/wrapper profiles should use a typed connection graph rather than arbitrary command strings.

## Residual gaps

- exact immutable upstream pins/license/SBOM tables for 044–047/054/055;
- authoritative Snell implementation/version landscape;
- exact standards/engine behavior matrix for SOCKS/HTTP;
- current issues/advisories/performance evidence;
- full client menus/platform packaging.

Server/install/crypto/wire/menu/reference expansion remains mandatory later v2.

## Next exact action

Continue original v1 immediately. Selected next high-value unfinished group: **Mesh / Overlay entries 056–062** (Tailscale, ZeroTier, NetBird, Netmaker, Nebula, Tinc, innernet according to current protocol matrix). Keep protocol/data-plane engines separate from control-plane/account/orchestration systems. Do not begin mass v2 yet.
