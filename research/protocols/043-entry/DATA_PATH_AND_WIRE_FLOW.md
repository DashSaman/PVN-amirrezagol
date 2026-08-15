# 043 Hysteria2 — data path and wire flow

Canonical wire flow:
1. client reaches configured UDP endpoint/port-hop transport; optional Salamander wraps QUIC packets;
2. standard QUIC/TLS connection establishes;
3. client sends HTTP/3 `POST /auth` with auth/rate/padding headers;
4. server returns status 233 plus UDP and receive-rate capability on success; otherwise serves/forwards masquerade HTTP/3 behavior;
5. for each TCP proxy connection, client opens a QUIC bidirectional stream and sends TCPRequest ID `0x401`, address and padding; server returns status/message/padding then relays bytes;
6. UDP uses QUIC DATAGRAM with 32-bit session ID, 16-bit packet ID, fragment ID/count, destination string and payload;
7. oversized UDP is fragmented or discarded; all fragments must arrive before processing;
8. congestion behavior uses the exchanged `Hysteria-CC-RX` values or normal congestion algorithms according to zero/`auto` semantics.

Local TUN/SOCKS/HTTP capture and server outbound/ACL are outside the protocol wire header.
