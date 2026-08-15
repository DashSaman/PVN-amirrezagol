# uTLS / TLS Fingerprinting — COMPLETE-REFERENCE-v2 index

Research date: 2026-08-15

Classification: **client-side TLS ClientHello/fingerprint compatibility capability**, not a standalone VPN protocol and not a replacement for TLS authentication.

## Canonical pins

- canonical uTLS repository: `refraction-networking/utls`
- license: BSD-3-Clause
- Xray-selected uTLS commit/pseudo-version: `aa6edf4b11af82e110eea845bb2983d30138d651` / `v1.8.3-0.20260301010127-aa6edf4b11af`
- current uTLS `master` observed 2026-08-15: `23b1dac19c06c51e278468e29ac329eec605a31f` (2026-08-02)
- selected Xray-core: `7d214f8b094f75322fa3990f8aadad1c912f24f5`, MPL-2.0
- Xray exact `go.mod` confirms the selected uTLS pseudo-version above.

## Current Xray integration

`transport/internet/tls/tls.go` at the selected Xray pin directly imports uTLS, copies actual TLS trust/server-name/ECH/ALPN configuration into the uTLS configuration, and exposes ClientHello profile selection through `GetFingerprint`.

Recommended preset names in current Xray source include `chrome`, `firefox`, `safari`, `ios`, `android`, `edge`, `360`, `qq`, `random`, `randomized`, and `randomizednoalpn`, plus explicit versioned ClientHello IDs.

## Existing evidence reused

- `research/protocols/078-utls/V1_RESEARCH.md`
- `research/protocols/078-entry/V1_GATE_RECONCILIATION.md`
- `research/protocols/077-tls/REFERENCE_V2_AUDIT.md`
- Xray-family shared architecture/client/security evidence.

## Primary references

- `https://github.com/refraction-networking/utls`
- `https://github.com/refraction-networking/utls/commit/aa6edf4b11af82e110eea845bb2983d30138d651`
- `https://github.com/refraction-networking/utls/commit/23b1dac19c06c51e278468e29ac329eec605a31f`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/go.mod`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/transport/internet/tls/tls.go`

Boundary: a requested browser-like fingerprint changes ClientHello presentation. It does not prove browser equivalence, anonymity, censorship resistance or server authenticity. TLS trust/certificate validation remains Entry 077; REALITY and other parent security/application/transport axes remain separate.
