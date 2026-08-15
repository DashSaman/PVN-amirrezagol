# 048 Snell — server UI and menu maps

Reviewed: 2026-08-15

The official standalone Snell server is configuration/CLI driven; no first-party standalone web panel is documented.

Authoritative server-facing controls evidenced across stable v5 and current v6 beta documentation include:
- listen/interface and port;
- PSK secret;
- DNS behavior;
- v5 `egress-interface` and systemd Socket Activation;
- TCP + UDP relay capability;
- v5 QUIC Proxy Mode (requires UDP port availability);
- v6 `dns-ip-preference` values `default`, `prefer-ipv4`, `prefer-ipv6`, `ipv4-only`, `ipv6-only`;
- v6 multi-address comma-separated `listen`;
- logging/wizard/service lifecycle.

Version boundary is mandatory: v6 controls must not be shown as stable v5 controls; v5 QUIC Proxy Mode must not be exposed for v6 because current vendor docs explicitly state v6 removes it.

No unverified server cipher/auth menu is invented. Proprietary internals not documented by Surge are marked unavailable rather than reverse-engineered into a product UI.
