# 043 Hysteria2 — ports, transports and handshake

Hysteria2 uses QUIC over UDP and QUIC DATAGRAM; no universal server port is mandated. Port hopping can select a configured UDP port set/range and is transport configuration, not a protocol constant.

Handshake/order: optional Salamander packet wrapping -> QUIC/TLS 1.3 -> HTTP/3 auth request `/auth` -> status 233 capability/rate response -> TCP QUIC streams and/or UDP QUIC DATAGRAM proxy traffic.

Authentication header carries credential string; rate negotiation uses `Hysteria-CC-RX`. Failed authentication must fall through to masquerade/ordinary HTTP/3 behavior.

TCP and UDP proxy capability must be modeled independently. PMTU, QUIC datagram size and port-hopping behavior are runtime/platform/network-sensitive acceptance tests, not hidden research gates.
