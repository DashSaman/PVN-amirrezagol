# AGENTS.md — PVNetwork AI / Developer Operating Rules

This file defines mandatory operating rules for all AI agents and human developers working on `DashSaman/PVN-amirrezagol`.

## 1. First rule

Read `AI_START_HERE.md` before doing anything else.

Then read:

- `PVNETWORK_MASTER_CONTEXT.md`
- `docs/PROJECT_STATE.md`
- `docs/ROADMAP.md`
- `docs/ARCHITECTURE.md`
- `docs/PROTOCOL_MATRIX.md`
- `docs/RESEARCH_LOG.md`

## 2. Current phase

The repository is currently in the **research / requirements / architecture phase**.

Do not claim implementation, protocol support, successful builds, tests, store readiness, or production readiness unless repository evidence exists.

## 3. Documentation is persistent memory

Important discoveries must be committed to the repository.

Use:

- `docs/RESEARCH_LOG.md` for chronological research findings
- `docs/PROJECT_STATE.md` for the exact current handoff state
- `docs/PROTOCOL_MATRIX.md` for protocol/core status
- `docs/ARCHITECTURE.md` for architectural direction
- `docs/ROADMAP.md` for phase progression
- `PVNETWORK_MASTER_CONTEXT.md` for consolidated product context
- `AI_START_HERE.md` for the reusable master AI prompt and mandatory continuation rules

## 4. Evidence rules

Keep these states separate:

1. Researched
2. Candidate identified
3. License reviewed
4. Architecture approved
5. Implemented
6. Builds
7. Unit tested
8. Integration tested
9. E2E tested
10. Real-device tested
11. Store verified
12. Production verified

Never promote a feature without evidence.

## 5. Research rules

For current or changing facts, prefer current primary sources:

- official project repositories
- official project documentation
- official platform/store documentation
- release notes
- source code
- issue/PR history

When selecting an engine, investigate:

- protocol coverage
- current maintenance
- license
- security history
- supported platforms
- store compatibility
- embedding/redistribution model
- binary size
- performance
- dependency complexity
- known regressions

## 6. Competitor learning

Study mature clients and cores, including but not limited to:

- v2rayN
- v2rayNG
- Hiddify
- Happ
- Amnezia VPN
- Clash Verge Rev
- FlClash
- Mihomo Party
- Karing
- NekoBox
- Throne
- Xray-core
- Mihomo
- sing-box
- OpenConnect
- OpenVPN
- WireGuard
- SoftEther
- strongSwan
- Hysteria

Learn from architecture, bugs, issues, regressions, UX, routing, DNS, permissions, crash recovery, battery, and store publication problems.

Do not copy branding or incompatible-license code.

## 7. Branding

Product name: **PVNetwork**.

Use the exact supplied official logo when it becomes available in the repository.

Never generate a replacement brand identity without explicit owner approval.

## 8. Localization

Persian and English are mandatory first-class languages.

Persian must have proper RTL behavior.

Do not break:

- IP addresses
- ports
- URLs
- protocol identifiers
- hashes
- file paths
- logs

inside RTL layouts.

## 9. Platform scope

Long-term targets:

- Android
- Android tablets/foldables
- Android TV / Google TV
- Windows
- macOS
- iPhone/iPad
- Linux

Do not assume one networking implementation fits every operating system.

## 10. Store rules

Store policies change. Before release decisions, re-check official rules for:

- Google Play
- Android TV / Google TV
- Apple App Store
- Mac App Store where relevant
- Microsoft Store
- chosen Linux distribution channels

Never hardcode stale policy assumptions as eternal truth.

## 11. Architecture rule

Prefer a unified application layer and stable Core Adapter abstraction.

Do not couple UI directly to a specific VPN engine.

Do not implement cryptography from scratch.

## 12. Git discipline

Use meaningful commit messages, e.g.:

- `docs(research): compare enterprise VPN engines`
- `docs(protocols): expand protocol matrix`
- `docs(architecture): record core adapter decision`
- `feat(openvpn): add profile parser`
- `test(dns): add leak regression test`

Do not use meaningless messages such as `update`, `fix`, `stuff`.

## 13. Anti-loop rule

If the same exact approach fails twice, do not repeat it unchanged a third time.

Document failure evidence and change strategy.

## 14. Session end

Before ending a meaningful work unit:

1. Update relevant docs.
2. Update `docs/RESEARCH_LOG.md` if new research was performed.
3. Update `docs/PROJECT_STATE.md`.
4. Commit the work.
5. Record the next exact action.

## 15. Repository over chat

If chat history and repository state disagree, verify and prefer the repository and current primary-source evidence.

## 16. Continuous handoff rule

The owner requires every meaningful work unit to remain resumable from the repository. After each meaningful inspection, research unit, decision, file change, or blocker:

1. record the detailed evidence in the relevant research file and `docs/RESEARCH_LOG.md`;
2. keep `docs/PROJECT_STATE.md` synchronized with the real repository state;
3. add a compact session/handoff note to this file before ending the work unit;
4. include the exact next action so another AI can continue without chat history.

Do not log every low-level API call. Log meaningful work units and decisions.

### Current handoff — 2026-08-14

- Repository state was re-read from `main` instead of relying on chat memory.
- The current state files were found to lag some newer research directories, so future agents must verify the actual tree and recent history as well as trackers.
- Work is continuing from the highest-value incomplete shared research dossier.
- Detailed technical evidence belongs in `docs/RESEARCH_LOG.md` and the relevant `research/upstreams/` dossier.
- Next action: finish the current shared-family dossier, then synchronize the tracker and project state before moving to the next incomplete family.