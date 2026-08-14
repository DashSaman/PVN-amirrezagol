# 041 — Shadowsocks 2022 — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **041 — Shadowsocks 2022**

Decision: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT CERTIFIED`**

Shadowsocks 2022 / AEAD-2022 is a distinct protocol/cipher family from entry 040 classic/current Shadowsocks AEAD. It must not inherit classic password/key-derivation semantics or be silently collapsed into entry 040.

## Authoritative specification baseline

- `Shadowsocks-NET/shadowsocks-specs@20b4952e8a54e696ebcabc5f91b5dad7f322f2da`
- reviewed tree: `b98edead15d26bc345f20cf5f776dfdbcf893fc0`
- main spec: `2022-1-shadowsocks-2022-edition.md`
- EIH spec: `2022-2-shadowsocks-2022-extensible-identity-headers.md`
- implementation/reference inventory: `2022-4-shadowsocks-implementations.md`
- proposal/discussion: `shadowsocks/shadowsocks-org#196` (`SIP022`)

The spec repo's latest reviewed commit itself says parts of its implementation-list document are outdated, so that list is discovery evidence rather than current implementation certification.

## Dedicated implementation baseline

- `shadowsocks/shadowsocks-rust@9214fdaf1f8938a20f6c295b1260c69a625d1f4f`
- package version at pin: `1.25.0`
- license: MIT
- Rust/Cargo workspace

Exact source separation:

- TCP classic AEAD: `crates/shadowsocks/src/relay/tcprelay/aead.rs`
- TCP AEAD-2022: `crates/shadowsocks/src/relay/tcprelay/aead_2022.rs`
- UDP classic AEAD: `crates/shadowsocks/src/relay/udprelay/aead.rs`
- UDP AEAD-2022: `crates/shadowsocks/src/relay/udprelay/aead_2022.rs`
- Cargo features separately expose `aead-cipher` and `aead-cipher-2022`.
- `crates/shadowsocks/src/config.rs` has AEAD-2022-specific base64 handling plus explicit `ServerUser` key/identity-hash structures.

## SS2022 security/key semantics

The official spec defines a secure TCP/UDP L4 proxy/tunnel using AEAD with pre-shared symmetric keys. Compared with earlier Shadowsocks editions:

- operator supplies a cryptographically secure **fixed-length PSK** directly;
- implementations **MUST NOT** use old `EVP_BytesToKey` or generate the protocol key from arbitrary passwords;
- PSKs are represented in base64;
- session subkeys use BLAKE3 derivation;
- required methods are `2022-blake3-aes-128-gcm` and `2022-blake3-aes-256-gcm`;
- full replay protection is mandatory;
- UDP is session-based with session ID + packet ID semantics;
- SS2022 does **not** provide forward secrecy.

| Required method | PSK | Salt |
|---|---:|---:|
| `2022-blake3-aes-128-gcm` | 16 B | 16 B |
| `2022-blake3-aes-256-gcm` | 32 B | 32 B |

Optional ChaCha-family methods are separate capabilities and require exact implementation evidence.

## Replay / wire behavior

TCP uses timestamps, salts, request/response association, replay tracking and specified initial read/write behavior to avoid simple parser-length probing. UDP is redesigned around session IDs, packet IDs, separate-header protection and AEAD-encrypted bodies. Parser acceptance alone does not prove replay/interoperability correctness.

## Extensible Identity Headers

The EIH spec defines optional chained identity layers:

- `iPSK`: identity/relay key;
- `uPSK`: user key;
- TCP identity headers sit after salt and before AEAD chunks;
- UDP request identity headers sit after the separate header and before AEAD ciphertext;
- relays can use identity PSKs for target selection;
- single-port multi-user servers can identify user PSKs through identity hashes.

The reviewed shadowsocks-rust config source has explicit user key and identity-hash structures, so EIH/multi-user is a real implementation capability to certify, not only a paper concept.

## Product classification

**`MODERN SHADOWSOCKS TARGET / DEDICATED MIT CORE STRONG CANDIDATE / EXACT METHOD+EIH CERTIFICATION REQUIRED`**

No automatic entry-040 -> 041 migration is allowed because keying, replay, framing and UDP semantics materially differ.

## 20-gate reconciliation

| # | Gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients identified/justified | PASS | Existing multi-protocol client dossiers cover major Shadowsocks clients; shadowsocks-rust supplies dedicated client/server/service reference. |
| 2 | Canonical sources pinned | PASS | Exact spec commit/tree and exact shadowsocks-rust commit pinned. |
| 3 | Licenses reviewed | PASS | shadowsocks-rust MIT; alternate core/GUI licenses remain separate. |
| 4 | Source-tree manifest captured | PASS | Exact recursive implementation tree and exact spec tree reviewed; dedicated AEAD2022 paths recorded. |
| 5 | Languages/build mapped | PASS | Rust/Cargo primary implementation; alternate multi-core/client stacks documented elsewhere. |
| 6 | Architecture mapped | PASS | client/server/optional relay, TCP and session-based UDP, protocol/service/config/network layers separated. |
| 7 | Core integration mapped | PASS | dedicated binaries/crates provide direct engine path; PVNetwork retains engine-neutral adapter/profile boundary. |
| 8 | UI/menu map | PASS for V1 | Existing client UI research covers Shadowsocks import/profile/routing/settings/log flows; SS2022 UI must expose method/fixed-key semantics. Exhaustive fields/screenshots remain V2. |
| 9 | Config/import/export | PASS | Exact method, base64 PSK, endpoint, TCP/UDP, optional EIH/user identity and original source must be preserved; password-style classic conversion is invalid. |
| 10 | Persistence/secrets | PASS | PSKs/iPSKs/uPSKs are reusable secrets requiring secure storage and log/export redaction. |
| 11 | Platform integrations | PASS for research | shadowsocks-rust provides desktop/server/local networking modes; mobile integration is represented by client references and later exact-device certification. |
| 12 | Logs/diagnostics | PASS | Upstream logging/service diagnostics known; all key/raw-config material requires redaction. |
| 13 | Assets/screenshots refs | PASS for V1 | GUI assets are reference-only; dedicated core is non-GUI; exhaustive screenshot audit remains V2. |
| 14 | Forks/alternatives | PASS | dedicated shadowsocks-rust is distinguished from multi-core Xray/sing-box and community implementations. |
| 15 | Issues/releases/advisories | PASS | SIP022, current spec revision, implementation-list staleness warning, shadowsocks-rust source/activity/CI reviewed; selected-release advisory refresh remains source-freeze work. |
| 16 | Docs/forums | PASS | official SS2022/EIH specs, SIP discussion and implementation docs reviewed. |
| 17 | Tests/CI | PASS | shadowsocks-rust build/test, MSRV, release, clippy, cargo-deny and TCP/UDP test suites documented. |
| 18 | Store/privacy/security | PASS | fixed identity/user keys require protected storage; no-forward-secrecy semantics must be accurate; platform/Store and alternate-core licenses are later release gates. |
| 19 | PVNetwork reuse decision | PASS | benchmark maintained/licensable dedicated SS2022 core against any multi-core candidate; do not choose a core solely because it is already used elsewhere. |
| 20 | Uncertainties explicit | PASS | exact production engine/release, optional methods, EIH matrix, interoperability, platform lifecycle, performance and V2 wire/install/UI/topology evidence remain explicit. |

## Canonical PVNetwork rules

1. `Shadowsocks` and `Shadowsocks2022` are distinct protocol types.
2. SS2022 stores exact method + fixed-length base64 PSK semantics, not a generic password.
3. EIH identity/user keys are typed and capability-gated.
4. TCP and UDP are separate certification dimensions.
5. Preserve original imported source separately from normalized profile.
6. Never silently derive/truncate/pad a wrong-length PSK.
7. Never silently migrate classic Shadowsocks to SS2022.
8. No home-grown cryptography.
9. Do not claim forward secrecy.
10. Parser acceptance is not replay/interoperability certification.

## Future acceptance work — not V1 blockers

Before support claim: select exact engine/release; freeze SBOM/licenses/advisories; certify required AES and any optional methods separately; negative-test key size/base64 handling; test TCP replay/timestamp/salt behavior; test UDP session/packet replay/reordering; test EIH/multi-user only if advertised; build client/server version matrix; test import/export/redaction, routing/DNS/TUN/platform lifecycle and performance; then complete COMPLETE-REFERENCE-v2 server/install/client-UI/crypto/data-flow/ports/topology work.

## Final V1 decision

All 20 original research gates are evidence-backed, with SS2022 key, replay, UDP and EIH semantics explicitly separated from classic Shadowsocks. Entry 041 is therefore **`COMPLETE-RESEARCH-v1`**, while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
