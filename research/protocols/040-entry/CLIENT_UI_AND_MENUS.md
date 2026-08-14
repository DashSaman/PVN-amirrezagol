# 040 Shadowsocks — major client UI/menu maps

Reviewed: 2026-08-15

## Official Android/TV project

Pinned `shadowsocks/shadowsocks-android@ae28fd91931fe4d2d5aab044de9ceaf9ed07ad56`, GPL-3.0-or-later license text. Functional surfaces include profile list/edit, server/port/password/method, plugin options, import/QR/config delivery, per-app/VPN routing, service connect/disconnect, traffic/status, settings and diagnostics. Android and TV have distinct application targets.

## Shadowsocks-Windows

Pinned `shadowsocks/shadowsocks-windows@891d971682eefcaa2e640258d3b352a3ad3b2233`, GPL-3.0. Tray/menu-oriented GUI manages servers, selected server, system proxy/PAC/global behavior, import/QR/subscription-style configuration where supported, logs/settings/update. Its last branch push is 2025-01-01; it is a UX/compatibility reference, not automatically the preferred embedded engine.

## Dedicated rust client

`sslocal` is CLI/service-first; profile/config UI remains product-owned. PVNetwork must preserve exact method, password/key, plugin fields, source format, and TCP/UDP capabilities separately. Unsafe/deprecated stream ciphers must not be exposed as default choices.
