# PVNetwork Project State

Last updated: 2026-08-14

## Repository

- Repository: `DashSaman/PVN-amirrezagol`
- Default branch: `main`
- Product: **PVNetwork**
- Current phase: **Research / requirements / architecture**
- Production application code: **Not yet started**

## Current objective

Build a complete, evidence-based technical foundation before implementation begins.

The immediate goal is to research and document:

1. the complete protocol/technology scope;
2. best available open-source client/core candidates for each family;
3. licensing and commercial redistribution constraints;
4. platform support constraints;
5. store publication constraints;
6. competitor lessons and known failure modes;
7. the minimum set of engines needed for maximum coverage;
8. the eventual cross-platform architecture.

## Completed so far

- Created consolidated master context: `PVNETWORK_MASTER_CONTEXT.md`
- Created mandatory AI entry point and reusable prompt: `AI_START_HERE.md`
- Created persistent AI/developer rules: `AGENTS.md`
- Defined initial product goal: universal multi-core VPN/proxy client
- Defined long-term target platforms
- Defined Persian/English and RTL requirements
- Defined store-readiness as a core engineering requirement
- Built an initial 93-entry technology scope
- Identified initial high-value engine candidates including OpenVPN 3, WireGuard, AmneziaWG, Xray-core, Mihomo, OpenConnect, strongSwan/native IPsec, SoftEther, and Hysteria2
- Identified major reference clients/cores for competitor research

## Not completed yet

- No application implementation exists yet.
- No protocol has been verified as implemented by PVNetwork.
- No production UI exists.
- No PVNetwork logo asset has yet been verified in the repository.
- No builds exist.
- No automated tests exist.
- No E2E connection tests exist.
- No real-device compatibility tests exist.
- No store submission package exists.
- License review is preliminary, not final legal sign-off.

## Current research task

Build and refine the research matrices before implementation:

- `docs/PROTOCOL_MATRIX.md`
- engine/core selection
- licensing implications
- platform/store risks
- competitor failure lessons

## Current candidate core strategy

Research baseline only — not yet approved for implementation:

1. OpenVPN 3
2. Official WireGuard stack
3. AmneziaWG
4. Xray-core
5. Mihomo
6. OpenConnect
7. strongSwan and/or native platform IPsec APIs
8. SoftEther
9. Hysteria2 official implementation where useful

The goal is to minimize engine count while maximizing real protocol coverage and maintaining reasonable licensing/store compatibility.

## Current blockers / open questions

- Exact UI framework and shared-code architecture are not yet selected.
- Exact license architecture for GPL/LGPL/AGPL dependencies must be researched before integration decisions.
- Store-specific feasibility of each core must be validated.
- Proprietary enterprise VPN families may lack reusable open-source engines.
- Exact minimum OS versions have not yet been approved.
- Official PVNetwork logo files still need to be added/identified and audited for required icon variants.

## Evidence status

Current documentation mostly represents **research and candidate selection**, not implementation proof.

## Next exact actions

1. Maintain the full 93-entry `docs/PROTOCOL_MATRIX.md`.
2. Research each protocol family against best open-source clients/cores.
3. Record license and platform constraints.
4. Create a competitor lessons/risk database.
5. Select an initial minimum viable core set only after evidence is sufficient.
6. Then design a concrete implementation milestone plan.

## Handoff instruction

Any future AI must read `AI_START_HERE.md` first and then follow the mandatory reading order there.
