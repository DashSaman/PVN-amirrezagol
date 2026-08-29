# PVNetwork Cross-Platform Client Research

Status: **research/reference complete for the client-source survey defined on 2026-08-29; no new client implementation is claimed by this folder.**

Research snapshot: 2026-08-29

This folder records the evidence needed to design a future PVNetwork cross-platform VPN/proxy client without blindly forking a competitor application.

## Goal

Study the strongest current client applications and reusable upstream components for:

- Android phones/tablets and Android TV / Google TV
- iPhone/iPad and tvOS where feasible
- Windows
- macOS
- Linux

Primary named targets from the owner:

- Karing
- V2Box
- v2rayNG
- Happ
- NPV/NV Tunnel / NapsternetV

Additional high-value references included because they materially improve the cross-platform design decision:

- Hiddify
- AmneziaVPN
- NekoBox for Android
- v2rayN
- FlClash
- Clash Verge Rev
- upstream sing-box, Xray-core and Mihomo boundaries
- independent `imanheidary/v2box` Flutter plugin (not the official V2Box app)

## Files

- `CLIENT_SOURCE_REUSE_MATRIX.md` — canonical source/repository status, license, source availability, useful subsystems, direct-reuse decision, and risks.
- `CROSS_PLATFORM_ARCHITECTURE_RECOMMENDATION.md` — recommended clean-room product architecture for PVNetwork based on the survey.
- `AGENT_HANDOFF.md` — exact continuation point for a later design/implementation agent.

## Core conclusion

The future PVNetwork application should **not** be a fork of Karing, Hiddify, v2rayNG, Amnezia or another mature client.

The preferred direction is:

```text
PVNetwork-owned Flutter UI/product shell
        -> PVNetwork canonical profile/subscription/routing model
        -> stable EngineAdapter / PlatformAdapter contracts
        -> audited upstream engines and OS VPN APIs
```

Why:

1. Karing demonstrates that Flutter can successfully cover Windows, Android, Linux, iOS, macOS and tvOS while presenting a single product UX, but Karing itself is GPL and carries a branding restriction.
2. Hiddify is also a strong Flutter architecture reference, but its repository adds explicit non-commercial, fork, attribution, release and naming/UI conditions. It is therefore unsuitable as a direct code base for an independent commercial PVNetwork application without written permission.
3. v2rayNG is an excellent Android/Xray lifecycle and import reference but is Android-specific and GPL-3.0.
4. AmneziaVPN is a strong multi-platform native-integration reference using C++/Qt/QML, but its client is GPL-3.0 and would impose a very different UI/toolchain choice.
5. Happ's public repositories currently expose release/readme material rather than the application source tree, so Happ is a UX/behavior reference, not a code-reuse source.
6. No authoritative open-source repository for the commercial V2Box application was verified. Repositories named `v2box` must not automatically be treated as V2Box app source.
7. No authoritative source tree for NPV Tunnel/NapsternetV was verified. Binary mirrors and reverse-engineering/config-decryption repositories are not acceptable upstream source for PVNetwork.
8. The independent `imanheidary/v2box` Flutter plugin is technically interesting because its own glue code is MIT-licensed and exposes a cross-platform dual-core Xray/sing-box abstraction. It is **not affiliated with the official V2Box app** and must receive a normal dependency/security/maintenance audit before adoption. The licenses of Xray/sing-box and any generated/bundled artifacts remain separate obligations.

## Reuse vocabulary

This folder uses four distinct decisions:

- **DIRECT-CANDIDATE** — code may be technically and license-wise suitable for reuse after dependency/security audit and preservation of notices. This is not automatic approval.
- **GPL-ONLY** — source is useful, but copying it into PVNetwork would require a GPL-compatible distribution strategy; use clean-room concepts instead if PVNetwork is not intended to be GPL.
- **REFERENCE-ONLY** — use public behavior, architecture concepts, protocols/specifications and UI lessons; do not copy source/assets.
- **NO-SOURCE** — an authoritative source tree was not verified; do not treat mirrors, binaries, decompilations or unrelated same-name repositories as source.

## Legal / provenance boundary

This is an engineering license screen, not final legal advice.

Before shipping any third-party code:

- pin the exact revision/tag;
- capture the exact license at that revision;
- audit transitive/native dependencies separately;
- preserve required copyright/license notices;
- verify commercial use and redistribution terms;
- verify App Store / Google Play / Microsoft Store compatibility;
- generate an SBOM;
- run security and supply-chain checks.

Public GitHub visibility does **not** mean source is safe to copy.

## Primary upstream evidence

- Karing: https://github.com/KaringX/karing
- Karing license: https://github.com/KaringX/karing/blob/main/LICENSE.md
- Hiddify: https://github.com/hiddify/hiddify-app
- Hiddify license: https://github.com/hiddify/hiddify-app/blob/main/LICENSE.md
- v2rayNG: https://github.com/2dust/v2rayNG
- AmneziaVPN client: https://github.com/amnezia-vpn/amnezia-client
- Happ organization: https://github.com/Happ-proxy
- NekoBox for Android: https://github.com/MatsuriDayo/NekoBoxForAndroid
- v2rayN: https://github.com/2dust/v2rayN
- FlClash: https://github.com/chen08209/FlClash
- Clash Verge Rev: https://github.com/clash-verge-rev/clash-verge-rev
- independent MIT Flutter dual-core plugin: https://github.com/imanheidary/v2box
- sing-box: https://github.com/SagerNet/sing-box
- Xray-core: https://github.com/XTLS/Xray-core
- Mihomo: https://github.com/MetaCubeX/mihomo

## Important naming warning: V2Box

Three different things can appear in searches and must not be conflated:

1. **V2Box the end-user application** — no authoritative public source tree was verified in this research snapshot.
2. **SagerNet/v2box** — an archived Go migration/library project from SagerNet; it is not the V2Box GUI application's source.
3. **imanheidary/v2box** — an independent 2026 Flutter VPN/core plugin under MIT; useful as a candidate integration library, but it is not official V2Box source.

## Relationship to existing PVNetwork architecture

This research reinforces, rather than replaces, `docs/ARCHITECTURE.md`:

- UI must remain engine-independent.
- configuration must normalize into a PVNetwork-owned canonical profile model.
- routing/DNS are shared product subsystems.
- platform differences remain behind platform adapters.
- no protocol cryptography should be reimplemented in the product layer.

The new recommendation is that **Flutter is the leading UI/product-shell candidate** because multiple successful current clients use it across desktop/mobile, while the network engine boundary should remain independent of Flutter and independent of any single proxy core.
