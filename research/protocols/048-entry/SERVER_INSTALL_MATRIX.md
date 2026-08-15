# 048 Snell — server install matrix

Reviewed: 2026-08-15

| Target | Authoritative evidence | Result |
|---|---|---|
| Linux amd64 | official v5.0.1 zip | PASS |
| Linux i386 | official v5.0.1 zip | PASS |
| Linux aarch64 | official v5.0.1 zip | PASS |
| Linux armv7l | official v5.0.1 zip; historical packaging mismatch means artifact verification mandatory | PASS with supply-chain caution |
| macOS official standalone server | no current public standalone package established; Surge Mac embeds a Snell V1 server | BOUNDED / different product-generation |
| Windows official standalone server | no official package established | N/A |
| Docker/OCI | no canonical official image established | N/A |
| Kubernetes/Helm/operator | no canonical official project established | N/A |
| Android/iOS server | no canonical official unattended server | N/A |

Do not infer a platform from third-party source portability. Server support claims stay tied to vendor-published binaries or separately reviewed community implementations.
