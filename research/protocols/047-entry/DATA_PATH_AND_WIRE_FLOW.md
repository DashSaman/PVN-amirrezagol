# 047 NaiveProxy — data path and wire flow

Bounded flow:
1. local SOCKS/HTTP/redir request reaches Naive client;
2. Chromium network stack establishes HTTPS HTTP/2 CONNECT or QUIC/HTTP3 CONNECT path to configured frontend/server;
3. proxy authentication/headers are sent inside the protected HTTP connection;
4. Naive client/server negotiate padding capability through Naive-specific `padding` headers;
5. first eight read/write operations use randomized padding behavior (up to 255 bytes) when negotiated; H2 includes Naive RST_STREAM/camouflage behavior;
6. optional `fastopen` header allows first-request payload behavior only when server path supports it;
7. server forwardproxy authorizes and relays CONNECT target bytes to destination;
8. if Naive padding is not negotiated, ordinary proxy interoperability behavior is preserved where documented.

The exact H2/H3 framing is owned by the pinned Chromium/Caddy stacks; Naive-specific additions are padding/first-contact/camouflage conventions rather than a new TLS cipher.
