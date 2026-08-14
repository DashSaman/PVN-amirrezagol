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
`research/RESEARCH_COMPLETENESS.md` is authoritative. No entry is currently `COMPLETE-RESEARCH-v1`. High-priority families are `IN-RESEARCH`; others remain `SKELETON`, `RESERVED`, `EVIDENCE-GAPS`, or `PENDING`.

### Connector limitation recorded
Some legitimate detailed research-file writes are rejected by the GitHub connector safety layer. The no-loop rule applies: do not repeat the same blocked write unchanged. Preserve evidence through smaller safe files and keep gaps explicit in the tracker.

### Next exact action
Continue from the tracker, finishing the highest-value incomplete shared/client dossier to the full 21-section template before calling it complete. Prioritize OpenVPN completion, WireGuard platform clients, Xray/client ecosystem persistence, and top cross-platform client UI/storage/issues research.

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
