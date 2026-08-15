# GRE over IPsec — Server / Peer Implementations

Reviewed: 2026-08-15

GRE over IPsec is a composition: a GRE endpoint plus an IPsec/IKE security endpoint. It is not a distinct standardized daemon.

## Serious implementations

- **Linux kernel GRE + Linux XFRM/IPsec + strongSwan** — principal open-source reference. The GRE half is pinned in entry 063 (`torvalds/linux@15ef2f78...`, iproute2 `da2ccdf...`). The IPsec/IKE server/client ecosystem, source/license/activity pins and platform distinctions are already exhaustively mapped under `research/upstreams/strongswan-family/reference-v2/` and entries 004–007.
- **Cisco IOS XE 17.x** — proprietary native implementation. Cisco's 2025 GRE-over-IPsec guide explicitly combines a tunnel interface with an IPsec profile/IKEv2 configuration and documents platform/release constraints.
- Other routers may support the composition, but this dossier does not promote unverified vendor implementations.

## Architecture boundary

GRE supplies payload flexibility/routing. IPsec supplies confidentiality/integrity/authentication. Never attribute IPsec cryptographic properties to GRE itself. Conversely, do not treat GRE protocol 47 as the outer network transport when ESP/NAT-T encapsulates/protects it.

## Reuse decision

Prefer OS-native kernel/XFRM + established IKE implementation (e.g. strongSwan where appropriate) behind platform adapters. Cisco is interoperability/reference-only. Do not implement GRE or IPsec cryptography from scratch.

Evidence: entry 063 dossier; strongSwan family V2 dossier; Cisco GRE over IPsec guide: https://www.cisco.com/c/en/us/td/docs/switches/lan/c9000/lyr3-fwd/gre/gre-configuration-guide/m-gre-over-ipsec.html
