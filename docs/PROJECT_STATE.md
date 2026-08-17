# PVNetwork Project State

Last synchronized: 2026-08-18

## Repository truth

- Repository: `DashSaman/PVN-amirrezagol`, branch `main`.
- Research V1: **93/93 COMPLETE-RESEARCH-v1**.
- Research V2: **93/93 COMPLETE-REFERENCE-v2**.
- Strict research validator: **PASS**.
- Research remains closed unless a real contradiction appears.

## Phase progression

- R5 architecture decision: PASS.
- R6 minimum viable engine set: PASS.
- M0 application foundation: PASS.
- M1 first desktop client shell: PASS.
- M2 core networking wave 1: **PASS**.
- M3 modern proxy wave: **IN PROGRESS**.

## M2 evidence established

### WireGuard

PVNetwork-owned WireGuard import/adapter source is built/tested, and GitHub Actions run `31939414530` performed a real Linux kernel WireGuard namespace handshake plus three tunneled pings at 0% loss. This is scoped CI interoperability evidence, not device or production certification.

### OpenVPN

PVNetwork-owned OpenVPN import/adapter and Linux/JVM unbundled system-process runtime are implemented, built and tested. GitHub Actions run `31942028587` passed the adapter/import contracts, isolated Linux namespace real-link harness, and the actual `JvmSystemOpenVpnRuntimeFactory` against Ubuntu's system OpenVPN 2.6.19 plus a real TLS peer. The product runtime reaches `CONNECTED`, creates the requested TUN interface, cleanly stops to `DISCONNECTED`, and verifies TUN removal. This scoped Linux/Ubuntu CI runtime path is **INTEROPERABILITY VERIFIED**. No OpenVPN3/native library is embedded in PVNetwork.

### Xray/VLESS

The PVNetwork-owned VLESS adapter/import/model and host-supplied POSIX/JVM Xray runtime are now implemented and tested. The runtime probes an external `xray` executable, resolves identity through `SecretStore`, materializes private transient config, validates with `xray run -test -c`, launches without a shell, maps readiness/lifecycle into canonical connection states, and removes transient config on stop/failure.

GitHub Actions run `32072138649` is the authoritative scoped runtime receipt. Both the adapter/share-link suite and **Real Xray VLESS JVM data path** job passed. The real job used an exact-checksum ephemeral `XTLS/Xray-core v26.7.28` CI fixture and proved a bidirectional marker through:

`PVNetwork SOCKS -> PVNetwork VLESS outbound -> real Xray VLESS server -> Freedom -> isolated IPv4 TCP echo origin`.

The verified interoperability scope is VLESS + RAW + `security=none` on Ubuntu 24.04 CI. It does not silently certify TLS/REALITY/Vision/WebSocket/gRPC/XHTTP/mKCP combinations.

The separate bundled/imported production Xray gate remains **BLOCKED** because the reviewed stable release is in an affected advisory range and reviewed patched releases are prereleases. That blocker does not invalidate or block the verified host-supplied runtime model.

Detailed evidence: `docs/M2_XRAY_HOST_RUNTIME_VALIDATION.md` and `docs/M2_XRAY_SLICE.md`.

## M2 milestone decision

Roadmap M2 requires WireGuard, OpenVPN, Xray, and real connection tests rather than parsing-only evidence. The retained WireGuard, OpenVPN and selected host-supplied Xray runtime receipts satisfy that milestone scope. M2 is therefore **PASS** and the implementation state advances to M3.

## Active M3 scope

The active roadmap wave is **M3 — Modern proxy wave**:

- Mihomo and/or selected modern core capabilities;
- VLESS / VMess / Trojan / Shadowsocks;
- REALITY / XTLS / XHTTP where supported;
- Hysteria2 / TUIC / AnyTLS where selected.

The next implementation work must inventory existing product adapters/runtime boundaries, reuse the verified external-core boundary only where technically valid, select exact upstream cores for remaining capabilities, and retain real connection/data-path evidence before interoperability promotion.

## Product evidence state

- RESEARCHED: V1 93/93 and V2 93/93.
- IMPLEMENTED: M0, M1, M2 selected WireGuard/OpenVPN/Xray runtime scopes.
- BUILT/TESTED: scoped M0/M1/M2 CI gates PASS.
- INTEROPERABILITY VERIFIED: WireGuard Linux kernel isolated CI; OpenVPN system-package and actual PVNetwork JVM runtime path; Xray host-supplied JVM VLESS RAW/no-security real data path.
- DEVICE VERIFIED: none.
- PRODUCTION READY: no.

## Non-negotiable boundaries

- Do not infer product runtime support from parser/adapter tests.
- Do not infer all Xray transport/security combinations from one VLESS interoperability receipt.
- Do not reimplement protocol cryptography.
- Do not import or bundle third-party cores before exact source/release/license/SBOM/platform gates.
- Do not log or persist reusable secrets in plaintext.
- Real connection/data-path evidence is required before promoting a selected M3 capability to interoperability-verified status.
