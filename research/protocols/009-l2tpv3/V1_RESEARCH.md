# 009 — L2TPv3 — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: Layer-2 pseudowire/tunneling technology, primarily advanced/site-to-site rather than normal consumer remote access.

Research decision:

**`ADVANCED SITE-TO-SITE / KERNEL-OR-OS-IMPLEMENTATION FIRST / LOW CONSUMER PRIORITY`**

Primary directions:

- Linux kernel L2TP subsystem for Linux data plane;
- typed userspace helper/service for configuration;
- SoftEther/router implementations as interoperability references.

Security rule: plain L2TPv3 provides encapsulation, not confidentiality. Do not market it as encrypted VPN protection.

Shared evidence: `research/upstreams/classic-tunnels-family/`.

Later v2 must add exact Linux/server/router implementations, install matrices, full menus/config fields, packet/data path, ports/session establishment and deployment topologies.
