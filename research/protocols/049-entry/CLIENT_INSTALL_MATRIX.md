# 049 SOCKS4 — Client install matrix

Review: 2026-08-15

Primary reusable client/library candidate remains curl/libcurl pinned in shared evidence at `d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`, tree `39bb285e8839dc38e3406812ecabe29723fe5063`.

| Target | Reference status |
|---|---|
| Windows | curl/libcurl ecosystem supports SOCKS4; final app packaging/service/TUN lifecycle requires later certification |
| macOS | curl/libcurl ecosystem reference; app signing/notarization is later product work |
| Linux | curl/libcurl packages/library integration are mature reference paths |
| Android | libcurl can be embedded/cross-built; exact Android app/ABI packaging is later implementation evidence |
| iOS/iPadOS | library integration is possible; Network Extension/product lifecycle is separate from SOCKS4 |
| Android TV / Google TV | no protocol-specific package; product adapter/UI certification is later |

Current curl documentation distinguishes `socks4://` from `socks4a://`, `socks5://` and `socks5h://`; SOCKS4 resolves the target locally. OpenSSH dynamic forwarding is an additional desktop/CLI reference, not the selected generic client library.