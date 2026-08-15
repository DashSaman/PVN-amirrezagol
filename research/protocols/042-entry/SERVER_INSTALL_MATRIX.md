# 042 Hysteria v1 — server install matrix

Reviewed: 2026-08-15

| Target | Evidence-backed treatment | Result |
|---|---|---|
| Linux | v1 release/build + official legacy installer | PASS |
| macOS | v1.3.5 release has Darwin binaries | PASS |
| FreeBSD | v1.3.5 release has FreeBSD binaries | PASS |
| Windows | legacy build/release scripts support Windows artifacts | PASS |
| Docker/Compose | frozen v1 Dockerfile + compose | PASS |
| Kubernetes | no canonical v1 Helm/operator established at frozen pin | N/A |
| Android/iOS server | not canonical server deployment targets | N/A |

Any shipping artifact must freeze exact v1.3.5 hash/build tags and dependency graph; current Hysteria2 packages are not substitutes.
