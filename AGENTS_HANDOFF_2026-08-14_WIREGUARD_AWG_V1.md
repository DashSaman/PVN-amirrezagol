# AGENTS Handoff — 2026-08-14 — WireGuard / AmneziaWG v1 Closure

Mandatory continuation checkpoint for `DashSaman/PVN-amirrezagol`.

## State transition

Entries 002 WireGuard and 003 AmneziaWG plus their shared family are now:

**`V1-HANDOFF-READY / NOT IMPLEMENTED`**

This is a research milestone only. Original `COMPLETE-RESEARCH-v1` remains active across all 93 entries.

## Current WireGuard pins

- `WireGuard/wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0`
- `WireGuard/wireguard-windows@4e6726c23ae9c5cb58e0c9910f3b7515621d133d`
- `WireGuard/wireguard-android@e7b3a3c118836e112620b1302a8ba1873ad4daac`
- `WireGuard/wireguard-apple@2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`

GitHub WireGuard repositories are mirrors; canonical provenance is recorded in `SOURCE_REVISIONS.md`.

## Current AWG pins

- Go core `amnezia-vpn/amneziawg-go@1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`
- Android `d6cd6647465a9a593aa9ccadbbd20c44bf600d5b`
- Apple `e5410a539f28b8ce5dd1d060c45e4fa555e9a210`
- Windows client `c8fa887db05ade03b9281b0e9de60579f744f995`
- Windows tunnel/library `1326e9bbdc71be88ddcc20925e092c6f5b9513da`

Current Windows client pins `amneziawg-go/v3` and `amneziawg-windows/v3` to `v3.1.20260813`.

## New files/commits

- `WINDOWS_CLIENT.md` — `ec78389e3f01bda0b15a0fc59a99dfabd40f0297`
- `AMNEZIAWG_PLATFORMS.md` — `0f98191d68888954945aa309311fcd2e5e04e624`
- `DEPENDENCIES_SBOM.md` — `008acc4c451fa9ff8381683698d71fdfde722f61`
- expanded `SOURCE_REVISIONS.md` — `0be71d781460c2ba4fa4e9b95e71f4f0d4302470`
- expanded `LESSONS_AND_TESTS.md` — `67ccc33f4a03376aeb005dfef60e4ce0cf2b541a`
- `SUPPORT_REUSE_DECISIONS.md` — `af097fa45f055dbc705676b52761d0d1c8298ade`
- entry 002 sync — `b69199c05f36d07309bb109e41b853f30ebbe4be`
- entry 003 sync — `7cca9eec1026321baee9be6d97bc82d529a72994`
- family README v1 handoff — `2145b224b97f555afbf8076f24218cd12fb55472`
- status snapshot — `2af4454e395ba032b93ecfdf84983b1a4130dfbb`

## Important WireGuard findings

- official Windows source separates privileged manager/service from user UI via local IPC;
- official Windows saved tunnel configs use DPAPI-protected `.conf.dpapi`, while `.conf` remains import/export;
- Android/Apple lifecycle remains platform-owned even when a core/tunnel library is reused;
- official/platform implementations should be preferred per OS behind a product-owned adapter rather than forcing `wireguard-go` everywhere.

## Important AmneziaWG findings

- AWG is a WireGuard-derived compatibility/obfuscation family, not a reason for PVNetwork to invent cryptography;
- canonical profile needs explicit AWG generation/version and versioned extension fields;
- AWG3.1 current Windows source adds `RandomTrailers` and `DisableCookies`;
- current Go core head fixes a runtime panic on `HandshakeCookie` when random trailers are enabled;
- current Apple head fixes an excluded-route behavior capable of disturbing a Linux peer;
- AWG Go has a broader dependency/SBOM surface than reviewed WireGuard-Go.

## Residual gaps — preserve

- final production pins/advisory scans;
- AWG Android/Apple exact embedded-core graphs;
- exact file-level license for AWG Windows tunnel reusable path;
- complete UI/screenshots/accessibility;
- device/server/performance/Store evidence;
- server installers/menus/cryptography/wire-flow deferred to mandatory v2.

## Exact next action

1. Update machine state/checkpoint/Project State/AGENTS pointer to leave WireGuard closure.
2. Inspect actual `research/upstreams/` and `research/RESEARCH_COMPLETENESS.md` to identify the next high-value unfinished original-v1 family.
3. Continue that family immediately without asking the owner.
4. Do not start mass `COMPLETE-REFERENCE-v2` until original v1 gates across the campaign reach their intended state.
