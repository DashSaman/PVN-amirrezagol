# 049 SOCKS4 — Server installers and deployment projects

Review: 2026-08-15

Canonical deployment subject: `3proxy/3proxy@4fb5c957046c6011b5a0b45f48c1b854daf70bca`.

3proxy documents CMake/GCC/Visual C++ builds, Windows service install/remove, Unix/Linux/macOS installation, and Docker deployment. The `socks` service defaults to TCP 1080; bind address/port, auth, ACL, logging and chaining are explicit configuration concerns. Current docs warn that Docker/network treatment is especially relevant for SOCKS5 UDP/BIND; those features are not attributed to SOCKS4.

Security review: server deployment requires deliberate bind/firewall/ACL/auth choices. SOCKS4 USERID is not password authentication. Exposing an unauthenticated SOCKS listener publicly is unsafe. Remote one-line installers are not selected or trusted by this dossier; no blind `curl | sh` path is recommended.

Upgrade/uninstall/rollback follows the selected 3proxy package/service/container path; configuration and logs must be backed up separately. PVNetwork consumer support does not require shipping a SOCKS4 server.