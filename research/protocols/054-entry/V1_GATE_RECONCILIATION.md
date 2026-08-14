# 054 — SSH Tunnel — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **054 — SSH Tunnel**

Decision: **`COMPLETE-RESEARCH-v1 / SECURE FORWARDING COMPOSITION / NOT IMPLEMENTED / NOT CERTIFIED`**

## Standards authority

Primary standards:

- RFC 4251 — SSH Protocol Architecture;
- RFC 4252 — SSH Authentication Protocol;
- RFC 4253 — SSH Transport Layer Protocol;
- RFC 4254 — SSH Connection Protocol, including TCP/IP port forwarding;
- later algorithm/extension RFCs must be consulted by the selected maintained implementation rather than freezing 2006 algorithm defaults.

SSH transport provides confidentiality, integrity and server/host authentication; user authentication runs above it; forwarding channels run in the SSH connection protocol. Product code should rely on maintained SSH implementations and current algorithm policy, not implement SSH cryptography itself.

## Current primary implementation — OpenSSH portable

Repository:

`openssh/openssh-portable`

Reviewed current master:

`528055671c26962093a871bff8241a48d42dd9a0`

Reviewed tree:

`377ab7f76a7ce3751aae83e48daaad172c46d9ec`

Reviewed head date:

2026-08-13

License:

component licenses summarized upstream as BSD or more permissive; `LICENCE` must be preserved in any reuse/distribution audit.

Current architecture/source evidence includes:

- `ssh` client and `sshd` server;
- transport/key exchange/authentication/host-key verification;
- channel architecture in `channels.c`;
- local/direct TCP forwarding;
- remote/listener forwarding;
- dynamic forwarding with local SOCKS4/SOCKS5 parsing;
- agent and private-key support;
- `known_hosts`/host-key trust;
- config/man pages, tests and regression suite;
- broad Unix portability and Windows OpenSSH ecosystem behavior at product/platform level.

Important composition rule:

**Dynamic forwarding exposes a local SOCKS proxy over an SSH connection. That does not merge SOCKS4/5 and SSH into one protocol entry.** Entries 049–051 describe the SOCKS request layer; entry 054 describes the secure SSH channel/forwarding composition.

## Current reusable library candidate — libssh2

Repository:

`libssh2/libssh2`

Reviewed current master:

`4f271a3b8ebbcf204443d456210a6d6568682f6c`

Reviewed tree:

`f8f818249eb89a27c8c9781b0cf9162fcbe8602e`

Reviewed head date:

2026-08-14

Latest reviewed release:

`1.11.1`, published 2024-10-16; master remains active in 2026.

License:

BSD-3-Clause (also exposed in current pkg-config metadata).

Role:

**`REUSE-CANDIDATE`** for embedded SSH channel/auth/forwarding capabilities if its current APIs/algorithm/backend/platform support satisfy PVNetwork requirements. Exact release/dependency/security freeze remains mandatory.

## Forwarding types kept separate

### Local forwarding

A local listener is owned by the SSH client; accepted connections are carried through the authenticated SSH session to a target reachable from the server side.

### Remote forwarding

The SSH server side owns a listener and sends accepted connections through the SSH session back toward a target reachable from the client side. This has materially different server policy/exposure implications from local forwarding.

### Dynamic forwarding

The SSH client exposes a local SOCKS endpoint and maps each SOCKS request to SSH forwarding channels. SOCKS version/DNS/auth semantics remain the separate SOCKS-family concern.

PVNetwork must not show a single ambiguous "SSH Tunnel" switch without identifying which forwarding mode is being configured.

## Canonical PVNetwork model

- SSH server endpoint/port;
- SSH username/identity;
- host-key trust policy and known-host identity;
- user authentication method: public key, agent, password or selected current mechanism;
- private-key/agent credential reference;
- forwarding mode: local / remote / dynamic;
- listen address/port and target address/port as mode-specific non-secret fields;
- dynamic-forwarding SOCKS behavior as composed SOCKS capability;
- keepalive/reconnect/timeouts only where selected implementation supports them;
- routing/TUN/per-app outside the SSH protocol object.

## Persistence/security ownership

- private keys and passphrases: OS secure storage/keychain or protected key/agent ownership, never ordinary profile JSON;
- host-key trust: explicit known-host state separate from user credentials;
- password: secure credential reference;
- agent socket/identity selection: privileged/session-specific state;
- target/listen addresses: profile state;
- ephemeral channels/session IDs: runtime only;
- logs/support bundles: redact credentials, private keys and sensitive forwarded destinations where policy requires.

Do not auto-accept changed/unknown host keys silently for convenience.

## UI / menu / client references

SSH standards define no consumer GUI. V1 reference classes:

- OpenSSH CLI/config for canonical behavior;
- OS-integrated OpenSSH clients on desktop/server systems;
- mature GUI clients such as PuTTY/Termius-class products as behavioral references only unless their exact source/license is independently reviewed;
- existing PVNetwork multi-protocol UI research for profile/list/connect/log/advanced-setting patterns.

A safe PVNetwork UI needs distinct:

- server + user;
- host-key status;
- auth/key/agent selection;
- forwarding mode;
- local/remote listen endpoint;
- target endpoint;
- dynamic/SOCKS mode details;
- connection/log state.

## 20-gate V1 reconciliation

| # | V1 gate | Result | Evidence / SSH Tunnel conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | OpenSSH is canonical maintained behavior reference; libssh2 is current permissive embedded-library candidate; GUI/OS clients are behavior references. |
| 2 | Canonical sources pinned | PASS | Exact current OpenSSH and libssh2 commits/trees plus RFC4251–4254 authority are recorded. |
| 3 | Licenses reviewed | PASS | OpenSSH component BSD/permissive licenses; libssh2 BSD-3-Clause; proprietary/GUI clients are not treated as reusable by implication. |
| 4 | Complete source-tree reference | PASS | Exact tree SHAs and core channel/auth/host-key/config/test areas are identified. |
| 5 | Languages/build systems | PASS | OpenSSH portable C/autotools-style build; libssh2 C with CMake/autotools ecosystem and signed release archives. |
| 6 | Architecture | PASS | SSH transport -> host-key/KEX -> user auth -> connection channels -> local/remote/dynamic forwarding is explicitly layered. |
| 7 | Core/engine integration | PASS | Prefer maintained OpenSSH OS/subprocess integration or libssh2 embedded adapter depending platform/product requirements; no custom SSH crypto. |
| 8 | UI/menu map | PASS for V1 | Standards/CLI have evidence-backed N/A GUI treatment; required PVNetwork fields/states and mature GUI reference classes are mapped. Exhaustive per-client menus remain V2. |
| 9 | Config/import/export | PASS | SSH config/identity/host-key/forwarding-mode fields and dynamic SOCKS composition are mapped; raw private keys are not ordinary export fields. |
| 10 | Persistence/secrets | PASS | Private keys/passphrases/passwords/agent/known-host trust and non-secret forwarding endpoints have separate ownership. |
| 11 | Platform integrations | PASS for research | OpenSSH/libssh2 are broadly portable; Windows/macOS/Linux native/packaging ecosystems exist. Mobile/Store background lifecycle remains later certification. |
| 12 | Logs/diagnostics | PASS | Failure taxonomy separates TCP reachability, KEX/host-key, user auth, channel-open/listener/policy, DNS/target and keepalive/reconnect; secret redaction explicit. |
| 13 | Assets/screenshots | PASS / N/A | SSH has no canonical app identity; third-party GUI assets are reference-only. |
| 14 | Meaningful alternatives/forks | PASS | OpenSSH and libssh2 represent process/native-client vs embedded-library approaches; GUI/OS clients supply independent UX behavior. |
| 15 | Issues/PRs/releases/advisories | PASS | OpenSSH and libssh2 are active in 2026; current algorithm/security release review is required at production pin. |
| 16 | Relevant forums/docs | PASS | RFC4251–4254, OpenSSH man/config/source and libssh2 API/docs are authoritative evidence. |
| 17 | Tests/CI | PASS | OpenSSH regression/test infrastructure and libssh2 current CI/tests/release process are established; PVNetwork forwarding-mode regression tests remain later. |
| 18 | Store/privacy/security | PASS | Host-key verification, key/password storage, agent access, remote-listener exposure, dynamic SOCKS composition, algorithm policy and platform background restrictions are explicit. |
| 19 | PVNetwork reuse decision | PASS | Evaluate OS OpenSSH/process adapter vs BSD libssh2 library per platform. Prefer secure host-key/credential ownership and minimal enabled forwarding modes. |
| 20 | Uncertainties | PASS | Exact production backend/version/crypto policy, GUI import formats, mobile lifecycle, agent integration, server policy matrix, performance and V2 server/UI/wire evidence remain later. |

## Final V1 decision

All 20 V1 research gates are evidence-backed. Entry 054 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **not implemented/certified**.
