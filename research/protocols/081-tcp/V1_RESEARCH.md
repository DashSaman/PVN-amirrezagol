# 081 — TCP — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: foundational OS transport protocol, not a VPN protocol.

Decision: **`OS TRANSPORT / NO DEDICATED ENGINE`**.

Use the operating system/networking runtime selected by each engine. Engine-specific TCP framing, keepalive, congestion and connection policy remain inside the relevant adapter/capability model.

Later v2 adds standards/transport behavior, OS/library implementation references and technical data-path relationships.