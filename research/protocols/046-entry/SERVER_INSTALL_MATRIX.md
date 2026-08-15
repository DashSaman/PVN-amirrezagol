# 046 ShadowTLS — server install matrix

| Target | Evidence-backed path | Result |
|---|---|---|
| Linux major arches | official v0.2.25 musl assets/source | PASS |
| macOS x64/arm64 | official v0.2.25 assets/source | PASS |
| Windows | no official v0.2.25 native asset; broader hosts may support it | BOUNDED, no first-party proof |
| Docker/Compose | official repository files | PASS |
| Kubernetes | no canonical official Helm/operator established | N/A for canonical installer |
| Android/iOS server | not canonical unattended server targets | N/A |

A sing-box deployment can broaden platform coverage but must be pinned/certified independently.
