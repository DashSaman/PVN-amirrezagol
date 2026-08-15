# GRE — Server / Peer Implementations

Reviewed: 2026-08-15

GRE is a symmetric tunnel/encapsulation mechanism, not a client/server service. `server` therefore means a tunnel endpoint/peer.

## Canonical and serious implementations

- **Linux kernel GRE** — canonical open-source implementation for this dossier. Pinned tree: `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`; implementation: `net/ipv4/ip_gre.c` (`SPDX-License-Identifier: GPL-2.0-or-later`). Userspace configuration is normally via iproute2.
- **iproute2** — canonical Linux configuration tool. Pinned `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`; `ip tunnel` explicitly supports `mode gre`, local/remote endpoints, key/checksum/sequence flags, TTL/TOS, PMTUD and device binding.
- **Cisco IOS XE 17.x** — proprietary router implementation; current Cisco routing guide documents `tunnel mode gre ip`, source/destination, keys and keepalive.
- **Juniper Junos OS** — proprietary router implementation; current Juniper tunnel-services documentation covers GRE tunnel interfaces, encapsulation/decapsulation and platform-specific behavior.

## Project boundary

GRE itself has no canonical daemon, account system, controller, web panel or installer project. Linux/Cisco/Juniper are endpoint implementations. DMVPN (entry 071), GRE-over-IPsec (064), and Ethernet-over-GRE are related but separate capabilities and must not be silently merged into bare GRE.

## Reuse decision

PVNetwork should treat GRE as an infrastructure/platform capability. On Linux, reuse the kernel networking stack through a platform adapter rather than embedding/copying kernel or iproute2 code. Cisco/Juniper implementations are interoperability references only.

## Evidence

- RFC 2784: https://www.rfc-editor.org/rfc/rfc2784.html
- RFC 2890: https://www.rfc-editor.org/rfc/rfc2890.html
- Linux GRE source: https://github.com/torvalds/linux/blob/15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6/net/ipv4/ip_gre.c
- iproute2 man page: https://github.com/iproute2/iproute2/blob/da2ccdf862cb1eab45de082cc71fcb4e5d712e78/man/man8/ip-tunnel.8
- Cisco IOS XE 17 tunnel guide: https://www.cisco.com/c/en/us/td/docs/routers/ios/config/17-x/ip-routing/b-ip-routing/m_ir-impl-tun-xe.html
- Juniper GRE guide: https://www.juniper.net/documentation/us/en/software/junos/interfaces-encryption/topics/topic-map/configuring-gre-tunnel-interfaces.html
