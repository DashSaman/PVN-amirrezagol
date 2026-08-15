# 047 NaiveProxy — ports, transports and handshake

No universal fixed port. HTTPS deployments conventionally use 443 but endpoint is configured in proxy URI.

Client upstream schemes:
- `https://` -> TLS + HTTP/2 CONNECT path according to Chromium proxy negotiation;
- `quic://` -> QUIC/TLS + HTTP/3 CONNECT where supported.

Padding/fast-open negotiation occurs in HTTP proxy headers; client must not blindly send padded early payload before capability is known. Local SOCKS/HTTP/redir are input interfaces, not remote Naive transports.

HTTP/2, HTTP/3, QUIC and TLS each remain independently reviewable standards/stack layers; generic support for them alone is not proof of Naive padding/fingerprint compatibility.
