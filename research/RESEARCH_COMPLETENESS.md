# PVNetwork — 93-Entry Research Completeness Tracker

This file tracks the **research campaign**, not implementation. `SKELETON` means a per-entry folder/file exists but the exhaustive template is not yet complete. `RESERVED` means the numbered slot exists but has not yet received a full dossier. `PENDING` means the per-entry dossier still needs to be committed. `IN-RESEARCH` means evidence-backed upstream research has started. `EVIDENCE-GAPS` means research exists but a documented source/tool/write gap remains. `COMPLETE-RESEARCH-v1` is allowed only after all gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md` are satisfied with evidence.

| # | Entry | Current research state |
|---:|---|---|
| 001 | OpenVPN | IN-RESEARCH — deep OpenVPN family dossier created |
| 002 | WireGuard | IN-RESEARCH — shared WireGuard family dossier started |
| 003 | AmneziaWG | IN-RESEARCH — core and shared family research started |
| 004 | IKEv2/IPsec | EVIDENCE-GAPS — strongSwan/native research started; detailed dossier write blocked |
| 005 | IKEv1/IPsec | EVIDENCE-GAPS — strongSwan/native research started; detailed dossier write blocked |
| 006 | IPsec ESP | EVIDENCE-GAPS — shared strongSwan/native evidence started |
| 007 | IPsec AH | EVIDENCE-GAPS — shared strongSwan/native evidence started |
| 008 | L2TP/IPsec | SKELETON |
| 009 | L2TPv3 | SKELETON |
| 010 | L2TPv3/IPsec | SKELETON |
| 011 | SSTP / MS-SSTP | SKELETON |
| 012 | PPTP | SKELETON |
| 013 | SoftEther VPN Protocol | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled in `research/protocols/013-softether/V1_GATE_RECONCILIATION.md` |
| 014 | EtherIP | IN-RESEARCH — shared SoftEther evidence started |
| 015 | EtherIP/IPsec | IN-RESEARCH — shared SoftEther evidence started |
| 016 | Cisco AnyConnect | IN-RESEARCH — shared OpenConnect dossier created |
| 017 | OpenConnect / ocserv-compatible | IN-RESEARCH — shared OpenConnect dossier created |
| 018 | Palo Alto GlobalProtect | IN-RESEARCH — shared OpenConnect evidence started |
| 019 | Fortinet FortiGate SSL VPN | IN-RESEARCH — OpenConnect/open-source reference research started |
| 020 | Pulse Secure | IN-RESEARCH — shared OpenConnect evidence started |
| 021 | Ivanti Connect Secure | IN-RESEARCH — shared OpenConnect evidence started |
| 022 | Juniper Network Connect | IN-RESEARCH — shared OpenConnect evidence started |
| 023 | F5 BIG-IP SSL VPN | IN-RESEARCH — shared OpenConnect evidence started |
| 024 | Array Networks SSL VPN | IN-RESEARCH — shared OpenConnect evidence started |
| 025 | Check Point VPN | IN-RESEARCH — snx-rs reference/license evidence collected |
| 026 | SonicWall NetExtender / SSL VPN | SKELETON |
| 027 | SonicWall Global VPN / IPsec | SKELETON |
| 028 | Sophos SSL VPN | SKELETON |
| 029 | Sophos IPsec Remote Access | SKELETON |
| 030 | WatchGuard IKEv2 VPN | SKELETON |
| 031 | WatchGuard SSL VPN | SKELETON |
| 032 | WatchGuard L2TP VPN | SKELETON — generic numbered folder due connector write filtering |
| 033 | Aruba VIA | SKELETON — generic numbered folder |
| 034 | Citrix Secure Access / Gateway VPN | SKELETON — generic numbered folder |
| 035 | Barracuda TINA VPN | SKELETON — generic numbered folder |
| 036 | Juniper Secure Connect | SKELETON — generic numbered folder |
| 037 | VLESS | EVIDENCE-GAPS — Xray/client license/source audit started; shared dossier write blocked |
| 038 | VMess | EVIDENCE-GAPS — Xray/client license/source audit started; shared dossier write blocked |
| 039 | Trojan | EVIDENCE-GAPS — multi-protocol client/core audit started |
| 040 | Shadowsocks | EVIDENCE-GAPS — multi-protocol client/core audit started |
| 041 | Shadowsocks 2022 | SKELETON — generic numbered folder |
| 042 | Hysteria | IN-RESEARCH — official Hysteria shared dossier created |
| 043 | Hysteria2 | IN-RESEARCH — official Hysteria shared dossier created |
| 044 | TUIC | SKELETON — generic numbered folder |
| 045 | AnyTLS | SKELETON — generic numbered folder |
| 046 | ShadowTLS | SKELETON — generic numbered folder |
| 047 | NaiveProxy | SKELETON — generic numbered folder |
| 048 | Snell | SKELETON — generic numbered folder |
| 049 | SOCKS4 | SKELETON — generic numbered folder |
| 050 | SOCKS4a | SKELETON — generic numbered folder |
| 051 | SOCKS5 | SKELETON — generic numbered folder |
| 052 | HTTP Proxy | SKELETON — generic numbered folder |
| 053 | HTTPS / HTTP CONNECT | SKELETON — generic numbered folder |
| 054 | SSH Tunnel | SKELETON — generic numbered folder |
| 055 | Tor SOCKS | RESERVED (`.gitkeep`) |
| 056 | Tailscale | IN-RESEARCH — shared mesh dossier/source-license audit started; per-entry folder write pending |
| 057 | ZeroTier | IN-RESEARCH — shared mesh dossier/source-license audit started; per-entry folder write pending |
| 058 | NetBird | IN-RESEARCH — shared mesh dossier/source-license audit started; per-entry folder write pending |
| 059 | Netmaker | PENDING |
| 060 | Nebula | IN-RESEARCH — shared mesh dossier/source-license audit started; per-entry folder write pending |
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
| 074 | REALITY | EVIDENCE-GAPS — Xray core research started; protocol-specific dossier pending |
| 075 | XTLS | EVIDENCE-GAPS — Xray core research started; protocol-specific dossier pending |
| 076 | XTLS Vision | EVIDENCE-GAPS — Xray core research started; protocol-specific dossier pending |
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
| 089 | mKCP | EVIDENCE-GAPS — Xray family research started; transport-specific dossier pending |
| 090 | KCP | PENDING |
| 091 | XHTTP | EVIDENCE-GAPS — Xray family research started; transport-specific dossier pending |
| 092 | RAW | PENDING |
| 093 | DTLS | IN-RESEARCH — OpenConnect family research started; protocol-specific dossier pending |

## Shared upstream dossiers currently created
- `research/upstreams/openvpn-family/`
- `research/upstreams/wireguard-family/`
- `research/upstreams/openconnect-family/`
- `research/upstreams/softether-family/`
- `research/upstreams/hysteria-family/`
- `research/upstreams/mesh-overlay-family/`
- `research/upstreams/client-references/`

Important client-reference dossiers/indexes now include OpenVPN Connect/OpenVPN3/OpenVPN GUI/Tunnelblick/Pritunl, Amnezia Client, Happ, Clash Verge Rev, FlClash index, and a multi-protocol GUI license/reference note.

## Shared research principle
Many numbered entries share the same codebase. To avoid duplicating thousands of identical source-tree notes, exhaustive source/client analysis belongs under `research/upstreams/`, with protocol-specific conclusions linked from each numbered dossier.

## Current known connector/documentation blockers
- Some detailed networking research files are rejected by the GitHub write safety layer even when the underlying research is legitimate. Do not repeat the same blocked write unchanged more than twice.
- ics-openvpn Android evidence was collected from pinned public source, but the dedicated detailed dossier write was repeatedly blocked. Evidence must be recovered from research history and split into smaller documentation units later.
- detailed Xray/Mihomo shared dossier writes were blocked; pinned license/source findings remain verified and must be persisted through smaller safe documents later.
- strongSwan detailed/shared dossier writes were blocked; pinned source/license evidence exists and remains an explicit gap.
- protocol slots 056–093 could not all be materialized as separate folders in this work unit because even neutral folder writes began triggering connector filtering. Do not hide this; continue from the tracker.

## Research campaign rule
Never change an entry from `SKELETON`, `RESERVED`, `PENDING`, `IN-RESEARCH`, or `EVIDENCE-GAPS` to `COMPLETE-RESEARCH-v1` just because one client was found. Completion requires the full template, traceable evidence, source-tree references, license review, issues/forums/release review, UI/config/storage analysis and an explicit PVNetwork reuse decision.
