# 042 Hysteria v1 — data path / wire flow

Pinned source `core/cs/protocol.go` defines protocol version 3, 10s control timeout, `clientHello`, `serverHello`, TCP/UDP request/response and UDP message structures.

Bounded flow:
1. client opens selected packet transport to server;
2. QUIC/TLS connection is established with configured ALPN/certificate policy;
3. client opens control stream and sends one-byte protocol version `3`;
4. client hello sends advertised send/receive rates plus auth bytes;
5. server returns auth result and accepted rate; client installs Hysteria's congestion sender according to returned rate;
6. each proxied TCP connection uses a QUIC stream and sends host/port request before payload;
7. UDP opens a request stream, receives a 32-bit UDP session ID, then sends QUIC messages/datagrams carrying session ID, destination, message/fragment IDs and payload; receiver defragments by message metadata.

Local SOCKS/HTTP/TUN/TProxy/relay handling is outside the core remote framing.
