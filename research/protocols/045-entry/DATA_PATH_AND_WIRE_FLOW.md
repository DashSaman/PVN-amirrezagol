# 045 AnyTLS — data path and wire flow

Current canonical protocol version metadata is `v=2`.

Bounded flow:
1. TCP connection -> TLS handshake;
2. client sends `sha256(password)` + padding0 length + padding0;
3. on successful auth, peers enter multiplexed session loop over TLS;
4. every session begins with `cmdSettings`; frame format is `command:uint8 | streamId:uint32be | dataLength:uint16be | data`;
5. `cmdSYN` opens logical stream; v2 server can return `cmdSYNACK` after/around outbound connect result;
6. `cmdPSH` carries stream data, `cmdFIN` closes logical stream without closing reusable TLS session;
7. v2 heartbeat request/response detects stuck sessions; server settings negotiates v2 capability;
8. padding updates can be pushed by server and applied to subsequent sessions;
9. TCP stream's first logical data identifies SOCKS-format destination;
10. UDP uses sing-box `udp-over-tcp` v2 by targeting `sp.v2.udp-over-tcp.arpa`.

Session pooling reuses newest idle TLS-backed sessions and separately times out stale idle sessions. Product routing/TUN is outside this wire layer.
