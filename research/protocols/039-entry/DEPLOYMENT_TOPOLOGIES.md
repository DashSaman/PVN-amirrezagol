# 039 Trojan — deployment topologies

Reviewed: 2026-08-15

Evidence-backed reference topologies:

1. client -> genuine TLS -> Trojan server -> destination;
2. local SOCKS/TUN/system-proxy client layer -> Xray Trojan outbound -> Xray Trojan inbound -> routed destination;
3. Trojan server with fallback endpoint for invalid/non-Trojan traffic, as documented by original Trojan and Xray fallbacks;
4. panel-managed multi-node Xray deployment where management/subscriptions are separate from the Trojan wire protocol;
5. chained Xray outbounds where each hop has an independent protocol/security review.

Do not infer universal CDN, HA, Kubernetes, double-hop or alternate-Xray-transport compatibility from the Trojan protocol itself. Canonical Trojan-over-TLS remains the baseline interoperability topology.
