# 078 — uTLS / TLS Fingerprinting — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: advanced TLS ClientHello/fingerprinting compatibility capability, not a standalone VPN protocol.

Primary reference: `refraction-networking/utls`.

Decision: **`LIBRARY OR ENGINE FEATURE / NO DEDICATED VPN ENGINE`**.

PVNetwork must keep fingerprint/profile selection separate from real TLS trust/certificate validation. A browser-like ClientHello does not replace server authentication.

Shared evidence: `research/upstreams/transport-security-family/`.

Later v2 adds exact source/license/version pin, fingerprint model, TLS handshake relationship, supported clients/cores and security/performance evidence.