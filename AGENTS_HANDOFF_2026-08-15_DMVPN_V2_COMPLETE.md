# PVNetwork Handoff — DMVPN V2 Complete

Date: 2026-08-15

- V1 remains **93/93**.
- Entry **071 — DMVPN** has a complete evidence-backed 16-gate V2 dossier under `research/protocols/071-dmvpn/` and is approved for tracker promotion.
- DMVPN remains modeled as composed infrastructure (`mGRE + NHRP + routing + normally IPsec/IKE`), not a consumer VPN wire protocol. Cisco IOS XE is proprietary behavior reference; FRR/Linux/strongSwan are the public component path with GPL/legal separation preserved.

## Exact continuation

Next required V2 entry: **072 — Cisco FlexVPN**. Evaluate all 16 gates independently using the mature V1 dossier/current Cisco behavior evidence and completed generic IKEv2/IPsec V2 evidence. Treat FlexVPN as a Cisco IKEv2/IPsec framework, preserve proprietary IOS XE source boundaries, document router/client/server role models, route-based/virtual-template behavior, authentication/authorization/security policy and lifecycle without inventing a separate crypto engine.

After 072 continue **073 — GETVPN**.
