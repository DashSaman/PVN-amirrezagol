# 040 Shadowsocks — server install matrix

Reviewed: 2026-08-15

| Target | Evidence-backed path | Result |
|---|---|---|
| Linux | crates.io/source/static release/Homebrew/Snap/distro packages | PASS |
| macOS x64/arm64 | Homebrew + static release | PASS |
| Windows | static release/source build + Windows service support | PASS |
| Docker/OCI | official GHCR server/client images | PASS |
| Kubernetes | upstream YAML + Helm chart | PASS |
| OpenWrt/router | community project referenced by upstream; separately versioned | BOUNDED |
| Android/iOS unattended server | not canonical consumer server deployment | N/A |

Container IPv6 is explicitly an infrastructure concern in upstream documentation; do not infer full dual-stack certification from image availability.
