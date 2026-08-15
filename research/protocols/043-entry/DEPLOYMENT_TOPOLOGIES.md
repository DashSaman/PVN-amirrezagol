# 043 Hysteria2 — deployment topologies

Evidence-backed topologies:
1. official client local SOCKS/HTTP/TUN -> Hysteria2 QUIC server -> destination;
2. TCP/UDP forward/relay and Linux transparent-proxy modes;
3. direct TLS certificate or ACME-managed server identity;
4. HTTP/3 masquerade serving local content or reverse-proxying an upstream site for unauthenticated probes;
5. optional Salamander-obfuscated QUIC;
6. UDP port hopping across configured server ports;
7. ACL/resolver/outbound-controlled server egress;
8. deployment behind product-owned service/process supervisor with canonical profile generated into Hysteria config.

No Hysteria v1 faketcp/wechat framing is inherited. No generic CDN/TCP fallback is inferred from HTTP/3 masquerade.
