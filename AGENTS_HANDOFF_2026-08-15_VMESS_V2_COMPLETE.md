# PVNetwork handoff — VMess V2 complete — 2026-08-15

Repository: `DashSaman/PVN-amirrezagol`  
Branch: `main`

## Authoritative checkpoint after this promotion

- V1: **93/93 COMPLETE-RESEARCH-v1**
- V2: **38/93 COMPLETE-REFERENCE-v2**
- first unfinished V2 entry: **039 — Trojan**

Always re-fetch `main` and derive the first PENDING row before writing because other agents may advance concurrently.

## Entry 038 result

VMess now has granular contract files for server implementations/installers/install matrix/server UI/client matrix/client UI/cryptography/wire flow/ports/transports/topologies/index and an exact 16-gate audit.

Important pinned facts:

- Xray-core MPL-2.0 `v26.7.28` -> `5ca6f4b7d4dc20a881d4330e498892697627ec0c`.
- advisory GHSA-5wf9-h793-w73c patched from v26.7.11; older v26.3.27 is not selected as a production candidate.
- current Xray config warns VMess lacks Forward Secrecy and recommends VLESS Encryption.
- current VMess AEAD AuthID requires clock agreement within ±120 seconds and has anti-replay handling.
- current body security is AES-128-GCM or ChaCha20-Poly1305/auto in Xray config.
- Xray installer/panel/client evidence is reused from the same pinned family, but VMess crypto/wire semantics were independently reviewed.

## Exact next action

Start **039 Trojan**. Apply all exact 16 V2 gates. Reuse Xray deployment/panel/client evidence only where genuinely shared, but independently verify Trojan's protocol definition, TLS/security boundary, authentication/password handling, wire/data path, transport composition and current Xray source/config at the safe pin. If a stronger canonical Trojan implementation/spec is appropriate, review and pin it instead of assuming Xray alone defines the ecosystem. Promote only if all applicable gates are evidence-backed, then continue to entry 040 Shadowsocks.
