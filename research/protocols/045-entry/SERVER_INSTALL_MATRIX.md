# 045 AnyTLS — server install matrix

Reviewed: 2026-08-15

| Target | Evidence-backed path | Result |
|---|---|---|
| Linux amd64/arm64 | anytls-go v0.0.13 release assets; sing-box implementations | PASS |
| Windows amd64/arm64 | anytls-go v0.0.13 release assets | PASS |
| macOS amd64/arm64 | anytls-go v0.0.13 release assets | PASS |
| Docker/OCI | implementation-specific; no canonical anytls-go image pin established here | BOUNDED |
| Kubernetes | no canonical AnyTLS operator/Helm established | N/A for canonical installer |
| Android/iOS server | not canonical unattended server targets | N/A |

Platform assets are software-distribution evidence only; runtime/service integration remains acceptance work. Source-license ambiguity remains regardless of platform.
