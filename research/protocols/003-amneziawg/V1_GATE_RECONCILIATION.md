# Entry 003 — AmneziaWG — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research layer only; no implementation, runtime, Store, device, or production claim.

Primary evidence base: `research/upstreams/wireguard-family/` plus `research/upstreams/wireguard-family/reference-v2/`.

AmneziaWG is treated as a versioned WireGuard-derived implementation family with its own source, config-generation and platform behavior. It is not collapsed into standard WireGuard and PVNetwork does not reimplement cryptography/packet behavior from memory.

## 20-gate result

| # | Original v1 gate | Result | Evidence |
|---:|---|---|---|
| 1 | Top clients/implementations identified and justified | PASS | `AMNEZIAWG_PLATFORMS.md`, `SOURCE_REVISIONS.md`, `SUPPORT_REUSE_DECISIONS.md` |
| 2 | Canonical sources pinned | PASS | `SOURCE_REVISIONS.md` pins amneziawg-go plus Android, Apple, Windows client/tunnel repositories |
| 3 | Licenses reviewed | PASS | component-specific MIT/Apache evidence and the explicitly unresolved Windows tunnel path-level license confirmation are recorded instead of inferred |
| 4 | Complete source-tree references captured | PASS | recursive-tree/source references in `SOURCE_REVISIONS.md` and platform dossiers |
| 5 | Languages/build systems mapped | PASS | Go/Kotlin/Swift/Windows component and module/build boundaries documented |
| 6 | Architecture mapped | PASS | `CORE_ARCHITECTURE.md`, `AMNEZIAWG_DELTA.md`, `AMNEZIAWG_PLATFORMS.md`, platform source dossiers |
| 7 | Core/engine integration mapped | PASS | portable Go, platform wrappers/drivers and generation-specific fields are separated |
| 8 | UI/menu map completed | PASS | platform dossiers plus `reference-v2/CLIENT_UI_AND_MENUS.md` include AWG-specific controls and platform distinctions |
| 9 | Config/import/export mapped | PASS | base WireGuard config plus versioned AWG fields, unknown-field preservation and generation validation are documented |
| 10 | Persistence/secrets mapped | PASS | key/PSK storage remains platform-protected; AWG extension metadata is kept separate from secrets |
| 11 | Platform integrations mapped | PASS | Android, Apple, Windows, userspace/kernel/Linux boundaries are documented with exact source pins where available |
| 12 | Logs/diagnostics mapped | PASS | engine/platform version, generation, status/error and redaction requirements are recorded |
| 13 | Asset/screenshot references mapped | PASS | platform resource/source references are mapped; third-party visual assets are reference-only absent explicit reuse rights |
| 14 | Meaningful forks reviewed | PASS | AWG itself is the meaningful WireGuard-derived family; component relationships and divergence are explicitly audited |
| 15 | Important issues/PRs/releases/advisories reviewed | PASS | `LESSONS_AND_TESTS.md`, `AMNEZIAWG_DELTA.md`, current source-head fixes and v2 generation/interoperability issue evidence |
| 16 | Relevant forums/docs reviewed | PASS | canonical AmneziaWG repositories/readmes plus linked protocol/source references in v2 index |
| 17 | Tests/CI reviewed | PASS | source/build/test surfaces and generation-specific regression requirements are documented, including uncommon handshake/cookie paths |
| 18 | Store/privacy/security implications reviewed | PASS | Apple entitlement/build provenance, Android/Windows packaging, SBOM/dependency and secret-handling boundaries are documented |
| 19 | PVNetwork reuse decision documented | PASS | `SUPPORT_REUSE_DECISIONS.md`: maintained AWG implementations behind a versioned capability/adapter, no silent fallback to standard WireGuard |
| 20 | Uncertainties explicitly listed | PASS | exact cross-generation interoperability, Store/source correspondence, runtime/device tests and Windows tunnel file-level license confirmation remain explicit |

## Important uncertainty treatment

The exact reusable-path license for the separate `amneziawg-windows` tunnel/library remains a recorded component-level uncertainty. This does **not** make the whole research entry incomplete because the project does not infer or approve that path for reuse; the uncertainty is explicitly preserved and the reuse decision is conditional.

`reference-v2/ENTRY_002_003_V2_GATE_RECONCILIATION.md` additionally records all 16 second-layer source/reference categories as evidence-backed while separating external runtime/signing/interoperability receipts.

# Formal result

All 20 written original-v1 completion gates are evidence-backed with uncertainties explicitly retained.

**Entry 003 may be promoted to `COMPLETE-RESEARCH-v1`.**
