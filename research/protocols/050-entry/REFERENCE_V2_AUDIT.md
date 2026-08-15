# 050 SOCKS4a — COMPLETE-REFERENCE-v2 audit

Review: 2026-08-15

Decision: **COMPLETE-REFERENCE-v2 / legacy plaintext proxy reference / not implementation certification**.

| # | Exact V2 gate | Result | Evidence / conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | Canonical 3proxy supports SOCKSv4.5, explicitly described as the v4 extension for server-side name resolution; shared SOCKS-family evidence reused only where protocol-identical. |
| 2 | Official/major installer/deployment projects reviewed | PASS | 3proxy native/service/source/container deployment paths are the selected server reference; no blind community installer selected. |
| 3 | Server OS/container/orchestration matrix | PASS | Linux/Unix, Windows, macOS/BSD and container deployment inherited from the same 3proxy server binary; Kubernetes/mobile-server roles are N/A/uncertified, not hidden gates. |
| 4 | Server panel/UI/menu maps | PASS | SOCKS4a has no canonical GUI. 3proxy config/service/admin surfaces are the server controls; PVNetwork UI must expose protocol/listen/ACL fields without inventing a standard panel. |
| 5 | Client install matrix | PASS | curl/libcurl is the selected cross-platform client reference; `socks4a://` means proxy-side hostname resolution. Product packaging certification remains separate. |
| 6 | Major client UI/menu maps | PASS | curl is CLI/API; PVNetwork needs host, port, optional USERID/policy and remote-DNS semantics. No canonical GUI is claimed. |
| 7 | Cryptographic design | PASS | SOCKS4a adds no cryptography; confidentiality/integrity require a separately modelled outer TLS/SSH/VPN composition. |
| 8 | Data path/wire flow | PASS | client opens TCP to proxy, sends SOCKS4 CONNECT with sentinel IPv4 and hostname extension, proxy resolves hostname, opens destination TCP, replies, then relays bytes. No UDP/multiplexing invented. |
| 9 | Ports/transports/handshake | PASS | TCP; conventional port 1080; SOCKS4 request/reply semantics plus hostname extension. The defining difference from SOCKS4 is proxy-side DNS. |
| 10 | Deployment topologies | PASS | Standalone forward proxy, local/remote proxy composition and parent-proxy chaining are applicable; VPN/mesh/site-to-site roles are N/A. |
| 11 | Source/license/activity pins | PASS | Reuse `research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`; canonical 3proxy master pin `4fb5c957046c6011b5a0b45f48c1b854daf70bca` (2026-08-12). License boundaries remain those of the selected upstreams, not the protocol itself. |
| 12 | Security/supply-chain installer risks | PASS | Plaintext proxy exposure, unauthenticated/weak USERID semantics, DNS privacy shift to proxy, ACL/firewall risks and no-blind-installer policy recorded. |
| 13 | Upgrade/uninstall/rollback | PASS | Same selected 3proxy native/service/container lifecycle as SOCKS4; preserve config/logs and rollback binary/container tag independently. |
| 14 | Differences/uncertainties | PASS | SOCKS4a differs from SOCKS4 specifically by proxy-side hostname resolution; it still lacks SOCKS5 method negotiation, IPv6 address model and UDP ASSOCIATE. Runtime/device/Store certification remains later work. |
| 15 | REFERENCE_INDEX | PASS | `REFERENCE_INDEX.md` maps this audit, shared family evidence and selected upstream references. |
| 16 | Latest handoff exact continuation state | PASS when promotion commit lands | Promotion advances to entry 051 SOCKS5 and preserves exact next action. |

## Evidence anchors

- 3proxy current wiki: https://github.com/3proxy/3proxy/wiki/socks — server supports SOCKSv4, SOCKSv4.5 (server-side name resolution), SOCKSv5; default listen port 1080.
- 3proxy configuration wiki: https://github.com/3proxy/3proxy/wiki/3proxy.cfg — `socks4+` is SOCKSv4 with name resolution and is explicitly distinguished from `socks4b`.
- Historical SOCKS4a specification mirror/reference: https://github.com/gotoh/ssh-connect — links `socks4a.protocol.txt` and describes hostname-based v4a requests.
- Repository shared evidence: `research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`.

No runtime/device/Store/interoperability receipt is treated as a hidden research gate.