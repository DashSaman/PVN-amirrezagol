# 038 VMess — deployment topologies

Reviewed: 2026-08-15

Evidence-backed reference topologies:

1. **Direct client/server** — local wrapper/inbound -> Xray VMess outbound -> Xray VMess inbound -> destination.
2. **VMess + separate stream security** — same topology with a reviewed TLS/REALITY/other supported stream-security layer.
3. **HTTP-family encapsulation** — VMess carried by a supported WebSocket/gRPC/XHTTP/HTTPUpgrade-style transport where the pinned core supports that exact combination.
4. **Client TUN/system proxy** — OS traffic capture occurs before Xray and is a platform/client layer, not VMess itself.
5. **Managed nodes** — 3X-UI/Remnawave-style control planes can provision users/inbounds/subscriptions/nodes; management state is separate from wire protocol.
6. **Chained/routed outbounds** — Xray routing can send traffic to another outbound; each hop has an independent protocol/security capability boundary.
7. **Mux** — current VMess command handling includes Mux; enablement/support remains core/config specific.

No universal CDN, HA, load-balancer, double-hop, Kubernetes or censorship-evasion claim is inferred from the existence of HTTP transports/panels. Such infrastructure combinations require their own evidence.
