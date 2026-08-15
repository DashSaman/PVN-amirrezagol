# 049 SOCKS4 — COMPLETE-REFERENCE-v2 audit

Review: 2026-08-15

Decision: **COMPLETE-REFERENCE-v2 / legacy plaintext proxy reference / not implementation certification**.

| # | Exact V2 gate | Result | Evidence / conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | 3proxy current server pin plus OpenSSH dynamic-forwarding boundary in `SERVER_IMPLEMENTATIONS.md`. |
| 2 | Official/major installer/deployment projects reviewed | PASS | 3proxy native/service/source/Docker paths reviewed; no blind community installer selected. |
| 3 | Server OS/container/orchestration matrix | PASS | Linux/Unix, Windows, macOS/BSD, Docker mapped; K8s/mobile server roles explicitly N/A/uncertified. |
| 4 | Server panel/UI/menu maps | PASS | Protocol has no canonical GUI; 3proxy config/service/admin surfaces mapped with evidence-backed N/A boundary. |
| 5 | Client install matrix | PASS | curl/libcurl cross-platform reference mapped; product packaging certification kept separate. |
| 6 | Major client UI/menu maps | PASS | curl/OpenSSH are CLI/API references; PVNetwork protocol fields and error states mapped without inventing canonical GUI. |
| 7 | Cryptographic design | PASS | Explicit authoritative boundary: SOCKS4 has no protocol crypto; outer SSH/TLS/VPN is separate composition. |
| 8 | Data path/wire flow | PASS | local DNS -> TCP proxy -> CONNECT -> proxy outbound -> relay path documented; no UDP/multiplexing invented. |
| 9 | Ports/transports/handshake | PASS | TCP, conventional 1080, request/reply sequence, IPv4/USERID and version boundaries documented. |
| 10 | Deployment topologies | PASS | standalone proxy, SSH dynamic composition and proxy chaining mapped; non-applicable VPN/mesh roles explicit. |
| 11 | Source/license/activity pins | PASS | curl/OpenSSH/3proxy exact commits and trees pinned; 3proxy current 2026 activity confirmed. |
| 12 | Security/supply-chain installer risks | PASS | public listener/auth/firewall/container/config risks and no-blind-installer policy recorded. |
| 13 | Upgrade/uninstall/rollback | PASS | selected 3proxy service/container/native lifecycle mapped; config/log backup separated. |
| 14 | Differences/uncertainties | PASS | SOCKS4 vs 4a/5, local DNS, IPv4, USERID/no modern auth, no encryption and later product certification explicit. |
| 15 | REFERENCE_INDEX | PASS | `REFERENCE_INDEX.md` links complete dossier and pins. |
| 16 | Latest handoff exact continuation state | PASS when promotion commit lands | Promotion handoff advances to entry 050 SOCKS4a and preserves exact next action. |

## Evidence anchors

Canonical/current evidence reused from `research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`; current curl documentation confirms SOCKS4 resolves hostnames locally and defaults to port 1080, while SOCKS4a asks the proxy to resolve. Current 3proxy documentation distinguishes `socks4` from `socks4+` and states SOCKSv4 does not support IPv6; current canonical 3proxy master remains pinned at `4fb5c957046c6011b5a0b45f48c1b854daf70bca` (2026-08-12).

No runtime/device/Store/interoperability receipt is treated as a hidden research gate.