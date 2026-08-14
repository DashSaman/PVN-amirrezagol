# AGENTS Handoff — 2026-08-14 — Hysteria v1 Closure

Mandatory continuation checkpoint.

## State transition

Entries 042 Hysteria and 043 Hysteria2 are now:

`V1-HANDOFF-READY / NOT IMPLEMENTED`

## Evidence

- `research/upstreams/hysteria-family/SOURCE_ARCHITECTURE.md`
- `DEPENDENCIES_SECURITY_TESTS.md`
- `SUPPORT_REUSE_DECISIONS.md`
- `research/protocols/042-entry/V1_RESEARCH.md`
- `research/protocols/043-entry/V1_RESEARCH.md`
- status `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_HYSTERIA_V1.md`

## Decisions

042 Hysteria v1:

`LEGACY COMPATIBILITY TARGET / DO NOT INFER FROM HYSTERIA2`

043 Hysteria2:

`HIGH-PRIORITY MODERN QUIC PROXY TARGET / UPSTREAM ENGINE CANDIDATE`

Primary Hysteria2 upstream: `apernet/hysteria`; root license reviewed MIT.

## Important architecture rule

QUIC/TLS/auth/application protocol/TUN are separate layers. A working local Hysteria2 proxy is not proof of a full-device VPN implementation.

## Residual gaps

Exact release pin/SBOM, legacy v1 source, issue matrix, mobile/client menus, device/performance/Store evidence remain. Server installers/menus/crypto/wire flow are mandatory later v2 work.

## Exact next action

1. Activate the **IKE/IPsec original-v1 family**, starting with entries 004 IKEv2/IPsec, 005 IKEv1/IPsec, 006 ESP, 007 AH and 008 L2TP/IPsec relationships.
2. Read existing strongSwan/native-IPsec research first; do not restart.
3. Pin current strongSwan source/license/release and map charon/libstrongswan/plugins/front ends/platform integrations.
4. Separate IKE control/authentication from ESP/AH data protection and L2TP composition.
5. Compare reusable strongSwan components with native Windows/Apple/Android platform APIs where appropriate.
6. Add dependency/security/test/reuse decisions and sync numbered entries.
7. Checkpoint and continue next unfinished original-v1 family without owner prompting.
8. Do not start mass COMPLETE-REFERENCE-v2 yet.
