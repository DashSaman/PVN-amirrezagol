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

## M2 WireGuard evidence

The PVNetwork-owned WireGuard adapter/import boundary is implemented, built and tested. GitHub Actions run `31939414530` also executed a real Linux kernel WireGuard namespace lab: three tunneled pings succeeded with 0% loss, both peer handshake timestamps were non-zero, transfer counters increased, and the marker `PVNetwork WireGuard real-link: PASS` was recorded.

That is scoped interoperability evidence for the isolated Linux CI harness only. No PVNetwork privileged platform runtime, device verification or production readiness is inferred from it.

## M2 OpenVPN active work

A PVNetwork-owned `.ovpn` import/adapter module is now the active independent work item. It protects the complete source and inline key/TLS/certificate material behind `SecretStore`, reports unsupported or external-file directives, and gates capability on a concrete runtime.

No OpenVPN3 dependency is imported yet. The authoritative dossier pin is `OpenVPN/openvpn3@1fd271caefc9a71406afdc2ff2460999dcfdb234`, with the recorded `AGPL-3.0-only OR MPL-2.0` licensing and an explicit MPL/dependency/platform review gate before product import.

## Product evidence state

- RESEARCHED: V1 93/93 and V2 93/93.
- IMPLEMENTED: M0 foundation, M1 desktop shell, WireGuard adapter/import; OpenVPN adapter/import source is active.
- BUILT: M0/M1/WireGuard adapter gates PASS; OpenVPN gate pending.
- TESTED: M0/M1/WireGuard adapter tests PASS; OpenVPN tests pending.
- INTEROPERABILITY VERIFIED: WireGuard Linux kernel isolated CI namespace harness only.
- DEVICE VERIFIED: none.
- PRODUCTION READY: no.

## Non-negotiable boundaries

- M2 still requires actual product runtime/connection evidence and the other first-wave cores.
- Do not reimplement cryptography.
- Do not infer OpenVPN runtime support from parsing or research.
- Dependency/runtime imports remain governed by exact dossier source/license/SBOM gates.
- Do not log or persist reusable secrets in plaintext.
- Store/device claims require their own downstream evidence.
