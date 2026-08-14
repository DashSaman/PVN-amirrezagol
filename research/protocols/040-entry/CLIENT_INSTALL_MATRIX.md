# 040 Shadowsocks — client install matrix

Reviewed: 2026-08-15

| Platform | Reference | Pin/license | Result |
|---|---|---|---|
| Windows | `shadowsocks-rust` CLI/service; `shadowsocks/shadowsocks-windows` GUI | rust `9214fd...` MIT; Windows `891d971682eefcaa2e640258d3b352a3ad3b2233` GPL-3.0 | PASS |
| Linux | `sslocal` + related GUI/community wrappers | rust MIT | PASS |
| macOS | `sslocal` via Homebrew/static; client ecosystem wrappers | rust MIT; wrappers independent | PASS |
| Android/ChromeOS | official `shadowsocks/shadowsocks-android@ae28fd91931fe4d2d5aab044de9ceaf9ed07ad56`, GPL-3.0-or-later text | PASS |
| Android TV | same official repo explicitly documents separate Android TV app | PASS as project/install evidence; no device certification |
| iPhone/iPad | multiple Shadowsocks-compatible apps exist but source/license/Store availability varies | BOUNDED; no canonical open-source app chosen here |

Android repo uses shadowsocks-rust core and current 5.3.5-nightly source line; Store/device receipts remain later certification.
