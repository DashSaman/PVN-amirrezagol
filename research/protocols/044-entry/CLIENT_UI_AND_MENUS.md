# 044 TUIC v5 — major client UI/menu maps

Reviewed: 2026-08-15

ClashRS is the selected permissive open-source UI/core reference. Its TUIC converter maps the practical profile fields required by the product UI:
- server/port;
- UUID and raw password;
- UDP relay mode, defaulting in that implementation to `native`;
- SNI/ALPN and certificate verification;
- heartbeat, request/idle/GC timeouts;
- congestion controller;
- UDP packet-size and QUIC stream/send/receive windows;
- optional TLS cert/key and connect-via behavior.

Generic product/client layers include profile import/select, routing/DNS/TUN/system proxy, connect/disconnect, logs/traffic and subscription lifecycle. Those are not TUIC commands.

PVNetwork must store the raw reusable password in secure storage because the session token is derived from it using the TLS exporter. Certificate verification bypass and 0-RTT must be explicit high-risk/advanced controls, never inferred defaults.
