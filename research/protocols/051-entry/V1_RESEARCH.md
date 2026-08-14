# 051 — SOCKS5 — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: foundational generic proxy protocol.

Decision: **`CORE PRODUCT PROXY CAPABILITY / USE EXISTING APPROVED CORE OR LIBRARY`**.

PVNetwork canonical profile should keep endpoint, optional username/password secure references, DNS behavior, TCP capability and UDP ASSOCIATE capability separate. Do not infer UDP support from a successful TCP proxy test.

No dedicated SOCKS5 engine should be added if an approved core/library already provides correct behavior.

Later v2 adds standards/source implementations, server/client install matrices, menus, authentication/data flow and interoperability evidence.
