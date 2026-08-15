# 050 SOCKS4a — Reference index

## Canonical / selected evidence
- Shared family pins: `research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`.
- 3proxy canonical repository: https://github.com/3proxy/3proxy ; selected current master pin `4fb5c957046c6011b5a0b45f48c1b854daf70bca` (2026-08-12).
- 3proxy SOCKS service documentation: https://github.com/3proxy/3proxy/wiki/socks — SOCKSv4.5 is the v4 extension for server-side name resolution; default port 1080.
- 3proxy configuration reference: https://github.com/3proxy/3proxy/wiki/3proxy.cfg — `socks4+` means SOCKSv4 with name resolution.
- SOCKS4a specification reference/mirror index: https://github.com/gotoh/ssh-connect .

## Entry-specific boundaries
SOCKS4a is SOCKS4 plus proxy-side hostname resolution. It remains TCP-only and plaintext at protocol level. It does not inherit SOCKS5 authentication negotiation, IPv6 address framing, UDP ASSOCIATE, or protocol encryption. Any outer TLS/SSH/VPN is a separate composition.

## Dossier map
- `REFERENCE_V2_AUDIT.md` — exact 16-gate reconciliation and evidence anchors.
- `V1_GATE_RECONCILIATION.md` — prior exact V1 research closure.
- `README.md` — entry identity.

Research completion is not implementation/device/Store/interoperability certification.