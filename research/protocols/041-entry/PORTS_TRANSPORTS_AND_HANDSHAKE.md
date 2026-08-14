# 041 Shadowsocks 2022 — ports, transports and handshake

SS2022 has no universal fixed server port; port is profile configuration. It supports TCP and UDP proxying with materially different framing/replay state.

There is no public-key/TLS handshake in the base protocol. Client/server pre-share exact method and PSK. TCP session establishment begins with random salt + authenticated timestamp/header, optional EIH, then encrypted data chunks. UDP establishes logical relay sessions using random session IDs and packet IDs.

TCP messages over 30 seconds from system time are replay; salts are tracked 60 seconds. UDP message timestamps over 30 seconds are replay; each relay session uses sliding-window packet-ID filtering and must persist at least 60 seconds.

Plugins/encapsulation are independent transport layers with separate protocol/source/license/security review. Port examples such as 8388 are examples only, never constants.
