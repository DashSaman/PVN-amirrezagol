# Entry 002 — WireGuard — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research layer only; no implementation, runtime, Store, device, or production claim.

Primary evidence base: `research/upstreams/wireguard-family/` plus `research/upstreams/wireguard-family/reference-v2/`.

Canonical provenance rule: GitHub WireGuard repositories are treated as mirrors where their descriptions point to canonical `git.zx2c4.com` upstreams; immutable mirror SHAs are retained for reproducible research.

## 20-gate result

| # | Original v1 gate | Result | Evidence |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | `README.md`, `SUPPORT_REUSE_DECISIONS.md`, official Windows/Android/Apple dossiers |
| 2 | Canonical sources pinned | PASS | `SOURCE_REVISIONS.md` pins wireguard-go, Windows, Android, Apple and records canonical provenance |
| 3 | Licenses reviewed | PASS | `SOURCE_REVISIONS.md`, `DEPENDENCIES_SBOM.md`, per-platform dossiers; component licenses kept separate |
| 4 | Complete source-tree references captured | PASS | recursive-tree URLs in `SOURCE_REVISIONS.md` |
| 5 | Languages/build systems mapped | PASS | `SOURCE_REVISIONS.md`, `CORE_ARCHITECTURE.md`, platform dossiers |
| 6 | Architecture mapped | PASS | `CORE_ARCHITECTURE.md`, Windows/Android/Apple dossiers, v2 data-path files |
| 7 | Core/engine integration mapped | PASS | kernel/userspace/platform-native boundaries in `CORE_ARCHITECTURE.md` and `SUPPORT_REUSE_DECISIONS.md` |
| 8 | UI/menu map completed | PASS | Windows/Android/Apple dossiers plus `reference-v2/CLIENT_UI_AND_MENUS.md` |
| 9 | Config/import/export mapped | PASS | `.conf`, QR/import/export and platform handling in client/UI/source dossiers |
| 10 | Persistence/secrets mapped | PASS | Windows DPAPI pattern, platform secure-storage requirements, key/PSK separation in family dossiers |
| 11 | Platform integrations mapped | PASS | Windows, Android, Apple, Linux kernel/userspace paths and target limitations are documented |
| 12 | Logs/diagnostics mapped | PASS | Windows ring logger/service diagnostics, platform status and redaction guidance in dossiers |
| 13 | Asset/screenshot references mapped | PASS | source resource/localization trees and official client docs are reference sources; assets are not copied without rights |
| 14 | Meaningful forks reviewed | PASS | AmneziaWG is explicitly treated as a separate maintained derivative (entry 003), not silently folded into WireGuard |
| 15 | Important issues/PRs/releases/advisories reviewed | PASS | `LESSONS_AND_TESTS.md`, `DEPENDENCIES_SBOM.md`, source activity/pins and v2 security audits |
| 16 | Relevant forums/docs reviewed | PASS | canonical WireGuard protocol/project documentation and source references linked in `reference-v2/REFERENCE_INDEX.md` |
| 17 | Tests/CI reviewed | PASS | source tests/build/CI and derived regression requirements in `LESSONS_AND_TESTS.md` and platform dossiers |
| 18 | Store/privacy/security implications reviewed | PASS | Apple entitlement/build provenance, Android/Windows packaging, key storage and supply-chain files |
| 19 | PVNetwork reuse decision documented | PASS | `SUPPORT_REUSE_DECISIONS.md`: official/native stack first behind a PVNetwork-owned adapter |
| 20 | Uncertainties explicitly listed | PASS | exact shipped pins, Store/source correspondence, runtime/device/interoperability and release-freeze questions remain explicit |

## Cross-check against deeper reference work

`reference-v2/ENTRY_002_003_V2_GATE_RECONCILIATION.md` already finds all 16 second-layer source/reference categories evidence-backed for WireGuard. That deeper source/reference coverage reinforces, but does not replace, the original 20-gate v1 audit above.

External install/device/Store/interoperability receipts remain future implementation/certification evidence and are not silently promoted into research gates.

# Formal result

All 20 written original-v1 completion gates are evidence-backed.

**Entry 002 may be promoted to `COMPLETE-RESEARCH-v1`.**
