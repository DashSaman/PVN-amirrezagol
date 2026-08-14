# 021 — Ivanti Connect Secure

V1 status: `COMPLETE-RESEARCH-v1`.

V2 status: `COMPLETE-REFERENCE-v2` as of 2026-08-14 UTC.

Entry 021 is the **current proprietary Ivanti Connect Secure (ICS) appliance/headend target**. Historic Pulse Connect Secure/Pulse Secure naming is lineage evidence from entry 020, not a second current implementation.

Current server activity baseline: **ICS 25.1.2.1 build 15773**. Current desktop client baseline: **Ivanti Secure Access Client (ISAC) 22.8R7 build 48847**. Current mobile lineage is independently versioned; 22.8.7 iOS/Android/ChromeOS documentation is used as the reviewed mobile baseline.

Important qualification boundary: the published ISAC 22.8R7 desktop server-compatibility table qualifies ICS 25.x only through **25.1.1.1**. It does not prove the newer 25.1.2.1 × 22.8R7 pair. That remains later certification, not a fabricated V2 result.

OpenConnect `--protocol=pulse` is a separate LGPL compatible implementation and remains capability-limited/experimental relative to proprietary ISAC/ICS behavior.
