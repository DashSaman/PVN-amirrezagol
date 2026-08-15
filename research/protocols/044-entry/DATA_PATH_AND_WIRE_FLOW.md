# 044 TUIC v5 — data path and wire flow

Canonical TUIC v5 command header is `VER | TYPE | OPT` with version `0x05`.

Typical QUIC flow:
1. establish underlying QUIC/TLS connection;
2. client opens a unidirectional stream and sends `Authenticate` (UUID + exporter-derived token); authentication may run in parallel with relay commands, while server pauses pre-auth tasks until auth succeeds;
3. TCP: client opens a bidirectional stream and sends `Connect` + destination address; after the header it may immediately relay bytes—there is no standardized server success response;
4. UDP: `Packet` carries 16-bit associate ID, packet ID, fragment count/id, size and destination/source address; client/server keep per-connection associate-ID -> UDP-socket tables;
5. UDP packet can travel as QUIC unidirectional stream (`quic` mode) or QUIC DATAGRAM (`native` mode); server replies using the mode established by the first packet of the association;
6. `Dissociate` releases a UDP association;
7. `Heartbeat` is periodically sent in QUIC DATAGRAM while relay tasks are active.

Address types are none/domain/IPv4/IPv6. Error behavior is intentionally implementation-defined: close connection, close stream or ignore are all possible.
