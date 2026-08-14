# 077 — TLS

Research state: `COMPLETE-RESEARCH-v1` after numbered 20-gate reconciliation in `research/protocols/077-entry/V1_GATE_RECONCILIATION.md`. PVNetwork implementation/certification state: not implemented / not certified.

Classification: **security protocol layer**, not a standalone VPN protocol.

Current standards baseline at the 2026-08-14 review is **RFC 9846 (TLS 1.3, July 2026)**, which obsoletes RFC 8446 while retaining TLS version number 1.3. RFC 8446 and RFC 5246 remain historical/interoperability references rather than the current standards endpoint.

Primary implementation/reuse reference for the Go/Xray engine family is the maintained Go `crypto/tls` standard library, with current reviewed stable source pinned to Go 1.26.5. Xray-core remains the product integration/configuration reference. PVNetwork should use maintained upstream/native TLS implementations and must not implement independent TLS cryptography.

Detailed current standards/source/license/tree/security/release/config/platform/reuse evidence is in the numbered reconciliation; runtime/server/device/Store interoperability remains later implementation/V2/certification work rather than a hidden research-completion condition.
