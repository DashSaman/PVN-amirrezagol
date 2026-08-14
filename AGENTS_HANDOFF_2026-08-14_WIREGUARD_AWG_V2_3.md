# AGENTS Handoff — 2026-08-14 — WireGuard / AmneziaWG v2 slice 3

Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`  
Entries: 002 WireGuard, 003 AmneziaWG  
State: **IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED**

## Durable evidence added this slice

### Server management / supply chain

`research/upstreams/wireguard-family/reference-v2/SERVER_INSTALLERS_AND_PROJECTS.md` now records:

- wg-easy v15 example container privilege boundary: `NET_ADMIN`, `SYS_MODULE`, `/lib/modules:ro`, forwarding sysctls and separate UDP 51820 / TCP 51821 exposure;
- management-plane security rule: panel exposure is not equivalent to WireGuard transport exposure; bind/proxy/authenticate management separately;
- v14 `PASSWORD_HASH` guidance is not silently inherited into v15; v15 authentication/bootstrap needs version-specific source audit;
- immutable image digest + rollback receipt remain required even when an example uses the major-version `:15` tag;
- third-party AWG installer remains orchestration/reference, not canonical engine.

### AWG generation interoperability

The same file now preserves explicit unresolved generation boundaries:

- historical tools issue #31 as regression evidence for 1.5-era configuration parsing;
- AWG 2.0 S3/S4 compatibility risk linked to upstream kernel issue #168;
- open issue #191 (2026-07-24) makes raw-tools 1.x -> 2.0 upgrade/parallel-interface behavior UNVERIFIED until upstream resolution or execution receipts;
- Xray AWG 1.5/2.0 feature request #6200 closed as not planned, so Xray is not assumed to bridge AWG generations;
- six-row execution matrix added, including explicit negative and mixed-generation tests.

### Apple import/export lifecycle

`research/upstreams/wireguard-family/APPLE_CLIENT.md` now records:

- official WireGuard Apple app/NetworkExtension/keychain architecture remains pinned reference;
- standalone AmneziaWG Apple `.conf` open/share and QR import are distinguished from the main Amnezia client;
- a current feature request documents absence of the proposed standalone `amneziawg://` one-tap import scheme, so deep-link import is **not claimed**;
- main Amnezia `vpn://` behavior is not inherited into standalone AWG without separate source evidence;
- official WireGuard Apple mirror freshness caveat and 2026 open-PR regression context are explicit;
- exact export/share semantics, exact QR source path and real-device receipts remain open.

## Commits

- `58d7e712593c039a745f8ec380bfa10e2c31b032` — deepen server panel privilege/auth and AWG generation interop evidence.
- `89f80ac031ca51b81059cfebfc2c133811c3f274` — pin Apple WireGuard/AWG import/deep-link boundaries.

## Strict checks

- entries 002/003 COMPLETE-REFERENCE-v2: **NO**.
- documentation-only interop rows converted to PASS: **NO**.
- unsupported standalone AWG Apple deep-link capability claimed: **NO**.
- management panel treated as canonical WireGuard server engine: **NO**.
- file-presence treated as completion: **NO**.

## Residual gates

1. source-pin wg-easy v15 authentication/session/bootstrap implementation and image/dependency provenance;
2. exact install/update/uninstall/rollback execution receipts on representative WireGuard/AWG server targets;
3. exact source function/path for official WireGuard Apple QR/file import and export/share/archive behavior;
4. pin standalone AmneziaWG Apple revision and exact import/export source paths;
5. execute AWG 1.x/1.5/2.0 interop and upgrade matrix; issue #191 remains a blocker for claims, not for independent research;
6. reconcile entries 002/003 line-by-line against `FULL_PROTOCOL_REFERENCE_CONTRACT.md` and only then change tracker status.

## Exact next action

Continue `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`. Deep-audit wg-easy v15 authentication/session/persistence source and immutable release/container provenance, then pin exact Apple import/export source functions. If execution infrastructure is unavailable for AWG interop receipts, record that as an external execution blocker and continue source/advisory reconciliation rather than promoting documentation to PASS. Keep entries 002/003 PENDING until every applicable gate is evidence-backed.
