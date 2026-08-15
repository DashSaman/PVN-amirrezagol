# PVNetwork handoff — ShadowTLS V2 complete — 2026-08-15

After promotion: V1 93/93, V2 **46/93**, first PENDING **047 — NaiveProxy**. Re-fetch live main before writes.

Official ShadowTLS remains MIT `02dd0bc...`; latest tagged release is still v0.2.25 and does not include all 2025 HEAD fixes. V3 is preferred: strict requires TLS1.3 handshake server; non-strict permits TLS1.2. V3 authenticates ClientHello SessionID with password-derived HMAC and uses ServerRandom/stateful HMAC protected TLS-ApplicationData-shaped framing after switching to the inner data server. ShadowTLS remains camouflage only; inner encrypted proxy is separate and mandatory for payload security claims.

Exact next action: 047 NaiveProxy. Pin Chromium/naiveproxy version/source/license relationship, HTTP/2 or HTTP/3 CONNECT semantics, TLS/uTLS/Chromium network-stack identity, server support (Caddy forwardproxy or current alternatives), auth/config/install/UI/client matrices, wire flow and release lifecycle. Then continue to 048 Snell.
