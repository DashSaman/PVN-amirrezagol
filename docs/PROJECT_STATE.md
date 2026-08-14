# PVNetwork Project State

Last synchronized: 2026-08-14

## Repository

- Repository: `DashSaman/PVN-amirrezagol`
- Branch: `main`
- Product: **PVNetwork**
- Phase: **research / requirements / architecture**
- Production implementation: **not started**
- Research scope: **93 numbered entries** in `docs/PROTOCOL_MATRIX.md`

## Recovery order

A new AI must read:

1. `AI_START_HERE.md`
2. `AGENTS.md`
3. this file
4. `docs/RESEARCH_LOG.md`
5. the newest `docs/RESEARCH_CAMPAIGN_STATUS_*.md` file
6. `research/RESEARCH_COMPLETENESS.md`
7. relevant numbered and shared research dossiers
8. recent Git history and the actual repository tree

The completeness tracker can lag newer commits because some large connector writes are rejected. Do not restart work solely from a stale tracker row.

## Current research state

Major shared research exists for OpenVPN, WireGuard/AmneziaWG, OpenConnect, SoftEther, Hysteria, mesh/overlay, Xray and major client references.

### WireGuard / AmneziaWG

Deep committed evidence now covers source pins, portable-core architecture, Android, Apple, AmneziaWG compatibility/versioning and issue-derived regression requirements. Windows source-level research and AmneziaWG platform pins are preserved in Research Log/AGENTS where standalone writes were blocked.

### OpenConnect / Enterprise — latest work unit

Latest detailed status:

`docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_OPENCONNECT.md`

Shared evidence now includes:

- `research/upstreams/openconnect-family/SOURCE_PIN.md`
- `VENDOR_COMPATIBILITY_MATRIX.md`
- `LESSONS_AND_TESTS.md`
- synchronized shared `README.md`

Numbered enterprise dossiers 017–024 have been linked to the current shared research. Entry 016 remains an explicit connector-write documentation gap after two materially different update attempts; its research conclusions remain in the shared matrix/status file.

## Current architecture direction

- Keep a stable PVNetwork Core Adapter above selected upstream/native engines.
- Keep UI/business state independent from private engine internals.
- Do not reimplement mature protocol cryptography/security primitives.
- Keep import/export format, canonical profile model, protected persistence and runtime engine configuration separate.
- Enterprise compatibility must be vendor/version/capability based, not a single support boolean.

## No false completion

There is still no production PVNetwork application, build/package, automated PVNetwork test suite, E2E proof, real-device certification or Store approval. No research entry is `COMPLETE-RESEARCH-v1` merely because one upstream was inspected.

## Known connector gaps

Several legitimate networking-research writes have been rejected. Do not repeat the same blocked write unchanged. Use smaller accepted files, dated status snapshots, `docs/RESEARCH_LOG.md`, and `AGENTS.md` as recovery evidence.

## Next exact action

Continue from the latest OpenConnect status snapshot: current issue/MR-to-fix mapping by vendor, selected front-end/UI/credential-storage research, and dependency/SBOM/LGPL integration review. Then move to the next highest-value incomplete family from actual repository state.

At the end of every meaningful work unit, update Research Log, Project State/status snapshot, and AGENTS handoff.