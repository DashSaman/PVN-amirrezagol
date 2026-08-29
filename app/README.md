# PVNetwork Cross-Platform Client Research

Status: **research/reference complete for the client-source survey and public-mobile readiness baseline defined on 2026-08-29; no new client implementation is claimed by this folder.**

Research snapshot: 2026-08-29

This folder records the evidence needed to design a future PVNetwork cross-platform VPN/proxy client without blindly forking a competitor application.

## Goal

Study the strongest current client applications and reusable upstream components for:

- Android phones/tablets and Android TV / Google TV
- iPhone/iPad and tvOS where feasible
- Windows
- macOS
- Linux

Primary named targets:

- Karing
- V2Box
- v2rayNG
- Happ
- NPV/NV Tunnel / NapsternetV

Additional high-value references:

- Hiddify
- AmneziaVPN
- NekoBox for Android
- v2rayN
- FlClash
- Clash Verge Rev
- upstream sing-box, Xray-core and Mihomo boundaries
- independent `imanheidary/v2box` Flutter plugin (not the official V2Box app)

## Files

- `CLIENT_SOURCE_REUSE_MATRIX.md` — canonical source/repository status, licenses, reusable subsystems, direct-reuse decisions and risks.
- `KARING_DEEP_SOURCE_ANALYSIS.md` — deep Karing app/core/ruleset inspection: protocols, transports, Naive implementation, GeoIP/GeoSite/ACL, Iran preset, routing fields/actions, DNS, TLS and reuse boundaries.
- `KARING_PLATFORM_IMPLEMENTATION_ANALYSIS.md` — OS-by-OS Karing implementation analysis covering Android/Android TV, iOS/iPadOS, tvOS, macOS, Windows and Linux: native shells, VPN/service boundaries, permissions/entitlements, lifecycle, packaging, missing/generated core artifacts, security observations and PVNetwork design lessons.
- `PRODUCTION_READINESS_GAP_ANALYSIS.md` — gap analysis for a professional public Android/iOS release: organization/store blockers, `VpnService`/Network Extension, API/SDK requirements, privacy, billing, account deletion, backend/control plane, consumer UX, security, device QA, release engineering and prioritized P0-P6 execution gates.
- `CROSS_PLATFORM_ARCHITECTURE_RECOMMENDATION.md` — recommendation aligned to the actual current PVNetwork implementation.
- `AGENT_HANDOFF.md` — exact continuation point for a later design/implementation agent.

## Core conclusion

The future PVNetwork application should **not** be a fork of Karing, Hiddify, v2rayNG, Amnezia or another mature client.

After inspecting the actual repository, the preferred direction is:

```text
PVNetwork-owned Kotlin Multiplatform / Compose product shell
        -> PVNetwork canonical profile/subscription/routing/DNS model
        -> stable EngineAdapter / PlatformAdapter contracts
        -> audited upstream engines and OS VPN APIs
```

The reason is not toolkit preference: PVNetwork already has a Kotlin Multiplatform `core:foundation`, common product domains under `commonMain`, a Compose Desktop shell and separate WireGuard/OpenVPN/Xray/Mihomo/OpenConnect adapters. That work should be preserved.

Flutter remains a **secondary experimental UI option**, because Karing, Hiddify and FlClash prove its viability in this category. If a future spike demonstrates a concrete advantage, Flutter should sit above PVNetwork-owned contracts rather than replace the network/domain architecture.

The public-mobile readiness audit adds an important project priority: **the next distance-to-market bottleneck is mobile/product/store engineering, not another broad protocol-count wave.** The current repository still has no Android/iOS app module, KMP currently targets JVM only, device verification is zero, and Store/privacy/billing/release infrastructure is not implemented. See `PRODUCTION_READINESS_GAP_ANALYSIS.md`.

## Key findings

1. **Karing** is the strongest whole-product cross-platform UX/architecture reference, but its source is GPL v3-or-later and carries a naming/association condition. Treat it as clean-room reference unless PVNetwork intentionally adopts the applicable GPL obligations.
2. **Hiddify** is a strong Flutter/core-boundary reference, but its current repository license adds non-commercial, fork, attribution, release and naming/UI conditions. Treat it as reference-only for an independent commercial product unless written permission/legal approval says otherwise.
3. **v2rayNG** is an excellent Android/Xray lifecycle and import reference but is Android-specific and GPL-3.0.
4. **v2rayN** is a strong desktop multi-core/process/system-proxy reference and is GPL-3.0.
5. **AmneziaVPN** is a strong multi-platform native/service/privilege reference using C++/Qt/QML and is GPL-3.0.
6. **Happ** public repositories inspected in this snapshot are release/readme oriented rather than a reusable full application source tree; use Happ as UX/behavior reference.
7. No authoritative public source tree for the **official V2Box end-user application** was verified. Same-name repositories must not automatically be treated as its source.
8. No authoritative app source tree for **NPV Tunnel/NapsternetV** was verified. Binary mirrors and reverse-engineering/config-decryption repositories are not acceptable implementation provenance.
9. The independent `imanheidary/v2box` project is an **MIT-licensed Flutter/plugin glue candidate**, but it is not official V2Box. Its own code may be eligible for a bounded audit; Xray/sing-box/native artifacts retain separate licenses.
10. **Xray-core (MPL-2.0)** is the most immediately relevant reusable upstream to the existing PVNetwork architecture because an Xray adapter/runtime boundary already exists in the repository.
11. Deeper Karing inspection confirms that its capability comes from a three-layer stack: Flutter app + a Karing-maintained sing-box fork + Karing rulesets. The core source verifies Naive, AnyTLS, VLESS/VMess/Trojan, Shadowsocks/ShadowTLS, Hysteria/Hysteria2/TUIC, WireGuard, SSH/Tor and extensive routing/DNS/TLS features; see `KARING_DEEP_SOURCE_ANALYSIS.md`.
12. Per-platform source inspection shows Karing does not make Flutter own the tunnel lifecycle: Android delegates to an external `vpn_service`, Apple targets delegate packet/system extensions to `LibVpnCore`, Windows injects a separate release core directory, and Linux packages a separate `karingService`; tvOS additionally uses a dedicated native SwiftUI shell with LAN/QR provisioning. See `KARING_PLATFORM_IMPLEMENTATION_ANALYSIS.md`.
13. Current Store rules make organization identity a release gate for VPN apps: Apple VPN apps must be offered by organization-enrolled developers, and Google Play requires organization accounts for apps approved to use `VpnService` under its current/upcoming Play Console requirements. Public release planning therefore starts with publisher/legal identity, privacy posture and billing/account design in parallel with mobile implementation.

## Reuse vocabulary

- **DIRECT-CANDIDATE** — source may be suitable for bounded reuse after exact revision, dependency, security, platform and license audit. This is not automatic approval.
- **GPL-ONLY** — source can be studied, but direct copying requires an intentionally GPL-compatible distribution strategy and all associated obligations.
- **REFERENCE-ONLY** — use public behavior, architecture concepts and interoperability lessons; independently implement PVNetwork-owned code and do not copy source/assets.
- **NO-SOURCE** — no authoritative reusable source tree was verified; do not substitute mirrors, binaries, decompilations or unrelated same-name repositories.

## Legal / provenance boundary

This is an engineering license screen, not final legal advice.

Before shipping any third-party code:

- pin the exact revision/tag and hashes;
- capture the exact license at that revision;
- audit transitive/native dependencies separately;
- preserve required copyright/license notices;
- verify commercial use and redistribution terms;
- verify App Store / Google Play / Microsoft Store/distribution compatibility;
- generate an SBOM;
- run security and supply-chain checks;
- ensure the dependency is behind PVNetwork-owned interfaces and has a replacement path.

Public GitHub visibility does **not** mean source is safe to copy.

## Primary upstream evidence

- Karing: https://github.com/KaringX/karing
- Karing sing-box fork: https://github.com/KaringX/sing-box
- Karing rulesets: https://github.com/KaringX/karing-ruleset
- Hiddify: https://github.com/hiddify/hiddify-app
- v2rayNG: https://github.com/2dust/v2rayNG
- v2rayN: https://github.com/2dust/v2rayN
- AmneziaVPN client: https://github.com/amnezia-vpn/amnezia-client
- Happ organization: https://github.com/Happ-proxy
- NekoBox for Android: https://github.com/MatsuriDayo/NekoBoxForAndroid
- FlClash: https://github.com/chen08209/FlClash
- Clash Verge Rev: https://github.com/clash-verge-rev/clash-verge-rev
- independent MIT Flutter dual-core plugin: https://github.com/imanheidary/v2box
- upstream sing-box: https://github.com/SagerNet/sing-box
- Xray-core: https://github.com/XTLS/Xray-core
- Mihomo: https://github.com/MetaCubeX/mihomo

## Important naming warning: V2Box

Three different things can appear in searches and must not be conflated:

1. **V2Box the end-user application** — no authoritative public source tree was verified in this research snapshot.
2. **SagerNet/v2box** — an archived Go migration/library project from SagerNet; it is not the V2Box GUI application's source.
3. **imanheidary/v2box** — an independent 2026 Flutter VPN/core plugin under MIT; potentially useful in a Flutter-specific experiment, but not official V2Box source.

## Relationship to existing PVNetwork architecture

This research reinforces, rather than replaces, `docs/ARCHITECTURE.md` and the current repository structure:

- UI must remain engine-independent.
- configuration must normalize into a PVNetwork-owned canonical profile model.
- routing/DNS are shared product subsystems.
- platform differences remain behind platform adapters.
- no protocol cryptography should be reimplemented in the product layer.
- the existing KMP/common domain and tested engine adapters should be preserved while mobile/TV targets are added incrementally.

See `CROSS_PLATFORM_ARCHITECTURE_RECOMMENDATION.md` for the implementation-order recommendation and platform matrix, and `PRODUCTION_READINESS_GAP_ANALYSIS.md` for the current distance-to-Store execution gates.
