# 048 Snell — data path and wire flow

Reviewed: 2026-08-15

Because the official Snell wire format is proprietary/non-published, byte-level authoritative framing is not fabricated. The evidence-backed functional flow is:

1. Surge/client selects explicit Snell generation, endpoint and PSK;
2. client establishes the generation-specific Snell session to official/server implementation;
3. server authenticates/accepts the proprietary encrypted proxy session and relays TCP traffic;
4. UDP relay is supported for v4/v5/v6 according to current Surge documentation;
5. v4/v5 ordinary UDP relay follows the generation's Snell relay behavior, including UDP-over-TCP support documented by Surge;
6. v5 detects QUIC and can select QUIC Proxy Mode: QUIC handshake receives Snell protection/authentication and later already-encrypted QUIC packets are forwarded raw over UDP;
7. v5 server can accept v4 clients;
8. v6 uses a PSK-derived deployment-specific profile, removes v5 QUIC Proxy Mode, and remains beta.

OpenSnell's reverse-engineered packet details may be used only for lab/interoperability understanding after rights review; they are not treated as official wire specification.
