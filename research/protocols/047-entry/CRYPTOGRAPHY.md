# 047 NaiveProxy — cryptographic/security design

NaiveProxy intentionally reuses the pinned Chromium network/TLS stack rather than defining custom bulk cryptography. HTTPS paths inherit Chromium TLS certificate validation, current TLS handshakes/ciphers/key-agreement and proxy authentication behavior; QUIC paths inherit the matching Chromium QUIC/TLS/HTTP3 implementation.

This is precisely why Naive and Chromium versions must be pinned together. PVNetwork must not rewrite TLS/H2/H3 fingerprint behavior with an unrelated library and still claim equivalent Naive runtime identity.

Proxy username/password and Authorization material are reusable secrets. TLS verification remains enabled. `ssl-key-log-file` intentionally exports TLS traffic secrets and is developer-only. NetLog may contain sensitive network/request metadata. `--no-post-quantum` changes current Chromium key-agreement behavior and is an explicit compatibility/security override, not a default.
