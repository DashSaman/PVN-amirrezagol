# L2TPv3/IPsec — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Entry: **010 — L2TPv3/IPsec**

State: `IN-RESEARCH / ADVANCED PROTECTED PSEUDOWIRE COMPOSITION / NOT IMPLEMENTED`

Original v1 state: `V1-HANDOFF-READY / NOT IMPLEMENTED`.

## Composition model

Entry 010 is a layered infrastructure technology:

`Layer-2 service / attachment circuit`

`-> L2TPv3 pseudowire (entry 009)`

`-> IPsec ESP protection (entries 004–007 reusable security model)`

`-> routed/untrusted underlay`

It must preserve separate typed configuration for:

- L2TPv3 tunnel/session/pseudowire IDs, cookies, sequencing and attachment circuit;
- direct-IP protocol 115 vs UDP encapsulation;
- IPsec IKE/authentication/proposals/security policy;
- credentials/trust;
- IPsec selectors binding the intended L2TPv3 flow;
- underlay routing/firewall/MTU;
- Layer-2 bridge/VLAN/service behavior.

Do not duplicate all IPsec fields inside an opaque L2TPv3 profile blob.

## Standards/security authority

Primary references:

- RFC 3931 — L2TPv3, especially Section 4.1.3 L2TP and IPsec;
- RFC 4301 — IPsec architecture;
- RFC 4303 — ESP;
- RFC 7296 and current IKEv2 updates/guidance where IKEv2 is selected;
- RFC 3193 as the L2TP-over-UDP IPsec filtering/composition recommendation referenced by RFC3931;
- current algorithm guidance from the completed IKE/IPsec dossier rather than old DES/3DES/SHA1 examples;
- RFC 9601 for current L2TPv3 ECN propagation behavior.

RFC3931 states:

- L2TPv3 over UDP and directly over IP may be secured with IPsec;
- for direct-IP L2TPv3, selectors are the source/destination tunnel endpoint IPs plus L2TPv3 IP protocol **115**;
- for UDP L2TPv3, the RFC3193 filtering/protection recommendation applies;
- the L2TPv3 data channel has no cryptographic security itself.

## Reused source/implementation pins

### Pseudowire layer

From entry 009:

- Linux kernel L2TP: `torvalds/linux@2f1baf1fc8929e6c48370be543ad028ac7ad4131`
- iproute2: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`
- go-l2tp/ql2tpd: `katalix/go-l2tp@0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- Cisco IOS XE: current proprietary L2TPv3 interop reference by official documentation.

### IPsec layer

From entries 004–007:

- strongSwan 6.0.7 commit `5973ff8e41deef4e015e1138a2de688acedf6f75`
- Libreswan v5.4 commit `5eb03b7772b312e705feab9ad5868678a3c007e6`
- Linux kernel XFRM/native IPsec;
- current IPsec standards/security model in `strongswan-family/reference-v2/`.

## Primary Linux architecture direction

`PVNetwork operator profile`

`-> L2TPv3 Adapter`

`-> Linux kernel L2TP tunnel/session/netdevice`

plus

`PVNetwork IPsec Adapter`

`-> strongSwan/Libreswan`

`-> Linux XFRM ESP SAs/policies selecting the L2TPv3 transport`

The two adapters share a composed deployment transaction/state model but remain separate backend domains.

## Mandatory v2 files

| File | State |
|---|---|
| `SERVER_IMPLEMENTATIONS.md` | started |
| `SERVER_INSTALLERS_AND_PROJECTS.md` | pending |
| `SERVER_INSTALL_MATRIX.md` | pending |
| `SERVER_UI_AND_MENUS.md` | pending |
| `CLIENT_INSTALL_MATRIX.md` | pending |
| `CLIENT_UI_AND_MENUS.md` | pending |
| `CRYPTOGRAPHY.md` | started |
| `DATA_PATH_AND_WIRE_FLOW.md` | started |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | started |
| `DEPLOYMENT_TOPOLOGIES.md` | pending |
| `REFERENCE_INDEX.md` | active |

## Non-negotiable rules

1. Plain entry-009 L2TPv3 and protected entry-010 L2TPv3/IPsec are separate capabilities.
2. IPsec must protect the intended L2TPv3 flow before Layer-2 production traffic is accepted on an untrusted underlay.
3. Direct-IP L2TPv3 selector includes endpoint IPs + protocol 115; do not invent UDP ports for this mode.
4. UDP L2TPv3 protection follows the UDP L2TP/IPsec policy/binding model and must account for selected/dynamic ports.
5. Cookie/control authentication remains distinct from IPsec authentication and ESP traffic keys.
6. IPsec does not solve Layer-2 loops/VLAN/STP/MAC/broadcast risks inside the protected pseudowire.
7. Do not copy old RFC algorithm examples as 2026 security defaults.
8. Do not silently fall back to plain L2TPv3 if IPsec establishment fails.
9. Do not silently change direct-IP to UDP or L2TPv3 to L2TPv2 to “make it work.”
10. Cisco or another peer is only protected-entry-010 certified when both pseudowire and IPsec composition are proven on exact versions.

## Exact next action

Complete the 11 mandatory v2 files, reconcile all 16 reference gates, and preserve external Linux/XFRM/strongSwan/Cisco protected-pseudowire packet/interoperability blockers. Keep entry 010 strict-tracker PENDING until the composed path is actually exercised.
