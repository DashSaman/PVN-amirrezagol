# AGENTS Handoff — 2026-08-14 — WireGuard / AmneziaWG v2 slice 9

Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`  
Entries: 002 WireGuard, 003 AmneziaWG

## State transition

The source/reference research for this family is now:

**`REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / NOT IMPLEMENTED`**

Strict `COMPLETE-REFERENCE-v2` tracker promotion remains forbidden because the current repository work unit requires external execution receipts that are not available in this environment.

## New durable evidence

### Apple build identity and Store provenance

File:

`research/upstreams/wireguard-family/reference-v2/APPLE_BUILD_IDS_AND_STORE_PROVENANCE.md`

Findings:

- pinned WireGuard Apple source externalizes `DEVELOPMENT_TEAM`, `APP_ID_IOS` and `APP_ID_MACOS` through developer configuration;
- app, packet-tunnel extension and macOS login-helper bundle identifiers are derived from those supplied IDs;
- pinned standalone AmneziaWG Apple source contains `DEVELOPMENT_TEAM = X7UJ388FXK`, `APP_ID_IOS = org.amnezia.awg`, `APP_ID_MACOS = org.amnezia.awg` and marketing version `3.0.1`;
- the public AmneziaWG Store listing reviewed in this campaign exposed version `2.0.2`, so the pinned source is not claimed as provenance of the current Store binary;
- public Store identity and source-to-binary correspondence remain separate evidence classes.

### Exact wg-easy dependency/request boundary

File:

`research/upstreams/wireguard-family/reference-v2/WGEASY_V15_3_NITRO_DEPENDENCY_BOUNDARY.md`

Findings:

- pinned wg-easy v15.3.0 lock resolves Nuxt `3.21.5`, `nitropack` `2.13.4`, h3 `1.15.11` on the Nitro-2 path;
- canonical h3 v1.15.11 source was inspected directly;
- `getRequestHost` uses ordinary Host by default and only uses X-Forwarded-Host with explicit opt-in;
- `getRequestProtocol` accepts exact `x-forwarded-proto: https` by default unless disabled;
- X-Forwarded-For use is explicit opt-in and the source itself warns about trusted-proxy assumptions;
- h3's Node adapter does not itself install a Host/Origin allowlist;
- a current Nitro Git ref named like v2.13.4 was not treated as exact package provenance after its checked package metadata identified Nitro 3 beta-era source; wg-easy's committed lockfile remains authoritative.

### Formal v2 gate reconciliation

File:

`research/upstreams/wireguard-family/reference-v2/ENTRY_002_003_V2_GATE_RECONCILIATION.md`

All 16 research/reference categories in `FULL_PROTOCOL_REFERENCE_CONTRACT.md` now have traceable evidence for both entries, with N/A/narrower-AWG applicability recorded where needed.

Synchronized index:

`research/upstreams/wireguard-family/reference-v2/REFERENCE_INDEX.md`

## Strict remaining blockers

The following require external environments and must not be fabricated:

1. representative server/container install -> start -> upgrade -> rollback -> uninstall receipts;
2. representative Windows/Android/Apple clean install/update/uninstall receipts;
3. Apple archive/signing/TestFlight/App Store build-to-source correspondence;
4. real-device Network Extension execution;
5. built wg-easy/Nitro image exercised behind a real reverse proxy;
6. executed AWG generation × kernel/userspace × platform interoperability matrix.

These are execution blockers, not missing source research.

## Failed/unsafe approaches not to repeat

- do not infer App Store binary provenance from product-name or bundle-id similarity;
- do not treat a same-named current Nitro Git tag as the exact `nitropack@2.13.4` dependency without package correspondence;
- do not promote 002/003 to strict complete merely because all mandatory dossier files exist;
- do not repeat source research already reconciled unless upstream evidence materially changes.

## Tracker decision

Keep entries 002 and 003 `PENDING` in `research/REFERENCE_V2_COMPLETENESS.md` under the repository's stricter execution-evidence standard.

## Next work unit

Per `AGENTS_HANDOFF_2026-08-14_OPENVPN_V2_TO_WIREGUARD_AWG_V2.md`, continue with:

**`IKE-IPSEC-COMPLETE-REFERENCE-V2`**

Primary entries:

- 004 IKEv2/IPsec
- 005 IKEv1/IPsec
- 006 IPsec ESP
- 007 IPsec AH

Start from the existing strongSwan/native v1 evidence, keep IKE negotiation/authentication separate from ESP/AH packet protection, inventory serious server/client implementations and installers, build server/client OS matrices, map management/client UIs, document cryptography/wire flow/ports/topologies, and checkpoint continuously. Do not wait for owner prompting.
