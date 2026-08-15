# IPIP — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Path / requirements |
|---|---|---|
| Linux with IPIP-capable kernel | Supported | Kernel `ipip` tunnel support + iproute2; CAP_NET_ADMIN/root; routable outer IPv4 endpoints; firewall permits protocol 4 |
| Containers/Podman | Not an IPIP implementation itself | Can use host/kernel tunnel capability only with required network namespace/capabilities; no canonical IPIP image |
| Kubernetes | No canonical IPIP server install contract | CNI systems may use IPIP internally, but that does not create a protocol-owned Helm/operator reference for this entry |
| Windows/macOS/mobile consumer targets | Not promoted | No authoritative generic IPIP consumer endpoint path established in this dossier |

Architecture support follows the underlying OS/kernel rather than a separate IPIP binary. Exact production support must remain platform-evidence based.
