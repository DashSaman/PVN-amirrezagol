# 041 Shadowsocks 2022 — server implementations / ecosystem

Reviewed: 2026-08-15

Primary dedicated implementation candidate: `shadowsocks/shadowsocks-rust`, MIT.

- reviewed active source pin: `9214fdaf1f8938a20f6c295b1260c69a625d1f4f` (source line 1.25.0)
- latest stable GitHub release observed: `v1.24.0` -> `7ee1aa9223ed8f4d34734aac919036c8ad4502c2`
- SS2022 feature: `aead-cipher-2022`
- TCP implementation: `crates/shadowsocks/src/relay/tcprelay/aead_2022.rs`
- UDP implementation: `crates/shadowsocks/src/relay/udprelay/aead_2022.rs`
- config/EIH/user-key handling: `crates/shadowsocks/src/config.rs`

Canonical specification source: `Shadowsocks-NET/shadowsocks-specs@20b4952e8a54e696ebcabc5f91b5dad7f322f2da`, especially the 2022 Edition and Extensible Identity Header documents. The implementation-list document in that repository explicitly warns that much of its inventory is outdated, so it is discovery evidence only.

Classic Shadowsocks AEAD (entry 040) and SS2022 are distinct protocol generations. No automatic profile/key migration is valid.
