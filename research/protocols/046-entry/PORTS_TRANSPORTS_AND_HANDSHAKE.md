# 046 ShadowTLS — ports, transports and handshake

No universal protocol port is mandated; 443 is a common real-TLS camouflage deployment choice, not a constant.

Transport is TCP. Handshake topology is `client -> ShadowTLS server -> real TLS handshake server`, then authenticated switch to a separate inner data server. v3 strict requires TLS1.3-capable handshake server; non-strict permits TLS1.2 compatibility.

ShadowTLS payload framing imitates TLS ApplicationData records after switch but the implementation intentionally avoids becoming a full TLS implementation. SNI/wildcard-SNI and handshake target selection are implementation/config capabilities. Inner Shadowsocks/VLESS/etc ports/protocol are separate.
