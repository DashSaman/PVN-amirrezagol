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
| 009 | L2TPv3 | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled with infrastructure/N-A consumer treatment in `research/protocols/009-l2tpv3/V1_GATE_RECONCILIATION.md` |
| 010 | L2TPv3/IPsec | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled as a protected L2TPv3 + typed IPsec composition in `research/protocols/010-l2tpv3-ipsec/V1_GATE_RECONCILIATION.md` |
| 011 | SSTP / MS-SSTP | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled; Linux client source corrected to canonical GitLab tag 1.0.20 in `research/protocols/011-sstp/V1_GATE_RECONCILIATION.md` |
| 012 | PPTP | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled with obsolete-security/legacy-only policy and pinned Poptop 1.5.0 evidence in `research/protocols/012-pptp/V1_GATE_RECONCILIATION.md` |
| 013 | SoftEther VPN Protocol | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled in `research/protocols/013-softether/V1_GATE_RECONCILIATION.md` |
| 014 | EtherIP | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled with infrastructure/N-A treatment in `research/protocols/014-etherip/V1_GATE_RECONCILIATION.md` |
| 015 | EtherIP/IPsec | COMPLETE-RESEARCH-v1 — all 20 original research gates reconciled as an EtherIP + typed-IPsec composition in `research/protocols/015-etherip-ipsec/V1_GATE_RECONCILIATION.md` |
| 016 | Cisco AnyConnect | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled in shared OpenConnect family audit `V1_GATE_RECONCILIATION_016_024.md`; proprietary vendor certification remains separate |
| 017 | OpenConnect / ocserv-compatible | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled against OpenConnect v9.21 exact source pin and shared family evidence |
| 018 | Palo Alto GlobalProtect | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled; SSO/HIP/server-version certification uncertainties retained |
| 019 | Fortinet FortiGate SSL VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with partial/experimental upstream capability boundaries retained |
| 020 | Pulse Secure | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with auth/posture limitations explicit |
| 021 | Ivanti Connect Secure | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with appliance/version/auth/posture matrix retained for certification |
| 022 | Juniper Network Connect | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with legacy/browser/TNCC/IPv6 limitations explicit |
| 023 | F5 BIG-IP SSL VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with experimental auth/DTLS limitations explicit |
| 024 | Array Networks SSL VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with limited/experimental support boundary explicit |
| 025 | Check Point VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with current snx-rs v6.2.4 exact source pin, AGPL boundary, official-vendor authority and route/reconnect regression lessons in `research/protocols/025-check-point-vpn/V1_GATE_RECONCILIATION.md` |
| 026 | SonicWall NetExtender / SSL VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled against current NetExtender 10.3/Windows 10.3.5 official evidence, proprietary-source N/A boundary, multi-transport capability model and unmerged OpenConnect limitation in `research/protocols/026-sonicwall-netextender/V1_GATE_RECONCILIATION.md` |
| 027 | SonicWall Global VPN / IPsec | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with current SonicWall GroupVPN/GVC provisioning/XAUTH/RCF/virtual-adapter evidence and strongSwan 6.0.7 standards-engine boundary in `research/protocols/027-sonicwall-global-vpn-ipsec/V1_GATE_RECONCILIATION.md` |
| 028 | Sophos SSL VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with current Sophos Connect 2.5 MR1/2.0 MR1 evidence, `.ovpn` OpenVPN compatibility, `.pro` provisioning boundary and OpenVPN3-first reuse decision in `research/protocols/028-sophos-ssl-vpn/V1_GATE_RECONCILIATION.md` |
| 029 | Sophos IPsec Remote Access | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with current SFOS modern IKEv1 remote-access mode, retired `IPsec (legacy)` separation, `.scx/.tgb/.pro` boundaries and strongSwan 6.0.7-first standards-engine decision in `research/protocols/029-sophos-ipsec-remote-access/V1_GATE_RECONCILIATION.md` |
| 030 | WatchGuard IKEv2 VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with native Windows/macOS/iOS, Android strongSwan, optional WatchGuard Windows client, certificate/EAP-MS-CHAPv2/AuthPoint and generated profile evidence in `research/protocols/030-watchguard-ikev2/V1_GATE_RECONCILIATION.md` |
| 031 | WatchGuard SSL VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with current `client.ovpn` OpenVPN compatibility, first-party Windows/macOS SAML boundary, AuthPoint/MFA, `.wgssl`, TLS/client lifecycle and OpenVPN3-first reuse decision in `research/protocols/031-watchguard-ssl-vpn/V1_GATE_RECONCILIATION.md` |
| 032 | WatchGuard L2TP VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with secure-default L2TP/IPsec layering, PSK/certificate tunnel auth, MS-CHAPv2/RADIUS/AuthPoint user auth, native Windows/macOS/iOS and Android 12+ native-unavailable boundary in `research/protocols/032-watchguard-l2tp/V1_GATE_RECONCILIATION.md` |
| 033 | Aruba VIA | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with current HPE Aruba Networking VIA/VPNC profile, IKE/IPsec, auth, split/full-tunnel, five-platform client, lifecycle and proprietary-source boundaries in `research/protocols/033-aruba-via/V1_GATE_RECONCILIATION.md` |
| 034 | Citrix Secure Access / Gateway VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with current Citrix Secure Access/NetScaler Gateway TLS-DTLS, nFactor/EPA, routing/DNS, five-platform client, 2026 release and proprietary-source boundaries in `research/protocols/034-citrix-secure-access/V1_GATE_RECONCILIATION.md` |
| 035 | Barracuda TINA VPN | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with current Barracuda TINA proprietary transport/auth/profile/failover, modern-crypto and official-client boundaries in `research/protocols/035-barracuda-tina/V1_GATE_RECONCILIATION.md` |
| 036 | Juniper Secure Connect | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled with current SRX/vSRX, IKE/IPsec, EAP/certificate, protected-network, client-platform, licensing and proprietary-source boundaries in `research/protocols/036-juniper-secure-connect/V1_GATE_RECONCILIATION.md` |
| 037 | VLESS | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled against pinned Xray-core/libXray and major-client architecture/license/config/storage/UI/security/test evidence in `research/protocols/037-entry/V1_GATE_RECONCILIATION.md`; exact runtime combinations remain later certification gates |
| 038 | VMess | COMPLETE-RESEARCH-v1 — all 20 original gates reconciled against pinned Xray-core/libXray and shared client/config/storage/UI/security/test evidence in `research/protocols/038-entry/V1_GATE_RECONCILIATION.md`; VMess remains a compatibility target distinct from VLESS |
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