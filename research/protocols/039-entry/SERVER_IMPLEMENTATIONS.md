# 039 Trojan — server implementations / ecosystem

Reviewed: 2026-08-15

Primary maintained engine candidate: **XTLS/Xray-core** (MPL-2.0), pinned for this audit to `v26.7.28` -> `5ca6f4b7d4dc20a881d4330e498892697627ec0c`.

Trojan-specific Xray areas: `proxy/trojan/`, `infra/conf/trojan.go`, client/server/protocol/validator/config source. Current Xray can run Trojan inbound and outbound behind the shared Xray routing/transport/security runtime.

Historical canonical protocol/reference implementation: `trojan-gfw/trojan@3e7bb9aecdc694f9bcae8d646fae395f773d60f8`, GPLv3, master last changed 2020-11-08, tree `7d474d9099336c5e86024df3cb72f327f6594c22`, latest observed release v1.16.0 (2020-06-10). Its `docs/protocol.md` is a primary protocol-behavior reference.

Meaningful historical fork: `p4gefau1t/trojan-go@2dc60f52e79ff8b910e78e444f1e80678e936450`, GPLv3, release v0.10.6 / 2021-09-14. Reference/interop only by default.

PVNetwork decision: `Xray-first maintained engine candidate; original Trojan/Trojan-Go reference-only; protocol remains distinct from TLS and outer transport configuration.`
