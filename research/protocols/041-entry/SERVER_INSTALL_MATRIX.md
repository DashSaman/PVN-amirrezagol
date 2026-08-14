# 041 Shadowsocks 2022 — server install matrix

Reviewed: 2026-08-15

| Target | Evidence-backed path | Result |
|---|---|---|
| Linux | shadowsocks-rust source/crates/static packages with SS2022 feature | PASS |
| macOS | Homebrew/static/source runtime, feature-dependent | PASS |
| Windows | static/source runtime, feature-dependent | PASS |
| Docker/OCI | official shadowsocks-rust images; digest freeze later | PASS |
| Kubernetes | upstream YAML + Helm chart | PASS |
| OpenWrt/router | community integration only; exact SS2022 feature/package must be proven | BOUNDED |
| Android/iOS unattended server | not canonical server target | N/A |

Availability of a generic Shadowsocks package is not proof that its build enables AEAD-2022.
