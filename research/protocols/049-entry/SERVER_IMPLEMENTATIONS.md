# 049 SOCKS4 — Server implementations

Review: 2026-08-15

## Selected current server reference

**3proxy/3proxy** is the maintained server/admin reference. Pin: `4fb5c957046c6011b5a0b45f48c1b854daf70bca`, tree `b12b0c1a80ae44158d78c44810e387f1092f676a` (2026-08-12). Its current docs expose a `socks` service on conventional port 1080 and distinguish SOCKS4, SOCKS4a (`socks4+`) and SOCKS5 parent modes. Current docs also state SOCKSv4 does not support IPv6. License boundary: current `copying` terms described in `research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`; exact redistribution path still needs legal review.

3proxy is a server/interoperability/admin reference, not a reason to embed a server in the consumer client.

## Other implementation references

OpenSSH portable dynamic forwarding is an independent SOCKS4 parser/gateway reference, pinned in shared V1 evidence, but is not a generic standalone remote SOCKS server: the SOCKS listener is local and the forwarded stream is carried over SSH.

## Protocol boundary

SOCKS4 is CONNECT-oriented, IPv4-oriented, has USERID but no SOCKS5 method negotiation, and provides no confidentiality/integrity layer. SOCKS4a and SOCKS5 remain separate entries.

Evidence: current 3proxy canonical repository/wiki and pinned source; curl/OpenSSH pins in `research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`.