# PVNetwork — Exhaustive Protocol / Client Research Template

> This template is mandatory for every numbered entry in `docs/PROTOCOL_MATRIX.md`. A protocol/technology dossier is **not complete** until every applicable section below is evidence-backed.

## 1. Identity
- Matrix number:
- Name:
- Classification: VPN / proxy / enterprise compatibility / mesh / site-to-site / security layer / transport / other
- Current research status:
- Last reviewed date:
- Researcher/agent:

## 2. Top clients and implementations
For every serious client/implementation candidate record:
- Project/product name
- Canonical website
- Canonical source repository, if public
- Upstream owner/maintainer
- Latest reviewed release/tag
- Pinned commit SHA used for source analysis
- Project activity/maintenance status
- Supported platforms
- Why this client belongs in the top-client set
- Whether it is a behavioral reference, reusable code candidate, or both

## 3. License and legal reuse audit
For every upstream:
- License name and exact license file/path
- Copyright holder(s)
- Commercial use allowed?
- Redistribution allowed?
- Modification allowed?
- Source-disclosure/copyleft obligations
- Dynamic/static linking implications where relevant
- Network-use obligations where relevant
- Attribution/NOTICE obligations
- Trademark/branding restrictions
- Store-distribution concerns
- Final reuse classification: `REUSE-CANDIDATE`, `REFERENCE-ONLY`, `NEEDS-LEGAL-REVIEW`, `DO-NOT-COPY`

Always follow `research/SOURCE_MIRROR_POLICY.md`.

## 4. Complete repository/source-tree inventory
For each public upstream:
- Repository URL
- Pinned SHA/tag
- Default branch at review time
- Complete recursive tree/API URL or stored path manifest
- Top-level directories and purpose
- Important source subtrees
- Build scripts
- CI workflows
- Tests
- Packaging/installers
- platform-specific code
- third-party/vendor directories
- localization resources
- assets/screenshots/icons
- documentation/examples

Do not paste entire copyrighted source trees into PVNetwork by default. Record a complete manifest/reference and only vendor files after license approval.

## 5. Programming languages and build system
Record:
- Main programming language(s)
- UI framework(s)
- Core/engine language
- Native bindings/FFI
- Build system
- Package/dependency manager
- Platform-specific toolchains
- Generated code
- Minimum runtime/OS requirements

## 6. Architecture
Document with file/module evidence:
- Process/service architecture
- UI layer
- application/business layer
- core/engine layer
- adapter/native platform layer
- IPC/RPC/control interfaces
- privilege separation
- background services/extensions
- configuration parser/generator
- state machine
- update subsystem
- diagnostics/log subsystem
- dependency boundaries

## 7. Engine/core details
Record:
- Engine name and upstream
- Embedded library vs subprocess vs OS-native API
- Engine version management
- Core startup/shutdown ownership
- Configuration handoff model
- Status/statistics interface
- Error propagation
- crash/restart behavior
- platform-specific engines or variants
- whether cryptography is upstream/native rather than reimplemented

## 8. UI, menus, navigation, and states
Create a full UI map from source/screenshots/docs:
- Main navigation
- Home/dashboard
- profile/server list
- add/import workflow
- subscription/account workflow
- connection controls
- routing/DNS screens
- advanced settings
- logs/diagnostics
- about/update screens
- dialogs/context menus
- tray/menu-bar behavior
- notifications
- onboarding/permissions
- empty/loading/error/offline states
- TV/desktop/mobile differences
- accessibility and keyboard/D-pad support

For each screen/menu item, reference the source file/resource or official screenshot/docs where possible.

## 9. Configuration formats
Record all applicable:
- file extensions
- URI schemes
- JSON/YAML/TOML/INI/custom schema
- QR format
- subscription format
- import/export behavior
- default values
- validation rules
- migration/versioning
- unsupported/lossy conversions
- extension/vendor fields

## 10. Persistence and secure storage
Document:
- profile database/files
- preferences/settings storage
- subscription storage
- cache
- logs
- secrets/credentials
- private keys/certificates
- OS keychain/keystore integration
- encryption-at-rest behavior
- migration/backup/restore
- deletion behavior
- sensitive-data redaction

## 11. Connection lifecycle — architectural analysis
Document at a safe architectural level:
- profile selection
- validation
- permission/privilege acquisition
- engine preparation
- connection-state transitions
- tunnel/interface ownership
- route/DNS integration
- health/status reporting
- reconnect/network-change behavior
- disconnect/cleanup
- crash recovery

Do not turn the research archive into operational bypass instructions; focus on software architecture and interoperability.

## 12. Platform integration
For each supported platform document:
- Windows
- Android
- Android TV/Google TV
- iOS/iPadOS
- macOS
- Linux

Record native APIs, service/extension model, permissions, packaging, background restrictions, architecture support (x64/ARM64/etc.), and known platform limitations.

## 13. Logs, diagnostics, and observability
Record:
- log locations
- log levels/categories
- redaction behavior
- diagnostic export
- crash reporting
- connection statistics
- developer/debug modes
- privacy implications

## 14. Images, UI assets, and visual references
Record:
- official screenshots
- repo screenshot/assets directories
- icons/logos
- store screenshots
- diagrams
- relevant UI captures in docs/issues
- license/copyright status of each asset category

Store links/metadata by default. Do not copy third-party imagery unless reuse rights are clear.

## 15. Fork ecosystem
Record meaningful forks:
- Fork URL
- Why it exists
- Important architectural/feature differences
- Maintenance status
- License differences
- fixes/features not yet upstream
- whether PVNetwork should study the fork

## 16. Issues, PRs, releases, advisories, forums, and community lessons
Search and summarize:
- high-impact open issues
- high-impact closed issues
- regression fixes
- crash issues
- DNS/route cleanup issues
- reconnect loops
- battery/performance issues
- sleep/resume/network-switch problems
- import/parser failures
- UI/accessibility/RTL/localization problems
- update/package failures
- Store/review problems
- security advisories/CVEs
- release notes
- official forum/wiki discussions
- maintainer guidance

Every lesson must include a source reference and a proposed PVNetwork mitigation/test when applicable.

## 17. Tests and quality evidence
Record upstream:
- unit tests
- integration tests
- end-to-end tests
- platform tests
- CI matrix
- fuzzing/static analysis
- security testing
- interoperability tests
- notable missing coverage

## 18. Performance and resource behavior
Where evidence exists record:
- CPU/RAM
- startup/connection latency
- throughput
- battery/background impact
- binary size
- process count
- platform-specific constraints

Do not invent benchmarks.

## 19. Store, privacy, and security implications
Record:
- Google Play implications
- Android TV implications
- Apple App Store/Mac App Store implications
- Microsoft Store implications
- Linux packaging implications
- permissions/capabilities
- privacy/data collection
- telemetry
- secret handling
- dependency supply-chain risk

Policies must be rechecked from current official sources before release.

## 20. PVNetwork reuse decision
Choose and justify one or more:
- Reuse core/library
- Wrap executable/service
- Use native OS API
- Reimplement only non-cryptographic adapter/UI logic
- Study architecture only
- Study UX only
- Compatibility-test only
- Defer
- Do not support

Record:
- selected upstream(s)
- rejected alternatives and reasons
- license rationale
- maintenance rationale
- Store rationale
- security rationale
- required acceptance/regression tests
- unresolved blockers

## 21. Completion gate
A dossier may be marked `COMPLETE-RESEARCH-v1` only when:
- [ ] Top clients identified and justified
- [ ] Canonical sources pinned
- [ ] Licenses reviewed
- [ ] Complete source-tree reference/manifest captured
- [ ] Languages/build systems mapped
- [ ] Architecture mapped
- [ ] Core/engine integration mapped
- [ ] UI/menu map completed
- [ ] Config/import/export mapped
- [ ] Persistence/secrets mapped
- [ ] Platform integrations mapped
- [ ] Logs/diagnostics mapped
- [ ] Asset/screenshot references mapped
- [ ] Meaningful forks reviewed
- [ ] Important issues/PRs/releases/advisories reviewed
- [ ] Relevant forums/docs reviewed
- [ ] Tests/CI reviewed
- [ ] Store/privacy/security implications reviewed
- [ ] PVNetwork reuse decision documented
- [ ] Uncertainties explicitly listed

Research completion is **not** implementation completion.