# PVNetwork Agent Handoff — EtherIP V2 Complete (014–015)

Date: 2026-08-14 UTC

Authoritative phase: `COMPLETE-REFERENCE-v2`; V1 remains 93/93.

## Completed in this checkpoint

- 014 — EtherIP: `COMPLETE-REFERENCE-v2`
- 015 — EtherIP/IPsec: `COMPLETE-REFERENCE-v2`

Unlike the preceding classic-tunnel batch, these entries did not already have dedicated V2 dossiers. This checkpoint built entry-specific mandatory reference files by reconciling the completed V1 research with pinned SoftEther source evidence, canonical RFC/OS-native behavior references and the already-completed IPsec family where applicable.

Key boundaries preserved:

- raw EtherIP is IPv4 protocol 97 / RFC 3378 L2 encapsulation and has no integrated encryption;
- consumer app/install/menu concepts are evidence-backed N/A and replaced with infrastructure peer/operator maps rather than invented UI;
- SoftEther's directly reviewed EtherIP/IPsec/IKE path is pinned at `b1f7ef00040786d00bfa06c27fa463d106851e0c`, Apache-2.0 root license with separately tracked submodule/third-party obligations;
- entry 015 is not “EtherIP plus a checkbox”: EtherIP mapping, IKE control plane, ESP data plane, bridge state and host OS IPsec ownership remain separate;
- the reviewed SoftEther IKE path is IKEv1-style; that implementation detail is not generalized to every possible EtherIP/IPsec composition;
- OpenBSD native EtherIP + IPsec is retained as an independent platform/reference topology;
- no runtime, Store, device, packet-capture, performance or interoperability receipt is fabricated or required as a hidden V2 gate.

## Exact continuation state

V2: **15/93**.

Next unfinished V2 entry: **016 — Cisco AnyConnect**.

Exact next action: reconcile Cisco AnyConnect against all exact 16 V2 gates. Preserve Cisco proprietary server/client/source/license boundaries; reuse OpenConnect/ocserv evidence only as a separately identified compatible ecosystem when directly traceable. Fill real gaps, promote only if all applicable gates pass, then continue entry 017 OpenConnect / ocserv-compatible.
