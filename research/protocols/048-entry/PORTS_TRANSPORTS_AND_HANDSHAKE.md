# 048 Snell — ports, transports and handshake

Reviewed: 2026-08-15

Snell has no protocol-fixed universal port. Official examples include port 6160 and Surge proxy examples use arbitrary ports; endpoint is configuration.

Evidence-backed transport behavior:
- core client/server proxy uses a generation-specific encrypted Snell session;
- UDP relay is supported for v4/v5/v6;
- v5 QUIC Proxy Mode uses UDP-over-UDP for detected QUIC and requires server UDP port reachability;
- non-QUIC UDP traffic under v5 continues through the ordinary UDP-over-TCP mode described by Surge;
- v6 removes v5 QUIC Proxy Mode;
- optional v4 HTTP obfuscation is a generation-specific client feature;
- optional ShadowTLS is a separate outer camouflage layer and not part of the Snell handshake.

The official proprietary authentication/record handshake beyond documented PSK and generation behavior is unavailable; no byte sequence/cipher exchange is invented.
