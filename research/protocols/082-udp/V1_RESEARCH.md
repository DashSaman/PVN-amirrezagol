# 082 — UDP — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: foundational OS datagram transport, not a VPN protocol.

Decision: **`OS TRANSPORT / NO DEDICATED ENGINE`**.

Use the OS/runtime networking stack selected by each engine. Test NAT behavior, IPv4/IPv6, MTU, packet loss, network handover and platform background restrictions per actual protocol using UDP.

Later v2 adds standards/OS implementations and exact role in each higher-level protocol data path.