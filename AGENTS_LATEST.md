# AGENTS Latest Resume Pointer

This file is an additional deterministic recovery pointer for long-running PVNetwork research.

Always also read `AGENTS.md`, `AGENT_EXECUTION_CONTRACT.md`, `docs/AGENT_RUN_STATE.json`, recent Git history and the actual repository tree.

## Latest completed shared-family handoff

`AGENTS_HANDOFF_2026-08-14_HYSTERIA_V1.md`

## Original-v1 families already handoff-ready in this campaign segment

- OpenConnect / Enterprise compatibility — handoff-ready, not implemented
- Xray / modern proxy — handoff-ready, not implemented
- WireGuard / AmneziaWG — handoff-ready, not implemented
- OpenVPN — handoff-ready, not implemented
- SoftEther / EtherIP family — handoff-ready, not implemented
- Hysteria / Hysteria2 — handoff-ready, not implemented

No family-level research state means product support/certification.

## Current next work unit

**IKE/IPsec original-v1 closure**

Priority entries:

- 004 IKEv2/IPsec
- 005 IKEv1/IPsec
- 006 IPsec ESP
- 007 IPsec AH
- 008 L2TP/IPsec relationship
- related vendor/native platform compatibility where it materially affects engine selection.

## Exact next action

1. inspect existing strongSwan/IPsec research and numbered entries;
2. pin current strongSwan source/release/license;
3. map `charon`, `libstrongswan`, plugins, client/front-end/platform integration;
4. distinguish IKE negotiation/authentication from ESP/AH data plane;
5. compare strongSwan reuse against native Windows/Apple/Android IKE/IPsec capabilities;
6. map dependencies/security/advisories/tests;
7. make per-entry support/reuse decisions;
8. checkpoint and continue the next unfinished original-v1 family;
9. do not start mass `COMPLETE-REFERENCE-v2` until original v1 gates across the 93-entry campaign reach intended state.

## Important later work

After original v1, execute `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` for all applicable entries, including server installers/panels, server/client install matrices, exhaustive UI menus, cryptography, wire/data flow, ports/handshake and deployment topologies.
