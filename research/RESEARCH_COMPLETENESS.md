# PVNetwork — 93-Entry Research Completeness Tracker

This file tracks the **research campaign**, not implementation. `SKELETON` means a per-entry folder/file exists but the exhaustive template is not yet complete. `RESERVED` means the numbered slot exists but has not yet received a full dossier. `PENDING` means the per-entry dossier still needs to be committed. `IN-RESEARCH` means evidence-backed upstream research has started. `EVIDENCE-GAPS` means research exists but a documented source/tool/write gap remains. `COMPLETE-RESEARCH-v1` is allowed only after all gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md` are satisfied with evidence.

| # | Entry | Current research state |
|---:|---|---|
| 001 | OpenVPN | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled with current OpenVPN3 3.11.7 release pin in `research/protocols/001-openvpn/V1_GATE_RECONCILIATION.md` |
| 002 | WireGuard | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled in `research/protocols/002-wireguard/V1_GATE_RECONCILIATION.md` |
| 003 | AmneziaWG | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled with versioned AWG/platform uncertainties retained in `research/protocols/003-amneziawg/V1_GATE_RECONCILIATION.md` |
| 004 | IKEv2/IPsec | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled with strongSwan 6.0.7/native-platform evidence in `research/protocols/004-ikev2-ipsec/V1_GATE_RECONCILIATION.md` |
| 005 | IKEv1/IPsec | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled with explicit legacy/vendor-compatibility policy in `research/protocols/005-ikev1-ipsec/V1_GATE_RECONCILIATION.md` |
| 006 | IPsec ESP | COMPLETE-RESEARCH-v1 — all applicable original research gates reconciled as an IPsec data-plane capability in `research/protocols/006-ipsec-esp/V1_GATE_RECONCILIATION.md` |
| 007 | IPsec AH | COMPLETE-RESEARCH-v1 — all applicable original research gates reconciled with non-encrypting/advanced/N-A-standalone treatment in `research/protocols/007-ipsec-ah/V1_GATE_RECONCILIATION.md` |
| 008 | L2TP/IPsec | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled as a layered IPsec + L2TPv2 + PPP composition in `research/protocols/008-l2tp-ipsec/V1_GATE_RECONCILIATION.md` |
| 009 | L2TPv3 | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled with infrastructure peer/N-A consumer treatment in `research/protocols/009-l2tpv3/V1_GATE_RECONCILIATION.md` |
| 010 | L2TPv3/IPsec | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled as a protected L2TPv3 + typed IPsec composition in `research/protocols/010-l2tpv3-ipsec/V1_GATE_RECONCILIATION.md` |
| 011 | SSTP / MS-SSTP | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled; Linux client source corrected to canonical GitLab tag 1.0.20 in `research/protocols/011-sstp/V1_GATE_RECONCILIATION.md` |
| 012 | PPTP | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled with obsolete-security/legacy-only policy and pinned Poptop 1.5.0 evidence in `research/protocols/012-pptp/V1_GATE_RECONCILIATION.md` |
| 013 | SoftEther VPN Protocol | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled in `research/protocols/013-softether/V1_GATE_RECONCILIATION.md` |
| 014 | EtherIP | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled with infrastructure/N-A treatment in `research/protocols/014-etherip/V1_GATE_RECONCILIATION.md` |
| 015 | EtherIP/IPsec | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled as an EtherIP + typed-IPsec composition in `research/protocols/015-etherip-ipsec/V1_GATE_RECONCILIATION.md` |
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
- `research/upstreams/strongswan-family/`
- `research/upstreams/openconnect-family/`
- `research/upstreams/softether-family/`
- `research/upstreams/hysteria-family/`
- `research/upstreams/mesh-overlay-family/`
- `research/upstreams/client-references/`

Important client-reference dossiers/indexes now include OpenVPN Connect/OpenVPN3/OpenVPN GUI/Tunnelblick/Pritunl, Amnezia Client, Happ, Clash Verge Rev, FlClash index, and a multi-protocol GUI license/reference note.

## Shared research principle
Many numbered entries share the same codebase. To avoid duplicating thousands of identical source-tree notes, exhaustive source/client analysis belongs under `research/upstreams/`, with protocol-specific conclusions linked from each numbered dossier.

## Current known connector/documentation blockers
- Some detailed networking research files were historically rejected by the GitHub write safety layer. Do not repeat the same blocked write unchanged more than twice; use smaller evidence-backed documents.
- Detailed Xray/Mihomo shared dossier writes had earlier connector filtering; pinned findings must remain traceable through smaller documents.
- Some protocol slots 056–093 were not initially materialized as separate detailed folders. Continue from tracker truth rather than treating absence of a folder as completion.

## Research campaign rule
Never change an entry from `SKELETON`, `RESERVED`, `PENDING`, `IN-RESEARCH`, or `EVIDENCE-GAPS` to `COMPLETE-RESEARCH-v1` just because one client was found. Completion requires the full template, traceable evidence, source-tree references, license review, issues/forums/release review, UI/config/storage analysis and an explicit PVNetwork reuse decision.