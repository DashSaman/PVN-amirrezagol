# PVNetwork handoff — NaiveProxy V2 complete — 2026-08-15

After promotion: V1 93/93, V2 **47/93**, first PENDING **048 — Snell**. Re-fetch live main before writes.

Naive stable remains v150.0.7871.63-1 / Chromium 150.0.7871.63 / commit 3ba967e..., with SHA-256 release assets. Server fork `klzgrad/forwardproxy` naive branch is d62c80d... Apache-2.0. Provenance must use stable tags because Naive master is rebased. Chromium network/TLS/H2/H3 behavior, padding negotiation/first-eight operations and fast-open are part of runtime identity; full Chromium third-party notices/SBOM remain required.

Exact next action: 048 Snell. Preserve proprietary/source-unavailable boundaries. Pin current official Snell protocol/version/client/server packages and official documentation, supported ciphers/auth/UDP behavior/install matrices and licensing/EULA evidence without fabricating source. Treat open-source parsers/clients only as interoperability evidence. Then continue to SOCKS4.
