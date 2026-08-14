# 038 VMess — server install matrix

Reviewed: 2026-08-15

| Target | Evidence-backed treatment | Result |
|---|---|---|
| Linux systemd (Debian/CentOS/OpenSUSE-class) | official `XTLS/Xray-install` plus direct Xray binary | PASS |
| Alpine/Gentoo/OpenRC | official installer repo has dedicated OpenRC guidance | PASS |
| Windows | Xray ships/builds for Windows and can expose a VMess inbound from config | PASS |
| macOS | Xray source/releases/Homebrew ecosystem support CLI runtime; server role is config-driven | PASS |
| BSD | Xray release/source ecosystem provides BSD builds; use current patched source/package | PASS |
| Docker/OCI | upstream lists `ghcr.io/xtls/xray-core` | PASS |
| Podman | OCI use may work, but no separate official certification was established | BOUNDED |
| Kubernetes | no canonical XTLS Helm/operator was established in this review | evidence-backed N/A for canonical installer; community deployment requires separate review |
| Android/iOS unattended server | not a canonical server deployment target for this entry | N/A |

No minimum distro/kernel minor version or production compatibility receipt is invented. Those remain implementation/certification evidence.
