# 046 ShadowTLS — server UI/menu maps

Official runtime is CLI/config driven and has no first-party web panel.

Required server controls: listen endpoint, explicit version, v3 strict mode, password/shared secret, handshake server/address, SNI/tls-name or wildcard-SNI mapping, inner data server/detour, TCP options and logs. Failed authentication/probes must retain real handshake-server forwarding behavior where the selected implementation supports it.

sing-box provides typed inbound fields for Version, Password/users, Handshake target(s), StrictMode and WildcardSNI. Panel/UI remains product-owned; do not conflate handshake server with inner data server or payload encryption.
