# 080 — TLS Fragmentation — Reference Index

Status: **COMPLETE-RESEARCH-v1 + COMPLETE-REFERENCE-v2**
Review date: 2026-08-15

## Canonical implementation

- XTLS/Xray-core
- commit: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- license: MPL-2.0
- config parser: `infra/conf/freedom.go`
- runtime writer: `proxy/freedom/freedom.go`

## Dossier

- `V1_GATE_RECONCILIATION.md` — exact 20-gate V1 reconciliation.
- `REFERENCE_V2_AUDIT.md` — exact 16-gate V2 reconciliation, server/client N/A boundaries, wire effect and lifecycle.

## Core conclusions

- Not an IETF TLS extension and not a standalone VPN/proxy protocol.
- Canonical selected implementation is Xray Freedom outbound fragmentation.
- `packets: "tlshello"` is distinct from generic TCP write segmentation.
- No standalone server, server installer or server panel exists for this capability; those V2 gates are evidence-backed N/A.
- No cryptography of its own; Entry 077 TLS remains the TLS security authority.
- Distinct from Entry 078 uTLS/TLS Fingerprinting.
- Client UI should expose it only as an advanced, version-aware outbound capability with explicit mode/length/interval/maxSplit semantics.
- Effectiveness and exact network packetization are environment/runtime dependent and are not claimed by research completion.

## Sources / evidence anchors

- Pinned Xray source at `7d214f8b094f75322fa3990f8aadad1c912f24f5`.
- `infra/conf/freedom.go` validates and maps `packets`, `length`, `interval`, `maxSplit`.
- `proxy/freedom/freedom.go` implements the TCP-only `FragmentWriter` and TLS-record-aware branch.
- Xray upstream issue/discussion history is treated as operational context only; community parameter recipes are not promoted to universal defaults.

## Reuse decision

**Reuse through the pinned Xray adapter.** Do not build a second fragmentation engine merely to duplicate Xray. Keep capability detection/version validation in the product adapter and preserve configuration losslessly on import/export.

## Exact continuation

Next authoritative V2 entry: **081 — TCP**. Apply all exact 16 `FULL_PROTOCOL_REFERENCE_CONTRACT.md` gates, treating TCP as a foundational transport rather than inventing a standalone PVNetwork VPN server/client product.
