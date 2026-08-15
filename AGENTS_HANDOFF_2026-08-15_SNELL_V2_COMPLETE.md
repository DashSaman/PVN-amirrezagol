# PVNetwork handoff — Snell V2 complete — 2026-08-15

After this promotion: V1 **93/93**, V2 **48/93**, first PENDING **049 — SOCKS4**. Re-fetch live `main` before every write.

Snell remains proprietary and permission-bound. Official stable server baseline is v5.0.1 Linux binary-only; no official source/open-source license is published. Surge explicitly asks others not to reverse-analyze or build compatible clients. Current Surge manual supports v6 on newer iOS/Mac but marks it beta and potentially incompatible; v6 removes v5 QUIC Proxy Mode and adds deployment-specific PSK profile, DNS IP preference and multi-listen controls. Third-party `missuo/opensnell@3100984...` is GPL-3.0 interoperability evidence only and does not grant Surge protocol/vendor rights.

Exact next action: **049 SOCKS4**. Use the historical specification/reference implementations and current maintained libraries/servers where useful. Apply all exact 16 gates, clearly separate SOCKS4 from SOCKS4a and SOCKS5, map CONNECT-only IPv4 semantics, user-id/no-auth security limitations, ports/wire flow/client/server/deployment matrices/source-license pins, then continue to 050 SOCKS4a.
