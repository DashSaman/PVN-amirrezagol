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

### Current handoff — 2026-08-14 — WireGuard / AmneziaWG work unit

- Repository state was re-read from `main`; `docs/PROJECT_STATE.md` and parts of the completeness tracker were found to lag newer committed research. The actual tree/history must be checked before restarting any work.
- `docs/PROJECT_STATE.md` has now been synchronized with the real research tree and explicitly records connector-blocked documentation gaps.
- `docs/RESEARCH_LOG.md` now contains the detailed chronological WireGuard/AmneziaWG research entry.
- WireGuard family was selected because entries 002/003 were high-value but the shared dossier was previously close to README-only.
- New committed evidence under `research/upstreams/wireguard-family/`:
  - `SOURCE_REVISIONS.md` — commit `fb4e4a4d451a19b55129bad4e2acf5c5d6d1abb4`
  - `CORE_ARCHITECTURE.md` — commit `32eca180186c2b30032dc859443dd1805f3642fa`
  - `ANDROID_CLIENT.md` — commit `162a82a782bc8c9cbc4c7c98e60268a74299a824`
  - `APPLE_CLIENT.md` — commit `5221b0a071a516b46c2003dc1f2085b6b6871125`
  - `AMNEZIAWG_DELTA.md` — commit `325dee3f7f3109d529f3f2adf55a61eea6a1a4c8`
  - `LESSONS_AND_TESTS.md` — commit `cb7a1ad5cb8bc8ff01eb0a9ba2f2cff4c6116a24`
- Pinned upstream research now covers official WireGuard Go/Windows/Android/Apple and AmneziaWG Go, with additional current platform pins for AmneziaWG Windows/Apple/Android recorded in Project State and Research Log.
- Important architecture decisions from evidence: keep a stable PVNetwork Core Adapter above platform-specific engines; keep standard WireGuard and AmneziaWG as distinct versioned compatibility capabilities; separate import/export format, canonical profile model, protected persistence and engine runtime representation; do not reimplement cryptography.
- Windows source research verified service/UI separation and protected DPAPI persistence, but both a detailed Windows dossier and a smaller source/UI-map write were rejected by the GitHub connector. Do not retry the same writes unchanged.
- A synchronized rewrite of `research/upstreams/wireguard-family/README.md` was rejected; use the individual committed evidence files plus Project State/Research Log as current truth.
- A separate AmneziaWG platform-reference file was also rejected; the verified platform pins/license findings are preserved in Project State/Research Log.
- Official WireGuard mailing-list research was converted into regression classes in `LESSONS_AND_TESTS.md`: Android Always-On/permission conflicts, reboot/restore, control-surface state sync, delayed network/DNS readiness, network/sleep/address-family transitions, route-helper assumptions, and Apple NetworkExtension/Store release risks.
- An attempted full synchronization of `research/RESEARCH_COMPLETENESS.md` was rejected by the connector. Do not hide this or treat the stale row text as proof that the new WireGuard files do not exist.
- No protocol is implemented or production-tested by PVNetwork yet; this remains research evidence only.

### Next exact action

1. Finish remaining WireGuard-family completion gaps that can be documented safely: dependency/SBOM review, current release/fix-to-commit mapping, platform-specific AmneziaWG source deltas, assets/accessibility/UI completeness, and protocol-entry links for 002/003.
2. Attempt tracker synchronization only through a materially different/smaller safe strategy; do not repeat the blocked full rewrite.
3. Then choose the next highest-value incomplete family from the **actual tree + Project State + tracker**, not from chat memory.
4. At the end of the next work unit, update `docs/RESEARCH_LOG.md`, `docs/PROJECT_STATE.md`, and this handoff again.

## 17. Latest handoff pointer

The newest work unit is stored in:

`AGENTS_HANDOFF_2026-08-14_OPENCONNECT.md`

Every next AI must read that file after `AGENTS.md` before continuing. It contains the OpenConnect/Enterprise commits, connector blockers, current conclusions and exact next action. Detailed state is also in `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_OPENCONNECT.md` and the synchronized `docs/PROJECT_STATE.md`.