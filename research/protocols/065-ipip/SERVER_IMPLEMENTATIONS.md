# IP-in-IP / IPIP — Server / Peer Implementations

Reviewed: 2026-08-15

IPIP is symmetric tunnel encapsulation, not a client/server daemon.

- RFC 2003 is the authoritative IP-within-IP specification. It defines an outer IPv4 header around the original IP packet and uses outer IP Protocol value 4.
- Linux kernel is the principal open-source implementation reference: `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `net/ipv4/ipip.c`, SPDX `GPL-2.0-or-later`.
- Linux configuration uses pinned iproute2 `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`; `ip tunnel` supports `mode ipip`, local/remote endpoint, TTL/TOS, PMTU and interface binding.

No canonical standalone IPIP daemon, account server, controller or web panel exists. Other router vendors may implement IP-in-IP, but this dossier does not promote unverified vendor support.

PVNetwork decision: infrastructure/platform capability only; use OS-native tunnel APIs behind platform adapters. Do not copy kernel/iproute2 code into the product.

Evidence:
- https://www.rfc-editor.org/rfc/rfc2003.html
- https://github.com/torvalds/linux/blob/15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6/net/ipv4/ipip.c
- https://github.com/iproute2/iproute2/blob/da2ccdf862cb1eab45de082cc71fcb4e5d712e78/man/man8/ip-tunnel.8
