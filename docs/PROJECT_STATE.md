# PVNetwork Project State

Last synchronized: 2026-08-16

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
- M2 core networking wave 1: **IN PROGRESS**.

## M2 evidence now established

### WireGuard

PVNetwork-owned WireGuard import/adapter source is built/tested, and GitHub Actions run `31939414530` performed a real Linux kernel WireGuard namespace handshake plus three tunneled pings at 0% loss. This is narrow CI-harness interoperability evidence, not production runtime or device certification.

### OpenVPN

PVNetwork-owned OpenVPN `.ovpn` import/adapter source is built/tested. GitHub Actions run `31939586890` passed its KMP/JVM test gate. No OpenVPN3 dependency or runtime has been imported; the exact MPL/dependency/platform gate remains open.

### Xray/VLESS active work

A PVNetwork-owned VLESS share-link/canonical-combination/runtime-boundary module is the active work item. It keeps VLESS separate from security/flow/transport, protects the identity and original link behind `SecretStore`, and does not import Xray-core/libXray before a production stable-release/SBOM/MPL/platform decision.

## Product evidence state

- RESEARCHED: V1 93/93 and V2 93/93.
- IMPLEMENTED: M0, M1, WireGuard adapter/import, OpenVPN adapter/import; Xray/VLESS adapter source active.
- BUILT: M0/M1/WireGuard/OpenVPN scoped gates PASS; Xray gate pending.
- TESTED: M0/M1/WireGuard/OpenVPN scoped tests PASS; Xray pending.
- INTEROPERABILITY VERIFIED: WireGuard Linux kernel isolated CI namespace harness only.
- DEVICE VERIFIED: none.
- PRODUCTION READY: no.

## Non-negotiable boundaries

- Do not infer product runtime support from parser/adapter tests.
- Do not reimplement protocol cryptography.
- Do not import third-party cores before exact source/release/license/SBOM/platform gates.
- Do not log or persist reusable secrets in plaintext.
- M2 cannot close before roadmap real-connection requirements are satisfied for the actual product runtime scope.
