# 044 TUIC v5 — server UI/menu maps

The canonical spec has no implementation/UI. shoes and Itsusinn/tuic are primarily config/CLI/server projects; ClashRS includes broader dashboard/product UI.

For a TUIC server the product-owned admin surface must map:
- listener address/port and optional multi-address/port-range support;
- protocol version `0x05` capability;
- UUID -> password credential mapping;
- QUIC/TLS certificate/key, SNI/ALPN/client-verification policy where implementation exposes it;
- 0-RTT enablement as explicit replay-sensitive capability;
- UDP relay modes (`native` QUIC DATAGRAM vs `quic` unidirectional stream) where supported;
- congestion, heartbeat, request/idle/GC timeouts, packet/window/stream limits;
- routing/DNS/TUN/masquerade/metrics as implementation/product layers, not TUIC wire fields;
- logs/diagnostics with UUID/password/TLS/API secrets redacted.

shoes current config explicitly supports `protocol: type: tuic`, UUID/password, `zero_rtt_handshake`, QUIC TLS verify/SNI/ALPN/cert controls and TUN integration. No third-party dashboard schema becomes PVNetwork's canonical profile.
