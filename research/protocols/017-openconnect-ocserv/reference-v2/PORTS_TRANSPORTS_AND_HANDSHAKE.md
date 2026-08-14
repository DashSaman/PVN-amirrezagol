# OpenConnect / ocserv — Ports / Transports / Handshake

Review date: 2026-08-14 UTC

Current canonical ocserv sample config defines global defaults:

- `tcp-port = 443`
- `udp-port = 443`

These are configurable server settings, not immutable protocol constants.

OpenConnect AnyConnect-compatible flow:

- HTTPS/TLS authentication and CSTP tunnel over TCP;
- optional DTLS data tunnel over UDP;
- auth cookie/session bridges authentication to tunnel establishment;
- `no-udp` can disable DTLS per server/vhost/user/group policy;
- MTU is negotiated between server setting/TUN and client-advertised values.

Proxies/firewalls may result in TLS-only operation. A reachable TCP 443 page is not proof of a valid CSTP VPN service.
