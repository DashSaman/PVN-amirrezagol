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

### Next research actions

- Expand the technology matrix with source, license, platform feasibility, and limitations.
- Build a dedicated dependency/license matrix.
- Build a competitor lessons database.
- Research current store/platform constraints.
- Select an initial implementation component set only after enough evidence exists.
- Keep `docs/PROJECT_STATE.md` updated after every meaningful work unit.

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
