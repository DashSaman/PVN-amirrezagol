# 041 Shadowsocks 2022 — client install matrix

Reviewed: 2026-08-15

| Platform | Reference | Evidence | Result |
|---|---|---|---|
| Linux/macOS/Windows | `shadowsocks-rust` `sslocal` | MIT source pin `9214fd...`, SS2022 feature | PASS |
| Android/ChromeOS | `shadowsocks/shadowsocks-android@ae28fd91931fe4d2d5aab044de9ceaf9ed07ad56` | GPL-3.0-or-later text; Gradle explicitly enables `aead-cipher-2022` in embedded shadowsocks-rust | PASS |
| Android TV | same official project has separate TV app; core feature shared | PASS as software evidence, not device certification |
| iPhone/iPad | multiple third-party clients may support SS2022 | BOUNDED; no canonical open-source Apple client selected without exact current evidence |

Classic Shadowsocks-Windows compatibility does not automatically imply SS2022 support and is not used as proof for this entry.
