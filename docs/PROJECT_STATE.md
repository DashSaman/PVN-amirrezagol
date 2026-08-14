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
3. the newest `AGENTS_HANDOFF_*.md` pointed to by `AGENTS.md`
4. this file
5. `docs/RESEARCH_LOG.md`
6. the newest `docs/RESEARCH_CAMPAIGN_STATUS_*.md` snapshot
7. `research/RESEARCH_COMPLETENESS.md`
8. relevant numbered/shared dossiers
9. recent Git history and actual repository tree

The completeness tracker can lag newer commits because some large connector writes are rejected. Repository tree, dated status snapshots and newest AGENTS handoff must be checked before restarting work.

## Priority order

### Priority 1 — original campaign

Continue the existing source/client/core/license/architecture/issues/platform research toward `COMPLETE-RESEARCH-v1` across the original 93-entry campaign.

### Priority 2 — exhaustive full reference

After the original research gates are satisfied for the relevant entry/family, execute:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

This second layer requires server implementations/installers, server/client OS install matrices, complete server/client UI/menu maps, cryptography, data path/wire flow, ports/transports/handshake and deployment topologies.

Do not allow the second layer to make unfinished original research disappear.

## Current research state

Major shared research exists for:

- OpenVPN;
- WireGuard / AmneziaWG;
- OpenConnect / Enterprise;
- SoftEther;
- Hysteria;
- mesh/overlay;
- Xray and modern-proxy client references;
- several major GUI/client projects.

No production protocol support is claimed.

## OpenConnect / Enterprise — family v1 handoff ready

Newest detailed status:

`docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_OPENCONNECT_V1_CLOSURE_2.md`

The shared family now has evidence for:

- canonical source/release/API provenance;
- vendor compatibility 016–024;
- issue/MR/fix history and regression lessons;
- dependency/LGPL integration architecture;
- upstream test/CI inventory;
- configuration/profile/session/secret separation;
- public API lifetime/callback rules;
- OpenConnect GUI frontend architecture and screen/storage mapping;
- NetworkManager Linux frontend, D-Bus/service and libsecret ownership;
- security/advisory history and release gates;
- packaging/distribution concerns by platform;
- asset/screenshot/reference policy;
- performance/resource evidence framework;
- research-stage support/reuse decisions.

Shared index:

`research/upstreams/openconnect-family/README.md`

State:

`V1-HANDOFF-READY / NOT IMPLEMENTED`

This means the family has a reasonable original-research handoff with explicit residual gaps; it does **not** mean any numbered vendor protocol is implemented or certified.

### OpenConnect residual gaps kept explicit

- authoritative materialized full v9.21 source-archive manifest remains tool-blocked;
- stronger machine-readable current OpenConnect GUI main/v1.6.2 source materialization;
- current running-client screenshots beyond source/resource references;
- exact dependency-advisory/SBOM review must be repeated for the build actually selected;
- reproducible exact performance benchmarks remain incomplete;
- vendor/server certification needs future implementation plus real lab versions;
- entry 016 Cisco README remains a connector-write documentation blocker, but Cisco evidence exists in shared files.

Do not loop on these blockers or erase them.

## WireGuard / AmneziaWG

Deep committed evidence covers source pins, core architecture, Android, Apple, AmneziaWG compatibility/versioning and issue-derived regression requirements. Windows source-level research and some Amnezia platform evidence exist in logs/handoffs where standalone writes were connector-blocked.

This family is materially researched but not yet `COMPLETE-RESEARCH-v1`.

## Current architecture direction

- Use stable product-owned Core Adapter contracts above selected upstream/native engines.
- Keep UI/business state independent from private engine internals.
- Do not reimplement mature protocol cryptography/security primitives.
- Keep import/export, canonical profile model, protected persistence and runtime engine configuration separate.
- Keep reusable secrets, non-secret remembered choices and temporary authenticated session material in separate storage classes.
- Treat enterprise/vendor compatibility as capability/version evidence, not one Boolean.
- Platform-specific services/extensions/drivers are expected behind one product-facing architecture.

## No false completion

There is still no production PVNetwork application, build/package, PVNetwork automated test suite, E2E proof, real-device certification or Store approval.

A family-level v1 handoff state is a research milestone only.

## Known connector gaps

Some legitimate detailed networking-research writes are rejected by the connector. Apply anti-loop:

- do not repeat the same rejected write unchanged;
- preserve evidence in smaller accepted files, dated status snapshots and AGENTS handoffs;
- keep blockers explicit.

## Next exact action

1. Create the newest AGENTS handoff for the OpenConnect v1 closure and point `AGENTS.md` to it.
2. Select the next highest-value unfinished **original `COMPLETE-RESEARCH-v1` family** from actual repository state; current likely candidates include Xray/modern-proxy ecosystem or remaining WireGuard-family closure work.
3. Continue original research before mass `COMPLETE-REFERENCE-v2` expansion.
4. For every meaningful work unit, update technical dossiers plus dated status/state and the newest AGENTS handoff.
