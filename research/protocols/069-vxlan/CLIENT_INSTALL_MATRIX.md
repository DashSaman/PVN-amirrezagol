# VXLAN — Client / Peer Install Matrix

Reviewed: 2026-08-15

VXLAN uses VTEPs and is not a canonical consumer VPN-client protocol.

Linux and supported virtual-switch/network platforms are valid infrastructure peers. Containers/namespaces may participate when the host kernel/platform supplies VXLAN and required privileges. Android/iOS/macOS/Windows consumer app support is not inferred or promoted by this dossier.

A workload behind a VTEP does not itself need a VXLAN client; the VTEP encapsulates/decapsulates Ethernet frames on its behalf.