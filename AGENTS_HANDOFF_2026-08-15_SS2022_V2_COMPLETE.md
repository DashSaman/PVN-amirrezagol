# PVNetwork handoff — Shadowsocks 2022 V2 complete — 2026-08-15

After promotion: V1 **93/93**, V2 **41/93**, first PENDING **042 — Hysteria**. Always re-fetch `main` before writes.

Entry 041 is independently complete from classic Shadowsocks. Canonical SS2022 spec pin is `20b4952...`; primary engine candidate is MIT `shadowsocks-rust@9214fd...`. Required AES methods use fixed-length base64 PSKs and BLAKE3-derived session keys, no EVP_BytesToKey and no forward secrecy. TCP uses timestamp/salt replay protection; UDP is session-ID/packet-ID based with sliding-window replay protection. EIH/iPSK/uPSK semantics are implemented in current rust source. Official Android explicitly enables `aead-cipher-2022`.

Exact next action: entry 042 Hysteria. Apply all 16 V2 gates, identify Hysteria v1 canonical source/release/spec separately from Hysteria2, pin server/client/deployment/config/TLS-QUIC/auth/rate-control/wire/topology evidence, and do not inherit Hysteria2 semantics. Then continue to 043.
