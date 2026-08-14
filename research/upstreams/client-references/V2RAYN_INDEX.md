# v2rayN Research Index

Status: `IN-RESEARCH / EVIDENCE-GAPS`.

- Repository: `2dust/v2rayN`
- Pinned revision: `e01717d8326a4f5060b335523590c5fda943fe03`
- Reviewed license: GPLv3
- Full source manifest: `https://api.github.com/repos/2dust/v2rayN/git/trees/e01717d8326a4f5060b335523590c5fda943fe03?recursive=1`

Important solution/source areas at the pinned revision:
- `v2rayN/ServiceLib/` — shared models/managers/handlers/helpers/events/resources and application service logic
- `v2rayN/ServiceLib.Tests/`
- `v2rayN/ServiceLib.UdpTest/`
- `v2rayN/v2rayN.Desktop/` — Avalonia desktop application
- desktop `Views/` and `ViewModels/` — add/edit server, group management, backup/restore, update and other UI workflows
- `ServiceLib/Resx/` — localization resources

Pinned central dependencies include Avalonia/ReactiveUI, SQLite-related packages, WebDAV, NLog, YAML, QR and test tooling.

PVNetwork classification: high-value multi-core desktop architecture/UI/storage reference; application source is reference-only by default for a closed product because of GPLv3. Underlying engines must be licensed separately.

A larger detailed dossier write was connector-blocked. Future work should split menu/UI, storage/database, core management, import/subscription, issues/releases/tests and localization into smaller research files rather than retry the blocked document unchanged.