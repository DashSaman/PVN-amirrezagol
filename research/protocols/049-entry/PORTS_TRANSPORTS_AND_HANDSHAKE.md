# 049 SOCKS4 — Ports, transports and handshake

Review: 2026-08-15

Transport: TCP. Conventional/default SOCKS listener port in current curl/3proxy documentation: **1080**, but the proxy port is configurable and is not a mandatory wire constant.

Handshake boundary:

1. client establishes TCP to proxy;
2. client sends SOCKS4 CONNECT request with version 4, CONNECT command, destination port, IPv4 destination and NUL-terminated USERID;
3. proxy attempts the outbound TCP connection;
4. proxy returns success/failure response;
5. on success, the connection becomes a bidirectional byte relay.

Ordinary SOCKS4 uses an IPv4 destination and local hostname resolution. SOCKS4a's hostname extension is entry 050. SOCKS5 BIND/UDP ASSOCIATE/method negotiation are entry 051 and must not be attributed here.

No TLS/DTLS/QUIC/ESP layer is inherent. Retry/fallback policy is implementation/product behavior rather than a SOCKS4 negotiation feature.