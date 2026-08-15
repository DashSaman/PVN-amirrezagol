# 048 Snell — deployment topologies

Reviewed: 2026-08-15

Evidence-backed topologies:
1. Surge client -> official standalone Linux Snell v5 server -> destination;
2. Surge v4 client -> v5 server backward-compatible path;
3. ordinary TCP + UDP-relay deployment;
4. v5 QUIC-optimized UDP-over-UDP path alongside ordinary UDP-over-TCP;
5. v5 server with egress-interface control / systemd Socket Activation / network-namespace-oriented deployment;
6. v6 beta client/server pair with multi-address listen and DNS IP preference, explicitly without v5 QUIC Proxy Mode;
7. Snell wrapped in ShadowTLS as a separate camouflage layer where Surge supports that composition;
8. Surge Mac embedded Snell V1 server as a distinct legacy/product topology, not equivalent to standalone v5.

No Docker/Kubernetes/HA/CDN topology is inferred from the official binary. Third-party community topologies stay separately licensed and permission-bound.
