# 041 Shadowsocks 2022 — deployment topologies

Evidence-backed topologies:
1. dedicated client (`sslocal`) -> SS2022 server (`ssserver`) -> destination;
2. Android VPN/local proxy layer -> embedded shadowsocks-rust SS2022 client -> server;
3. TCP-only, UDP-only or TCP+UDP according to runtime configuration;
4. EIH single-port multi-user server using uPSK identity lookup;
5. EIH relay chain using iPSKs to identify/forward to next layer/server;
6. Docker/OCI deployment and upstream Kubernetes/Helm deployment;
7. optional plugin process around the base transport, reviewed independently.

No CDN/TLS/WebSocket behavior is inherent to SS2022. No classic-AEAD profile can be auto-converted because PSK derivation, replay framing and UDP sessions differ.
