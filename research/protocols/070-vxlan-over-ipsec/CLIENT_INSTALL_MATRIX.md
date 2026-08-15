# VXLAN over IPsec — Client / Peer Install Matrix

Reviewed: 2026-08-15

This is infrastructure VTEP composition, not a canonical consumer VPN client.

Linux/network-platform peers with both VXLAN and IPsec capabilities are the evidence-backed deployment class. Containers/namespaces are conditional on host/kernel/network privileges. Android/iOS/macOS/Windows consumer support is not inferred from standalone native IPsec or networking APIs.

Workloads behind a protected VTEP do not themselves require a VXLAN-over-IPsec client.