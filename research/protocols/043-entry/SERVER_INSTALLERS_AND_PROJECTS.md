# 043 Hysteria2 — installers/deployment projects

Reviewed: 2026-08-15

Official repository/release provides current cross-platform binaries plus `hashes.txt`; release assets include GitHub SHA-256 digests. The repository contains install/service/container/release automation used by the current app line.

Production rule: pin exact `app/v2.12.1` asset filename + SHA-256 + source commit + resolved app/core/extras/dependency SBOM. Never download unqualified `latest` at runtime.

Server automation/config includes ACME with multiple DNS providers, certificate/key paths, authentication, traffic stats, ACL, masquerade, QUIC/bandwidth/obfs and listener controls. Third-party panels are not required and are not promoted without an exact current Hysteria2-capable pin.

Upgrade/rollback must treat binary, service config, certificates/ACME credentials, auth database/backend, masquerade content/upstream and routing/ACL as separate state.
