# AGENTS Handoff — 2026-08-14 — OpenVPN v1 Closure

Mandatory continuation checkpoint.

## State transition

OpenVPN shared family / entry 001 is now:

`V1-HANDOFF-READY / NOT IMPLEMENTED`

This is research only.

## New closure evidence

- `ICS_OPENVPN_ANDROID.md`
- `DEPENDENCIES_TESTS_SECURITY.md`
- `SUPPORT_REUSE_DECISIONS.md`
- synchronized entry 001 and shared family README
- dated status `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_OPENVPN_V1.md`

## Core decision

`OpenVPN/openvpn3@1fd271caefc9a71406afdc2ff2460999dcfdb234`

Research classification: `HIGH-PRIORITY CORE VPN TARGET / OPENVPN3-CORE-FIRST`.

## Important Android finding

Pinned ics-openvpn source uses AndroidX Security MasterKey + EncryptedFile for encrypted profile persistence. PVNetwork should keep `.ovpn` as interoperability format while storing reusable credentials/keys in protected product storage.

## Residual gaps preserved

- final OpenVPN3 release pin/SBOM/advisory review;
- current issue matrices;
- exhaustive menus/screenshots/accessibility;
- implementation/device/E2E/Store evidence;
- server/crypto/wire-flow v2 expansion.

## Exact next action

1. set active original-v1 family to SoftEther entries 013–015;
2. read actual `research/upstreams/softether-family/` tree and numbered 013–015 entries;
3. expand source/architecture/client/server-role/config/storage/license/tests/issues/platform evidence;
4. create support/reuse decisions and synchronize 013–015;
5. if handoff-ready, checkpoint and immediately choose next unfinished original-v1 family;
6. do not begin mass v2 expansion yet.
