# 049 SOCKS4 — Data path and wire flow

Review: 2026-08-15

SOCKS4 is a TCP proxy handshake followed by a relayed TCP byte stream.

```text
application -> local DNS resolution -> TCP connect to SOCKS4 proxy
            -> SOCKS4 CONNECT request (IPv4 destination + port + USERID)
            -> proxy connects to destination
            -> success/failure reply
            -> bidirectional application bytes through proxy
```

For ordinary SOCKS4, current curl behavior resolves the target hostname locally and passes the IPv4 address to the proxy. This is the defining privacy/capability difference from entry 050 SOCKS4a, which can send a hostname for proxy-side resolution.

There is no SOCKS4 UDP relay, stream multiplexing, protocol encryption, TUN device, route installation, roaming or session resumption. Those must not be inferred from SOCKS5 or an outer product layer. TCP MTU/retransmission behavior belongs to TCP. Outer SSH/TLS/VPN composition, if selected, adds a separate encapsulation/security path.