# Entry 006 — IPsec ESP — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Primary evidence: `research/upstreams/strongswan-family/reference-v2/ENTRY_004_007_V2_GATE_RECONCILIATION.md` plus the indexed strongSwan-family V2 dossier.

The shared reconciliation evaluates all exact 16 V2 gates for ESP. Where install/UI concepts do not exist as a standalone ESP application, the dossier uses evidence-backed backend/data-SA treatment instead of inventing a client or panel. ESP cryptography, raw IP protocol 50 vs UDP-encapsulated NAT-T behavior, kernel/XFRM data path, topologies, implementation families, source/license/activity, supply-chain and lifecycle are mapped.

Earlier promotion was withheld solely for live backend/packet-capture/interoperability receipts. Those remain future certification evidence and are not hidden gates in the current `FULL_PROTOCOL_REFERENCE_CONTRACT.md`.

Reuse decision: ESP is an IPsec data-plane capability owned by selected kernel/OS/IPsec backends, not a separately embedded consumer-client engine.

All 16 applicable V2 gates, including evidence-backed standalone N/A treatment, are satisfied.

**Entry 006 — IPsec ESP: `COMPLETE-REFERENCE-v2`.**
