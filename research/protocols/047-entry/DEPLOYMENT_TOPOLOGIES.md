# 047 NaiveProxy — deployment topologies

Evidence-backed topologies:
1. local browser/app -> Naive client -> HTTPS/H2 Naive forwardproxy -> Internet;
2. local client -> QUIC/H3 Naive-compatible server path where selected/current server supports it;
3. frontend/reverse-proxy + Naive Caddy forwardproxy routing based on authorization/proxy behavior;
4. HAProxy/fronting architecture documented upstream;
5. proxy chaining using supported URI schemes;
6. Android host app -> official Naive plugin APK -> server;
7. OpenWrt/desktop local proxy integration;
8. ordinary H2 proxy fallback/interoperability when Naive padding is not negotiated.

No assumption that CDN, generic Caddy, generic CONNECT or generic Chromium automatically equals NaiveProxy.
