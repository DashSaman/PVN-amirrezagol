# 069 — VXLAN — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: Layer-2 overlay encapsulation over IP/UDP; not encrypted by itself.

Decision: **`ADVANCED DATA-CENTER/SITE-TO-SITE OVERLAY / OS-KERNEL FIRST`**.

Prefer mature OS/kernel VXLAN implementation. Do not present raw VXLAN as encrypted VPN protection or prioritize it in consumer onboarding.

Later v2 adds exact implementations, VNI/UDP/data-flow semantics, install matrices, admin menus and deployment topologies.
