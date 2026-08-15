# 046 ShadowTLS — major client UI/menu maps

Throne dedicated ShadowTLS editor is the main source-backed GUI reference. A correct product editor must expose:
- endpoint/port;
- explicit v1/v2/v3;
- password secure-store reference;
- v3 strict vs non-strict security policy;
- handshake SNI/name set / wildcard mode where engine supports it;
- inner proxy/detour as a separate typed layer;
- Fast Open/Nagle/uTLS only when selected engine supports them;
- connect/log/diagnostic state separating TLS handshake, ShadowTLS auth/HMAC/switch, fallback and inner-proxy failures.

For new profiles prefer v3+strict when handshake server supports TLS1.3. Non-strict/TLS1.2 must be visibly weaker compatibility mode.
