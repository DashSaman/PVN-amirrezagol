# C00 — Executable baseline intake

- `task_id`: C00
- `status`: BLOCKED_EXTERNAL for private publication; local implementation and test preparation IN_PROGRESS.
- Observed: 2026-11-07T11:50:20Z / 2026-11-07 15:20:20 Asia/Tehran.
- `source_repo`: https://github.com/DashSaman/PVN-amirrezagol (PUBLIC).
- `base_sha`: 78dcd4511dbb830a999cc8c1796f2a38f88587e8.
- `tested_sha`: c85e8b598f452584597ccc9feb90c5fec44dd570 (documentation checks only).
- Authority: [PR #9](https://github.com/DashSaman/PVN-amirrezagol/pull/9), `docs/preflight-2026-11-06/00-integration-handoff.md`, especially §§6, 8, 10, 12, 13.
- `integration_sha`: not yet applicable; no product changes integrated.
- `reviewer`: pending independent review. PR URL is recorded in the publication receipt below when available.

## Verified intake

The current main HEAD equals the audited baseline. PR #9 remains open, non-draft, unmerged and mergeable at c85e8b5. All six source PRs remain open/unmerged with the exact heads in §15:

| PR | Head SHA |
|---|---|
| [#3](https://github.com/DashSaman/PVN-amirrezagol/pull/3) | 3f1d2919be00b494466f85850c8dc7c62c9ab12c |
| [#5](https://github.com/DashSaman/PVN-amirrezagol/pull/5) | 01033f4d46902229d283968e40727fe6417a08a5 |
| [#6](https://github.com/DashSaman/PVN-amirrezagol/pull/6) | 31f0e5fed1ccd0325806c436161465ec564e8dd3 |
| [#7](https://github.com/DashSaman/PVN-amirrezagol/pull/7) | bc53bd0aa3a6e4ff0b586cb89e7826f2193641ac |
| [#8](https://github.com/DashSaman/PVN-amirrezagol/pull/8) | d11471adfb1b4db676c4cf3cdc652eb85b44c496 |
| [#4](https://github.com/DashSaman/PVN-amirrezagol/pull/4) | 5c131bea2493605c4b95c9d9f6b17a572208dc0f |

No source report was merged. No general framework, competitor or protocol research was restarted. The closed V1/V2 research phase remains separate from this incomplete implementation phase.

`AI_START_HERE.md`, `AGENTS.md`, `AGENTS_LATEST.md`, execution contract, research state and newer handoff were reconciled. Old research/task pointers do not override the November handoff and the user's implementation instruction. KMP/Compose and §6 exclusive path ownership remain binding. Codex does not edit the Z.ai UI/presentation/string/test paths or create a placeholder UI there.

## Commands and outcomes

| Command/check | Exit/outcome | Coverage |
|---|---|---|
| `git rev-parse HEAD` | 0; c85e8b598f452584597ccc9feb90c5fec44dd570 | Isolated integration baseline |
| `git status --short --branch` | 0; clean before this receipt | Local branch `impl/2026-11-06/codex-core` |
| `python scripts/agent_state.py verify --require-complete` | 0; strict research structure/completion PASS | V1 93/93, V2 93/93; no product tests |
| `git diff --check` | 0 | Whitespace only |
| GitHub repository and PR metadata | Observed through connected GitHub account | Main, visibility, six pinned report heads, #9 |
| JDK `java -version` / `javac` | 21.0.10 available | Android Studio JBR; PATH Java alone is obsolete JRE8 |
| Exact Gradle9.5 discovery | Missing initially; provisioning in progress | Cached9.7.1 is not silently substituted |
| Android SDK inventory | Platforms34/35, build-tools35.0.0, command-tools12.0 | Required platforms36/37 absent initially |
| `adb devices -l` | 0; zero devices | No physical or emulator execution |

Application tests executed: **0**. Application build: **NOT RUN** at this intake. No skipped/fixture-free result is credited as a pass. No APK, signer, CI product-run URL, marker, traffic capture or physical-device receipt exists for the implementation revision yet.

## Isolation and ownership

The integration, C01 contract and C02 build workers have separate managed worktrees at the same handoff commit. Each owns disjoint paths; only Codex integrates finished commits serially. Planned product branches remain `impl/2026-11-06/codex-core` and `impl/2026-11-06/zai-ui`. **They are not falsely recorded as provisioned in a private remote.** Local child branches do not satisfy that remote gate.

This public checkpoint contains documentation only. Product source commits remain local until B01 resolves. The legacy research ledger retains its completed research meaning; `docs/IMPLEMENTATION_PHASE_STATE.json` records this phase as incomplete. The foreground activity file is outside the new §6.1 allowlist, so it is left unchanged rather than silently broadening write ownership.

## External blockers and next actions

- **B01 / product owner:** connected-account inventory and targeted private repository searches found no verified private PVNetwork destination. Related accessible product repositories are public. A private URL, authorized access and base/rules must be verified before proprietary pushes or product PRs. No existing visibility, licensing or code location was changed. The user instructed continued work; independent local code/test work proceeds under §13.
- **B02 / environment owner + Codex:** exact Gradle9.5.0 and SDK36/37 are being provisioned. An official Gradle checksum was retrieved; no unverified download is executed. Network and SDK license/tool availability are recorded by C02 as they are resolved. Toolchain mismatch is not permission for an unrecorded downgrade.
- **B04/B11 / phone and fixture operator:** no connected Android device or controlled encrypted peer/origin was supplied. Native and separate-UID TUN acceptance remain unavailable.
- **B03/B07/B12:** real commerce remains UNCONFIGURED; no production endpoints, payment, lease or entitlement are invented.
- **B05/B06/B09:** release signing custody, exact shipping license/native provenance sign-off and owner-approved branding remain open. Debug signing is not release signing.

Next executable work: C01 frozen product/platform contract and behavior tests; C02 wrapper/native cache validation and provenance; C33 deterministic stop-cleanup reproduction after the exact JVM toolchain is available. C03/C18/C12 follow the contract; other P0 work follows §8 dependencies. A missing phone stops device claims, not independent defect repair.

## Publication receipt

Pending. This checkpoint is eligible for a documentation-only public PR; proprietary implementation is not.
