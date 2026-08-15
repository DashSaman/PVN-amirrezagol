# 086 — HTTP/2 — Reference Index

Status: **COMPLETE-RESEARCH-v1 + COMPLETE-REFERENCE-v2**
Review date: 2026-08-15

## Authority / pins
- RFC 9110; RFC 9113.
- Go `c19862e5f8415b4f24b189d065ed739517c548ba` (go1.26.5), tree `0bb2fb1cc06c334c36a2a92d2f0b07fea7236d74`.
- Xray `7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`.

## Dossier
- `V1_GATE_RECONCILIATION.md`
- `REFERENCE_V2_AUDIT.md`

## Reuse decision
Use maintained runtime/engine HTTP/2; no custom framer/HPACK stack. TLS identity remains separate and h2c is capability-gated.

## Exact continuation
Next V2 entry: **087 — HTTP/3**.
