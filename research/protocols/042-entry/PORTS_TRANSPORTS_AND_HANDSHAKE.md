# 042 Hysteria v1 — ports, transports and handshake

No fixed universal server port is defined; listener/server endpoint is configuration.

Base transport is QUIC over packet transport. Frozen `core/pktconns` contains UDP plus generation-specific WeChat/fake-TCP and obfs adapters. These adapters are exact v1 capabilities and are not Hysteria2 transport claims.

Handshake sequence: packet transport -> QUIC/TLS with ALPN (default `hysteria`) -> control stream -> protocol version 3 -> client hello(rate/auth) -> server hello(auth/rate) -> application TCP streams/UDP sessions.

Port hopping is generation/config specific through client packet-connection behavior and `hop_interval`; it must be preserved as v1 semantics. TCP proxy payload uses QUIC streams; UDP proxy payload uses QUIC messages with Hysteria's own session/fragment header.
