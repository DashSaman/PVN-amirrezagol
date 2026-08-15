# 045 AnyTLS — deployment topologies

Evidence-backed topologies:
1. AnyTLS client -> TLS -> AnyTLS server -> TCP destination;
2. multiple logical proxied streams multiplexed over pooled TLS sessions;
3. UDP proxying through sing-box udp-over-tcp v2 special target;
4. failed authentication close or configured fallback to legitimate L7 application;
5. product local TUN/SOCKS/routing feeding an AnyTLS outbound;
6. TLS certificate/SNI/trust policy owned by a separate TLS layer/object;
7. v1/v2 mixed peers using negotiated/fallback capability;
8. server-driven padding scheme updates scoped to later client sessions.

Do not equate fallback with Trojan, AnyTLS with ShadowTLS, or generic TLS availability with AnyTLS compatibility.
