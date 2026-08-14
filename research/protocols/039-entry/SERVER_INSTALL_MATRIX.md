# 039 Trojan — server install matrix

Reviewed: 2026-08-15

| Target | Evidence-backed path | Result |
|---|---|---|
| Linux systemd | official Xray-install / direct Xray | PASS |
| Alpine/Gentoo OpenRC | Xray-install OpenRC guidance | PASS |
| Windows | Xray builds/config-driven Trojan inbound | PASS |
| macOS/BSD | Xray cross-platform source/releases; CLI/config server role | PASS |
| Docker/OCI | official Xray GHCR image | PASS |
| Kubernetes | no canonical XTLS Helm/operator established | N/A for canonical installer; community only if separately reviewed |
| Android/iOS unattended server | not canonical server targets | N/A |

Original `trojan-gfw/trojan` historical container/build paths are reference-only and do not supersede the maintained Xray path.
