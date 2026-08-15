# Cisco FlexVPN — Server / Peer Implementations

Reviewed: 2026-08-15

FlexVPN is Cisco's proprietary IKEv2/IPsec framework and configuration paradigm, not a new cryptographic wire protocol.

- Cisco IOS XE 17.x is the canonical server/router implementation; current Cisco documentation in the V1 dossier was updated 2026-04-24 and covers site-to-site, remote access, hub/spoke and partial-mesh roles.
- Generic/public IKEv2/IPsec implementation evidence is inherited from entry 004 and strongSwan pinned at `5011838b32ac88ba9593af4b727932c34b28e127`.
- Cisco implementation source is proprietary/reference-only; source/build internals are evidence-backed N/A.

PVNetwork decision: reuse the generic IKEv2/IPsec model/engine adapters and add only evidenced Cisco FlexVPN extensions. Never create a separate FlexVPN cryptographic core.