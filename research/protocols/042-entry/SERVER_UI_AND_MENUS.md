# 042 Hysteria v1 — server UI/menu maps

The canonical v1 server is CLI/config driven and has no first-party web panel; web-menu map is evidence-backed N/A.

Pinned v1 server config includes listener/protocol, ACME or cert/key, upload/download rate, UDP disable, ACL/MMDB, obfs, auth mode/config, ALPN, Prometheus listen, receive windows, max connections/client, PMTUD toggle, resolver preference, SOCKS5 outbound and bind-outbound controls.

Server validation requires listen plus either ACME or cert/key and rejects mutually supplied ACME+cert/key. Default ALPN is `hysteria`.

PVNetwork legacy UI must expose these as versioned v1 controls only and must not show Hysteria2 fields under a v1 profile.
