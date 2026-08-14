# PVNetwork Research Campaign Status — 2026-08-14 — WireGuard / AmneziaWG v1 Closure

Repository phase: research / requirements / architecture.

Shared family state: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

This is an original-research handoff milestone, not product implementation/certification.

## New closure evidence

### Windows official client/source

`research/upstreams/wireguard-family/WINDOWS_CLIENT.md`

Commit: `ec78389e3f01bda0b15a0fc59a99dfabd40f0297`

Source-level evidence now covers manager/service/UI/IPC architecture, DPAPI-protected `.conf.dpapi` persistence, standard `.conf` import/export, Tunnels/Log/Update pages and tray/tunnel actions.

### AmneziaWG current platform map

`research/upstreams/wireguard-family/AMNEZIAWG_PLATFORMS.md`

Commit: `0f98191d68888954945aa309311fcd2e5e04e624`

Current platform pins include Android, Apple, Windows client, Windows tunnel/library and portable Go core. AWG3.1-era Windows source adds `RandomTrailers` and `DisableCookies`.

### Dependency / SBOM matrix

`research/upstreams/wireguard-family/DEPENDENCIES_SBOM.md`

Commit: `008acc4c451fa9ff8381683698d71fdfde722f61`

Key conclusion: AmneziaWG Go has a broader dependency surface than reviewed WireGuard-Go; license/security review must be per component/platform/exact binary, not one root license label.

### Source revisions expanded

`research/upstreams/wireguard-family/SOURCE_REVISIONS.md`

Commit: `0be71d781460c2ba4fa4e9b95e71f4f0d4302470`

Now records current AWG Android/Apple/Windows/AWG3.1 relationships and separate platform licenses/provenance.

### Regression lessons updated

`research/upstreams/wireguard-family/LESSONS_AND_TESTS.md`

Commit: `67ccc33f4a03376aeb005dfef60e4ce0cf2b541a`

Added current AmneziaWG Apple route regression class and AWG3.1 `RandomTrailers` HandshakeCookie panic class plus required tests.

### Support/reuse decisions

`research/upstreams/wireguard-family/SUPPORT_REUSE_DECISIONS.md`

Commit: `af097fa45f055dbc705676b52761d0d1c8298ade`

- 002 WireGuard: `HIGH-PRIORITY CORE VPN TARGET / OFFICIAL-STACK-FIRST`
- 003 AmneziaWG: `HIGH-VALUE WIREGUARD-DERIVATIVE COMPATIBILITY TARGET / VERSIONED EXTENSION REQUIRED`

### Numbered entries synchronized

- 002 WireGuard — `b69199c05f36d07309bb109e41b853f30ebbe4be`
- 003 AmneziaWG — `7cca9eec1026321baee9be6d97bc82d529a72994`

Both now `V1-HANDOFF-READY / NOT IMPLEMENTED`.

### Shared README synchronized

`research/upstreams/wireguard-family/README.md`

Commit: `2145b224b97f555afbf8076f24218cd12fb55472`

## Current AWG3.1 version relationship

Current Windows client pin `c8fa887...` uses:

- `amneziawg-go/v3 v3.1.20260813`
- `amneziawg-windows/v3 v3.1.20260813`

Current portable Go head `1b86b2...` fixes a panic in AWG3.1 random-trailer handling on a HandshakeCookie path.

## Residual gaps preserved

- final production pins and full advisory scans;
- exact AWG Android/Apple embedded-core dependency graph;
- exact file-level license confirmation for `amneziawg-windows` reusable library path;
- exhaustive current UI/screenshots/accessibility;
- real-device/server interoperability/performance/power/Store evidence;
- exact driver/native artifact signing/provenance;
- server installers/menus/cryptography/wire-flow deferred to mandatory `COMPLETE-REFERENCE-v2`.

## Next exact action

Select the next unfinished original-v1 family from actual repository state and continue immediately without owner prompting. Do not begin mass v2 expansion yet.
