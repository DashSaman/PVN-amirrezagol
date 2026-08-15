# 047 NaiveProxy — server install matrix

| Target | Evidence-backed path | Result |
|---|---|---|
| Linux | Caddy + naive forwardproxy fork; Naive client release binaries | PASS |
| macOS | Naive client release; Caddy/Go server build possible subject to pin | PASS/bounded |
| Windows | Naive client release; server build possible but canonical deployment is Caddy module-specific | PASS/bounded |
| Android | official Naive plugin APK release assets are client/plugin evidence | PASS client only |
| OpenWrt | upstream documentation/community support referenced | BOUNDED |
| Docker/OCI | Caddy/module packaging possible; no immutable canonical image pinned here | BOUNDED |
| Kubernetes | no canonical Naive operator/Helm established | N/A |

Exact production server matrix belongs to the frozen Caddy+module build, not generic HTTP server support.
