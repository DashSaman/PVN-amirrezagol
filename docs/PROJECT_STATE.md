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
- M1 first desktop client shell: **PASS**.
- M2 core networking wave 1: **IN PROGRESS**.

## M1 close evidence

`apps/desktop` is implemented, built, unit-tested, packaged with `createDistributable`, and launch-smoke validated under Xvfb. GitHub Actions run `31939070255` executed the actual Compose desktop run task and recorded `PVNetwork desktop launch smoke: PASS`. The hosted runner also logged a Skiko GL-context fallback, so this is explicitly not treated as real-device/GPU verification.

## M2 current work

The first WireGuard adapter slice is being added under `engines/wireguard-adapter`. It reuses the completed official WireGuard dossier and keeps upstream/native runtime integration behind a product-owned boundary. No engine binary/source is imported yet and no cryptography is reimplemented.

The source moves imported private/pre-shared keys into `SecretStore`, keeps only opaque references in `PVProfile`, validates the first wg-quick configuration surface, and refuses to advertise the WireGuard capability while the concrete runtime is unavailable.

## Product evidence state

- RESEARCHED: V1 93/93 and V2 93/93.
- IMPLEMENTED: M0 foundation + M1 desktop shell; M2 WireGuard product-owned adapter/config source is the active slice.
- BUILT: M0 and M1 CI build gates PASS; M2 WireGuard adapter CI pending.
- TESTED: M0 and M1 scoped tests PASS; M2 WireGuard adapter tests pending.
- INTEROPERABILITY VERIFIED: none.
- DEVICE VERIFIED: none.
- PRODUCTION READY: no.

## Non-negotiable boundaries

- Do not infer protocol runtime implementation from research or config parsing.
- M2 requires real connection tests before phase close.
- Do not reimplement WireGuard cryptography.
- Dependency/runtime imports remain governed by `docs/ENGINE_SET_R6.md` and the exact WireGuard dossier pins/licenses.
- Do not log or persist reusable secrets in plaintext.
- Store/device claims require their own downstream evidence.
