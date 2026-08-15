# VXLAN — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Boundary |
|---|---|---|
| Modern Linux | Supported | Kernel VXLAN + matching iproute2; privileged network admin; UDP underlay reachability |
| Open vSwitch on supported OS | Supported separate implementation | OVS supplies its own VXLAN datapath/config model; lifecycle/license remain OVS-specific |
| Containers / namespaces | Conditional | VXLAN may exist in host or namespace with required capabilities; not a protocol-owned container package |
| Kubernetes | Infrastructure use possible | CNIs may use VXLAN, but a specific CNI is not the canonical VXLAN implementation for this entry |
| Consumer mobile/desktop | NOT PROMOTED | No canonical bare-VXLAN consumer app path; infrastructure capability only |

Architecture support follows the selected kernel/OVS/platform rather than an independent VXLAN binary.