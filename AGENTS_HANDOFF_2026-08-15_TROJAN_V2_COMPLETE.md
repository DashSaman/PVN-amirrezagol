# PVNetwork handoff — Trojan V2 complete — 2026-08-15

After this promotion: V1 **93/93**, V2 **39/93**, first PENDING **040 — Shadowsocks**.

Re-fetch live `main` before writing because concurrent agents may advance it.

Entry 039 now has all granular V2 contract files. Maintained engine reference is Xray-core MPL-2.0 `v26.7.28` / `5ca6f4b...`; original Trojan and Trojan-Go are old GPLv3 reference implementations. Canonical Trojan uses genuine TLS, a 56-byte `hex(SHA224(password))` token and SOCKS5-like TCP/UDP framing. TLS/certificate policy remains separate and the password still requires secure storage. Current Xray warns Trojan without Flow is deprecated toward VLESS with Flow & Seed; no automatic migration is allowed.

Exact next action: entry 040 Shadowsocks. Apply all 16 V2 gates. Prefer canonical Shadowsocks specifications/SIP documents and actively maintained implementations; distinguish classic Shadowsocks from entry 041 Shadowsocks 2022. Pin cipher/support/source/license independently and reuse client/deployment evidence only when traceable. Then continue to 041.
