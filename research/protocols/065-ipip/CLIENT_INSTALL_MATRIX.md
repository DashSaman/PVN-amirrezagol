# IPIP — Client / Peer Install Matrix

Reviewed: 2026-08-15

IPIP is an infrastructure tunnel endpoint capability, not a canonical consumer VPN client protocol.

| Target | State | Packaging / permissions |
|---|---|---|
| Linux desktop/server | Supported as infrastructure peer | OS kernel IPIP support + iproute2; CAP_NET_ADMIN/root; distribution update lifecycle |
| Containers | Conditional | Uses host/kernel IPIP capability with required namespace/capabilities; no independent IPIP client binary |
| Android / Android TV | NOT PROMOTED | Android app/VpnService capability does not prove direct generic IPIP tunnel-interface support; do not infer from Linux kernel ancestry |
| iOS/iPadOS | NOT PROMOTED | No authoritative generic bare-IPIP consumer endpoint path selected in this dossier |
| macOS | UNKNOWN for product support | No canonical bare-IPIP consumer packaging path established here |
| Windows | UNKNOWN for generic IPIP | No current authoritative native generic IPIP endpoint path selected in this dossier |

PVNetwork decision: expose IPIP only where a platform adapter has verified native capability. Do not advertise cross-platform consumer support from the protocol specification alone.
