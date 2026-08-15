# 047 NaiveProxy — server UI/menu maps

Canonical server is Caddy/forwardproxy configuration driven; no first-party Naive web panel.

Required server controls:
- HTTPS listener/domain/certificate;
- forwardproxy basic-auth users/credentials;
- Naive padding/camouflage/probe-hiding options provided by the fork;
- frontend/reverse-proxy routing and optional HAProxy/Caddy topology;
- HTTP/2 and, where supported by the selected stack, HTTP/3/QUIC capability;
- ordinary-site behavior for unauthenticated/probe traffic;
- logs/metrics with Authorization/credential redaction.

Caddy admin/API configuration is a management layer, not Naive wire framing. A generic Caddy menu/UI does not prove Naive padding support unless the exact fork/module is loaded.
