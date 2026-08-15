# 084 — WebSocket — Reference Index

Status: **COMPLETE-RESEARCH-v1 + COMPLETE-REFERENCE-v2**
Review date: 2026-08-15

## Authority / pins
- RFC 6455; RFC 8441 where explicitly supported.
- Gorilla WebSocket v1.5.3 commit `ce903f6d1d961af3a8602f2842c8b1c3fca58c4d`, tree `0cef094486eaeb81bbc1614c26bbbbd0cb3eb391`, BSD-2-Clause.
- Xray `7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`.

## Dossier
- `V1_GATE_RECONCILIATION.md`
- `REFERENCE_V2_AUDIT.md`

## Reuse decision
Use the parent engine's WebSocket transport; plain WS is not encrypted, WSS security belongs to TLS, and RFC8441 is capability-gated.

## Exact continuation
Next V2 entry: **085 — HTTP/1.1**.
