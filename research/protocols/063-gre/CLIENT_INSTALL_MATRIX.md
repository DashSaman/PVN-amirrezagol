# GRE — Client / Peer Install Matrix

Reviewed: 2026-08-15

GRE is peer-to-peer infrastructure encapsulation rather than a consumer VPN client protocol. The relevant install matrix is therefore endpoint capability.

| Target | State | Packaging / permissions |
|---|---|---|
| Linux desktop/server | Supported as infrastructure peer | OS kernel GRE support plus iproute2; CAP_NET_ADMIN/root to create tunnel; distribution update lifecycle |
| Cisco/Juniper network OS | Supported where vendor/platform documentation lists GRE | Built into proprietary network OS; administrator privilege |
| Android / Android TV | NOT-APPLICABLE as a canonical bare-GRE consumer client in this research layer | Do not infer support merely from Linux kernel ancestry; Android app sandbox/VpnService is a separate platform contract |
| iOS/iPadOS | NOT-APPLICABLE/unsupported by evidence in this dossier | No canonical bare-GRE consumer client path established |
| macOS | UNKNOWN for product support | No current authoritative bare-GRE product path selected here |
| Windows desktop/server | UNKNOWN for generic bare GRE | PPTP's use of GRE does not establish a generic GRE endpoint API; do not conflate them |

PVNetwork decision: treat GRE as an infrastructure capability behind platform adapters, not as an everywhere-available consumer profile. Implementation support must be capability/evidence based.
