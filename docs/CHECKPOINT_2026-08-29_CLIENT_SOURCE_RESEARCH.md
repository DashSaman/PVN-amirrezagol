# Checkpoint — Cross-Platform Client Source Research

Date: 2026-08-29

Status: **research slice complete; no implementation claimed**.

## Persisted outputs

- `app/README.md`
- `app/CLIENT_SOURCE_REUSE_MATRIX.md`
- `app/CROSS_PLATFORM_ARCHITECTURE_RECOMMENDATION.md`
- `app/AGENT_HANDOFF.md`

## Main decision

Preserve the existing PVNetwork Kotlin Multiplatform/common-domain and engine-adapter work. Default future client path is KMP + Compose Multiplatform with platform-specific VPN/TUN/service adapters. Flutter is an optional experiment, not a wholesale rewrite path.

## Reuse decision summary

- independent `imanheidary/v2box`: MIT application/plugin glue; conditional direct-code candidate after exact dependency/artifact/security audit; not official V2Box.
- Xray-core: MPL-2.0 and already relevant to the existing PVNetwork adapter boundary.
- Karing, v2rayNG, v2rayN, AmneziaVPN, FlClash, Clash Verge Rev, historical NekoBox: GPL-family/reference sources unless an intentional compatible distribution decision is made.
- Hiddify: reference-only for current independent/commercial direction because current repository license adds restrictive conditions including non-commercial use without permission.
- Happ, official V2Box app, NPV Tunnel/NapsternetV: no authoritative reusable full application source tree verified in this snapshot; behavior/UX reference only.

## Exact continuation point

Read `app/AGENT_HANDOFF.md`. Before implementation, run a design gate on KMP/Compose mobile target expansion. Recommended first vertical slice after design approval is Android using the existing canonical models and adapter contracts, with a real connect/data-path/disconnect/cleanup test.
