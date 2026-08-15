# 054 — SSH Tunnel — Reference Index

Current state: **COMPLETE-REFERENCE-v2** (research/reference only; not implementation/certification)

## Dossier

- `V1_GATE_RECONCILIATION.md` — exact 20-gate V1 audit
- `REFERENCE_V2_AUDIT.md` — exact 16-gate V2 audit

## Standards

- RFC 4251 — SSH Protocol Architecture
- RFC 4252 — SSH Authentication Protocol
- RFC 4253 — SSH Transport Layer Protocol
- RFC 4254 — SSH Connection Protocol / TCP/IP forwarding

## Pinned implementations

### OpenSSH portable

- `openssh/openssh-portable@528055671c26962093a871bff8241a48d42dd9a0`
- reviewed tree `377ab7f76a7ce3751aae83e48daaad172c46d9ec`
- stable tag `V_10_5_P1` / OpenSSH 10.5p1
- release commit `b3f7344209832eea8ece447d871ea748767c444b`
- release tree `13213785969f767d706fe319e3668f3fb2e1c539`
- OpenSSH component BSD/more-permissive notice bundle; preserve exact `LICENCE`

### libssh2

- `libssh2/libssh2@4f271a3b8ebbcf204443d456210a6d6568682f6c`
- tree `f8f818249eb89a27c8c9781b0cf9162fcbe8602e`
- reviewed release `1.11.1`
- BSD-3-Clause

## Platform reference

Microsoft OpenSSH Server for Windows documentation covers Server 2019/2022/2025 and Windows 10/11. Windows Server 2025 includes OpenSSH by default; supported older systems can add the client/server Windows capabilities. `sshd` is service-managed and setup normally creates the TCP/22 firewall rule.

## Critical boundaries

- local, remote and dynamic forwarding are distinct;
- dynamic forwarding composes SOCKS4/SOCKS5 request handling over SSH channels and does not merge the protocols;
- standard OpenSSH port forwarding is TCP-stream forwarding, not generic UDP tunneling;
- host-key trust, user credentials/private keys and agent access are separate security states;
- remote listener exposure and agent forwarding require explicit policy.

## Continuation

Next V2 entry: **055 — Tor SOCKS**.
