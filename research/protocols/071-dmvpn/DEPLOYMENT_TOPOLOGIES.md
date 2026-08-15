# DMVPN — Deployment Topologies

Reviewed: 2026-08-15

Canonical architecture is hub-and-spoke with multipoint GRE and NHRP, optionally/dominantly protected by IPsec. NHRP resolution can enable direct spoke-to-spoke data paths; routing protocols carry prefixes.

Common design distinctions include hub role vs spoke role, dynamic spoke NBMA addresses, redundant NHS/hubs, and shortcut/redirect behavior. Exact Cisco phase/features remain vendor/release dependent and require later lab interoperability testing.

DMVPN is distinct from plain GRE-over-IPsec (064): generic GRE+IPsec lacks the NHRP dynamic mapping/shortcut architecture.