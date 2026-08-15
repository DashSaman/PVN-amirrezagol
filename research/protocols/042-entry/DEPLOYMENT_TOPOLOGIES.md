# 042 Hysteria v1 — deployment topologies

Evidence-backed v1 topologies:
1. local SOCKS5/HTTP client -> Hysteria v1 QUIC session -> server -> destination;
2. TUN/TAP or Linux TProxy/REDIRECT -> same v1 remote session;
3. TCP/UDP relay listener -> v1 server -> configured remote destination;
4. v1 with ACME-managed or static TLS certificate;
5. v1 with UDP/faketcp/wechat packet transport and optional v1 obfs, where exact source/platform supports it;
6. Docker/Compose legacy server deployment;
7. port-hop/reconnect topology using v1 packet-connection logic.

No Hysteria2 masquerade/HTTP3/auth/obfs model is inherited. No current multi-node panel/Kubernetes HA claim is made for frozen v1 without separate evidence.
