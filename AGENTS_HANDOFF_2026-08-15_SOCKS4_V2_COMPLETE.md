# PVNetwork handoff — SOCKS4 V2 complete — 2026-08-15

After this promotion: V1 **93/93**, V2 **49/93**, first PENDING **050 — SOCKS4a**. Re-fetch live `main` before every write.

Entry 049 now has the full V2 reference file set and an exact 16-gate audit. Current reusable/reference pins remain curl `d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`, OpenSSH portable `528055671c26962093a871bff8241a48d42dd9a0`, and 3proxy `4fb5c957046c6011b5a0b45f48c1b854daf70bca` (2026-08-12). SOCKS4 is explicitly legacy/plaintext, TCP CONNECT/IPv4-oriented, uses local target DNS in ordinary curl SOCKS4 mode, has USERID but no SOCKS5-style auth negotiation, and has no protocol cryptography. Server UI/orchestration and client GUI topics that do not exist canonically are evidence-backed N/A rather than fabricated.

Exact next action: **050 SOCKS4a**. Reuse the same pinned family evidence only where traceable, but make the remote-DNS hostname extension the defining entry-specific distinction. Apply all exact 16 V2 gates, keep SOCKS4a separate from SOCKS4 and SOCKS5, then continue directly to 051 SOCKS5.
