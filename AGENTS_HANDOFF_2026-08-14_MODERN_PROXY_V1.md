# AGENTS Handoff — 2026-08-14 — Modern Proxy / Tunnel v1 Closure

Mandatory continuation checkpoint.

## State transition

Entries 044–055 are now:

**`V1-HANDOFF-READY / NOT IMPLEMENTED`**

Research only; no product support/certification exists.

## Shared evidence

`research/upstreams/modern-proxy-family/`

- `SOURCE_REUSE_MATRIX.md`
- `SUPPORT_REUSE_DECISIONS.md`

## Numbered entries

Each of 044–055 now has `V1_RESEARCH.md` with an independent decision.

## Key decisions

- TUIC / AnyTLS / ShadowTLS / NaiveProxy: compare direct upstream with already-approved multi-protocol cores before adding engines.
- Snell: do not assume original source/server is open; legally compatible implementation required.
- SOCKS4/4a/5 and HTTP/CONNECT: foundational capabilities, not separate engines.
- SSH: use mature OpenSSH/native implementation; no custom SSH cryptography.
- Tor SOCKS: use Arti/Tor daemon; Tor is a privacy overlay, not normal one-hop VPN.

## Product architecture rule

Chained/wrapper profiles must use a typed connection graph with secure credential references, not arbitrary engine command strings or opaque URLs as authoritative storage.

## Residual gaps

Exact upstream pins/licenses/SBOM for several direct implementations, Snell authoritative landscape, standards/engine matrices, current issues/performance and full menus/platform packaging remain explicit. Mandatory v2 later adds server/install/crypto/wire/full menu evidence.

## Exact next action

1. Activate original-v1 Mesh/Overlay group entries 056–062.
2. Read current mesh shared evidence and actual numbered folders first.
3. Separate data plane from control plane/account/orchestration.
4. Compare Tailscale, ZeroTier, NetBird, Netmaker, Nebula, Tinc and innernet source/license/platform/client roles.
5. Record whether PVNetwork should embed, interoperate with, launch, or merely reference each ecosystem.
6. Synchronize each numbered entry and checkpoint.
7. Immediately continue the next unfinished original-v1 family without owner prompting.
8. Do not start mass COMPLETE-REFERENCE-v2 yet.
