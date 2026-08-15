# 046 ShadowTLS v3 — data path/wire flow

1. Client creates/authenticates custom TLS ClientHello SessionID and connects to ShadowTLS server.
2. Server parses/validates ClientHello. On failure/probe it continues TCP relay to the genuine handshake server.
3. On valid client it still forwards TLS handshake to handshake server, extracts ServerRandom and transforms/authenticates returned ApplicationData.
4. Client validates server identity indirectly through the ServerRandom/HMAC transformation. Hijacked/nonmatching flow follows muddled-request/close behavior rather than exposing inner proxy.
5. Client signals switch using authenticated ApplicationData-shaped frame.
6. Server stops handshake-server forwarding and connects/forwards to inner data server.
7. Both directions wrap inner TCP bytes as TLS ApplicationData-shaped frames with direction-specific stateful HMAC.
8. HMAC failure/tamper/order/cut-splice error is closed with TLS Alert semantics.

This is a TCP flow wrapper. UDP/TUN/routing belong to the inner proxy/product, not ShadowTLS v3.
