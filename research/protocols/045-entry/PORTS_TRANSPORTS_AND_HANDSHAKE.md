# 045 AnyTLS — ports, transports and handshake

Canonical URI defaults to port 443 when omitted, but AnyTLS has no protocol-mandatory fixed port; listener/endpoint is configurable.

Base transport is TLS over a reliable byte stream/TCP in the reference design. Handshake order: TCP -> TLS -> AnyTLS password-hash authentication + padding0 -> settings/version negotiation -> multiplexed streams/padding/heartbeat.

AnyTLS v2 remains backward-compatible by capability fallback:
- v2 client + v1 server: absent server settings causes client to operate v1 behavior;
- v2 server + v1 client: server sees client v1 and disables v2 features.

UDP is proxied **over the reliable session stream** using sing-box udp-over-tcp v2 semantics; it is not native UDP transport. TLS/REALITY-like wrappers and fallback behavior are independent deployment/implementation layers and must not be described as AnyTLS cryptography.
