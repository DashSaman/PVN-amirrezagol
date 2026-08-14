# 040 Shadowsocks — deployment topologies

Reviewed: 2026-08-15

Evidence-backed topologies:

1. `sslocal/SOCKS -> classic Shadowsocks AEAD -> ssserver -> destination`;
2. local HTTP/redir/TUN/DNS modes feeding the same remote Shadowsocks session, where compiled/enabled;
3. TCP-only, UDP-only or TCP+UDP deployments according to configured mode;
4. optional plugin process between client/server transport endpoints, with independent supply-chain/protocol review;
5. Docker/OCI server/client; Kubernetes Service/Helm deployment provided upstream;
6. manager/multi-server operations through `ssmanager` or separate panel/control plane.

Do not infer CDN/TLS/WebSocket semantics from the base protocol. Do not auto-upgrade classic AEAD credentials to Shadowsocks 2022.
