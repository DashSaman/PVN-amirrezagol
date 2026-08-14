# PVNetwork Research Log

Purpose: chronological persistent record of research, decisions, uncertainties, and next steps.

This repository is currently in the **research / requirements / architecture phase**. No production application implementation exists yet.

---

## 2026-08-14 — Initial research foundation

### Established

- Product name: **PVNetwork**
- Goal: a multi-platform universal networking client with one consistent branded user experience
- Long-term target platforms: Android, Android TV / Google TV, Windows, macOS, iPhone/iPad, Linux
- Mandatory first-class languages: Persian and English
- Persian must use correct RTL behavior
- Final product must use the exact official PVNetwork logo supplied by the owner
- Public-store readiness must be considered from the beginning
- GitHub documentation is the persistent memory for future AI sessions

### Scope work completed

- Consolidated the project discussion into `PVNETWORK_MASTER_CONTEXT.md`
- Created `AI_START_HERE.md` as the mandatory first file for every future AI/chat
- Created `AGENTS.md` with persistent operating rules
- Created `docs/PROJECT_STATE.md`
- Created `docs/ROADMAP.md`
- Created `docs/ARCHITECTURE.md`
- Created `docs/PROTOCOL_MATRIX.md`
- The current technology scope contains 93 numbered entries, carefully classified so transports and supporting technologies are not incorrectly advertised as literal VPN protocols

### Current design direction

Research currently favors a modular architecture in which one PVNetwork application uses a stable internal adapter layer and carefully selected mature upstream components or official platform APIs.

No final engine set has been approved yet.

### Important project rules established

- Do not claim support without PVNetwork evidence.
- Do not fake implementation, build results, tests, screenshots, or store readiness.
- Do not rely on chat memory when repository evidence exists.
- Do not implement security-sensitive primitives from scratch when mature reviewed implementations exist.
- Do not assume open-source code can automatically be embedded into a commercial product without license review.
- Study successful comparable projects for architecture, regressions, platform problems, UX issues, and maintenance lessons without cloning their branding or incompatible code.
- Treat store requirements as changing constraints that must be rechecked from current official documentation.

### Current open questions

1. Which cross-platform UI/shared-code approach is most appropriate?
2. Which upstream components are suitable for direct integration on each platform?
3. What is the smallest maintainable component set that provides the useful target coverage?
4. What are the exact license and redistribution obligations for each candidate?
5. Which vendor-specific compatibility targets are realistically feasible?
6. What subset is feasible on Apple platforms?
7. What minimum OS versions should be supported?
8. What real-device compatibility matrix is required?
9. Which known bugs from comparable projects should become PVNetwork regression tests?
10. How should advanced capability remain available without making the normal UI complicated?

---

## 2026-08-14 — Exhaustive 93-entry research campaign started

### Research system created

Added permanent campaign infrastructure:
- `research/AI_RESEARCH_CAMPAIGN.md`
- `research/PROTOCOL_RESEARCH_TEMPLATE.md`
- `research/RESEARCH_COMPLETENESS.md`
- `research/SOURCE_MIRROR_POLICY.md`
- `research/protocols/`
- `research/upstreams/`

`AI_START_HERE.md` was updated so future AI/chat sessions must read these research files before continuing.

### Research completion standard
A numbered entry is not complete because a client name or repository was found. The mandatory template now requires evidence for source provenance, pinned revisions, licensing, complete source-tree references, languages/build systems, architecture, engine boundaries, UI/menu map, configuration, storage, platform integration, diagnostics, assets, forks, issues/PRs/releases/forums, tests/CI, privacy/Store implications, and an explicit PVNetwork reuse/support decision.

### Source mirroring policy
Third-party repositories and images are not copied into PVNetwork by default. Public availability is not treated as redistribution permission. The preferred research archive is pinned source/tree references plus developer-level analysis. Vendoring requires explicit license/attribution review.

### Deep/shared upstream research started
Created or started shared research for:
- OpenVPN family
- WireGuard / AmneziaWG family
- OpenConnect enterprise-client family
- SoftEther family
- Hysteria family
- mesh/overlay family
- major GUI/client references

### OpenVPN family progress
Created detailed shared files covering:
- OpenVPN 3 core role, source and license
- official OpenVPN Connect UX/settings/import behavior
- OpenVPN GUI Windows source/menu/Registry/config reference
- Tunnelblick macOS source reference
- Pritunl Client license restriction
- issue-derived failure lessons and future regression requirements

Android ics-openvpn public source was also inspected in detail. Evidence includes profile model, profile persistence, UI files, settings/log components and GPL limitations, but repeated dedicated dossier writes were blocked by the GitHub connector; this remains a tracked documentation gap rather than being hidden.

### Major license/source corrections
Pinned source review supersedes earlier preliminary assumptions:

- Mihomo: reviewed current license is **GPLv3**, not MIT.
- Xray-core: reviewed license is **MPL-2.0**.
- v2rayN/v2rayNG: reviewed licenses are **GPLv3**.
- Hiddify application: reviewed current license includes additional conditions; commercial reuse cannot be assumed.
- sing-box: reviewed current license is GPLv3-or-later plus an additional naming/association condition.
- Pritunl Client: reviewed public license restricts commercial use/redistribution.
- Happ Desktop: reviewed GitHub repository is not confirmed complete licensed application source; treat as product/reference evidence only until canonical source is found.
- Amnezia Client and AmneziaWG core have different licenses and must be evaluated separately.
- SoftEther reviewed root license is Apache-2.0.
- Hysteria reviewed license is MIT.
- WireGuard family reviewed components have separate platform/component licenses and canonical upstream provenance that must remain pinned.

### Successful client architecture research
- Clash Verge Rev: pinned source and deep developer dossier created. Source shows TypeScript frontend plus Rust/Tauri native layer, centralized navigation metadata, pages/services/providers/locales and native config/core/process modules.
- FlClash: pinned source contains an upstream-authored `.agents/architecture.md` describing platform-specific core hosting, shared controller/interface, Riverpod state separation, Drift/SQLite storage, manager stack, business-action layer, packaging and local plugins. A concise research index was committed because a larger dossier write was connector-blocked.
- Amnezia Client: pinned multi-language/multi-platform source and GPL application license recorded separately from the MIT AmneziaWG core.
- Happ: source-availability warning documented.
- Other active references pinned/reviewed include Karing, NekoBox, Throne and snx-rs.

### Issue-derived lessons
OpenVPN/Android issue research identified recurring classes worth converting into PVNetwork regression tests:
- network-transition/reconnect reliability
- address-family/reachability change
- sleep/resume state mismatch
- profile import/semantic conversion mismatch
- parser/UI representation mismatch
- stale state/race conditions
- incorrect user-visible statistics

These are now documented in `research/upstreams/openvpn-family/LESSONS_AND_TESTS.md`.

### Tracker truth
`research/RESEARCH_COMPLETENESS.md` is authoritative when synchronized, but a future agent must also inspect actual tree/history because large connector writes can be rejected. No entry is currently `COMPLETE-RESEARCH-v1`. High-priority families are `IN-RESEARCH`; others remain `SKELETON`, `RESERVED`, `EVIDENCE-GAPS`, or `PENDING`.

### Connector limitation recorded
Some legitimate detailed research-file writes are rejected by the GitHub connector safety layer. The no-loop rule applies: do not repeat the same blocked write unchanged. Preserve evidence through smaller safe files and keep gaps explicit.

---

## 2026-08-14 — WireGuard / AmneziaWG deep work unit

### Repository state checked first

Re-read `AGENTS.md`, `docs/PROJECT_STATE.md`, `research/RESEARCH_COMPLETENESS.md` and the actual `research/upstreams/` tree on `main`.

Found that the previous project-state document and parts of the tracker lagged newer committed research. In particular, `research/upstreams/xray-family/` exists even though older tracker text still describes part of that shared dossier as blocked/partial. `docs/PROJECT_STATE.md` has now been synchronized and future agents are explicitly told to inspect Git history/tree as well as trackers.

`AGENTS.md` was also updated with a mandatory continuous-handoff rule: meaningful research/decisions/blockers must be persisted so another chat can resume without relying on conversation memory.

### Why WireGuard family was selected next

`research/upstreams/wireguard-family/` was still essentially a README-only shared dossier while entries 002 WireGuard and 003 AmneziaWG are high-value candidates. It was selected as the next deep work unit.

### Source provenance and pinned revisions

Created `research/upstreams/wireguard-family/SOURCE_REVISIONS.md` and pinned:

- `WireGuard/wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0`
- `WireGuard/wireguard-windows@4e6726c23ae9c5cb58e0c9910f3b7515621d133d`
- `WireGuard/wireguard-android@e7b3a3c118836e112620b1302a8ba1873ad4daac`
- `WireGuard/wireguard-apple@2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`
- `amnezia-vpn/amneziawg-go@1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`

The official WireGuard GitHub repositories explicitly state they are mirrors of canonical `git.zx2c4.com` repositories. The research archive records both provenance and immutable recursive-tree references rather than pretending GitHub is canonical.

Reviewed repository-level licenses at these pins: WireGuard Go MIT, Windows MIT, Android Apache-2.0, Apple MIT, AmneziaWG Go MIT. Final reuse still requires dependency/path-level review.

### Core architecture

Created `research/upstreams/wireguard-family/CORE_ARCHITECTURE.md`.

The pinned portable core source separates protocol/device state, connection/socket binding, virtual interface and IPC/control concerns. PVNetwork direction is therefore:

`PVNetwork UI -> application/session layer -> stable Core Adapter -> platform-selected WireGuard implementation`

Do not bind product UI to `wireguard-go`, and do not reimplement WireGuard protocol cryptography.

The official source itself indicates that platform-native/kernel or fuller platform integrations can be preferable to the portable userspace implementation, reinforcing a platform-specific engine strategy behind one product adapter contract.

### Android client research

Created `research/upstreams/wireguard-family/ANDROID_CLIENT.md`.

Verified source architecture includes separate `tunnel/` and `ui/` modules. The tunnel module exposes a backend abstraction with multiple backend paths; `Application.kt` chooses a backend at runtime rather than letting UI depend on a single engine implementation.

Verified storage split:

- application preferences use Android Preferences DataStore (`settings`);
- tunnel/profile configurations are managed separately through a `ConfigStore` abstraction and `FileConfigStore`.

Verified UI/settings evidence includes tunnel list flows, adaptive phone/tablet layouts, Settings activity and settings for restore-on-boot, export, quick tile, logs, theme and advanced/system-oriented options.

PVNetwork requirement derived: keep ordinary preferences, canonical profile data, secrets, cache/import material and logs as distinct storage classes; Android TV support must be proven separately and not inferred from mobile layouts.

### Apple client research

Created `research/upstreams/wireguard-family/APPLE_CLIENT.md`.

Verified source layers:

- `Sources/WireGuardApp/`
- `Sources/WireGuardNetworkExtension/`
- `Sources/WireGuardKit/`
- `Sources/Shared/`

`PacketTunnelProvider` is a `NEPacketTunnelProvider` that owns a WireGuard adapter, maps errors, handles start/stop and exposes a narrow app-extension runtime-information path.

`Sources/Shared/Keychain.swift` uses Apple Security/Keychain APIs with persistent references and platform-specific access behavior instead of treating raw configuration text as the normal long-lived cross-process storage model.

PVNetwork direction: Apple support should follow an app + NetworkExtension + adapter/shared-model separation, with Keychain/protected storage and explicit entitlement/access-group design.

### Windows source research and connector gap

Windows source was inspected deeply at the pinned revision. Verified areas include `conf/`, `manager/`, `ui/`, `driver/`, `services/`, `ringlogger/`, `updater/`, `elevate/`, `l18n/` and official Windows docs.

Verified architecture/details include:

- a privileged/background manager service with per-user UI process and IPC;
- tunnel/config/log/update/tray UI areas;
- configuration parser/writer/migration separation;
- normal persisted configurations protected using Windows DPAPI (`.conf.dpapi`) while plaintext `.conf` is an interchange/import form.

Engineering conclusion: import/export text format must not automatically become PVNetwork's persistence format; Windows should use protected canonical storage and explicit export semantics.

Two attempts to persist a dedicated Windows research file were rejected by the GitHub write-safety layer. Per anti-loop rules, the same write was not repeated. Evidence is preserved in `docs/PROJECT_STATE.md` and this log.

### AmneziaWG compatibility delta

Created `research/upstreams/wireguard-family/AMNEZIAWG_DELTA.md`.

GitHub identifies `amneziawg-go` as fork-derived from WireGuard Go. Upstream documentation exposes an expanded and version-dependent configuration/compatibility surface beyond ordinary WireGuard. PVNetwork must therefore keep AmneziaWG as a separate versioned capability/schema rather than silently flattening it into WireGuard.

The research file intentionally records architecture, versioning, parser and interoperability implications rather than turning the repository into an operational traffic-evasion tuning guide.

### AmneziaWG platform repositories inspected

Current platform repositories were identified and pinned:

- `amnezia-vpn/amneziawg-windows@1326e9bbdc71be88ddcc20925e092c6f5b9513da`
- `amnezia-vpn/amneziawg-apple@e5410a539f28b8ce5dd1d060c45e4fa555e9a210`
- `amnezia-vpn/amneziawg-android@d6cd6647465a9a593aa9ccadbbd20c44bf600d5b`

Findings:

- Windows repository README describes an MIT-licensed embeddable tunnel library rather than a complete end-user GUI.
- Apple repository is an active Swift fork derived from WireGuard Apple; repository metadata reports MIT.
- Android repository is an active Kotlin project; repository metadata reports Apache-2.0.

A separate platform-reference file was rejected by the connector write filter, so the verified evidence is preserved in this log and Project State.

### Upstream failure lessons / tests

Created `research/upstreams/wireguard-family/LESSONS_AND_TESTS.md` using official source plus WireGuard mailing-list evidence.

Historical/current failure classes converted into PVNetwork regression requirements include:

- Android OS VPN authorization and Always-On ownership conflicts;
- reboot/restore-state reliability;
- Quick Settings/UI/background state synchronization;
- delayed network/DNS readiness at startup;
- sleep/resume and network/address-family changes;
- route-helper policy assumptions;
- Apple NetworkExtension workarounds and Store review/release latency increasing regression risk.

These reports span different OS/client versions; they are used as **failure classes**, not claims that every old upstream bug is still current.

### Current WireGuard-family files committed in this work unit

- `SOURCE_REVISIONS.md`
- `CORE_ARCHITECTURE.md`
- `ANDROID_CLIENT.md`
- `APPLE_CLIENT.md`
- `AMNEZIAWG_DELTA.md`
- `LESSONS_AND_TESTS.md`

A synchronized rewrite of the family README was also attempted but rejected by the connector. Do not retry the identical write.

### Current conclusion

WireGuard/AmneziaWG research is materially deeper but still **not COMPLETE-RESEARCH-v1**. Remaining completion fields include dependency/SBOM review, current release/fix mapping, complete Windows-dossier persistence, platform-specific AmneziaWG deltas, full assets/UI/accessibility inventory, Store/package review, protocol-entry links and real interoperability/performance evidence.

### Next exact action

1. Synchronize the completeness tracker if connector allows it.
2. Update `AGENTS.md` with this exact work-unit handoff and next action.
3. Continue remaining WireGuard/AmneziaWG completion gaps, then move to the next highest-value incomplete family based on actual tree/tracker state.

---

## Update format for future research

Append new entries with:
- Date
- Question investigated
- Sources/projects checked
- Finding
- Confidence or uncertainty
- Impact on PVNetwork
- Files updated
- Next exact action

Do not erase failed or superseded research. Mark it as superseded so future agents do not repeat dead ends blindly.
