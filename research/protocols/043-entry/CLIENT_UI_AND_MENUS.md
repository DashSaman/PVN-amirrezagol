# 043 Hysteria2 — client UI/menu maps

Canonical upstream is CLI/YAML-config driven rather than a first-party GUI.

Current client profile surface includes:
1. server endpoint/auth;
2. transport UDP/port hopping;
3. Salamander obfs key;
4. TLS SNI, CA, pin, client cert/key, ECH and explicit insecure override;
5. QUIC receive/idle/keepalive/PMTU/socket controls;
6. bandwidth/congestion settings;
7. local SOCKS5/HTTP/TCP/UDP/TProxy/redirect/TUN modes;
8. TUN include/exclude route controls;
9. DNS/resolver/ACL/routing and logging/lifecycle.

UI must not enable unsupported socket/TUN fields simply because the shared schema contains them. Auth, client private key and obfs secret require protected storage/redaction. `insecure` must remain an explicit high-risk override.
