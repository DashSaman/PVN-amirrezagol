# 044 TUIC v5 — deployment topologies

Evidence-backed reference topologies:
1. local SOCKS/TUN/client -> TUIC v5 over QUIC/TLS -> server -> destination;
2. TCP relay through per-request QUIC bidirectional streams;
3. Full-Cone-style UDP association using native QUIC DATAGRAM mode;
4. UDP association using QUIC unidirectional-stream mode;
5. resumed QUIC/TLS session with implementation-supported 0-RTT, subject to replay-safe policy;
6. multi-bind/port-range server implementations such as current shoes;
7. product-managed routing/DNS/TUN/forwarding around the TUIC core;
8. theoretical integration into another multiplexed TLS service such as HTTP/3, only when an exact implementation supports it.

Do not equate TUIC with Hysteria2 or generic QUIC. No CDN/Kubernetes/HA topology is inferred from the base specification.
