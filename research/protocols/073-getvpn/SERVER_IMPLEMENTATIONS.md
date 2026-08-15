# Cisco GETVPN — Server / Peer Implementations

Reviewed: 2026-08-15

Cisco Group Encrypted Transport VPN (GETVPN) is a proprietary Cisco group-encryption architecture for private WAN multicast/unicast traffic. It uses centralized group key management plus IPsec group security associations rather than point-to-point overlay tunnels.

Canonical implementation: supported Cisco IOS/IOS XE key-server (KS) and group-member (GM) roles. Current Cisco IOS XE 17 documentation, updated 2026-04-24, documents both legacy GDOI and GETVPN G-IKEv2/GKM operation.

Standards context changed materially in 2025: RFC 9838 standardized G-IKEv2 and **obsoletes RFC 6407 GDOI**. Cisco's current 2026 documentation states its GETVPN G-IKEv2 exchanges conform to an IETF G-IKEv2 standards draft and exposes Cisco GKM versions; this dossier therefore does **not** claim exact RFC 9838 conformance without explicit Cisco evidence. Cisco also continues to document/offer GDOI alongside GKM on key servers.

Cisco source is proprietary/reference-only. No open-source GETVPN clone is selected. Generic IPsec implementations are standards/security references, not proof of GETVPN compatibility.