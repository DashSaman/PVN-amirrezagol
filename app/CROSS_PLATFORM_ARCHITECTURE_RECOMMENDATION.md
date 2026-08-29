# PVNetwork Cross-Platform Client Architecture Recommendation

Snapshot: **2026-08-29**

Status: **architecture/reuse recommendation only. This document does not authorize a rewrite and does not claim implementation or Store/production readiness.**

## Decision

**Keep the existing PVNetwork Kotlin Multiplatform foundation and engine-adapter architecture. Do not replace the current codebase with a Karing/Hiddify fork and do not perform a wholesale Flutter rewrite.**

The repository already contains the right architectural direction:

- Gradle/Kotlin project root;
- `core:foundation` using the Kotlin Multiplatform plugin;
- product-owned common source under `commonMain`;
- existing domains for adapters, connection, importing, network, profile, security, diagnostics and i18n;
- a working Compose Desktop shell;
- separate engine modules for WireGuard, OpenVPN, Xray, Mihomo and OpenConnect.

That is more valuable than switching frameworks just because Karing and Hiddify use Flutter.

### Recommended product stack

```text
Compose Multiplatform / platform UI
        |
        v
PVNetwork-owned presentation + application layer
        |
        v
core:foundation (KMP common domain)
  - canonical profile model
  - subscriptions/import normalization
  - routing + DNS policy
  - secure secret references
  - connection state
  - diagnostics
        |
        v
PVNetwork EngineAdapter / PlatformAdapter contracts
        |
        +------------------+------------------+------------------+
        |                  |                  |                  |
      Xray              Mihomo            WireGuard          OpenVPN ...
   process/native      process/native       native/API       process/native
        |
        v
OS VPN / tunnel / proxy APIs
```

The UI must never own a core-specific JSON schema and must never call Xray/sing-box/Mihomo directly.

---

# Why this beats a Flutter rewrite

Flutter is proven for this product category: Karing, Hiddify and FlClash demonstrate that it can deliver attractive VPN/proxy clients across several platforms. However, PVNetwork has already invested in a KMP common layer and engine adapters.

A rewrite would create three avoidable costs:

1. throw away or wrap already-tested Kotlin domain/adapters;
2. create a second application-state model in Dart;
3. delay mobile/network integration while reproducing work that already exists.

Therefore Flutter should be treated as a **fallback UI option**, not today's default architecture.

If a future prototype proves Compose Multiplatform unable to satisfy a required target or UX constraint, Flutter can still sit above a stable PVNetwork IPC/FFI boundary. The engine/domain architecture should survive either UI toolkit.

---

# Three architecture options

## Option A — RECOMMENDED: KMP + Compose Multiplatform

Use the existing `core:foundation` as common product logic and expand platform targets deliberately.

Benefits:

- preserves current implementation and tests;
- Kotlin Multiplatform is already configured in the repository;
- Android integration is natural;
- Compose Multiplatform can share a large amount of UI/product logic;
- Swift/Objective-C interop remains possible for Network Extension work on Apple platforms;
- desktop shell already exists;
- engine adapters remain independent from UI.

Risks to prove with spikes:

- iOS/tvOS packaging and Network Extension integration;
- exact shared-UI percentage on Apple;
- Android TV focus/remote UX;
- desktop privilege/service lifecycle per OS;
- Store constraints around bundled cores and dynamic downloads.

Decision: **default path**.

## Option B — Flutter shell over PVNetwork-owned boundary

Only choose this if a measured UI/platform spike shows a material advantage.

Flutter can reuse UX lessons from Karing/Hiddify/FlClash, but PVNetwork should expose its existing domain/engine control through a narrow bridge instead of moving all business logic to Dart.

Possible boundary:

```text
Flutter UI
   -> generated typed bridge / FFI / local RPC
       -> PVNetwork KMP/domain service
           -> EngineAdapter
```

The independent MIT `imanheidary/v2box` plugin may be useful as **reference or bounded reusable bridge code**, but should not replace PVNetwork's own `EngineAdapter` contracts.

Decision: **fallback / experimental spike**, not default rewrite.

## Option C — Qt/QML like AmneziaVPN

Technically strong for native networking and desktop/mobile deployment, but would replace too much current Kotlin/Compose work and change the toolchain substantially.

Decision: **not recommended unless future requirements invalidate KMP/Compose**.

---

# Platform strategy

| Platform | Product/UI recommendation | Engine/platform boundary | Main validation requirement |
|---|---|---|---|
| Android phone/tablet | Compose Multiplatform/Android | Android `VpnService`, native/JNI/IPC where required | permission lifecycle, always-on behavior, per-app routing, network changes |
| Android TV / Google TV | shared Compose UI with TV-specific navigation layer | same Android VPN platform adapter | D-pad focus, background lifecycle, no-touch flows |
| iPhone/iPad | Compose Multiplatform where suitable + native Swift shell where Apple APIs demand it | Network Extension / Packet Tunnel provider; audited native bridge | entitlements, extension memory/lifecycle, App Store policy |
| tvOS | shared domain + dedicated TV presentation as needed | Apple Network Extension capabilities must be independently verified | entitlement/API availability and remote UX |
| macOS | Compose desktop or shared CMP UI | native helper/process/Network Extension depending protocol | privilege model, sleep/wake, system proxy/TUN cleanup |
| Windows | existing Compose Desktop direction | process/service/native adapter | service privileges, Wintun/TUN cleanup, upgrade/restart |
| Linux | existing Compose Desktop direction | process/native/system adapter | distro variance, permissions, DNS/resolver integration |

Do not force identical low-level engine integration on every OS. Share product contracts and behavior; adapt implementation to platform constraints.

---

# Engine strategy

PVNetwork should be a **multi-engine product with capability-based routing**, not “a GUI for one core.”

## Xray

Priority candidate for the Xray family because:

- PVNetwork already has Xray adapter/runtime work and real scoped interoperability evidence;
- upstream is MPL-2.0 rather than GPL application code;
- it covers important VLESS/VMess/Trojan and REALITY/Vision/XHTTP-related use cases.

Keep exact capability/version checks. One tested VLESS path does not certify every Xray transport/security combination.

## Mihomo

Useful for broad modern proxy/routing/provider capabilities and already has a PVNetwork adapter. Treat GPL-family licensing as a shipping-architecture concern and keep the engine behind a replaceable boundary.

## sing-box

Excellent technical reference and broad capability engine, but GPL v3-or-later licensing and branding terms require an explicit distribution/legal decision before PVNetwork bundles or tightly integrates it.

Do not make sing-box mandatory just because Karing/Hiddify use it.

## WireGuard / OpenVPN / OpenConnect

Keep them as independent capability adapters. Do not translate all protocols through a proxy core when a direct mature implementation is better.

---

# Subscription and profile architecture

This is one of the most important lessons from Karing/Hiddify/v2rayNG.

Never let a raw subscription become the internal product model.

Required pipeline:

```text
URL / QR / clipboard / file / manual input
        -> fetch/import envelope
        -> format detection
        -> parser
        -> canonical PVNetwork Profile(s)
        -> validation + deduplication
        -> subscription ownership/metadata
        -> user policy / routing / DNS
        -> capability selection
        -> generated transient engine config
```

Persist:

- canonical user intent;
- subscription metadata;
- safe non-secret preferences;
- secret references.

Do not persist as source-of-truth:

- a competitor application's internal schema;
- generated Xray/Mihomo/sing-box runtime JSON;
- reusable secrets in ordinary preferences/logs.

Support later:

- subscription refresh and ETag/Last-Modified where applicable;
- node rename/grouping/favorites;
- profile overrides that survive subscription updates;
- last-known-good snapshot and rollback;
- update diff before applying destructive changes;
- QR/share-link import/export with secret-aware redaction;
- first-class expiration/traffic metadata where provider formats expose it.

---

# Routing and DNS architecture

Karing/FlClash/Mihomo-style products demonstrate why routing must be a product subsystem rather than a screen full of core-specific rules.

Recommended layers:

1. **Simple mode** — connect, direct, block, bypass-LAN, region/app presets.
2. **Advanced product rules** — domain, suffix, IP/CIDR, process/app where platform supports it, protocol, destination port, rule sets.
3. **Compiler** — transforms PVNetwork routing policy into the selected engine/platform representation.
4. **Diagnostics** — explains which rule matched and where DNS/traffic went.

DNS policy should similarly be canonical and then compiled for the selected engine/platform.

Tests must include DNS leaks, IPv4/IPv6 behavior, captive portal/network transition, split tunneling and route cleanup after crash.

---

# UI/UX lessons to reuse clean-room

Use Karing as the primary product benchmark, not as source code.

Recommended concepts to independently implement:

- one obvious connect control;
- profile/subscription list separate from raw nodes;
- favorites/recent/latency state;
- beginner mode vs advanced mode;
- clear active route/profile display;
- traffic and connection diagnostics without overwhelming normal users;
- import from URL/QR/clipboard/file;
- backup/export with secret warnings;
- adaptive desktop/mobile layouts;
- dark/light/system theme;
- RTL/Persian localization from the start;
- TV focus navigation as a first-class interaction model rather than a stretched phone UI.

Do not reproduce competitor branding, icons, screenshots, naming or distinctive screen layouts pixel-for-pixel.

---

# Source reuse decision mapped to the chosen KMP architecture

## Potential direct reuse

### `imanheidary/v2box` (MIT)

This is a **conditional** candidate because it is Flutter/Dart-oriented while PVNetwork's recommended architecture is KMP/Compose.

Use it in two ways:

1. inspect its native platform bridge and API design for ideas that can be independently mapped to PVNetwork adapters;
2. if a Flutter UI spike is ever approved, test the MIT glue behind a PVNetwork-owned adapter in an isolated prototype.

Do **not** import it into the production repository merely because the top-level license is MIT. Audit bundled core artifacts and native dependencies separately.

## Xray-core (MPL-2.0)

Stronger practical candidate because PVNetwork already integrates it behind an adapter. Continue pinning exact versions/checksums and preserving MPL notices/obligations.

## Permissive dependencies

Prefer small MIT/BSD/Apache-2.0 libraries for utility/UI/platform tasks when mature and maintained, but each dependency still needs provenance, security and transitive-license review.

---

# Sources that are not direct-code bases

- **Karing** — GPL + branding condition: architecture/UX reference unless PVNetwork intentionally adopts GPL obligations.
- **Hiddify** — extended GPL conditions including non-commercial use without permission: reference only for current independent product plan.
- **v2rayNG / v2rayN** — GPL: platform/behavior reference.
- **AmneziaVPN** — GPL: native integration/operations reference.
- **FlClash / Clash Verge Rev / historical NekoBox** — GPL: architecture/reference only.
- **Happ** — authoritative application source not verified: behavior/UX reference only.
- **official V2Box** — authoritative public source not verified: behavior/UX reference only.
- **NPV Tunnel/NapsternetV** — authoritative public app source not verified: behavior/UX reference only.

Never use decompiled proprietary clients or unofficial credential/config decryption code as a shortcut.

---

# Implementation sequence after design approval

The research supports the following order, but **implementation should start only after an explicit design decision**:

1. preserve and test current KMP `core:foundation` contracts;
2. make `core:foundation` truly target-neutral where feasible (today the Gradle module uses KMP but currently configures a JVM target);
3. formalize `PlatformAdapter` for VPN permission, TUN, secure storage, lifecycle, notifications, system proxy and privileged helpers;
4. keep existing engine adapters independent;
5. add Android target and a minimal Android client shell;
6. prove one real existing protocol end-to-end on Android;
7. add iOS framework/export boundary and a minimal Network Extension spike;
8. only after mobile lifecycle evidence, expand the shared Compose UI;
9. add Android TV focus shell from shared presentation logic;
10. validate desktop parity and package/update/service behavior;
11. then broaden subscriptions/routing/advanced engines.

This sequence proves the hardest platform constraints early instead of spending months polishing a cross-platform UI before the VPN engines can legally and reliably run on every target.

---

# Go / no-go gates

Before calling the future client cross-platform-ready, every target needs evidence for:

- build/package/signing;
- VPN/tunnel permission flow;
- connect/disconnect/reconnect;
- real data path, not parser-only tests;
- DNS and route correctness;
- network switch (Wi-Fi/mobile/Ethernet where relevant);
- sleep/wake and process death;
- crash cleanup;
- secret storage;
- subscription import/update;
- update/rollback behavior;
- accessibility/focus/navigation;
- Store/distribution policy for bundled cores;
- license/SBOM/attribution.

Research completion is not certification.

---

# Final recommendation

**Build the new PVNetwork application as a PVNetwork-owned product, continuing the existing Kotlin Multiplatform + adapter foundation.**

Borrow:

- Karing's product simplicity and cross-platform UX lessons;
- Hiddify's feature/core separation lessons;
- FlClash's platform-dependent engine-boundary idea;
- v2rayNG's Android lifecycle lessons;
- Amnezia's native privilege/service robustness;
- v2rayN/Clash Verge's desktop process and system integration lessons.

Reuse source only where provenance and license genuinely permit it. At this snapshot the independent MIT Flutter `imanheidary/v2box` glue is the clearest application-layer direct-code candidate, but it is **optional and architecture-dependent**; Xray-core behind the already-owned adapter is the more immediately relevant reusable upstream for the current PVNetwork codebase.
