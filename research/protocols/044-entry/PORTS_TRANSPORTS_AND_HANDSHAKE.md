# 044 TUIC v5 — ports, transports and handshake

TUIC has no universal fixed server port. Endpoint/port is implementation/profile configuration.

The protocol is defined over a multiplexable TLS-encrypted stream and is mainly designed for QUIC; canonical flow is QUIC/TLS -> TUIC Authenticate -> relay commands. The spec notes it can theoretically integrate with another multiplexed TLS service such as HTTP/3, so QUIC is the principal standardized deployment model, not a wire-format identity field that makes all other transports impossible.

TUIC v5 commands: Authenticate, Connect, Packet, Dissociate, Heartbeat. TCP uses bidirectional streams; UDP uses either QUIC unidirectional streams or QUIC DATAGRAM according to relay mode.

No universal command response/error code exists. 0-RTT availability is implementation/TLS-session-resumption specific; a config flag alone is not proof that early data was accepted. Itsusinn current tests explicitly distinguish config-path 1-RTT from actual resumed 0-RTT acceptance.
