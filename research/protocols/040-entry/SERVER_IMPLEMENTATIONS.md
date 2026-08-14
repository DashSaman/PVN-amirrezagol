# 040 Shadowsocks — server implementations/ecosystem

Reviewed: 2026-08-15

Primary dedicated candidate: `shadowsocks/shadowsocks-rust`, MIT, Rust/Cargo.

- current master reviewed: `9214fdaf1f8938a20f6c295b1260c69a625d1f4f` (2026-08-13), workspace version 1.25.0 source line;
- latest stable GitHub release observed: `v1.24.0` (2025-12-10) -> `7ee1aa9223ed8f4d34734aac919036c8ad4502c2`;
- core crate: `crates/shadowsocks`;
- services: `crates/shadowsocks-service`;
- binaries include `sslocal`, `ssserver`, `ssmanager`, `ssservice`, `ssurl`, Windows-service support.

Classic AEAD and Shadowsocks 2022 are source-separated (`aead.rs` vs `aead_2022.rs`, feature `aead-cipher` vs `aead-cipher-2022`). Entry 040 covers classic AEAD only.

Alternatives: Xray-core (MPL-2.0) and sing-box (reviewed in V1 with GPL-3.0-or-later plus additional condition) are multi-protocol comparison/reference candidates. Do not choose them merely for code reuse convenience; dedicated MIT `shadowsocks-rust` is a strong engine candidate.
