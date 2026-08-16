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

PVNetwork-owned WireGuard import/adapter source is built/tested, and GitHub Actions run `31939414530` performed a real Linux kernel WireGuard namespace handshake plus three tunneled pings at 0% loss. This is scoped CI interoperability evidence, not device or production certification.

### OpenVPN

PVNetwork-owned OpenVPN import/adapter and Linux/JVM unbundled system-process runtime are implemented, built and tested. GitHub Actions run `31942028587` passed three gates: adapter/import contracts, the isolated Linux namespace real-link harness, and the actual `JvmSystemOpenVpnRuntimeFactory` against Ubuntu's system OpenVPN 2.6.19 plus a real TLS peer. The product runtime test reaches `CONNECTED`, creates the requested TUN interface, then cleanly stops to `DISCONNECTED` and verifies TUN removal. This scoped Linux/Ubuntu CI runtime path is therefore **INTEROPERABILITY VERIFIED**. No OpenVPN3/native library is embedded in PVNetwork.

### Xray/VLESS

The PVNetwork-owned VLESS share-link/model/validation/runtime-boundary slice is **IMPLEMENTED + BUILT + TESTED** through GitHub Actions run `31940779078`. It keeps VLESS separate from security/flow/transport, protects identity/original link behind `SecretStore`, and fails closed on unsupported combinations. No Xray-core/libXray runtime has been imported or connected yet.

The active work unit is now **M2-XRAY-STABLE-RELEASE-IMPORT-GATE**: select one exact stable Xray-core release, lock source/assets/checksums and legal/security evidence, document the desktop managed-subprocess lifecycle/update/rollback strategy, then add the concrete runtime path.

## Product evidence state

- RESEARCHED: V1 93/93 and V2 93/93.
- IMPLEMENTED: M0, M1, WireGuard adapter/import, OpenVPN adapter/import + Linux/JVM system runtime, Xray/VLESS adapter/import/model slice.
- BUILT: scoped M0/M1/WireGuard/OpenVPN/Xray gates PASS.
- TESTED: scoped M0/M1/WireGuard/OpenVPN/Xray tests PASS.
- INTEROPERABILITY VERIFIED: WireGuard Linux kernel isolated CI harness; OpenVPN isolated system-package real link; OpenVPN actual PVNetwork `JvmSystemOpenVpnRuntimeFactory` Ubuntu CI path.
- Xray concrete runtime interoperability: not yet verified.
- DEVICE VERIFIED: none.
- PRODUCTION READY: no.

## Non-negotiable boundaries

- Do not infer product runtime support from parser/adapter tests.
- Do not reimplement protocol cryptography.
- Do not import third-party cores before exact source/release/license/SBOM/platform gates.
- Do not log or persist reusable secrets in plaintext.
- M2 cannot close before roadmap real-connection requirements are satisfied for the actual selected Xray product runtime scope.
