# Entry 062 — innernet — Reference index

State: `COMPLETE-REFERENCE-v2` eligible after tracker promotion.

## Pins
- canonical source: `tonarino/innernet`
- license: MIT
- stable release: `v2.0.0` (2026-07-02)
- reviewed main commit: `1ba6154b6ebacd68dfe79c3a4f6273fd3e8dea35` (2026-07-28)

## Dossier
- `V1_RESEARCH.md` — original V1 summary.
- `REFERENCE_V2_AUDIT.md` — exact 16-gate V2 reconciliation and evidence boundaries.

## Architecture summary
innernet is a coordination/policy layer around WireGuard. `innernet-server` manages peer membership, CIDRs, associations and endpoint information; WireGuard provides the encrypted peer data plane. The canonical UX is CLI. No canonical web panel or mobile/Windows GUI is claimed by the reviewed upstream.

## Security/reuse
MIT source is reusable subject to license compliance. Keep WireGuard attribution/trademark boundaries explicit. Upstream warns that the project has not received an independent security audit; that warning remains a certification risk, not a hidden research-completion gate.

## Next
Entry 063 — GRE.
