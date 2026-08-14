# Entry 007 — IPsec AH — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Primary evidence: `research/upstreams/strongswan-family/reference-v2/ENTRY_004_007_V2_GATE_RECONCILIATION.md` plus the indexed strongSwan-family V2 dossier.

The shared reconciliation evaluates all exact 16 V2 gates for AH. Install/UI categories are correctly represented through the IPsec backend rather than as a fictitious standalone app. AH's integrity/authentication-only data-plane role, IP protocol 51 behavior, NAT limitations/specialized topology, implementation/backend ownership, cryptographic guidance, source/license/activity, lifecycle, supply-chain and uncertainties are explicit.

Earlier promotion was withheld for an intentional non-NAT interoperability/runtime lab. That is later certification evidence, not an undocumented V2 research gate under the current contract. No runtime proof is fabricated.

Reuse decision: AH is a **specialized optional backend capability**, not a default confidentiality mechanism; expose only where an exact interoperability requirement justifies it.

All 16 applicable V2 gates, including evidence-backed standalone N/A treatment, are satisfied.

**Entry 007 — IPsec AH: `COMPLETE-REFERENCE-v2`.**
