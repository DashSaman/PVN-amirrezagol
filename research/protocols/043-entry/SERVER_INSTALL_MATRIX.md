# 043 Hysteria2 — server install matrix

Reviewed: 2026-08-15

| Target | Evidence-backed treatment | Result |
|---|---|---|
| Linux major arches | official release assets/source/service deployment | PASS |
| macOS x64/arm64 | official release assets | PASS |
| Windows | official release assets | PASS |
| FreeBSD | official release assets | PASS |
| Android binaries | official release assets exist; not automatically an unattended server product target | BOUNDED |
| Docker/OCI | official project deployment path/source can be containerized; exact current image/digest must be frozen | PASS/bounded to pinned artifact |
| Kubernetes | no canonical official Helm/operator is required by protocol; community deployment requires separate pin | N/A for canonical installer |
| iOS server | no canonical unattended server target established | N/A |

Platform asset presence is not full runtime/certification evidence.
