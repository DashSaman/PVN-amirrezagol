# 046 ShadowTLS — deployment topologies

Evidence-backed topologies:
1. inner encrypted proxy -> ShadowTLS client -> ShadowTLS server -> inner data server;
2. server forwards unauthenticated/probe traffic to real TLS handshake site;
3. v3 strict with TLS1.3 handshake server;
4. non-strict compatibility with TLS1.2 handshake server;
5. multi-SNI/wildcard-SNI handshake target selection where engine supports it;
6. SIP003 composition with Shadowsocks-compatible plugin chains;
7. sing-box composition tests with Shadowsocks/SS2022 as inner payload protocol.

Bare ShadowTLS is not an encrypted VPN/proxy. No UDP/native-TUN semantics are inferred from the wrapper.
