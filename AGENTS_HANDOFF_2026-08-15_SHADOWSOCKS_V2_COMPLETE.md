# PVNetwork handoff — classic Shadowsocks V2 complete — 2026-08-15

After promotion: V1 **93/93**, V2 **40/93**, first PENDING **041 — Shadowsocks 2022**. Re-fetch live main before writes.

Entry 040 uses dedicated MIT `shadowsocks-rust` as primary candidate. Current master pin `9214fdaf...` is active 1.25.0 source; latest stable GitHub release is `v1.24.0` -> `7ee1aa9...`. Canonical classic AEAD methods are ChaCha20-Poly1305, AES-128-GCM and AES-256-GCM with the standard salt/HKDF/chunk framing. Deprecated stream ciphers stay disabled/legacy-only. Android and Windows GUI sources are GPL reference projects. Plugins are independent supply-chain components.

Exact next action: entry 041 Shadowsocks 2022. Reuse deployment/client evidence where the same projects genuinely support SS2022, but independently audit SIP022/current AEAD-2022 source, PSK format/key size, identity headers, TCP/UDP replay/time/session semantics, nonce/key derivation and supported method matrix. Do not promote classic AEAD evidence as SS2022 crypto evidence.
