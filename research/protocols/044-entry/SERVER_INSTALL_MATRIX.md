# 044 TUIC v5 — server install matrix

Reviewed: 2026-08-15

| Target | Evidence-backed implementation path | Result |
|---|---|---|
| Linux | shoes / Itsusinn/tuic source or packaged runtime | PASS |
| Windows | Itsusinn/tuic documented builds; shoes Rust portability subject to selected release | PASS/bounded |
| macOS | Itsusinn/tuic documented builds; shoes source portability | PASS/bounded |
| FreeBSD | Itsusinn/tuic documented builds | PASS |
| Docker/OCI | Itsusinn/tuic container deployment; other implementations can be containerized only with separate artifact pin | PASS/bounded |
| Kubernetes | no canonical TUIC-protocol Helm/operator exists | N/A for canonical installer; community deployment requires separate review |
| Android/iOS unattended server | not canonical server targets | N/A |

Protocol spec alone never proves platform support. Final matrix belongs to the selected engine/release and its dependency/SBOM freeze.
