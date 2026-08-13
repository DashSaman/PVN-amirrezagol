# PVNetwork — 93-Entry Research Completeness Tracker

This file tracks the **research campaign**, not implementation. `SKELETON` means a per-entry folder/file exists but the exhaustive template is not yet complete. `RESERVED` means the numbered slot exists but has not yet received a full dossier. `PENDING` means even the per-entry folder still needs to be committed. `COMPLETE-RESEARCH-v1` is allowed only after all gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md` are satisfied with evidence.

| # | Entry | Current research state |
|---:|---|---|
| 001 | OpenVPN | SKELETON — deep upstream audit started |
| 002 | WireGuard | SKELETON |
| 003 | AmneziaWG | SKELETON |
| 004 | IKEv2/IPsec | SKELETON |
| 005 | IKEv1/IPsec | SKELETON |
| 006 | IPsec ESP | SKELETON |
| 007 | IPsec AH | SKELETON |
| 008 | L2TP/IPsec | SKELETON |
| 009 | L2TPv3 | SKELETON |
| 010 | L2TPv3/IPsec | SKELETON |
| 011 | SSTP / MS-SSTP | SKELETON |
| 012 | PPTP | SKELETON |
| 013 | SoftEther VPN Protocol | SKELETON |
| 014 | EtherIP | SKELETON |
| 015 | EtherIP/IPsec | SKELETON |
| 016 | Cisco AnyConnect | SKELETON |
| 017 | OpenConnect / ocserv-compatible | SKELETON |
| 018 | Palo Alto GlobalProtect | SKELETON |
| 019 | Fortinet FortiGate SSL VPN | SKELETON |
| 020 | Pulse Secure | SKELETON |
| 021 | Ivanti Connect Secure | SKELETON |
| 022 | Juniper Network Connect | SKELETON |
| 023 | F5 BIG-IP SSL VPN | SKELETON |
| 024 | Array Networks SSL VPN | SKELETON |
| 025 | Check Point VPN | SKELETON |
| 026 | SonicWall NetExtender / SSL VPN | SKELETON |
| 027 | SonicWall Global VPN / IPsec | SKELETON |
| 028 | Sophos SSL VPN | SKELETON |
| 029 | Sophos IPsec Remote Access | SKELETON |
| 030 | WatchGuard IKEv2 VPN | SKELETON |
| 031 | WatchGuard SSL VPN | SKELETON |
| 032 | WatchGuard L2TP VPN | SKELETON (generic numbered folder due connector write filtering) |
| 033 | Aruba VIA | SKELETON (generic numbered folder) |
| 034 | Citrix Secure Access / Gateway VPN | SKELETON (generic numbered folder) |
| 035 | Barracuda TINA VPN | SKELETON (generic numbered folder) |
| 036 | Juniper Secure Connect | SKELETON (generic numbered folder) |
| 037 | VLESS | SKELETON (generic numbered folder; Xray-family shared audit planned) |
| 038 | VMess | SKELETON (generic numbered folder; Xray-family shared audit planned) |
| 039 | Trojan | SKELETON (generic numbered folder) |
| 040 | Shadowsocks | SKELETON (generic numbered folder) |
| 041 | Shadowsocks 2022 | SKELETON (generic numbered folder) |
| 042 | Hysteria | SKELETON (generic numbered folder) |
| 043 | Hysteria2 | SKELETON (generic numbered folder) |
| 044 | TUIC | SKELETON (generic numbered folder) |
| 045 | AnyTLS | SKELETON (generic numbered folder) |
| 046 | ShadowTLS | SKELETON (generic numbered folder) |
| 047 | NaiveProxy | SKELETON (generic numbered folder) |
| 048 | Snell | SKELETON (generic numbered folder) |
| 049 | SOCKS4 | SKELETON (generic numbered folder) |
| 050 | SOCKS4a | SKELETON (generic numbered folder) |
| 051 | SOCKS5 | SKELETON (generic numbered folder) |
| 052 | HTTP Proxy | SKELETON (generic numbered folder) |
| 053 | HTTPS / HTTP CONNECT | SKELETON (generic numbered folder) |
| 054 | SSH Tunnel | SKELETON (generic numbered folder) |
| 055 | Tor SOCKS | RESERVED (`.gitkeep`) |
| 056 | Tailscale | PENDING |
| 057 | ZeroTier | PENDING |
| 058 | NetBird | PENDING |
| 059 | Netmaker | PENDING |
| 060 | Nebula | PENDING |
| 061 | Tinc | PENDING |
| 062 | innernet | PENDING |
| 063 | GRE | PENDING |
| 064 | GRE over IPsec | PENDING |
| 065 | IP-in-IP / IPIP | PENDING |
| 066 | IPIP over IPsec | PENDING |
| 067 | VTI/IPsec | PENDING |
| 068 | XFRM/IPsec | PENDING |
| 069 | VXLAN | PENDING |
| 070 | VXLAN over IPsec | PENDING |
| 071 | DMVPN | PENDING |
| 072 | Cisco FlexVPN | PENDING |
| 073 | GETVPN | PENDING |
| 074 | REALITY | PENDING |
| 075 | XTLS | PENDING |
| 076 | XTLS Vision | PENDING |
| 077 | TLS | PENDING |
| 078 | uTLS / TLS Fingerprinting | PENDING |
| 079 | Cloak | PENDING |
| 080 | TLS Fragmentation | PENDING |
| 081 | TCP | PENDING |
| 082 | UDP | PENDING |
| 083 | QUIC | PENDING |
| 084 | WebSocket | PENDING |
| 085 | HTTP/1.1 | PENDING |
| 086 | HTTP/2 | PENDING |
| 087 | HTTP/3 | PENDING |
| 088 | gRPC | PENDING |
| 089 | mKCP | PENDING |
| 090 | KCP | PENDING |
| 091 | XHTTP | PENDING |
| 092 | RAW | PENDING |
| 093 | DTLS | PENDING |

## Shared upstream dossiers
Many numbered entries share the same codebase. To avoid duplicating thousands of identical source-tree notes, create deep upstream dossiers under `research/upstreams/` and link each relevant numbered entry to them. Examples:

- OpenVPN family: OpenVPN 3 / OpenVPN 2 / Tunnelblick / Pritunl reference / Amnezia reference
- WireGuard family: official platform implementations / wireguard-go / AmneziaWG / Tailscale / NetBird
- Xray family: Xray-core / v2rayN / v2rayNG / Hiddify / compatible Mihomo clients
- Mihomo/Clash family
- sing-box family (research/reference subject to license architecture)
- OpenConnect enterprise family
- strongSwan/native IPsec family
- SoftEther family
- Hysteria/TUIC/modern proxy family
- Mesh/overlay family

A protocol dossier must still contain protocol-specific conclusions even when it links to a shared upstream dossier.

## Research campaign rule
Never change an entry from `SKELETON`, `RESERVED`, or `PENDING` to `COMPLETE-RESEARCH-v1` just because one client was found. Completion requires the full template, evidence, sources, license review, and a PVNetwork reuse decision.