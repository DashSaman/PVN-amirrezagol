# PVNetwork Project State

Last synchronized: 2026-08-14

## Repository

- Repository: `DashSaman/PVN-amirrezagol`
- Default branch: `main`
- Product: **PVNetwork**
- Current phase: **Research / requirements / architecture**
- Production application code: **Not started**
- Research campaign scope: **93 numbered entries** in `docs/PROTOCOL_MATRIX.md`

## Source of truth / recovery order

A future AI must read `AI_START_HERE.md` first, then `AGENTS.md`, this file, `docs/RESEARCH_LOG.md`, `research/RESEARCH_COMPLETENESS.md`, the relevant numbered protocol dossier, the relevant shared upstream dossier, and recent Git history/tree.

Important: the large completeness tracker can occasionally lag a newer committed dossier because GitHub connector write filtering has rejected some large updates. Never infer “not researched” from one stale tracker row without checking the actual tree/history.

## Current objective

Complete evidence-backed developer research for all 93 entries before pretending the product supports them. Each entry ultimately needs the completion contract in `research/PROTOCOL_RESEARCH_TEMPLATE.md`: sources/revisions, license, tree, architecture/core, UI, config, storage/secrets, platform integration, diagnostics, assets, forks, issues/releases/forums, tests, Store/privacy implications and an explicit PVNetwork reuse/support decision.

## Research infrastructure completed

- `PVNETWORK_MASTER_CONTEXT.md`
- `AI_START_HERE.md`
- `AGENTS.md`
- `docs/PROTOCOL_MATRIX.md`
- `docs/RESEARCH_LOG.md`
- `research/AI_RESEARCH_CAMPAIGN.md`
- `research/PROTOCOL_RESEARCH_TEMPLATE.md`
- `research/RESEARCH_COMPLETENESS.md`
- `research/SOURCE_MIRROR_POLICY.md`
- numbered protocol research structure under `research/protocols/`
- shared upstream/client research under `research/upstreams/`

`AGENTS.md` now requires a continuous handoff note so meaningful work remains resumable from GitHub even if the current chat stops unexpectedly.

## Shared upstream research present on `main`

Verified shared directories currently include:

- `research/upstreams/openvpn-family/`
- `research/upstreams/wireguard-family/`
- `research/upstreams/openconnect-family/`
- `research/upstreams/softether-family/`
- `research/upstreams/hysteria-family/`
- `research/upstreams/mesh-overlay-family/`
- `research/upstreams/xray-family/`
- `research/upstreams/client-references/`

The presence of `xray-family/` is newer than some text in the tracker; check actual repository state before repeating old work.

## High-value research already performed

### OpenVPN family

Deep research exists for OpenVPN 3, official Connect behavior, Windows GUI, Tunnelblick, Pritunl license restrictions and issue-derived regression lessons. Android ics-openvpn source was also inspected, but a dedicated detailed file remained connector-blocked.

### Client/reference ecosystem

Source/license/architecture research has started or been persisted for major references including Clash Verge Rev, FlClash, Karing, NekoBox, Throne, v2rayN, Amnezia Client, Happ and others. License findings must be taken from pinned source, not memory.

### WireGuard / AmneziaWG family — current work unit

The family was upgraded from a README-only skeleton to source-pinned research.

Committed files now include:

- `research/upstreams/wireguard-family/SOURCE_REVISIONS.md`
- `research/upstreams/wireguard-family/CORE_ARCHITECTURE.md`
- `research/upstreams/wireguard-family/ANDROID_CLIENT.md`
- `research/upstreams/wireguard-family/APPLE_CLIENT.md`
- `research/upstreams/wireguard-family/AMNEZIAWG_DELTA.md`
- `research/upstreams/wireguard-family/LESSONS_AND_TESTS.md`

Pinned research revisions include:

- `WireGuard/wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0`
- `WireGuard/wireguard-windows@4e6726c23ae9c5cb58e0c9910f3b7515621d133d`
- `WireGuard/wireguard-android@e7b3a3c118836e112620b1302a8ba1873ad4daac`
- `WireGuard/wireguard-apple@2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`
- `amnezia-vpn/amneziawg-go@1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`

Additional current platform-source research identified:

- `amnezia-vpn/amneziawg-windows@1326e9bbdc71be88ddcc20925e092c6f5b9513da` — reviewed README describes an MIT-licensed embeddable tunnel library rather than a complete end-user GUI.
- `amnezia-vpn/amneziawg-apple@e5410a539f28b8ce5dd1d060c45e4fa555e9a210` — active Swift fork derived from WireGuard Apple; GitHub metadata reports MIT.
- `amnezia-vpn/amneziawg-android@d6cd6647465a9a593aa9ccadbbd20c44bf600d5b` — active Kotlin platform repository; GitHub metadata reports Apache-2.0.

A dedicated platform-reference write for these three AmneziaWG repositories was connector-blocked, so the verified evidence is recorded here rather than hidden.

## WireGuard architectural conclusions so far

- PVNetwork should expose a stable Core Adapter above platform-specific WireGuard implementations, not bind UI to one engine/process.
- Standard WireGuard and AmneziaWG should share product-level concepts but remain distinct versioned compatibility capabilities/config schemas.
- Import/export format, canonical PVNetwork profile, protected persistence and runtime engine configuration must remain separate layers.
- Windows source demonstrates protected DPAPI-based persisted configuration and service/UI separation.
- Android source demonstrates a `Backend` abstraction with multiple backend paths, separate settings DataStore and profile config store.
- Apple source demonstrates app + NetworkExtension + adapter/shared-model architecture and Keychain-protected configuration references.
- Do not reimplement protocol cryptography.

## WireGuard failure classes already converted to regression requirements

Official source/mailing-list research identified recurring classes worth testing regardless of whether each historical bug remains current:

- Android OS VPN authorization / Always-On conflicts;
- reboot/restore-state behavior;
- Quick Settings/UI/background state synchronization;
- network readiness and delayed name resolution;
- Wi-Fi/cellular/address-family transitions;
- sleep/resume and stale runtime state;
- route-helper policy assumptions;
- Apple NetworkExtension lifecycle workarounds and Store release latency/regression risk.

See `research/upstreams/wireguard-family/LESSONS_AND_TESTS.md`.

## Connector/documentation blockers

The GitHub connector sometimes rejects legitimate networking research writes. Current examples:

- dedicated detailed WireGuard Windows dossier write;
- a smaller Windows source/UI-map write;
- synchronized rewrite of the WireGuard family README;
- AmneziaWG platform-reference dossier;
- older known gaps include detailed ics-openvpn, strongSwan and some Xray/Mihomo research writes.

Anti-loop rule: do not repeat an identical blocked write. Keep verified evidence in other safe handoff/research files and continue independent work.

## Not completed / no false claims

- No protocol is implemented by PVNetwork yet.
- No production UI/build/package exists.
- No automated PVNetwork test suite exists yet.
- No E2E or real-device compatibility evidence exists yet.
- No Store submission is approved.
- No research entry is `COMPLETE-RESEARCH-v1` merely because one client/core was inspected.
- License analysis is engineering research, not final legal advice/sign-off.

## Next exact actions

1. Synchronize `docs/RESEARCH_LOG.md`, `research/RESEARCH_COMPLETENESS.md` where connector permits, and `AGENTS.md` with this work unit.
2. Finish remaining WireGuard-family evidence: current release/fix mapping, dependency/SBOM review, platform-specific AmneziaWG deltas, assets/UI completeness and protocol-entry links for 002/003.
3. Then continue to the next highest-value incomplete family from the real tracker/tree rather than restarting research already committed.
4. Keep every meaningful work unit persisted in research files + project state + AGENTS handoff.

## Handoff rule

Repository evidence wins over chat memory. Do not mark a feature supported, tested or production-ready without PVNetwork implementation/test evidence.