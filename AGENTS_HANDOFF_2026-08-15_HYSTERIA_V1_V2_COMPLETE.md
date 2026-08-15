# PVNetwork handoff — Hysteria v1 V2 complete — 2026-08-15

After promotion: V1 **93/93**, V2 **42/93**, first PENDING **043 — Hysteria2**. Re-fetch live main before any write.

Entry 042 is frozen legacy Hysteria v1 only: `apernet/hysteria v1.3.5` -> `57c5164...`. Source is MIT, but `-tags gpl` executable distribution is GPLv3. The v1 wire path is packet transport -> QUIC/TLS -> protocol-version-3 control stream -> rate/auth hello -> TCP streams/UDP sessions. Current upstream labels 1.x legacy. Hysteria2 semantics were not imported.

Exact next action: entry 043 Hysteria2. Use current upstream source/release/docs/spec; independently map masquerade/auth/obfs/TLS/QUIC/UDP/TCP/bandwidth behavior, installer/service/Docker/client matrices and licenses. Do not treat v1 compatibility as v2 evidence except shared repository history.
