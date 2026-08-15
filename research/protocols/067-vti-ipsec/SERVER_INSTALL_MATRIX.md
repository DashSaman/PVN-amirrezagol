# VTI/IPsec — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Evidence-backed boundary |
|---|---|---|
| Linux kernel 3.6+ family | VTI exists; important behavior changed in later kernels | strongSwan current docs state VTI support since Linux 3.6 with important changes later; current production reference should use a maintained kernel |
| Modern Linux 4.19+ | Supported but XFRM interfaces generally preferred for new designs | VTI remains available; XFRM interfaces have documented advantages and are entry 068 |
| IPv4 VTI | Supported | `net/ipv4/ip_vti.c`; endpoint addresses + marks/policies; IPsec tunnel mode |
| IPv6 VTI (`vti6`) | Supported by Linux tooling/kernel family | Separate address-family interface path; exact kernel/platform support must be verified for deployment |
| Containers/netns | Conditional | Requires host/kernel XFRM/VTI, privileges and namespace design; no independent container server |
| Non-Linux consumer platforms | NOT PROMOTED | VTI is a Linux-local interface abstraction; native IPsec elsewhere does not imply Linux VTI semantics |

IPsec/IKE server/client OS/package matrices are reused from `research/upstreams/strongswan-family/reference-v2/`.
