# PVNetwork handoff — AnyTLS V2 complete — 2026-08-15

After promotion: V1 **93/93**, V2 **45/93**, first PENDING **046 — ShadowTLS**. Re-fetch live main before writes.

AnyTLS current reference pin remains `anytls-go@fd6167a...`, release v0.0.13 with official asset SHA-256 digests. Repository license metadata is still null; source remains REFERENCE-ONLY / DO-NOT-COPY. Protocol v2 is TLS -> SHA256(password)+padding auth -> multiplexed settings/streams, with SYNACK/heartbeat/server-settings recovery, dynamic padding and UDP via sing-box udp-over-tcp v2. TLS config is explicitly a separate layer. Client metadata is encrypted/self-declared and privacy-policy controlled.

Exact next action: **046 ShadowTLS**. Identify versioned ShadowTLS authority and current v3 implementations, pin source/licenses/releases, independently map real-TLS handshake proxying, password/auth/HMAC or record manipulation, TLS1.3/application-data behavior, fallback/handshake server, install/admin/client matrices and v1/v2/v3 differences. Then continue to 047 NaiveProxy.
