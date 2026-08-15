# 049 SOCKS4 — Deployment topologies

Review: 2026-08-15

Applicable topologies:

- application/client -> standalone SOCKS4 proxy -> IPv4 destination;
- application -> local OpenSSH dynamic SOCKS listener -> SSH session -> remote SSH host -> destination (composition reference, not a standalone SOCKS4 server architecture);
- chained proxy topologies where a server such as 3proxy uses SOCKS4 as one parent hop.

SOCKS4 is not itself a site-to-site VPN, mesh overlay, HA control plane, route distribution system or encrypted tunnel. HA/load-balancing, TUN/full-tunnel behavior and split routing are product/server orchestration concerns layered around the proxy.

DNS boundary is important: ordinary SOCKS4 resolves destination names client-side before the request. Dual-stack is limited by the SOCKS4 IPv4 destination model; SOCKS4 must not be presented as native IPv6 proxying.