# VXLAN — Data Path and Wire Flow

Reviewed: 2026-08-15

Typical path: inner Ethernet frame -> VTEP selects VNI and remote VTEP/FDB target -> prepend 8-byte VXLAN header -> UDP -> outer IP -> underlay -> remote VTEP -> validate VNI/framing -> decapsulate inner Ethernet -> bridge/deliver.

RFC 7348 defines a 24-bit VNI and UDP encapsulation. VXLAN is generally one-to-many: endpoint reachability can be learned dynamically or configured statically. Broadcast/unknown/multicast handling may use multicast or implementation-specific replication/control-plane behavior.

Encapsulation increases frame size. RFC 7348 recommends underlay MTU sufficient for added overhead and says VTEPs must not fragment VXLAN packets. Bare VXLAN payload remains unencrypted.

Linux current source is pinned at `drivers/net/vxlan/vxlan_core.c`; Linux documentation describes FDB learning/static entries and offload introspection.