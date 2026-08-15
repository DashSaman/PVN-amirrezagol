# 093 — DTLS — Reference Index

Status: **COMPLETE-RESEARCH-v1 + COMPLETE-REFERENCE-v2**
Review date: 2026-08-15

## Authority / pins
- RFC 9147 — DTLS 1.3; RFC 6347 retained only as legacy DTLS 1.2 interoperability reference.
- `pion/dtls` exact selected release `v3.1.4`, MIT.
- Xray `7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0.
- TLS baseline: Entry 077. UDP baseline: Entry 082.

## Dossier
- `V1_GATE_RECONCILIATION.md`
- `REFERENCE_V2_AUDIT.md`

## Reuse decision
Use a maintained DTLS library through the selected parent engine/application boundary. No custom DTLS cryptography and no standalone DTLS VPN product claim.

## Exact continuation
All numbered V2 entries are now research-complete after tracker promotion. Next action is **strict repository validation** with `python scripts/agent_state.py verify --require-complete`; overall completion must not be claimed until that passes.
